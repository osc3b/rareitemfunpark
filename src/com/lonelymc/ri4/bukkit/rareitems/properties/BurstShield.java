package com.lonelymc.ri4.bukkit.rareitems.properties;

import com.lonelymc.ri4.bukkit.rareitems.RareItemProperty;
import com.lonelymc.ri4.api.ItemPropertyRarity;
import com.lonelymc.ri4.api.PropertyCostType;
import com.lonelymc.ri4.util.FireworkVisualEffect;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.Vector;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BurstShield extends RareItemProperty {
    private final FireworkVisualEffect fireworks;
    private String BURSTER = "Burst shield activated!";
    private String BURSTED = "You were knocked back by burst shield!";

    public BurstShield() {
        super(
                "Burst Shield",
                "While worn on adds 10% chance per level to send an enemy flying away",
                ItemPropertyRarity.LEGENDARY,
                PropertyCostType.FOOD,
                2, //cost
                3  //max level
        );

        this.fireworks = new FireworkVisualEffect();
    }

    @Override
    public YamlConfiguration getConfig(YamlConfiguration config){
        this.getDefaultConfig(config);

        config.set("messages.burster", this.BURSTER);
        config.set("messages.bursted",this.BURSTED);

        return config;
    }

    @Override
    public void setConfig(YamlConfiguration config){
        this.setDefaultConfig(config);

        this.BURSTER = config.getString("messages.burster",this.BURSTER);
        this.BURSTED = config.getString("messages.bursted",this.BURSTED);
    }
    
    @Override
    public boolean onDamaged(Player pDamaged, EntityDamageEvent e, int level){
        if(!(e instanceof EntityDamageByEntityEvent)){
            return false;
        }

        EntityDamageByEntityEvent ed = (EntityDamageByEntityEvent) e;

        Entity eAttacker = ed.getDamager();
        
        if(!(eAttacker instanceof LivingEntity)){
           return false;
        }

        if(new Random().nextInt(100) > level * 10){
            return false;
        }

        try {
            this.fireworks.playFirework(eAttacker
                            .getWorld(), eAttacker.getLocation(),

                    FireworkEffect.builder()
                            .with(FireworkEffect.Type.BURST)
                            .withColor(Color.WHITE)
                            .build());
        } catch (Exception ex) {
            Logger.getLogger(BurstShield.class.getName()).log(Level.SEVERE, null, ex);
        }

        Vector unitVector = eAttacker.getLocation().toVector().subtract(pDamaged.getLocation().toVector()).normalize();

        unitVector.setY(0.55D / level);

        eAttacker.setVelocity(unitVector.multiply(level * 2));

        e.setCancelled(true);

        eAttacker.sendMessage(this.BURSTED);

        pDamaged.sendMessage(this.BURSTER);

        return true;
    }
}