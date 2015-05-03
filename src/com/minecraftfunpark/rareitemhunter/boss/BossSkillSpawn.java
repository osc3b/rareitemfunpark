package com.minecraftfunpark.rareitemhunter.boss;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class BossSkillSpawn extends BossSkill
{
    private final EntityType entityType;
    
    public BossSkillSpawn(String sName,EntityType e)
    {
        super(sName);
        
        this.entityType = e;
    }

    @Override
    public boolean activateSkill(Boss boss,EntityDamageByEntityEvent e, Entity eAttacker, int level)
    {
        Location l = e.getEntity().getLocation();
        
        World w = l.getWorld();
        
        for(int i=0;i<level;i++)
        {
            w.spawnEntity(l, this.entityType);
        }
        
        return true;
    }
}
