package com.lonelymc.ri4.bukkit.rareitems.properties;

import com.lonelymc.ri4.api.ItemPropertyRarity;
import com.lonelymc.ri4.api.PropertyCostType;
import com.lonelymc.ri4.bukkit.rareitems.RareItemProperty;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class Blink extends RareItemProperty {
    private final JavaPlugin plugin;
    private String ENTER_BLINK = "You blinked out of existence!";
    static String EXIT_BLINK = "You blinked back into existence!";// has to be used in the task
    private String TOO_SOON = "Cannot blink again for !seconds seconds!";
    private final HashMap<UUID, Long> lastUse;
    private long cooldownPeriod = 10;

    public Blink(JavaPlugin plugin) {
        super(
                "Blink", //Name (.toLowerCase() used as ID)
                "Enter spectator mode for 1 second / level",// Description
                ItemPropertyRarity.LEGENDARY,
                PropertyCostType.EXPERIENCE, //Cost type
                25, // Default cost
                3,   // Max level
                null
        );

        this.plugin = plugin;
        this.lastUse = new HashMap<UUID,Long>();
    }

    @Override
    public void refreshCooldowns() {
        long now = System.currentTimeMillis();
        Iterator it = this.lastUse.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<String, Long> next = (Map.Entry<String, Long>) it.next();

            if (next.getValue() < now) {
                it.remove();
            }
        }
    }

    public void takeCooldownCost(Player player, int level) {
        if(this.cooldownPeriod == 0){
            return;
        }

        this.lastUse.put(player.getUniqueId(), System.currentTimeMillis() + (this.cooldownPeriod) * 1000);
    }

    public boolean hasCooldownCost(Player player, int level) {
        if(this.cooldownPeriod == 0){
            return true;
        }

        Long cooldown = this.lastUse.get(player.getUniqueId());

        if (cooldown != null) {
            if (System.currentTimeMillis() < cooldown) {
                return false;
            }
        }

        return true;
    }

    @Override
    public YamlConfiguration getConfig(YamlConfiguration config){
        this.getDefaultConfig(config);

        config.set("cooldown", this.cooldownPeriod);

        config.set("messages.enterBlink", this.ENTER_BLINK);
        config.set("messages.exitBlink",this.EXIT_BLINK);
        config.set("messages.tooSoon",this.TOO_SOON);

        return config;
    }

    @Override
    public void setConfig(YamlConfiguration config){
        this.setDefaultConfig(config);

        this.cooldownPeriod = config.getLong("cooldown",this.cooldownPeriod);

        this.ENTER_BLINK = config.getString("messages.enterBlink",this.ENTER_BLINK);
        this.EXIT_BLINK = config.getString("messages.exitBlink", this.EXIT_BLINK);
        this.TOO_SOON = config.getString("messages.tooSoon",this.TOO_SOON);
    }

    @Override
    public boolean onInteracted(Player pInteracted, PlayerInteractEvent e, int level) {
        if(!pInteracted.getGameMode().equals(GameMode.SPECTATOR)){
            if(!this.hasCooldownCost(pInteracted,level)){
                long secondsLeft = (this.lastUse.get(pInteracted.getUniqueId()) - System.currentTimeMillis()) / 1000;

                pInteracted.sendMessage(this.TOO_SOON.replace("!seconds",String.valueOf(secondsLeft)));

                return false;
            }

            this.takeCooldownCost(pInteracted,level);

            final UUID uuid = pInteracted.getUniqueId();
            final GameMode oldMode = pInteracted.getGameMode();

            pInteracted.sendMessage(this.ENTER_BLINK);

            pInteracted.setGameMode(GameMode.SPECTATOR);

            plugin.getServer().getScheduler().runTaskLater(this.plugin,new Runnable(){
                @Override
                public void run() {
                    Player player = Bukkit.getPlayer(uuid);

                    player.setGameMode(oldMode);

                    player.sendMessage(Blink.EXIT_BLINK);
                }
            },10 + level * 10);

            return true;
        }
        return false;
    }
}