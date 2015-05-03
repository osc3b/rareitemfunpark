package com.minecraftfunpark.rareitemhunter.boss.skill;

import com.minecraftfunpark.rareitemhunter.boss.Boss;
import com.minecraftfunpark.rareitemhunter.boss.BossSkill;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class LightningStorm extends BossSkill
{
    public LightningStorm()
    {
        super("Lightning Storm");
    }
    
    @Override
    public boolean activateSkill(Boss boss,EntityDamageByEntityEvent e, Entity eAttacker, int level)
    {     
        if(!(eAttacker instanceof Player))
        {
            return false;
        }
        
        int count = 0;
        
        for(Entity ent : e.getEntity().getNearbyEntities(20, 20, 20))
        {
            if(count < level)
            {
                if(ent instanceof Player)
                {
                    ent.getWorld().strikeLightning(ent.getLocation());
                    
                    count++;
                }
            }
            else
            {
                break;
            }
        }
        
        return true;
    }
}
