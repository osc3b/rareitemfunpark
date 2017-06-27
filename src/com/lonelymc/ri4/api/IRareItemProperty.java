package com.lonelymc.ri4.api;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public interface IRareItemProperty {
    String getName();

    ItemPropertyRarity getRarity();

    String getDescription();

    String[] getRecipe();

    void setDescription(String sDescription);

    public int getMaxLevel();

    double getCost();

    void setCost(double cost);

    PropertyCostType getCostType();

    void setCostType(PropertyCostType costType);

    boolean onDamaged(Player pDamaged, EntityDamageEvent e, int level);

    boolean onDamagedOther(Player pAttacker, EntityDamageByEntityEvent e, int level);

    boolean onInteracted(Player pInteracted, PlayerInteractEvent e, int level);

    boolean onInteractEntity(Player pInteracted, PlayerInteractEntityEvent e, int level);

    boolean onLaunchProjectile(Player player, EntityShootBowEvent e, int level);

    boolean onArrowHitEntity(Player shooter, EntityDamageByEntityEvent e, int level);

    boolean onArrowHitGround(Player shooter, ProjectileHitEvent e, int level);

    boolean hasCost(Player pDamaged, int level);

    void takeCost(Player pDamaged, int level);

    void refreshCooldowns();

    void applyEffectToPlayer(Player player, int level);

    void removeEffectFromPlayer(Player player);

    String getDisplayName();

    void setDisplayName(String sDisplayName);

    void setRecipe(String[] recipe);

    public YamlConfiguration getConfig(YamlConfiguration config);

    public void setConfig(YamlConfiguration config);
}
