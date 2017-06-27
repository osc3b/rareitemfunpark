package com.lonelymc.ri4.bukkit.rareitems.properties;

import com.lonelymc.ri4.api.ItemPropertyRarity;
import com.lonelymc.ri4.api.PropertyCostType;
import com.lonelymc.ri4.bukkit.rareitems.RareItemProperty;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class Poison extends RareItemProperty {
    private String POISON = "Poisoned!";
    private String POISONED;

    public Poison() {
        super(
                "Poison",
                "3% chance/level to poison an enemy onhit",
                ItemPropertyRarity.UNCOMMON,
                PropertyCostType.FOOD,
                2.0D,
                4,
                new String[]{
                        "type=POTION;dura=16420;",
                        "type=POISONOUS_POTATO;",
                        "type=POTION;dura=16420;",
                        "type=POISONOUS_POTATO;",
                        "!UNCOMMON_ESSENCE",
                        "type=POISONOUS_POTATO;",
                        "type=POTION;dura=16420;",
                        "type=POISONOUS_POTATO;",
                        "type=POTION;dura=16420;"
                }
        );
    }

    @Override
    public YamlConfiguration getConfig(YamlConfiguration config){
        this.getDefaultConfig(config);

        config.set("messages.poison", this.POISON);
        config.set("messages.poisoned",this.POISONED);

        return config;
    }

    @Override
    public void setConfig(YamlConfiguration config){
        this.setDefaultConfig(config);

        this.POISON = config.getString("messages.poison",this.POISON);
        this.POISONED = config.getString("messages.poisoned",this.POISONED);
    }
    
    @Override
    public boolean onDamagedOther(Player attacker, EntityDamageByEntityEvent e, int level) {
        if ((new Random().nextInt(100) < 3 * level) &&
                ((e.getEntity() instanceof LivingEntity))) {
            LivingEntity le = (LivingEntity) e.getEntity();

            le.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 180, 1 * level));

            attacker.sendMessage(this.POISON);

            if ((e.getEntity() instanceof Player)) {
                ((Player) e.getEntity()).sendMessage(this.POISONED);
            }

            return true;
        }
        return false;
    }

    @Override
    public boolean onArrowHitEntity(Player shooter, EntityDamageByEntityEvent e, int level) {
        return onDamagedOther(shooter, e, level);
    }
}