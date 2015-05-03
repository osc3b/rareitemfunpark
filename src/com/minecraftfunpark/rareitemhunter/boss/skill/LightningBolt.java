package com.minecraftfunpark.rareitemhunter.boss.skill;

import com.minecraftfunpark.rareitemhunter.boss.Boss;
import com.minecraftfunpark.rareitemhunter.boss.BossSkill;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class LightningBolt extends BossSkill
{
    public LightningBolt()
    {
        super("Lightning Bolt");
    }
    
    @Override
    public boolean activateSkill(Boss boss,EntityDamageByEntityEvent e, Entity eAttacker, int level)
    {       
        if(!(eAttacker instanceof Player))
        {
            return false;
        }
        
        eAttacker.getWorld().strikeLightning(eAttacker.getLocation());
        
        return true;
    }
}
