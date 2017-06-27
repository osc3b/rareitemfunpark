package com.lonelymc.ri4.bukkit.rareitems.properties;

import com.lonelymc.ri4.api.ItemPropertyRarity;
import com.lonelymc.ri4.api.PropertyCostType;
import com.lonelymc.ri4.bukkit.rareitems.RareItemProperty;
import com.lonelymc.ri4.util.FireworkVisualEffect;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import java.util.List;

public class GreaterBurst extends RareItemProperty {
    private final FireworkVisualEffect fireworks;

    public GreaterBurst() {
        super(
                "Greater Burst",
                "Forcibly shoves all nearby creatures away",
                ItemPropertyRarity.UNCOMMON,
                PropertyCostType.FOOD,
                5.0D,
                5,
                new String[]{
                        "type=TNT;",
                        "type=EYE_OF_ENDER;",
                        "type=TNT;",
                        "type=EYE_OF_ENDER;",
                        "!UNCOMMON_ESSENCE",
                        "type=EYE_OF_ENDER;",
                        "type=TNT;",
                        "type=SKULL_ITEM;dura=1;",
                        "type=TNT;"
                }
        );

        this.fireworks = new FireworkVisualEffect();
    }

    @Override
    public boolean onDamagedOther(Player pAttacker, EntityDamageByEntityEvent e, int level) {
        return activate(pAttacker, level);
    }

    @Override
    public boolean onInteracted(Player pInteracted, PlayerInteractEvent e, int level) {
        return activate(pInteracted, level);
    }

    private boolean activate(Player player, int level) {
        List<Entity> nearbyEntities = player.getNearbyEntities(8.0D, 8.0D, 8.0D);
        if (!nearbyEntities.isEmpty()) {
            boolean showFx = false;
            Vector vPlayer = player.getLocation().toVector();
            for (Entity ent : nearbyEntities) {
                if ((ent instanceof LivingEntity)) {
                    Vector unitVector = ent.getLocation().toVector().subtract(vPlayer).normalize();

                    unitVector.setY(0.55D / level);

                    ent.setVelocity(unitVector.multiply(level * 2));

                    showFx = true;
                }
            }
            if (showFx) {
                try {
                    this.fireworks.playFirework(player
                                    .getWorld(), player.getLocation(),

                            FireworkEffect.builder()
                                    .with(FireworkEffect.Type.BALL_LARGE)
                                    .withColor(Color.WHITE)
                                    .build());
                } catch (Exception ex) {
                }
            } else {
                return false;
            }
            return true;
        }
        return false;
    }
}