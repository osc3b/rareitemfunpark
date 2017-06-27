package com.lonelymc.ri4.bukkit.rareitems.properties;

import com.lonelymc.ri4.api.ItemPropertyRarity;
import com.lonelymc.ri4.api.PropertyCostType;
import com.lonelymc.ri4.bukkit.rareitems.RareItemProperty;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.util.Vector;

import java.util.Random;

public class Multishot extends RareItemProperty {
    public Multishot() {
        super(
                "Multishot",
                "Fires one extra arrow per level when shot from a bow",
                ItemPropertyRarity.RARE,
                PropertyCostType.FOOD,
                1.0D,
                8,
                new String[]{
                        "type=ARROW;amount=64;",
                        "type=POTION;dura=16425;",
                        "type=ARROW;amount=64;",
                        "type=TRIPWIRE_HOOK;",
                        "!RARE_ESSENCE",
                        "type=TRIPWIRE_HOOK;",
                        "type=LEASH;",
                        "type=POTION;dura=16425;",
                        "type=LEASH;"
                }
        );
    }

    private double randomDouble(Random rand, double a, double b) {
        double random = rand.nextFloat() / 1.7976931348623157E+308D;
        double diff = b - a;
        double r = random * diff;
        return a + r;
    }

    public boolean onLaunchProjectile(EntityShootBowEvent e, Player shooter, int level) {
        Random r = new Random();
        Vector originalArrowVector = e.getProjectile().getVelocity();
        for (int i = 0; i < level * 2; i++) {
            Arrow bonusArrow = shooter.getWorld().spawnArrow(e.getProjectile().getLocation(), originalArrowVector, e.getForce(), 12.0F);

            bonusArrow.setVelocity(new Vector(originalArrowVector
                    .getX() + (r.nextFloat() - r.nextFloat()) / 1.5F, originalArrowVector
                    .getY() + (r.nextFloat() - r.nextFloat()) / 3.0F, originalArrowVector
                    .getZ() + (r.nextFloat() - r.nextFloat()) / 1.5F));


            bonusArrow.setShooter(shooter);
        }
        return true;
    }
}