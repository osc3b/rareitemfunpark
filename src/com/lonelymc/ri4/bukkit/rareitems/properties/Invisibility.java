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

public class Invisibility extends RareItemProperty {
    private String YOU_ARE_INVISIBLE = "You are now invisible!";
    private String YOU_CAST_INVIS = "You cast invis on !player!";
    private String PLAYER_CAST_INVIS = "!player cast invis on you!";

    public Invisibility() {
        super(
                "Invisibility",
                "Invisibility for 60 seconds per level",
                ItemPropertyRarity.UNCOMMON,
                PropertyCostType.EXPERIENCE,
                15,
                8
        );
    }

    @Override
    public YamlConfiguration getConfig(YamlConfiguration config){
        this.getDefaultConfig(config);

        config.set("messages.invis", this.YOU_ARE_INVISIBLE);
        config.set("messages.cast_invis", this.YOU_CAST_INVIS);
        config.set("messages.invised", this.PLAYER_CAST_INVIS);

        return config;
    }

    @Override
    public void setConfig(YamlConfiguration config){
        this.setDefaultConfig(config);

        this.YOU_ARE_INVISIBLE = config.getString("messages.invis", this.YOU_ARE_INVISIBLE);
        this.YOU_CAST_INVIS = config.getString("messages.cast_invis", this.YOU_CAST_INVIS);
        this.PLAYER_CAST_INVIS = config.getString("messages.invised", this.PLAYER_CAST_INVIS);
    }
    
    @Override
    public boolean onInteracted(Player pInteracted, PlayerInteractEvent e, int level) {
        e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 1200 * level, 1));

        e.getPlayer().sendMessage(this.YOU_ARE_INVISIBLE);

        return true;
    }

    @Override
    public boolean onInteractEntity(Player pInteracted, PlayerInteractEntityEvent e, int level){
        if ((e.getRightClicked() instanceof LivingEntity)) {
            LivingEntity le = (LivingEntity) e.getRightClicked();

            le.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 1200 * level, 1));

            e.getPlayer().sendMessage(this.YOU_CAST_INVIS.replace("!player", le.getName()));

            le.sendMessage(this.PLAYER_CAST_INVIS.replace("!player",pInteracted.getName()));

            return true;
        }
        return false;
    }
}