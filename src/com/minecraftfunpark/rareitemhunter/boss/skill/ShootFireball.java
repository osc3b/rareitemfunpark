package com.minecraftfunpark.rareitemhunter.boss.skill;

import com.minecraftfunpark.rareitemhunter.boss.Boss;
import com.minecraftfunpark.rareitemhunter.boss.BossSkill;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class ShootFireball extends BossSkill
{
    public ShootFireball()
    {
        super("Shoot Fireball");
    }
    
    @Override
    public boolean activateSkill(Boss boss,EntityDamageByEntityEvent e, Entity eAttacker, int level)
    {       
        LivingEntity leBoss = (LivingEntity) e.getEntity();   
        
        leBoss.launchProjectile(Fireball.class);
        
        return true;
    }
}
