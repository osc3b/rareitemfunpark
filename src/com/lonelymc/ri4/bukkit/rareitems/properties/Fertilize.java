package com.lonelymc.ri4.bukkit.rareitems.properties;

import com.lonelymc.ri4.api.ItemPropertyRarity;
import com.lonelymc.ri4.api.PropertyCostType;
import com.lonelymc.ri4.bukkit.rareitems.RareItemProperty;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public class Fertilize extends RareItemProperty {
    public Fertilize() {
        super(
                "Fertilize",
                "Turns clicked dirt blocks to grass",
                ItemPropertyRarity.COMMON,
                PropertyCostType.COOLDOWN,
                0.5,//Cost
                1//Max level
        );
    }

    @Override
    public boolean onInteracted(Player pInteracted, PlayerInteractEvent e, int level) {
        if ((e.hasBlock()) && (e.getClickedBlock().getType() == Material.DIRT)) {
            e.getClickedBlock().setType(Material.GRASS);

            return true;
        }
        return false;
    }
}