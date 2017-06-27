package com.lonelymc.ri4.bukkit.rareitems.properties;

import com.lonelymc.ri4.api.ItemPropertyRarity;
import com.lonelymc.ri4.api.PropertyCostType;
import com.lonelymc.ri4.bukkit.rareitems.RareItemProperty;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Backstab extends RareItemProperty {
    private String BACKSTAB_MSG = "Backstab!";
    private String BACKSTABBED_MSG = "You were backstabbed!";

    public Backstab() {
        super(
                "Backstab", //Name (.toLowerCase() used as ID)
                "Deal extra damage if attacking an enemy from behind (x2 at level 1, +50% damage for every level after that)",// Description
                ItemPropertyRarity.RARE,
                PropertyCostType.FOOD, //Cost type
                2.0D, // Default cost
                8,   // Max level
                new String[]{
                        "type=DEAD_BUSH;",
                        "type=ENDER_PORTAL_FRAME;",
                        "type=DEAD_BUSH;",
                        "type=ENDER_PORTAL_FRAME;",
                        "!RARE_ESSENCE",
                        "type=ENDER_PORTAL_FRAME;",
                        "type=DEAD_BUSH;",
                        "type=ENDER_PORTAL_FRAME;",
                        "type=DEAD_BUSH;"
                }
        );
    }

    @Override
    public YamlConfiguration getConfig(YamlConfiguration config){
        this.getDefaultConfig(config);

        config.set("messages.backstab", this.BACKSTAB_MSG);
        config.set("messages.backstabbed",this.BACKSTABBED_MSG);

        return config;
    }

    @Override
    public void setConfig(YamlConfiguration config){
        this.setDefaultConfig(config);

        this.BACKSTAB_MSG = config.getString("messages.backstab",this.BACKSTAB_MSG);
        this.BACKSTABBED_MSG = config.getString("messages.backstabbed",this.BACKSTABBED_MSG);
    }

    @Override
    public boolean onDamagedOther(Player p, EntityDamageByEntityEvent e, int level) {
        if (e.getEntity().getLocation().getDirection().dot(e.getDamager().getLocation().getDirection()) > 0.0D) {
            double backstabDamage = e.getDamage() * 2;

            if(level > 2){
                backstabDamage += e.getDamage() * 0.50 * (double) level;
            }

            e.setDamage(backstabDamage);

            p.sendMessage(this.BACKSTAB_MSG);

            if ((e.getEntity() instanceof Player)) {
                ((Player) e.getEntity()).sendMessage(this.BACKSTABBED_MSG);
            }

            return true;
        }
        return false;
    }

    @Override
    public boolean onArrowHitEntity(Player shooter, EntityDamageByEntityEvent e, int level) {
        return onDamagedOther(shooter,e,level);
    }
}