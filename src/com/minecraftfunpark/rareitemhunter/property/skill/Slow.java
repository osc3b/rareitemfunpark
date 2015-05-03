package com.minecraftfunpark.rareitemhunter.property.skill;

import com.minecraftfunpark.rareitemhunter.property.ItemProperty;
import com.minecraftfunpark.rareitemhunter.property.ItemPropertyTypes;
import java.util.Random;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Slow extends ItemProperty
{
    public Slow()
    {
        super(ItemPropertyTypes.SKILL,"Slow","25% chance to slow an attacked enemy",1,2);
    }
    
    @Override
    public boolean onDamageOther(final EntityDamageByEntityEvent e,Player p,int level)
    {
        if(new Random().nextInt(4) == 0
        && e.getEntity() instanceof LivingEntity)
        {
            LivingEntity le = (LivingEntity) e.getEntity();
            
            le.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,60,1*level));

            p.sendMessage("Slowed!");

            if(e.getEntity() instanceof Player)
            {
                ((Player) e.getEntity()).sendMessage("You are slowed!");
            } 
            
            return true;
        }
        
        return false;
    }
}