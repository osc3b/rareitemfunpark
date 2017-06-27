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

public class Slow extends RareItemProperty {
    private String SLOWER = "Slowed!";
    private String SLOWED = "You are slowed!";

    public Slow() {
        super(
                "Slow",
                "3% chance/level to slow an attacked enemy",
                ItemPropertyRarity.UNCOMMON,
                PropertyCostType.FOOD,
                1.0D,
                8
        );
    }
    
    @Override
    public YamlConfiguration getConfig(YamlConfiguration config){
        this.getDefaultConfig(config);

        config.set("messages.slower", this.SLOWER);
        config.set("messages.slowed",this.SLOWED);

        return config;
    }

    @Override
    public void setConfig(YamlConfiguration config){
        this.setDefaultConfig(config);

        this.SLOWER = config.getString("messages.slower",this.SLOWER);
        this.SLOWED = config.getString("messages.slowed",this.SLOWED);
    }

    public boolean onDamageOther(EntityDamageByEntityEvent e, Player p, int level) {
        if ((new Random().nextInt(100) < level * 3) &&
                ((e.getEntity() instanceof LivingEntity))) {
            LivingEntity le = (LivingEntity) e.getEntity();

            le.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 60, level));

            p.sendMessage(this.SLOWER);
            if ((e.getEntity() instanceof Player)) {
                ((Player) e.getEntity()).sendMessage(this.SLOWED);
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