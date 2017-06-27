package com.lonelymc.ri4.bukkit.rareitems;

import com.lonelymc.ri4.api.IRareItemProperty;
import com.lonelymc.ri4.api.ItemPropertyRarity;
import com.lonelymc.ri4.api.PropertyCostType;
import com.lonelymc.ri4.api.RI4Strings;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class RareItemProperty implements IRareItemProperty {
    private final ItemPropertyRarity rarity;
    private final int maxLevel;
    private final String name;
    private String[] recipe;
    private String displayName;
    private String description;
    private HashMap<String, Long> cooldowns;
    private PropertyCostType costType;
    private double cost;

    public RareItemProperty(String name, String description, ItemPropertyRarity rarity, PropertyCostType defaultCostType, double defaultCost, int maxLevel, String[] defaultRecipe) {
        this.name = name;
        this.description = description;
        this.rarity = rarity;
        this.costType = defaultCostType;
        this.cost = defaultCost;
        this.maxLevel = maxLevel;

        if (this.costType.equals(PropertyCostType.COOLDOWN)) {
            this.cooldowns = new HashMap<String, Long>();
        } else {
            this.cooldowns = null;
        }

        this.recipe = defaultRecipe;
    }

    public RareItemProperty(String name, String description, ItemPropertyRarity rarity, PropertyCostType defaultCostType, double defaultCost, int maxLevel) {
        this(name, description, rarity, defaultCostType, defaultCost, maxLevel, null);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDisplayName() {
        if (this.displayName == null) {
            return this.name;
        }
        return this.displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public ItemPropertyRarity getRarity() {
        return this.rarity;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int getMaxLevel() {
        return this.maxLevel;
    }

    @Override
    public double getCost() {
        return this.cost;
    }

    @Override
    public PropertyCostType getCostType() {
        return this.costType;
    }

    @Override
    public String[] getRecipe() {
        return this.recipe;
    }

    @Override
    public void setRecipe(String[] recipe) {
        this.recipe = recipe;
    }

    @Override
    public YamlConfiguration getConfig(YamlConfiguration config) {
        return this.getDefaultConfig(config);
    }

    public YamlConfiguration getDefaultConfig(YamlConfiguration config) {
        config.set("enabled", true);
        config.set("displayName", this.getDisplayName());
        config.set("description", this.getDescription());
        config.set("cost", this.getCost());
        config.set("costType", this.getCostType().name());
        config.set("recipe", this.getRecipe());

        return config;
    }

    @Override
    public void setConfig(YamlConfiguration config) {
        this.setDefaultConfig(config);
    }

    public void setDefaultConfig(YamlConfiguration config) {
        this.setDisplayName(config.getString("displayName", this.displayName));
        this.setDescription(config.getString("description", this.description));
        this.setCost(config.getDouble("cost", this.cost));

        List<String> recipe = config.getStringList("recipe");

        if (recipe == null || recipe.isEmpty()) {
            this.setRecipe(null);
        } else {
            this.setRecipe(recipe.toArray(new String[9]));
        }

        String sCostType = config.getString("costType");

        this.setCostType(PropertyCostType.valueOf(sCostType));
    }

    @Override
    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public void setCostType(PropertyCostType costType) {
        this.costType = costType;

        if (costType.equals(PropertyCostType.COOLDOWN)) {
            this.cooldowns = new HashMap<>();
        } else {
            this.cooldowns = null;
        }
    }

    @Override
    public boolean hasCost(Player player, int level) {
        if (this.getCost() == 0) {
            return true;
        }

        switch (this.getCostType()) {
            default:
                return false;
            case AUTOMATIC:
            case PASSIVE:
                return true;

            case FOOD:
                if (player.getFoodLevel() < this.getCost()) {
                    player.sendMessage(RI4Strings.RIP_NEED_MORE_FOOD
                            .replace("!food", String.valueOf(this.getCost() - player.getFoodLevel()))
                            .replace("!property", this.getDisplayName()));

                    return false;
                }

                return true;

            case EXPERIENCE:
                if (player.getTotalExperience() < this.getCost()) {
                    player.sendMessage(RI4Strings.RIP_NEED_MORE_EXPERIENCE
                            .replace("!experience", String.valueOf(this.getCost() - player.getTotalExperience()))
                            .replace("!property", this.getDisplayName()));

                    return false;
                }

                return true;

            case HEALTH:
                if (player.getHealth() < this.getCost()) {
                    player.sendMessage(RI4Strings.RIP_NEED_MORE_HEALTH
                            .replace("!health", String.valueOf(this.getCost() - player.getFoodLevel()))
                            .replace("!property", this.getDisplayName()));

                    return false;
                }

                return true;

            case COOLDOWN:
                Long cooldown = this.cooldowns.get(player.getUniqueId().toString());

                if (cooldown != null) {
                    if (System.currentTimeMillis() < cooldown) {
                        int secondsLeft = (int) ((System.currentTimeMillis() - cooldown) / 1000);

                        player.sendMessage(RI4Strings.RIP_NEED_MORE_COOLDOWN
                                .replace("!seconds", String.valueOf(secondsLeft))
                                .replace("!property", this.getDisplayName()));

                        return false;
                    }
                }

                return true;
        }
    }

    @Override
    public void takeCost(Player player, int level) {
        if (this.getCost() == 0) {
            return;
        }

        switch (this.getCostType()) {
            case AUTOMATIC:
                break;

            case PASSIVE:
                break;

            case FOOD:
                player.setFoodLevel((int) (player.getFoodLevel() - this.getCost()));
                break;

            case EXPERIENCE:
                int totalExperience = player.getTotalExperience();

                player.setTotalExperience(0);
                player.setLevel(0);
                player.setExp(0);
                player.giveExp((int) (totalExperience - this.getCost()));

                break;

            case HEALTH:
                player.setHealth(player.getHealth() - this.getCost());
                break;

            case COOLDOWN:
                this.cooldowns.put(player.getUniqueId().toString(), System.currentTimeMillis() + (long) this.getCost() * 1000L);
                break;
        }
    }

    @Override
    public void refreshCooldowns() {
        if (this.cooldowns != null) {
            long now = System.currentTimeMillis();
            Iterator it = this.cooldowns.entrySet().iterator();

            while (it.hasNext()) {
                Map.Entry<String, Long> next = (Map.Entry<String, Long>) it.next();

                if (next.getValue() < now) {
                    it.remove();
                }
            }
        }
    }


    @Override
    public boolean onInteracted(Player pInteracted, PlayerInteractEvent e, int level) {
        return false;
    }

    @Override
    public boolean onDamaged(Player pDamaged, EntityDamageEvent e, int level) {
        return false;
    }

    @Override
    public boolean onDamagedOther(Player pAttacker, EntityDamageByEntityEvent e, int level) {
        return false;
    }

    @Override
    public boolean onInteractEntity(Player pInteracted, PlayerInteractEntityEvent e, int level) {
        return false;
    }

    @Override
    public boolean onLaunchProjectile(Player shooter, EntityShootBowEvent e, int level) {
        return false;
    }

    @Override
    public boolean onArrowHitEntity(Player shooter, EntityDamageByEntityEvent e, int level) {
        return false;
    }

    @Override
    public boolean onArrowHitGround(Player shooter, ProjectileHitEvent e, int level) {
        return false;
    }

    @Override
    public void applyEffectToPlayer(Player p, int level) {
    }

    @Override
    public void removeEffectFromPlayer(Player p) {
    }
}