package com.minecraftfunpark.rareitemhunter.property.ability;

import com.minecraftfunpark.rareitemhunter.RareItemHunter;
import com.minecraftfunpark.rareitemhunter.property.ItemPropertyRepeatingEffect;
import com.minecraftfunpark.rareitemhunter.property.ItemPropertyTypes;
import com.minecraftfunpark.rareitemhunter.property.PropertyManager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Fly extends ItemPropertyRepeatingEffect
{
    public int counter = 1;
    private PropertyManager propertyManager;
    
    public Fly(PropertyManager propertyManager)
    {
        super(ItemPropertyTypes.ABILITY,"Fly","Allows flight, cost taken while flying",5,0);
        
        this.propertyManager = propertyManager;
        
        this.createRepeatingAppliedEffect(this,20 * 5);
        
        final ItemPropertyRepeatingEffect ip = this;
        
        final PropertyManager pm = propertyManager;
        
        Bukkit.getScheduler().scheduleSyncRepeatingTask(RareItemHunter.self,  new Runnable()
        {
            @Override
            public void run()
            {
                for(String sPlayer :  ip.getActivePlayers().keySet())
                {
					Player p = Bukkit.getPlayer(sPlayer);
                    
                    // Cheap garbage collection
                    if(p == null)
                    {
                        ip.getActivePlayers().remove(sPlayer);
                        
                        continue;
                    }

                    if(p.isFlying() && !pm.hasCost(p, 1))
                    {
                        p.setFlying(false);
                    }
                }
            }
        }, 20, 20);
    }

    @Override
    public void onEquip(Player p,int level)
    {
        activePlayers.put(p.getName(), level);
        
        p.setAllowFlight(true);
    }

    @Override
    public void onUnequip(Player p,int level)
    {
        activePlayers.remove(p.getName());
        
        p.setFlying(false);
        p.setAllowFlight(false);
    }
    
    @Override
    public void applyEffectToPlayer(Player p, int level)
    {
        if(p.isFlying())
        {
            if(this.propertyManager.hasCost(p,level))
            { 
                if(level <= counter)
                {
                    this.propertyManager.takeCost(p, counter);
                }
            }
            else
            {
                p.setFlying(false);
            }
        }

        if(counter == 5)
        {
            counter = 1;
        }
        else 
        {
            counter++;
        }
    }    
}
