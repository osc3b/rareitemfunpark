package com.lonelymc.ri4.bukkit.rareitems.properties;

import com.lonelymc.ri4.api.ItemPropertyRarity;
import com.lonelymc.ri4.api.PropertyCostType;
import com.lonelymc.ri4.bukkit.rareitems.RareItemProperty;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class Confuse extends RareItemProperty {
    private String CONFUSE = "You confused !player!";
    private String CONFUSED = "You have been confused!";

    public Confuse() {
        super(
                "Confuse",
                "5% chance / level on hit to severely confuse a target for 5 seconds",
                ItemPropertyRarity.UNCOMMON,
                PropertyCostType.FOOD,
                1,
                4
        );
    }

    @Override
    public YamlConfiguration getConfig(YamlConfiguration config) {
        this.getDefaultConfig(config);

        config.set("messages.confuse", this.CONFUSE);
        config.set("messages.confused", this.CONFUSED);

        return config;
    }

    @Override
    public void setConfig(YamlConfiguration config) {
        this.setDefaultConfig(config);

        this.CONFUSE = config.getString("messages.confuse", this.CONFUSE);
        this.CONFUSED = config.getString("messages.confused", this.CONFUSED);
    }

    @Override
    public boolean onDamagedOther(Player pAttacker, EntityDamageByEntityEvent e, int level) {
        if ((e.getEntity() instanceof Player) && new Random().nextInt(20) > level) {
            Player pAttacked = (Player) e.getEntity();

            pAttacked.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 100, level));

            pAttacker.sendMessage(this.CONFUSE.replace("!player", pAttacked.getName()));

            pAttacked.sendMessage(this.CONFUSED);

            return true;
        }
        return false;
    }

    @Override
    public boolean onArrowHitEntity(Player shooter, EntityDamageByEntityEvent e, int level) {
        return onDamagedOther(shooter, e, level);
    }
}