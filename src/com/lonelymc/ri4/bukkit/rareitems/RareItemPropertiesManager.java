package com.lonelymc.ri4.bukkit.rareitems;

import com.lonelymc.ri4.api.*;
import com.lonelymc.ri4.bukkit.RareItems4Plugin;
import com.lonelymc.ri4.util.ItemStackConvertor;
import com.lonelymc.ri4.util.MetaStringEncoder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;

public class RareItemPropertiesManager {
    private final RareItems4Plugin plugin;
    private final File customizationsDir;
    private final HashMap<String, IRareItemProperty> properties;

    public RareItemPropertiesManager(RareItems4Plugin plugin, IRareItems4API api) {
        this.plugin = plugin;
        this.customizationsDir = new File(plugin.getDataFolder(),"properties");

        this.customizationsDir.mkdirs();

        this.properties = new HashMap<>();
    }

    public List<IRareItemProperty> getAllProperties() {
        return new ArrayList<IRareItemProperty>(this.properties.values());
    }

    public void addItemProperty(IRareItemProperty rip) {
        // Load any customizations to the property
        File configFile = new File(customizationsDir, rip.getName()+".yml");

        YamlConfiguration yml = YamlConfiguration.loadConfiguration(configFile);

        YamlConfiguration riYml = rip.getConfig(new YamlConfiguration());

        if(!configFile.exists()){
            for(String key : riYml.getKeys(true)){
                yml.set(key,riYml.get(key));
            }
        }
        else{
            for(String key : riYml.getKeys(true)){
                if(yml.isSet(key)){
                    riYml.set(key,yml.get(key));
                }
                else{
                    yml.set(key,riYml.get(key));
                }
            }
        }

        try {
            yml.save(configFile);
        }
        catch (IOException e) {
            this.plugin.getAPI().log(Level.WARNING,RI4Strings.API_UNABLE_TO_SAVE.replace("!file",configFile.toString()),false);
        }

        if(!yml.getBoolean("enabled")){
            plugin.getLogger().log(Level.INFO,
                    RI4Strings.LOG_RAREITEM_DISABLED.replace("!property", rip.getName()));

            return;
        }

        rip.setConfig(riYml);


        this.properties.put(rip.getDisplayName().toLowerCase(), rip);

        RareItemPropertiesManager.refreshServerRecipe(rip);
    }

    public static void refreshServerRecipe(IRareItemProperty rip) {
        Iterator<Recipe> it = Bukkit.getServer().recipeIterator();

        String recipeName = MetaStringEncoder.encodeHidden(rip.getName(), "rir");

        while (it.hasNext()) {
            Recipe r = it.next();

            ItemStack result = r.getResult();

            if (result.getType().equals(Material.DIRT) && result.hasItemMeta()) {
                ItemMeta meta = result.getItemMeta();

                if (meta.getDisplayName().equals(recipeName)) {
                    it.remove();

                    break;
                }
            }
        }

        if (rip.getRecipe() == null) {
            return;
        }

        ItemStack isPlaceholder = new ItemStack(Material.DIRT);

        ItemMeta meta = isPlaceholder.getItemMeta();

        meta.setDisplayName(recipeName);

        isPlaceholder.setItemMeta(meta);

        ShapedRecipe recipe = new ShapedRecipe(isPlaceholder);

        String[] ripRecipe = rip.getRecipe();

        char[] availableRecipeChars = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I'};
        String recipeChars = "";

        Map<Material, Character> recipeShape = new HashMap<>();

        for (String ingredient : ripRecipe) {
            Character sChar;

            switch (ingredient) {
                default:
                    ItemStack is = ItemStackConvertor.fromString(ingredient);

                    sChar = recipeShape.get(is.getType());

                    if (sChar == null) {
                        sChar = availableRecipeChars[recipeShape.size()];

                        recipeShape.put(is.getType(), sChar);
                    }

                    recipeChars += sChar;

                    break;
                case "AIR":
                    recipeChars += " ";
                    break;

                case "!COMMON_ESSENCE":
                case "!UNCOMMON_ESSENCE":
                case "!RARE_ESSENCE":
                case "!LEGENDARY_ESSENCE":
                case "!STRANGE_ESSENCE":
                    String rarity = ingredient.substring(1, ingredient.indexOf("_"));

                    Material m = Material.valueOf(Essence.getMaterialByRarity(ItemPropertyRarity.valueOf(rarity)));

                    sChar = recipeShape.get(m);

                    if (sChar == null) {
                        sChar = availableRecipeChars[recipeShape.size()];

                        recipeShape.put(m, sChar);
                    }

                    recipeChars += sChar;

                    break;
            }
        }

        while (recipeChars.length() < 9) {
            recipeChars += " ";
        }

        recipe.shape(
                recipeChars.substring(0, 3),
                recipeChars.substring(3, 6),
                recipeChars.substring(6)
        );

        for (Map.Entry<Material, Character> entry : recipeShape.entrySet()) {
            recipe.setIngredient(entry.getValue(), entry.getKey(), (byte) -1);
        }

        Bukkit.getServer().addRecipe(recipe);
    }

    public IRareItemProperty getItemProperty(String propertyName) {
        propertyName = propertyName.toLowerCase();

        for (IRareItemProperty rip : this.properties.values()) {
            if (rip.getName().toLowerCase().equals(propertyName)) {
                return rip;
            }
        }

        return null;
    }

    public IRareItemProperty getItemPropertyByDisplayName(String propertyDisplayName) {
        return this.properties.get(propertyDisplayName.toLowerCase());
    }

    public void saveItemProperty(IRareItemProperty rip) {
        // Load any customizations to the property
        File configFile = new File(customizationsDir, rip.getName()+".yml");

        YamlConfiguration yml = YamlConfiguration.loadConfiguration(configFile);
        YamlConfiguration riYml = rip.getConfig(new YamlConfiguration());

        for(String key : riYml.getKeys(true)){
            yml.set(key,riYml.get(key));
        }

        try {
            yml.save(configFile);
        }
        catch (IOException e) {
            this.plugin.getAPI().log(Level.WARNING,RI4Strings.API_UNABLE_TO_SAVE.replace("!file",configFile.toString()),true);
        }
    }

    public List<IRareItemProperty> getAllPropertiesByRarity(ItemPropertyRarity rarity) {
        List<IRareItemProperty> rips = new ArrayList<>();

        for(IRareItemProperty rip : this.properties.values()){
            if(rip.getRarity().equals(rarity)){
                rips.add(rip);
            }
        }

        return rips;
    }

    public List<IRareItemProperty> getPropertiesByDisplayNameSearch(String sPropertyName) {
        sPropertyName = sPropertyName.toLowerCase();

        List<IRareItemProperty> rips = new ArrayList<>();

        for(IRareItemProperty rip : this.properties.values()){
            if(rip.getDisplayName().toLowerCase().contains(sPropertyName)){
                rips.add(rip);
            }
        }

        return rips;
    }
}
