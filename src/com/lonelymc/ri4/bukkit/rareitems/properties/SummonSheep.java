package com.lonelymc.ri4.bukkit.rareitems.properties;

import com.lonelymc.ri4.bukkit.rareitems.RareItemProperty;
import com.lonelymc.ri4.api.ItemPropertyRarity;
import com.lonelymc.ri4.api.PropertyCostType;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public class SummonSheep extends RareItemProperty
{
    public SummonSheep()
    {
        super(
            "Summon Sheep",
            "Creates one sheep / level",
            ItemPropertyRarity.RARE,
            PropertyCostType.EXPERIENCE,
            50,//Cost
            8,//Max level
            new String[]{
                    "type=MUTTON;",
                    "type=WOOL;",
                    "type=MUTTON;",
                    "type=WOOL;",
                    "!RARE_ESSENCE",
                    "type=WOOL;",
                    "type=MUTTON;",
                    "type=WOOL;",
                    "type=MUTTON;"
            }
        );
    }

    @Override
    public boolean onInteracted(Player pInteracted, PlayerInteractEvent e, int level)
    {
        for (int i = 0; i < level; i++) {
            e.getPlayer().getWorld().spawnEntity(e.getPlayer().getLocation(), EntityType.SHEEP);
        }
        return true;
    }
}