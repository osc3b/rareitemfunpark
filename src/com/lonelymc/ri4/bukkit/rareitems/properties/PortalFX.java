package com.lonelymc.ri4.bukkit.rareitems.properties;

import com.lonelymc.ri4.bukkit.rareitems.RareItemProperty;
import com.lonelymc.ri4.api.ItemPropertyRarity;
import com.lonelymc.ri4.api.PropertyCostType;
import com.lonelymc.ri4.util.ParticleEffect;
import org.bukkit.entity.Player;

public class PortalFX extends RareItemProperty {
    public PortalFX() {
        super(
                "Portal FX",
                "While worn as armor or a helmet shows a visual portal effect around you",
                ItemPropertyRarity.STRANGE,
                PropertyCostType.AUTOMATIC,
                3,//Cost
                1//Max level
        );
    }

    @Override
    public void applyEffectToPlayer(Player player, int level) {
        ParticleEffect.PORTAL.display(0.5F, 0.5F, 0.5F, 0.5F, 25, player.getLocation().add(0.0D, 1.5D, 0.0D), 50.0D);
    }
}