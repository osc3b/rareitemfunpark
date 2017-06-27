package com.lonelymc.ri4.api;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

public interface IRareItems4API {
    //notification of plugin being shutdown
    void save();

    // Item Properties
    void addItemProperty(IRareItemProperty property);

    IRareItemProperty getItemProperty(String propertyName);

    IRareItemProperty getItemPropertyByDisplayName(String propertyName);

    void saveItemProperty(IRareItemProperty rip);

    // Essences
    IEssence createEssence(ItemPropertyRarity rarity);

    IEssence createEssence(IRareItemProperty property);

    IEssence getEssence(ItemStack is);

    IEssence getEssence(int id);

    void saveEssence(IEssence essence);

    IEssence generateDummyEssence(IRareItemProperty rip);

    IEssence generateDummyEssence(ItemPropertyRarity rarity);

    boolean isDummyEssence(ItemStack is);

    // Rare Items
    IRareItem createRareItem(Map<IRareItemProperty,Integer> riProperties);

    IRareItem getRareItem(ItemStack is);

    IRareItem getRareItem(int rareItemId);

    void saveRareItem(IRareItem ri);

    IRareItem generateDummyRareItem(Map<IRareItemProperty, Integer> properties);

    //Effects management
    Map<IRareItemProperty,Integer> getActiveEffects(UUID playerId);

    void equipRareItem(Player player, ItemStack is);

    void equipRareItem(Player player, IRareItem ri);

    void unEquipRareItem(Player player, ItemStack is);

    void unEquipRareItem(Player player, IRareItem ri);

    void removeActiveEffects(Player player);

    List<IRareItemProperty> getAllItemProperties();

    void log(Level logLevel,String msg,boolean announceInGame);

    List<IRareItemProperty> getPropertiesByRarity(ItemPropertyRarity rarity);

    List<IRareItemProperty> getPropertiesByDisplayNameSearch(String sPropertyName);
}
