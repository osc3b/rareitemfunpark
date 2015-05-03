package com.minecraftfunpark.rareitemhunter.listener;

import com.minecraftfunpark.rareitemhunter.RareItemHunter;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

public class RareItemHunterBlockListener implements Listener
{
    private RareItemHunter plugin;

    public RareItemHunterBlockListener(RareItemHunter p)
    {
        this.plugin = p;
    }

    @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
    public void onEntityExplode(EntityExplodeEvent e)
    {
        for(Block b : e.blockList())
        {
            if(plugin.bossManager.isBossEgg(b))
            {
                plugin.bossManager.removeBossEggAtLocation(b.getLocation());
            }
        }
    }
    
    @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
    public void onPistonExtend(BlockPistonExtendEvent e)
    {
        BlockFace bf = e.getDirection();

        for(int i=1;i<=12;i++)
        {
            if(plugin.bossManager.isBossEgg(e.getBlock().getRelative(bf,i)))
            {
                e.setCancelled(true);

                break;
            }
        }
    }
    
    
}