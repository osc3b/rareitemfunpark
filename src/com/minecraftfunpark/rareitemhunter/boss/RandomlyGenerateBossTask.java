package com.minecraftfunpark.rareitemhunter.boss;

import com.minecraftfunpark.rareitemhunter.RareItemHunter;
import java.util.Random;
import java.util.logging.Level;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class RandomlyGenerateBossTask implements Runnable 
{
    private final RareItemHunter plugin;
    private final int maxChance;
    private final int timer;
    private final int expiration;

    public RandomlyGenerateBossTask(RareItemHunter plugin,int maxChance,int timer,int expiration)
    {
        this.plugin = plugin;
        this.maxChance = maxChance;
        this.timer = timer;
        this.expiration = expiration;
    }

    @Override
    public void run() 
    {
        Random random = new Random();
        
        int iRoll = random.nextInt(plugin.getServer().getMaxPlayers());
        int iDifficulty = (int) (((float) plugin.getServer().getOnlinePlayers().size()) * (((float) maxChance) / 100f));

        if(iRoll < iDifficulty)
        {
            if(plugin.bossManager.hasSpawnPoints())
            {
                final BossEgg spawnedEgg = plugin.bossManager.spawnRandomBossEgg();
                
                if(spawnedEgg == null)
                {
                    //alerts on this issue are handled in plugin.bossManager.spawnRandomBossEgg
                    return;
                }

                for(Player player : spawnedEgg.getLocation().getWorld().getPlayers())
                {                
                    player.sendMessage(ChatColor.DARK_GREEN+"A legendary monster egg has appeared!");
                }
                
                plugin.getLogger().log(Level.INFO, "A legendary monster egg has been spawned at X:{0} Y:{1} Z:{2}]", new Object[]{
                    spawnedEgg.getLocation().getBlockX(), 
                    spawnedEgg.getLocation().getBlockX(), 
                    spawnedEgg.getLocation().getBlockX()}
                );
                
                plugin.getServer().getScheduler().runTaskLater(plugin, new Runnable(){
                    @Override
                    public void run()
                    {
                        plugin.bossManager.removeBossEgg(spawnedEgg);

                        for(Player player : plugin.getServer().getOnlinePlayers())
                        {
                            if(player.getCompassTarget().equals(spawnedEgg.getLocation()))
                            {
                                plugin.getServer().broadcastMessage(ChatColor.DARK_GREEN+"The egg you were tracking has mysteriously disappeared!");

                                player.setCompassTarget(player.getWorld().getSpawnLocation());
                            }
                        }
                    }
                },expiration);
            }
            else
            {
                plugin.getServer().broadcast(ChatColor.DARK_GREEN+"-------------- RareItemHunter ----------------", "rareitemhunter.admin.notify");
                plugin.getServer().broadcast(ChatColor.RED+"Tried to spawn a boss, but no boss spawn points are defined!", "rareitemhunter.admin.notify");
                plugin.getServer().broadcast("Use /ri spointpoint to add some points", "rareitemhunter.admin.notify");
            }
            
        }
    }
}
