package com.lonelymc.ri4.bukkit.rareitems.properties;

import com.lonelymc.ri4.bukkit.rareitems.RareItemProperty;
import com.lonelymc.ri4.api.ItemPropertyRarity;
import com.lonelymc.ri4.api.PropertyCostType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public class MagicBag extends RareItemProperty {
    public MagicBag() {
        super(
                "Magic Bag",
                "Access your ender chest",
                ItemPropertyRarity.UNCOMMON,
                PropertyCostType.COOLDOWN,
                4.0D,
                1,
                new String[]{
                        "type=CHEST;", 
                        "type=ENDER_EYE;", 
                        "type=CHEST;", 
                        "type=ENDER_CHEST;", 
                        "!UNCOMMON_ESSENCE", 
                        "type=ENDER_CHEST;", 
                        "type=CHEST;",
                        "type=ENDER_EYE;", 
                        "type=CHEST;"
                }
        );
    }

    @Override
    public boolean onInteracted(Player pInteracted, PlayerInteractEvent e, int level) {
        e.getPlayer().openInventory(e.getPlayer().getEnderChest());

        return true;
    }
}