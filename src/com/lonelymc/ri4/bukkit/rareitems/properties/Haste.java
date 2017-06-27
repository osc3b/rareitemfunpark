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

public class Haste extends RareItemProperty {
    private String HASTED = "You are hasted!";
    private String PLAYER_CAST_HASTE = "!player cast haste on you!";
    private String PLAYER_HASTED = "You cast haste on !player!";

    public Haste() {
        super(
                "Haste",
                "Allows you to run faster for 30 seconds / level",
                ItemPropertyRarity.UNCOMMON,
                PropertyCostType.EXPERIENCE,
                5,//cost
                5// max level
        );
    }

    @Override
    public YamlConfiguration getConfig(YamlConfiguration config){
        this.getDefaultConfig(config);

        config.set("messages.hasted", this.HASTED);
        config.set("messages.castHaste",this.PLAYER_CAST_HASTE);
        config.set("messages.playerHasted",this.PLAYER_HASTED);

        return config;
    }

    @Override
    public void setConfig(YamlConfiguration config){
        this.setDefaultConfig(config);

        this.HASTED = config.getString("messages.hasted",this.HASTED);
        this.PLAYER_CAST_HASTE = config.getString("messages.castHaste",this.PLAYER_CAST_HASTE);
        this.PLAYER_HASTED = config.getString("messages.playerHasted",this.PLAYER_HASTED);
    }
    
    @Override
    public boolean onInteracted(Player pInteracted, PlayerInteractEvent e, int level) {
        e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 600 * level, level));

        e.getPlayer().sendMessage(this.HASTED);

        return true;
    }

    @Override
    public boolean onInteractEntity(Player pInteracted, PlayerInteractEntityEvent e, int level) {
        if ((e.getRightClicked() instanceof LivingEntity)) {
            LivingEntity le = (LivingEntity) e.getRightClicked();

            le.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 600 * level, level));

            e.getPlayer().sendMessage(PLAYER_HASTED.replace("!player",le.getName()));

            le.sendMessage(this.PLAYER_CAST_HASTE.replace("!player", pInteracted.getName()));

            return true;
        }
        return false;
    }
}