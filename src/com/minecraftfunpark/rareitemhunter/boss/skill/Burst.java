package com.minecraftfunpark.rareitemhunter.boss.skill;

import com.minecraftfunpark.rareitemhunter.boss.Boss;
import com.minecraftfunpark.rareitemhunter.boss.BossSkill;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

public class Burst extends BossSkill
{
    public Burst()
    {
        super("Burst");
    }
    
    @Override
    public boolean activateSkill(Boss boss,EntityDamageByEntityEvent e, Entity eAttacker, int level)
    {       
        if(e.getEntity() instanceof LivingEntity)
        {        
            LivingEntity le = (LivingEntity) eAttacker;
/*
            try
            {
                new FireworkVisualEffect().playFirework(
                    le.getWorld(), le.getLocation(),
                    FireworkEffect
                        .builder()
                        .with(FireworkEffect.Type.BURST)
                        .withColor(Color.WHITE)
                        .build()
                );
            }
            catch (Exception ex)
            {
                Logger.getLogger(Boss.class.getName()).log(Level.SEVERE, null, ex);
            }
*/
            Vector unitVector = eAttacker.getLocation().toVector().subtract(le.getLocation().toVector()).normalize();

            unitVector.setY(0.55/level);

            le.setVelocity(unitVector.multiply(2 * level));

            return true;
        }
        return false;
    }
}
