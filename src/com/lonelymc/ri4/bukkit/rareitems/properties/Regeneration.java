package com.lonelymc.ri4.bukkit.rareitems.properties;

import com.lonelymc.ri4.bukkit.rareitems.RareItemProperty;
import com.lonelymc.ri4.api.ItemPropertyRarity;
import com.lonelymc.ri4.api.PropertyCostType;
import org.bukkit.Effect;
import org.bukkit.entity.Player;

public class Regeneration extends RareItemProperty {
    public Regeneration() {
        super(
                "Regeneration",
                "While worn as armor or a helmet regenerates 1 HP per lvl every 5 seconds",
                ItemPropertyRarity.LEGENDARY,
                PropertyCostType.AUTOMATIC,
                5.0D,
                5,
                new String[]{
                        "type=POTION;dura=8257;",
                        "type=GOLDEN_APPLE;dura=1;",
                        "type=POTION;dura=8257;",
                        "type=GOLDEN_APPLE;dura=1;",
                        "!LEGENDARY_ESSENCE",
                        "type=GOLDEN_APPLE;dura=1;",
                        "type=POTION;dura=8257;",
                        "type=GOLDEN_APPLE;dura=1;",
                        "type=POTION;dura=8257;"
                }
        );
    }

    @Override
    public void applyEffectToPlayer(Player p, int level) {
        if (p.getHealth() < p.getMaxHealth()) {

            double iNewHP = p.getHealth() + level;

            if (iNewHP > p.getMaxHealth()) {
                iNewHP = p.getMaxHealth();
            }

            p.setHealth(iNewHP);

            //TODO: Verify this effect works
            p.getLocation().getWorld().playEffect(p.getLocation(), Effect.POTION_BREAK, 1);
        }
    }
}