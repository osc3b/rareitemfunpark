package com.lonelymc.ri4.bukkit.rareitems.properties;

import com.lonelymc.ri4.bukkit.rareitems.RareItemProperty;
import com.lonelymc.ri4.api.ItemPropertyRarity;
import com.lonelymc.ri4.api.PropertyCostType;
import com.lonelymc.ri4.util.ParticleEffect;
import org.bukkit.entity.Player;

public class WitchFX extends RareItemProperty {
    public WitchFX() {
        super(
                "Witch FX",
                "While worn as armor or a helmet shows purple sparkles",
                ItemPropertyRarity.STRANGE,
                PropertyCostType.AUTOMATIC,
                1,//Cost
                1//Max level
        );
    }

    @Override
    public void applyEffectToPlayer(Player player, int level) {
        ParticleEffect.SPELL_WITCH.display(0.5F, 0.5F, 0.5F, 0.25F, 10, player.getLocation().add(0.0D, 1.5D, 0.0D), 50.0D);
    }
}