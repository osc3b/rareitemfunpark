package com.minecraftfunpark.rareitemhunter.boss;

import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class BossSkill
{    
    String name;
    
    public BossSkill(String name)
    {
        this.name = name;
    }
    
    public boolean activateSkill(Boss boss, EntityDamageByEntityEvent e, Entity eAttacker, int level)
    {
        return false;
    }

    public String getName()
    {
        return this.name;
    }

    public String getYmlName()
    {
        return this.name.replace(" ","");
    }
}
