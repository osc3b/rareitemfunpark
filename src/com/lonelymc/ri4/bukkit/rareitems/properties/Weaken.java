package com.lonelymc.ri4.bukkit.rareitems.properties;

import com.lonelymc.ri4.bukkit.rareitems.RareItemProperty;
import com.lonelymc.ri4.api.ItemPropertyRarity;
import com.lonelymc.ri4.api.PropertyCostType;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class Weaken extends RareItemProperty {
    private String WEAKEN_MSG = "Weakened!";
    private String WEAKENED_MSG = "You have been weakened!";

    public Weaken() {
        super(
                "Weaken",
                "25% chance to weaken an enemy for 3 seconds/level",
                ItemPropertyRarity.UNCOMMON,
                PropertyCostType.FOOD,
                1.0D,
                4
        );
    }

    @Override
    public YamlConfiguration getConfig(YamlConfiguration config){
        this.getDefaultConfig(config);

        config.set("messages.weaken", this.WEAKEN_MSG);
        config.set("messages.weakened",this.WEAKENED_MSG);

        return config;
    }

    @Override
    public void setConfig(YamlConfiguration config){
        this.setDefaultConfig(config);

        this.WEAKEN_MSG = config.getString("messages.backstab",this.WEAKEN_MSG);
        this.WEAKENED_MSG = config.getString("messages.backstabbed",this.WEAKENED_MSG);
    }

    @Override
    public boolean onDamagedOther(Player p, EntityDamageByEntityEvent e, int level) {
        if ((new Random().nextInt(4) == 0) &&
                ((e.getEntity() instanceof LivingEntity))) {
            LivingEntity le = (LivingEntity) e.getEntity();

            le.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 60 * level, 1));

            p.sendMessage(this.WEAKEN_MSG);
            if ((e.getEntity() instanceof Player)) {
                ((Player) e.getEntity()).sendMessage(this.WEAKENED_MSG);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean onArrowHitEntity(Player shooter, EntityDamageByEntityEvent e, int level) {
        return onDamagedOther(shooter,e,level);
    }
}