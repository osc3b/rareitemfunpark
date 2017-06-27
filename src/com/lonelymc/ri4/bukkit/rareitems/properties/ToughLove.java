package com.lonelymc.ri4.bukkit.rareitems.properties;

import com.lonelymc.ri4.bukkit.rareitems.RareItemProperty;
import com.lonelymc.ri4.api.ItemPropertyRarity;
import com.lonelymc.ri4.api.PropertyCostType;
import com.lonelymc.ri4.util.ParticleEffect;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

public class ToughLove extends RareItemProperty {
    public ToughLove(){
        super(
                "Tough Love",
                "While worn as armor or a helmet causes a heart effect when harmed",
                ItemPropertyRarity.STRANGE,
                PropertyCostType.PASSIVE,
                0,//Cost
                1,//Max level
                new String[]{
                        "type=CAKE;",
                        "type=ANVIL;",
                        "type=CAKE;",
                        "type=ANVIL;",
                        "!STRANGE_ESSENCE",
                        "type=ANVIL;",
                        "type=CAKE;",
                        "type=ANVIL;",
                        "type=CAKE;"
                }
        );
    }

    @Override
    public boolean onDamaged(Player pDamaged, EntityDamageEvent e, int level) {
        ParticleEffect.HEART.display(0.4F, 0.75F, 0.4F, 0.1F, 4, pDamaged.getLocation().add(0.0D, 1.5D, 0.0D), 50.0D);

        return true;
    }
}
