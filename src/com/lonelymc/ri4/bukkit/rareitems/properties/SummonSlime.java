package com.lonelymc.ri4.bukkit.rareitems.properties;

import com.lonelymc.ri4.api.ItemPropertyRarity;
import com.lonelymc.ri4.api.PropertyCostType;
import com.lonelymc.ri4.bukkit.rareitems.RareItemProperty;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public class SummonSlime extends RareItemProperty {
    public SummonSlime() {
        super(
                "Summon Slime",
                "Creates one slime / level",
                ItemPropertyRarity.LEGENDARY,
                PropertyCostType.EXPERIENCE,
                120,//Cost
                8,//Max level
                new String[]{
                        "type=SLIME;",
                        "type=SLIME_BALL;",
                        "type=SLIME;",
                        "type=SLIME_BALL;",
                        "!LEGENDARY_ESSENCE",
                        "type=SLIME_BALL;",
                        "type=SLIME;",
                        "type=SLIME_BALL;",
                        "type=SLIME;"
                }
        );
    }

    @Override
    public boolean onInteracted(Player pInteracted, PlayerInteractEvent e, int level) {
        for (int i = 0; i < level; i++) {
            e.getPlayer().getWorld().spawnEntity(e.getPlayer().getLocation(), EntityType.SLIME);
        }
        return true;
    }
}