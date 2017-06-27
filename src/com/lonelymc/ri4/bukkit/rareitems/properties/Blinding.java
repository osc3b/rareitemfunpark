package com.lonelymc.ri4.bukkit.rareitems.properties;

import com.lonelymc.ri4.bukkit.rareitems.RareItemProperty;
import com.lonelymc.ri4.api.ItemPropertyRarity;
import com.lonelymc.ri4.api.PropertyCostType;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class Blinding extends RareItemProperty {
    private String BLINDED_BLINDER = "Blinded !player!";
    private String BLINDED_BLINDED = "You were blinded!";

    public Blinding() {
        super(
                "Blinding",
                "25% chance to blind a target onhit (3s / level)",
                ItemPropertyRarity.UNCOMMON,
                PropertyCostType.FOOD,
                2.0D,
                5
        );
    }

    @Override
    public YamlConfiguration getConfig(YamlConfiguration config){
        this.getDefaultConfig(config);

        config.set("messages.blinder", this.BLINDED_BLINDER);
        config.set("messages.blinded",this.BLINDED_BLINDED);

        return config;
    }

    @Override
    public void setConfig(YamlConfiguration config){
        this.setDefaultConfig(config);

        this.BLINDED_BLINDER = config.getString("messages.blinder",this.BLINDED_BLINDER);
        this.BLINDED_BLINDED = config.getString("messages.blinded",this.BLINDED_BLINDED);
    }
    
    @Override
    public boolean onDamaged(Player pDamaged, EntityDamageEvent e, int level) {
        if ((new Random().nextInt(4) == 0) &&
                ((e.getEntity() instanceof LivingEntity))) {
            LivingEntity le = (LivingEntity) e.getEntity();

            le.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 3 * level, 2));

            pDamaged.sendMessage(this.BLINDED_BLINDER.replace("!player",le.getName()));

            le.sendMessage(this.BLINDED_BLINDED);

            return true;
        }
        return false;
    }
}