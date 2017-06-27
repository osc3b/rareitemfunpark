package com.lonelymc.ri4.api;

import java.util.Map;

public interface IRareItemsPersistence {
    // I don't like this, but it makes building the rare item objects in persistence much simpler
    void setAPI(IRareItems4API api);

    // Essences
    IEssence createEmptyEssence(ItemPropertyRarity rarity);

    IEssence createFilledEssence(IRareItemProperty property);

    IEssence getEssence(int essenceId);

    void saveEssence(IEssence essence);

    // Rare Items
    IRareItem createRareItem(Map<IRareItemProperty, Integer> riProperties);

    IRareItem getRareItem(int rareItemId);

    void saveRareItem(IRareItem ri);

    void save();
}
