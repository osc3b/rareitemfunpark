package com.minecraftfunpark.rareitemhunter.property.skill;

import com.minecraftfunpark.rareitemhunter.property.ItemProperty;
import com.minecraftfunpark.rareitemhunter.property.ItemPropertyTypes;
import java.util.Random;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class CallLightning extends ItemProperty
{
    public CallLightning()
    {
        super(ItemPropertyTypes.SKILL,"Call Lightning","10% chance to strike an opponent with lightning per level",5,4);
    }
    
    @Override
    public boolean onDamageOther(final EntityDamageByEntityEvent e,Player p,int level)
    {
        if(new Random().nextInt(100) > level * 10
        && e.getEntity() instanceof LivingEntity)
        {
            int maxTargets = level * 2;
            int hitTargets = 0;
            
            Location l = e.getEntity().getLocation();

            l.getWorld().strikeLightningEffect(l);
            
            for(Entity ent : e.getEntity().getNearbyEntities(5, 5, 5))
            {                
                if(ent == p)
                {
                    continue;
                }
                
                if(hitTargets >= maxTargets) {
                    break;
                }
                
                if(ent instanceof LivingEntity)
                {
                    hitTargets++;
                    
                    LivingEntity lent = (LivingEntity) ent;
                    
                    //Emulate damaged by lightning
                    lent.damage(level*2, p);
                }
            }
                    
            return true;
        }
        return false;
    }
}