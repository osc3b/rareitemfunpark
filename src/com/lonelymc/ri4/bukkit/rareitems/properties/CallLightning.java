package com.lonelymc.ri4.bukkit.rareitems.properties;

import com.lonelymc.ri4.bukkit.rareitems.RareItemProperty;
import com.lonelymc.ri4.api.ItemPropertyRarity;
import com.lonelymc.ri4.api.PropertyCostType;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Random;

public class CallLightning extends RareItemProperty {
    public CallLightning() {
        super(
                "Call Lightning",
                "5% chance to strike an opponent with lightning per level",
                ItemPropertyRarity.LEGENDARY,
                PropertyCostType.FOOD,
                4.0D,
                5,
                new String[]{
                        "type=IRON_AXE;",
                        "type=BLAZE_POWDER;",
                        "type=IRON_AXE;",
                        "type=BLAZE_POWDER;",
                        "!LEGENDARY_ESSENCE",
                        "type=BLAZE_POWDER;",
                        "type=IRON_AXE;",
                        "type=BLAZE_POWDER;",
                        "type=IRON_AXE;"
                }
        );
    }

    @Override
    public boolean onDamagedOther(Player p, EntityDamageByEntityEvent e, int level) {
        if ((!e.getCause().equals(EntityDamageEvent.DamageCause.LIGHTNING)) &&
                (new Random().nextInt(100) < level * 5) &&
                ((e.getEntity() instanceof LivingEntity))) {
            int maxTargets = (int) (level * 1.5D);
            int hitTargets = 0;

            Location l = e.getEntity().getLocation();

            l.getWorld().strikeLightningEffect(l);

            int maxIterations = 20;
            for (Entity ent : e.getEntity().getNearbyEntities(5.0D, 5.0D, 5.0D)) {
                maxIterations--;
                if (maxIterations == 0) {
                    return true;
                }
                if (hitTargets >= maxTargets) {
                    break;
                }
                if (ent != p) {
                    if ((ent instanceof LivingEntity)) {
                        hitTargets++;

                        LivingEntity lent = (LivingEntity) ent;


                        lent.damage(level, null);
                    }
                }
            }
            return true;
        }
        return false;
    }
}