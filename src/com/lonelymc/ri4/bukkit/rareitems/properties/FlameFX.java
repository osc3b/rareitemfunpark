package com.lonelymc.ri4.bukkit.rareitems.properties;

import com.lonelymc.ri4.bukkit.rareitems.RareItemProperty;
import com.lonelymc.ri4.api.ItemPropertyRarity;
import com.lonelymc.ri4.api.PropertyCostType;
import com.lonelymc.ri4.util.ParticleEffect;
import org.bukkit.entity.Player;

public class FlameFX extends RareItemProperty {
    public FlameFX() {
        super(
                "Flame FX",
                "While worn as armor or a helmet shows a visual flame effect around you",
                ItemPropertyRarity.STRANGE,
                PropertyCostType.AUTOMATIC,
                3,//Cost
                1//Max level
        );
    }

    @Override
    public void applyEffectToPlayer(Player player, int level) {
        ParticleEffect.FLAME.display(0.05F, 0.1F, 0.05F, 0.05F, 12, player.getLocation().add(0.0D, 1.5D, 0.0D), 50.0D);
    }
}