package com.minecraftfunpark.rareitemhunter.boss.skill;

import com.minecraftfunpark.rareitemhunter.boss.Boss;
import com.minecraftfunpark.rareitemhunter.boss.BossSkill;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Disorient extends BossSkill
{
    public Disorient()
    {
        super("Disorient");
    }
    
    @Override
    public boolean activateSkill(Boss boss,EntityDamageByEntityEvent e, Entity eAttacker, int level)
    {       
        if(e.getEntity() instanceof LivingEntity)
        {        
            LivingEntity le = (LivingEntity) eAttacker;

            le.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION,20*20,level));
            le.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,20*25,level));

            return true;
        }
        return false;
    }
}
