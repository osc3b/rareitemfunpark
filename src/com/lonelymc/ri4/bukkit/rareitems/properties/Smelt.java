package com.lonelymc.ri4.bukkit.rareitems.properties;

import com.lonelymc.ri4.api.ItemPropertyRarity;
import com.lonelymc.ri4.api.PropertyCostType;
import com.lonelymc.ri4.bukkit.rareitems.RareItemProperty;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public class Smelt extends RareItemProperty {
    public Smelt() {
        super(
                "Smelt",
                "Turns clicked cobblestone into stone",
                ItemPropertyRarity.COMMON,
                PropertyCostType.COOLDOWN,
                1.0D,
                1,
                new String[]{
                        "type=STONE;",
                        "type=LAVA_BUCKET;",
                        "type=STONE;",
                        "type=COAL_BLOCK;",
                        "!COMMON_ESSENCE",
                        "type=COAL_BLOCK;",
                        "type=STONE;",
                        "type=LAVA_BUCKET;",
                        "type=STONE;"
                }
        );
    }

    @Override
    public boolean onInteracted(Player pInteracted, PlayerInteractEvent e, int level) {
        if ((e.getClickedBlock() != null) && (e.getClickedBlock().getType() == Material.COBBLESTONE)) {
            e.getClickedBlock().setType(Material.STONE);

            return true;
        }
        return false;
    }
}