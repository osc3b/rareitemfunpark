package com.lonelymc.ri4.bukkit.rareitems;

import com.lonelymc.ri4.api.*;
import com.lonelymc.ri4.bukkit.RareItems4Plugin;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RareItemsYMLPersistence implements IRareItemsPersistence {
    private IRareItems4API api;

    private final String essencesIndexLocation = "essencesIndexLocation";
    private final String rareItemsIndexLocation = "rareItemsIndexLocation";

    private final String saveFileName = "save.yml";
    private final File saveDir;
    private boolean saveIsDirty = false;
    private YamlConfiguration saveYml;

    private final Map<Integer, IEssence> cachedEssences;
    private final Map<Integer, IRareItem> cachedRareItems;

    public RareItemsYMLPersistence(RareItems4Plugin plugin, File saveDir) {
        this.saveDir = saveDir;

        this.cachedEssences = new HashMap<>();
        this.cachedRareItems = new HashMap<>();

        load();
    }

    @Override
    public void setAPI(IRareItems4API api){
        this.api = api;
    }

    @Override
    public IEssence createEmptyEssence(ItemPropertyRarity rarity) {
        int essenceId = this.saveYml.getInt(this.essencesIndexLocation);

        this.saveYml.set(this.essencesIndexLocation, essenceId + 1);

        IEssence essence = new Essence(essenceId, EssenceStatus.EMPTY, rarity);

        this.saveEssence(essence);

        return essence;
    }

    @Override
    public IEssence createFilledEssence(IRareItemProperty property) {
        int essenceId = this.saveYml.getInt(this.rareItemsIndexLocation);

        this.saveYml.set(this.rareItemsIndexLocation, essenceId + 1);

        IEssence essence = new Essence(essenceId, EssenceStatus.FILLED, property);

        this.saveEssence(essence);

        return essence;
    }

    @Override
    public IEssence getEssence(int essenceId) {
        IEssence essence = this.cachedEssences.get(essenceId);

        if (essence == null) {
            ConfigurationSection essencesSection = this.saveYml.getConfigurationSection("essences." + essenceId);

            if (essencesSection != null) {
                String propertyName = essencesSection.getString("property", null);
                ItemPropertyRarity rarity = ItemPropertyRarity.valueOf(essencesSection.getString("rarity", "COMMON"));

                EssenceStatus status = EssenceStatus.valueOf(essencesSection.getString("status"));

                if (propertyName == null) {
                    essence = new Essence(essenceId, status, rarity);
                } else {
                    IRareItemProperty property = api.getItemProperty(propertyName);

                    // The reason this is done is to allow addons to be removed without causing conflicts
                    if (property == null) {
                        property = new _Dummy(propertyName);
                    }

                    essence = new Essence(essenceId, status, property);
                }

                this.cachedEssences.put(essenceId, essence);
            }
        }

        return essence;
    }

    @Override
    public void saveEssence(IEssence essence) {
        this.saveIsDirty = true;

        int id = essence.getId();

        this.saveYml.set("essences."+id+".status",essence.getStatus().name());
        this.saveYml.set("essences."+id+".rarity",essence.getRarity().name());

        if(essence.getProperty() != null){
            this.saveYml.set("essences."+id+".property",essence.getProperty().getName());
        }
        else{
            this.saveYml.set("essences."+id+".property",null);
        }

        this.cachedEssences.put(essence.getId(),essence);
    }

    @Override
    public IRareItem createRareItem(Map<IRareItemProperty, Integer> riProperties) {
        int rareItemId = this.saveYml.getInt(rareItemsIndexLocation);

        this.saveYml.set(rareItemsIndexLocation, rareItemId + 1);

        IRareItem ri = new RareItem(rareItemId, riProperties, RareItemStatus.ACTIVE);

        this.saveRareItem(ri);

        this.saveIsDirty = true;

        return ri;
    }

    @Override
    public IRareItem getRareItem(int rareItemId) {
        IRareItem ri = this.cachedRareItems.get(rareItemId);

        if (ri == null) {
            ConfigurationSection riSection = this.saveYml.getConfigurationSection("rareitems." + rareItemId);

            if (riSection != null) {
                Map<IRareItemProperty, Integer> properties = new HashMap<>();

                ConfigurationSection propertiesSection = riSection.getConfigurationSection("properties");

                for (String sPropertyName : propertiesSection.getKeys(false)) {
                    int propertyLevel = propertiesSection.getInt(sPropertyName,1);

                    IRareItemProperty property = api.getItemProperty(sPropertyName);

                    if(property == null){
                        property = new _Dummy(sPropertyName);
                    }

                    properties.put(property, propertyLevel);
                }

                RareItemStatus status = RareItemStatus.valueOf(riSection.getString("status"));

                ri = new RareItem(rareItemId, properties, status);

                this.cachedRareItems.put(rareItemId, ri);
            }
        }

        return ri;
    }

    @Override
    public void saveRareItem(IRareItem ri) {
        this.saveIsDirty = true;

        int id = ri.getId();

        this.saveYml.set("rareitems."+id+".status",ri.getStatus().name());

        for(Map.Entry<IRareItemProperty,Integer> rip : ri.getProperties().entrySet()){
            this.saveYml.set("rareitems."+id+".properties."+rip.getKey().getName(),rip.getValue());
        }

        this.cachedRareItems.put(ri.getId(),ri);
    }

    public void load() {
        saveDir.mkdirs();

        try {
            File saveFile = new File(saveDir, this.saveFileName);

            saveYml = YamlConfiguration.loadConfiguration(saveFile);

            if (!saveFile.exists()) {
                saveYml.set(essencesIndexLocation, 1);
                saveYml.set(rareItemsIndexLocation, 1);
            }
        } catch (Exception e) {
            System.out.println("Unable to load " + this.saveFileName + "! (That's not good.)");

            e.printStackTrace();
        }
    }

    public void save() {
        if (this.saveIsDirty) {
            try {
                this.saveYml.save(new File(saveDir, this.saveFileName));

                this.saveIsDirty = false;
            } catch (IOException e) {
                Bukkit.broadcast("Unable to save " + this.saveFileName + "! (See console for additional info)", "ri4.admin.alerts");

                e.printStackTrace();
            }
        }
    }
}
