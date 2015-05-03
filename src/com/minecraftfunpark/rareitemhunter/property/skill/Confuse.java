package com.minecraftfunpark.rareitemhunter.property.skill;

import com.minecraftfunpark.rareitemhunter.property.ItemProperty;
import com.minecraftfunpark.rareitemhunter.property.ItemPropertyTypes;
import java.util.Random;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Confuse extends ItemProperty
{
    public Confuse()
    {
        super(ItemPropertyTypes.SKILL,"Confuse","25% chance on hit to confuse a target for 3 seconds / level",1,4);
    }
    
    @Override
    public boolean onDamageOther(final EntityDamageByEntityEvent e,Player p,int level)
    {
        if(new Random().nextInt(4) == 0
        && e.getEntity() instanceof LivingEntity)
        {
            LivingEntity le = (LivingEntity) e.getEntity();
            
            le.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION,60*level,1));
            
            p.sendMessage("Confused!");

            if(e.getEntity() instanceof Player)
            {
                ((Player) e.getEntity()).sendMessage("You are confused!");
            }
            
            return true;
        }
        
        return false;
    }
}