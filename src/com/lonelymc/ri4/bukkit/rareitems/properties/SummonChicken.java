package com.lonelymc.ri4.bukkit.rareitems.properties;

import com.lonelymc.ri4.bukkit.rareitems.RareItemProperty;
import com.lonelymc.ri4.api.ItemPropertyRarity;
import com.lonelymc.ri4.api.PropertyCostType;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public class SummonChicken extends RareItemProperty
{
    public SummonChicken()
    {
        super(
            "Summon Chicken",
            "Creates one chicken / level",
            ItemPropertyRarity.RARE,
            PropertyCostType.EXPERIENCE,
            60,//Cost
            8,//Max level
            new String[]{
                    "type=CHICKEN;",
                    "type=FEATHER;",
                    "type=CHICKEN;",
                    "type=EGG;",
                    "!RARE_ESSENCE",
                    "type=EGG;",
                    "type=CHICKEN;",
                    "type=FEATHER;",
                    "type=CHICKEN;"
            }
        );
    }

    @Override
    public boolean onInteracted(Player pInteracted, PlayerInteractEvent e, int level)
    {
        for (int i = 0; i < level; i++) {
            e.getPlayer().getWorld().spawnEntity(e.getPlayer().getLocation(), EntityType.CHICKEN);
        }
        return true;
    }
}