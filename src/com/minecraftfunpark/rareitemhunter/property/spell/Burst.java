package com.minecraftfunpark.rareitemhunter.property.spell;

import com.minecraftfunpark.rareitemhunter.property.ItemProperty;
import com.minecraftfunpark.rareitemhunter.property.ItemPropertyTypes;
import com.minecraftfunpark.util.FireworkVisualEffect;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

public class Burst extends ItemProperty
{
    private FireworkVisualEffect fireworks;
    
    public Burst(FireworkVisualEffect fireworks)
    {
        super(ItemPropertyTypes.SPELL,"Burst","Pushes a clicked target away",5,2);
        
        this.fireworks = fireworks;
    }
    
    @Override
    public boolean onDamageOther(EntityDamageByEntityEvent e, Player p, int level)
    {
        if(e.getEntity() instanceof LivingEntity)
        {        
            LivingEntity le = (LivingEntity) e.getEntity();
            
            try
            {
                fireworks.playFirework(
                    le.getWorld(), le.getLocation(),
                    FireworkEffect
                        .builder()
                        .with(Type.BURST)
                        .withColor(Color.WHITE)
                        .build()
                );
            }
            catch (Exception ex)
            {
                Logger.getLogger(Burst.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            Vector unitVector = le.getLocation().toVector().subtract(e.getDamager().getLocation().toVector()).normalize();
            
            unitVector.setY(0.55/level);
            
            le.setVelocity(unitVector.multiply(level*2));
            
            e.setCancelled(true);
            
            return true;
        }
        return false;
    }
}