package com.lonelymc.ri4.bukkit.rareitems.properties;

import com.lonelymc.ri4.bukkit.rareitems.RareItemProperty;
import com.lonelymc.ri4.api.ItemPropertyRarity;
import com.lonelymc.ri4.api.PropertyCostType;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CatsFeet extends RareItemProperty {
    private String YOU_JUMP_HIGHER = "You can jump higher!";
    private String YOU_CAST_ON = "You cast Cat's Feet on !player!";
    private String CASTED_ON = "!player cast Cat's Feet on you!";

    public CatsFeet() {
        super(
                "Cat's Feet",
                "Lets you or a clicked target jump higher for 60 seconds per level",
                ItemPropertyRarity.UNCOMMON,
                PropertyCostType.EXPERIENCE,
                30.0,
                5,
                new String[]{
                    "type=POTION;dura=8258;", 
                    "type=RABBIT_FOOT;", 
                    "type=POTION;dura=8258;", 
                    "type=RABBIT_FOOT;", 
                    "!UNCOMMON_ESSENCE", 
                    "type=RABBIT_FOOT;", 
                    "type=POTION;dura=8258;", 
                    "type=RABBIT_FOOT;", 
                    "type=POTION;dura=8258;"
                }
        );
    }

    @Override
    public YamlConfiguration getConfig(YamlConfiguration config){
        this.getDefaultConfig(config);

        config.set("messages.jumphigher", this.YOU_JUMP_HIGHER);
        config.set("messages.youCastOn",this.YOU_CAST_ON);
        config.set("messages.castedOn",this.CASTED_ON);

        return config;
    }

    @Override
    public void setConfig(YamlConfiguration config){
        this.setDefaultConfig(config);

        this.YOU_JUMP_HIGHER = config.getString("messages.jumphigher",this.YOU_JUMP_HIGHER);
        this.YOU_CAST_ON = config.getString("messages.youCastOn",this.YOU_CAST_ON);
        this.CASTED_ON = config.getString("messages.castedOn",this.CASTED_ON);
    }
    
    @Override
    public boolean onInteracted(Player pInteracted, PlayerInteractEvent e, int level) {
        e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 1200 * level, level));

        e.getPlayer().sendMessage(this.YOU_JUMP_HIGHER);

        return true;
    }

    @Override
    public boolean onInteractEntity(Player pInteracted, PlayerInteractEntityEvent e, int level) {
        if ((e.getRightClicked() instanceof LivingEntity)) {
            int duration = 1200 * level;

            LivingEntity le = (LivingEntity) e.getRightClicked();

            le.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, duration, level));

            e.getPlayer().sendMessage(this.YOU_CAST_ON.replace("!player", le.getName()));
            le.sendMessage(this.CASTED_ON.replace("!player", e.getPlayer().getName()));

            return true;
        }
        return false;
    }
}