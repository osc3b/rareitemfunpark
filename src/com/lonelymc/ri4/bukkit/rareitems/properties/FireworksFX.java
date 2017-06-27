package com.lonelymc.ri4.bukkit.rareitems.properties;

import com.lonelymc.ri4.bukkit.rareitems.RareItemProperty;
import com.lonelymc.ri4.api.ItemPropertyRarity;
import com.lonelymc.ri4.api.PropertyCostType;
import com.lonelymc.ri4.util.ParticleEffect;
import org.bukkit.entity.Player;

public class FireworksFX extends RareItemProperty {
    public FireworksFX() {
        super(
                "Fireworks FX",
                "While worn as armor or a helmet shows a visual fireworks effect around you",
                ItemPropertyRarity.STRANGE,
                PropertyCostType.AUTOMATIC,
                1,//Cost
                1//Max level
        );
    }

    @Override
    public void applyEffectToPlayer(Player player, int level) {
        ParticleEffect.FIREWORKS_SPARK.display(0.6F, 0.6F, 0.6F, 0.1F, 5, player.getLocation().add(0.0D, 1.5D, 0.0D), 50.0D);
    }
}