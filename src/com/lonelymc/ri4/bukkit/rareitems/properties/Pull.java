package com.lonelymc.ri4.bukkit.rareitems.properties;

import com.lonelymc.ri4.bukkit.rareitems.RareItemProperty;
import com.lonelymc.ri4.api.ItemPropertyRarity;
import com.lonelymc.ri4.api.PropertyCostType;

import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Pull extends RareItemProperty {
    private String PULLED = "!player pulled you towards them!";

    public Pull() {
        super(
                "Pull",
                "Pulls a clicked/hit with an arrow target towards you",
                ItemPropertyRarity.UNCOMMON,
                PropertyCostType.FOOD,
                1.0D,
                1
        );
    }

    @Override
    public YamlConfiguration getConfig(YamlConfiguration config){
        this.getDefaultConfig(config);

        config.set("messages.pulled", this.PULLED);

        return config;
    }

    @Override
    public void setConfig(YamlConfiguration config){
        this.setDefaultConfig(config);

        this.PULLED = config.getString("messages.pulled",this.PULLED);
    }
    
    @Override
    public boolean onDamagedOther(Player pAttacker, EntityDamageByEntityEvent e, int level) {
        if ((e.getEntity() instanceof LivingEntity)) {
            if ((e.getEntity() instanceof Player)) {
                Player attacked = (Player) e.getEntity();

                attacked.sendMessage(PULLED.replace("!player",pAttacker.getName()));
            }
            int randomNum = ThreadLocalRandom.current().nextInt(1, 4 + 1);
            if(randomNum==1){
	            e.getEntity().teleport(pAttacker);
	            return true;
            }
        }
        return false;
    }

    @Override
    public boolean onArrowHitEntity(Player shooter, EntityDamageByEntityEvent e, int level) {
        return onDamagedOther(shooter,e,level);
    }
}