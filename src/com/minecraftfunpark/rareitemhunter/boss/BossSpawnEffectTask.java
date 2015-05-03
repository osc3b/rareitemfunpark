package com.minecraftfunpark.rareitemhunter.boss;

import com.minecraftfunpark.rareitemhunter.RareItemHunter;
import com.minecraftfunpark.util.FireworkVisualEffect;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;

public class BossSpawnEffectTask implements Runnable
{
    private final Location l;
    private int timesRan = 0;
    private final RareItemHunter plugin;
    private int taskId;
    private final FireworkVisualEffect fireworks;
    
    public BossSpawnEffectTask(RareItemHunter plugin,Location l)
    {
        this.plugin = plugin;
        this.l = l;
        
        this.fireworks = new FireworkVisualEffect();
    }
        
    @Override
    public void run()
    {
        timesRan++;
        
        Location lFx = l;
        lFx.add(0, 2 * timesRan, 0);

        if(timesRan == 0)
        {
            
        }
        else if(timesRan == 5)
        {
            try
            {
                fireworks.playFirework(lFx.getWorld(),lFx,FireworkEffect
                    .builder()
                    .with(FireworkEffect.Type.CREEPER)
                    .withColor(Color.GREEN)
                    .build());
            }
            catch (Exception ex)
            {
                //Gracefully downgrade
            }
            
            plugin.bossManager.hatchBossAtLocation(l);
            
            this.cancel();
            
            return;
        }

        try
        {
            fireworks.playFirework(lFx.getWorld(),lFx,FireworkEffect
                .builder()
                .with(FireworkEffect.Type.BALL)
                .withColor(Color.PURPLE)
                .build());
        }
        catch (Exception ex)
        {
            //Gracefully downgrade
        }
    }

    void setTaskId(int taskId)
    {
        this.taskId = taskId;
    }
    
    void cancel()
    {
        plugin.getServer().getScheduler().cancelTask(taskId);
    }
}
