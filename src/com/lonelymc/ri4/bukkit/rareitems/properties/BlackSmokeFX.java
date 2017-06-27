package com.lonelymc.ri4.bukkit.rareitems.properties;

import com.lonelymc.ri4.bukkit.rareitems.RareItemProperty;
import com.lonelymc.ri4.api.ItemPropertyRarity;
import com.lonelymc.ri4.api.PropertyCostType;
import com.lonelymc.ri4.util.ParticleEffect;
import org.bukkit.entity.Player;

public class BlackSmokeFX extends RareItemProperty {
    public BlackSmokeFX() {
        super(
                "Black Smoke FX",
                "While worn as armor or a helmet shows a visual black smoke effect around you",
                ItemPropertyRarity.STRANGE,
                PropertyCostType.AUTOMATIC,
                3,//Cost
                1//Max level
        );
    }

    public void applyEffectToPlayer(Player player, int level) {
        ParticleEffect.REDSTONE.display(0.6F, 0.6F, 0.6F, 0.01F, 10, player.getLocation().add(0.0D, 1.5D, 0.0D), 50.0D);
    }
}