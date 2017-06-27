package com.lonelymc.ri4.bukkit.rareitems.properties;


import com.lonelymc.ri4.api.ItemPropertyRarity;
import com.lonelymc.ri4.api.PropertyCostType;
import com.lonelymc.ri4.bukkit.rareitems.RareItemProperty;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Random;

public class VampiricRegeneration extends RareItemProperty {
    private String STOLEN_GAINER = "Stole !hp hp!";
    private String STOLEN_LOSER = "!hp was stolen from you!";

    public VampiricRegeneration() {
        super(
                "Vampiric Regeneration",
                "5% chance/level to steal 1-5HP from an enemy",
                ItemPropertyRarity.RARE,
                PropertyCostType.FOOD,
                1.0D,
                5
        );
    }

    @Override
    public YamlConfiguration getConfig(YamlConfiguration config){
        this.getDefaultConfig(config);

        config.set("messages.stolehp_gainer", this.STOLEN_GAINER);
        config.set("messages.stolehp_loser",this.STOLEN_LOSER);

        return config;
    }

    @Override
    public void setConfig(YamlConfiguration config){
        this.setDefaultConfig(config);

        this.STOLEN_GAINER = config.getString("messages.stolehp_gainer",this.STOLEN_GAINER);
        this.STOLEN_LOSER = config.getString("messages.stolehp_loser",this.STOLEN_LOSER);
    }

    @Override
    public boolean onDamagedOther(Player pAttacker, EntityDamageByEntityEvent e, int level) {
        Random random = new Random();

        if (e.getEntity() instanceof LivingEntity
                && random.nextInt(100) < 5 * level) {

            LivingEntity attacked = (LivingEntity) e.getEntity();

            int hpToSteal = random.nextInt(4) + 1;

            double attackerNewHP = attacked.getHealth() + hpToSteal;
            double attackedNewHP = pAttacker.getHealth() - hpToSteal;

            if (attackedNewHP < 2.0D) {//1 heart
                return false;
            }

            if (attackerNewHP > pAttacker.getMaxHealth()) {
                attackerNewHP = pAttacker.getMaxHealth();
            }

            attacked.setHealth(attackedNewHP);
            pAttacker.setHealth(attackerNewHP);

            pAttacker.sendMessage(this.STOLEN_GAINER.replace("!hp", String.valueOf(hpToSteal)));
            pAttacker.sendMessage(this.STOLEN_LOSER.replace("!hp", String.valueOf(hpToSteal)));

            return true;
        }
        return false;
    }
}
