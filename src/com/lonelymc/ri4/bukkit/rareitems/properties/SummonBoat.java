package com.lonelymc.ri4.bukkit.rareitems.properties;

import com.lonelymc.ri4.bukkit.rareitems.RareItemProperty;
import com.lonelymc.ri4.api.ItemPropertyRarity;
import com.lonelymc.ri4.api.PropertyCostType;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public class SummonBoat extends RareItemProperty {
    public SummonBoat() {
        super(
            "Summon Boat",
            "Summons a boat. Legend says it was created by a wizard who was sick of crashing his boats.",
            ItemPropertyRarity.COMMON,
            PropertyCostType.COOLDOWN,
            45,//Cost
            1,//Max level
            new String[]{
                    "type=SPRUCE_BOAT;",
                    "type=PLANKS;",
                    "type=JUNGLE_BOAT;",
                    "type=BIRCH_BOAT;",
                    "!COMMON_ESSENCE",
                    "type=DARK_OAK_BOAT;",
                    "type=SPRUCE_BOAT;",
                    "type=PLANKS;",
                    "type=SPRUCE_BOAT;"
            }
        );
    }

    @Override
    public boolean onInteracted(Player pInteracted, PlayerInteractEvent e, int level) {
        pInteracted.getWorld().spawnEntity(pInteracted.getLocation(),EntityType.BOAT);

        return true;
    }
}