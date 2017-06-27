package com.lonelymc.ri4.bukkit.rareitems.properties;

import com.lonelymc.ri4.bukkit.rareitems.RareItemProperty;
import com.lonelymc.ri4.api.ItemPropertyRarity;
import com.lonelymc.ri4.api.PropertyCostType;
import com.lonelymc.ri4.util.FireworkVisualEffect;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Burst extends RareItemProperty {
    private final FireworkVisualEffect fireworks;

    public Burst() {
        super(
                "Burst",
                "Pushes a clicked target away",
                ItemPropertyRarity.UNCOMMON,
                PropertyCostType.FOOD,
                5.0D,
                3,
                new String[]{
                        "type=TNT;",
                        "type=EYE_OF_ENDER;",
                        "type=TNT;",
                        "type=EYE_OF_ENDER;",
                        "!UNCOMMON_ESSENCE",
                        "type=EYE_OF_ENDER;",
                        "type=TNT;",
                        "type=WATCH;",
                        "type=TNT;"
                }
        );

        this.fireworks = new FireworkVisualEffect();
    }

    @Override
    public boolean onDamagedOther(Player pAttacker, EntityDamageByEntityEvent e, int level){
        if ((e.getEntity() instanceof LivingEntity)) {
            LivingEntity le = (LivingEntity) e.getEntity();
            try {
                this.fireworks.playFirework(le
                                .getWorld(), le.getLocation(),

                        FireworkEffect.builder()
                                .with(FireworkEffect.Type.BURST)
                                .withColor(Color.WHITE)
                                .build());
            } catch (Exception ex) {
                Logger.getLogger(Burst.class.getName()).log(Level.SEVERE, null, ex);
            }
            Vector unitVector = le.getLocation().toVector().subtract(e.getDamager().getLocation().toVector()).normalize();

            unitVector.setY(0.55D / level);

            le.setVelocity(unitVector.multiply(level * 2));

            e.setCancelled(true);

            return true;
        }
        return false;
    }
}