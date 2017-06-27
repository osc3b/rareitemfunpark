package com.lonelymc.ri4.bukkit.rareitems.properties;

import com.lonelymc.ri4.bukkit.rareitems.RareItemProperty;
import com.lonelymc.ri4.api.ItemPropertyRarity;
import com.lonelymc.ri4.api.PropertyCostType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

public class Rage extends RareItemProperty {
    public Rage() {
        super(
                "Rage",
                "While worn as armor or a helmet damage is decreased at high health, but heavily increased at low health. Higher levels modify damage even more",
                ItemPropertyRarity.RARE,
                PropertyCostType.PASSIVE,
                0.0D,
                5
        );
    }


    @Override
    public boolean onDamaged(Player pDamaged, EntityDamageEvent e, int level){
        e.setDamage(this.getModifiedDamage(level,pDamaged.getHealth(),e.getDamage()));

        return true;
    }

    public double getModifiedDamage(double level, double hp, double damage) {
        if (hp == 10.0D) {
            return damage;
        }
        double levelModifier = 0.15D + level * 0.6D;

        double hpModifier = (hp - 10.0D) * -1.0D;

        double newDamage = damage + levelModifier * hpModifier;
        if (newDamage < 1.0D) {
            newDamage = 1.0D;
        }
        return newDamage;
    }
}