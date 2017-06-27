package com.lonelymc.ri4.bukkit.rareitems.properties;

import com.avaje.ebean.enhance.ant.StringReplace;
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

public class FireResistance extends RareItemProperty {
    private String UPPED_FIRE_RESIS = "You upped your fire resistance!";
    private String YOU_CAST_FIRE_RESIS = "You cast fire resistance on !player!";
    private String CAST_FIRE_RESIS = "!player cast fire resistance on you!";

    public FireResistance() {
        super(
                "Fire Resistance",
                "Grants you or a clicked target 20% fire resistance per level for 60 seconds",
                ItemPropertyRarity.UNCOMMON,
                PropertyCostType.EXPERIENCE,
                20,
                5
        );
    }

    @Override
    public YamlConfiguration getConfig(YamlConfiguration config){
        this.getDefaultConfig(config);

        config.set("messages.uppedFireResis", this.UPPED_FIRE_RESIS);
        config.set("messages.youCastFireResis", this.YOU_CAST_FIRE_RESIS);
        config.set("messages.castFireResis", this.CAST_FIRE_RESIS);

        return config;
    }

    @Override
    public void setConfig(YamlConfiguration config){
        this.setDefaultConfig(config);

        this.UPPED_FIRE_RESIS = config.getString("messages.uppedFireResis",this.UPPED_FIRE_RESIS);
        this.YOU_CAST_FIRE_RESIS = config.getString("messages.youCastFireResis",this.YOU_CAST_FIRE_RESIS);
        this.CAST_FIRE_RESIS = config.getString("messages.castFireResis",this.CAST_FIRE_RESIS);
    }
    
    @Override
    public boolean onInteracted(Player pInteracted, PlayerInteractEvent e, int level) {
        e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 1200, 20 * level));

        e.getPlayer().sendMessage(this.UPPED_FIRE_RESIS);

        return true;
    }

    @Override
    public boolean onInteractEntity(Player pInteracted, PlayerInteractEntityEvent e, int level) {
        if ((e.getRightClicked() instanceof LivingEntity)) {
            LivingEntity le = (LivingEntity) e.getRightClicked();

            le.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 1200, 20 * level));

                e.getPlayer().sendMessage(this.YOU_CAST_FIRE_RESIS.replace("!player",pInteracted.getName()));

                le.sendMessage(this.CAST_FIRE_RESIS.replace("!player",le.getName()));

            return true;
        }
        return false;
    }
}