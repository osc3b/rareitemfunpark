package com.lonelymc.ri4.bukkit.rareitems.properties;

import com.lonelymc.ri4.bukkit.rareitems.RareItemProperty;
import com.lonelymc.ri4.api.ItemPropertyRarity;
import com.lonelymc.ri4.api.PropertyCostType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class MermaidsGift extends RareItemProperty {
    public MermaidsGift() {
        super(
              "Mermaid's Gift",
              "While worn as armor or a helmet allows for better control and faster movement while underwater",
              ItemPropertyRarity.RARE,
              PropertyCostType.AUTOMATIC,
              1,//Cost
              1//Max level
        );
    }

    @Override
    public void applyEffectToPlayer(Player player, int level) {
        Location pLoc = player.getLocation();

        boolean isInWater = (pLoc.clone().getBlock().getType().equals(Material.STATIONARY_WATER)) && (pLoc.clone().add(0.0D, 1.0D, 0.0D).getBlock().getType().equals(Material.STATIONARY_WATER)) && (pLoc.clone().add(0.0D, -1.0D, 0.0D).getBlock().getType().equals(Material.STATIONARY_WATER)) && (pLoc.clone().add(-1.0D, 0.0D, 1.0D).getBlock().getType().equals(Material.STATIONARY_WATER)) && (pLoc.clone().add(1.0D, 0.0D, -1.0D).getBlock().getType().equals(Material.STATIONARY_WATER));

        if (isInWater) {
            if (!player.isFlying()) {
                player.setAllowFlight(true);
                player.setFlying(true);
                player.setFlySpeed(0.1F);
            }
            if (!player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 72000, 2));
            }
        }
        else if (player.isFlying()) {
            player.setAllowFlight(false);

            if (player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
                player.removePotionEffect(PotionEffectType.NIGHT_VISION);
            }
        }
    }

    @Override
    public void removeEffectFromPlayer(Player player) {
        player.setAllowFlight(false);

        if (player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
            player.removePotionEffect(PotionEffectType.NIGHT_VISION);
        }
    }
}