package com.lonelymc.ri4.bukkit.rareitems.properties;

import com.lonelymc.ri4.bukkit.rareitems.RareItemProperty;
import com.lonelymc.ri4.api.ItemPropertyRarity;
import com.lonelymc.ri4.api.PropertyCostType;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public class SummonBat extends RareItemProperty
{
    public SummonBat()
    {
        super(
            "Summon Bat",
            "Creates one bat / level",
            ItemPropertyRarity.UNCOMMON,
            PropertyCostType.EXPERIENCE,
            25,//Cost
            8//Max level
        );
    }

    @Override
    public boolean onInteracted(Player pInteracted, PlayerInteractEvent e, int level)
    {
        for (int i = 0; i < level; i++) {
            e.getPlayer().getWorld().spawnEntity(e.getPlayer().getLocation(), EntityType.BAT);
        }
        return true;
    }
}