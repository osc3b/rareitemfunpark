package com.lonelymc.ri4.bukkit.rareitems.properties;

import com.lonelymc.ri4.api.ItemPropertyRarity;
import com.lonelymc.ri4.api.PropertyCostType;
import com.lonelymc.ri4.bukkit.rareitems.RareItemProperty;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Strength extends RareItemProperty {
    public Strength() {
        super(
                "Strength",
                "Adds 1 damage per lvl when put on a melee weapon",
                ItemPropertyRarity.LEGENDARY,
                PropertyCostType.PASSIVE,
                0.0D,
                3,
                new String[]{
                        "type=POTION;dura=8265;",
                        "type=DIAMOND_SWORD;",
                        "type=POTION;dura=8265;",
                        "type=IRON_BLOCK;",
                        "!LEGENDARY_ESSENCE",
                        "type=IRON_BLOCK;",
                        "type=POTION;dura=8265;",
                        "type=NETHER_STAR;",
                        "type=POTION;dura=8265;"
                }
        );
    }

    @Override
    public boolean onDamagedOther(Player pAttacker, EntityDamageByEntityEvent e, int level) {
        e.setDamage(e.getDamage() + level);

        return true;
    }
}