package com.minecraftfunpark.rareitemhunter.boss.skill;

import com.minecraftfunpark.rareitemhunter.boss.Boss;
import com.minecraftfunpark.rareitemhunter.boss.BossSkill;
import com.minecraftfunpark.util.FireworkVisualEffect;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

public class GreaterBurst extends BossSkill
{
    public GreaterBurst()
    {
        super("Greater Burst");
    }
    
    @Override
    public boolean activateSkill(Boss boss,EntityDamageByEntityEvent e, Entity eAttacker, int level)
    {       
        List<Entity> nearbyEntities = e.getEntity().getNearbyEntities(8, 8, 8);
        
        if(!nearbyEntities.isEmpty())
        {
            boolean showFx = false;
            Vector vPlayer = e.getEntity().getVelocity();
            
            for(Entity ent : nearbyEntities)
            {
                if(ent instanceof LivingEntity)
                {
                    Vector unitVector = ent.getLocation().toVector().subtract(vPlayer).normalize();

                    unitVector.setY(0.55/level);

                    ent.setVelocity(unitVector.multiply(level*2));
                    
                    showFx = true;
                }
            }
            
            if(showFx)
            {/* was causing lag
                try
                {
                    new FireworkVisualEffect().playFirework(
                        e.getEntity().getWorld(), e.getEntity().getLocation(),
                        FireworkEffect
                            .builder()
                            .with(FireworkEffect.Type.BALL_LARGE)
                            .withColor(Color.WHITE)
                            .build()
                    );
                }
                catch (Exception ex)
                {
                    Logger.getLogger(GreaterBurst.class.getName()).log(Level.SEVERE, null, ex);
                }*/
            }
            else
            {
                return false;
            }
            
            return true;
        }
        
        return false;
    }
}
