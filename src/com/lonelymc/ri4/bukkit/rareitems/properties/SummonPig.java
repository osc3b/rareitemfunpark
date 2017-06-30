package com.lonelymc.ri4.bukkit.rareitems.properties;

import com.lonelymc.ri4.bukkit.rareitems.RareItemProperty;
import com.lonelymc.ri4.api.ItemPropertyRarity;
import com.lonelymc.ri4.api.PropertyCostType;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public class SummonPig extends RareItemProperty
{
    public SummonPig()
    {
        super(
            "Summon Pig",
            "Creates one pig / level",
            ItemPropertyRarity.RARE,
            PropertyCostType.EXPERIENCE,
            65,//Cost
            8,//Max level
            new String[]{
                    "type=PORKCHOP;",
                    "type=CARROT_ON_A_STICK;",
                    "type=PORKCHOP;",
                    "type=PORKCHOP;",
                    "!RARE_ESSENCE",
                    "type=PORKCHOP;",
                    "type=PORKCHOP;",
                    "type=CARROT_ON_A_STICK;",
                    "type=PORKCHOP;"
            }
        );
    }

    @Override
    public boolean onInteracted(Player pInteracted, PlayerInteractEvent e, int level)
    {
        for (int i = 0; i < level; i++) {
            e.getPlayer().getWorld().spawnEntity(e.getPlayer().getLocation(), EntityType.PIG);
        }
        return true;
    }
}