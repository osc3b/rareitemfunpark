package com.lonelymc.ri4.bukkit.rareitems.properties;

import com.lonelymc.ri4.bukkit.rareitems.RareItemProperty;
import com.lonelymc.ri4.api.ItemPropertyRarity;
import com.lonelymc.ri4.api.PropertyCostType;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public class SummonCow extends RareItemProperty
{
    public SummonCow()
    {
        super(
            "Summon Cow",
            "Creates one cow / level",
            ItemPropertyRarity.RARE,
            PropertyCostType.EXPERIENCE,
            130,//Cost
            8//Max level
        );
    }

    @Override
    public boolean onInteracted(Player pInteracted, PlayerInteractEvent e, int level)
    {
        for (int i = 0; i < level; i++) {
            e.getPlayer().getWorld().spawnEntity(e.getPlayer().getLocation(), EntityType.COW);
        }
        return true;
    }
}