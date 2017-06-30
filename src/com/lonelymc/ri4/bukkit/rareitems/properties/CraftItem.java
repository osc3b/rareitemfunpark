package com.lonelymc.ri4.bukkit.rareitems.properties;

import com.lonelymc.ri4.bukkit.rareitems.RareItemProperty;
import com.lonelymc.ri4.api.ItemPropertyRarity;
import com.lonelymc.ri4.api.PropertyCostType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public class CraftItem extends RareItemProperty {
    public CraftItem() {
        super(
                "Craft Item",
                "Opens a crafting table",
                ItemPropertyRarity.COMMON,
                PropertyCostType.COOLDOWN,
                5.0D,
                1,
                new String[]{
                        "type=CRAFTING_TABLE;", 
                        "type=LOG;",
                        "type=CRAFTING_TABLE;", 
                        "type=GOLD_INGOT;", 
                        "!COMMON_ESSENCE", 
                        "type=IRON_INGOT;", 
                        "type=CRAFTING_TABLE;", 
                        "type=STONE;", 
                        "type=CRAFTING_TABLE;"
                }
        );
    }

    @Override
    public boolean onInteracted(Player pInteracted, PlayerInteractEvent e, int level) {
        pInteracted.openWorkbench(null, true);

        return true;
    }
}
