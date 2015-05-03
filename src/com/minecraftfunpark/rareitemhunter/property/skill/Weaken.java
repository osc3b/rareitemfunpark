package com.minecraftfunpark.rareitemhunter.property.skill;

import com.minecraftfunpark.rareitemhunter.property.ItemProperty;
import com.minecraftfunpark.rareitemhunter.property.ItemPropertyTypes;
import java.util.Random;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Weaken extends ItemProperty
{
    public Weaken()
    {
        super(ItemPropertyTypes.SKILL,"Weaken","25% chance to weaken an enemy for 3 seconds/level",1,4);
    }
    
    @Override
    public boolean onDamageOther(final EntityDamageByEntityEvent e,Player p,int level)
    {
        if(new Random().nextInt(4) == 0
        && e.getEntity() instanceof LivingEntity)
        {
            LivingEntity le = (LivingEntity) e.getEntity();
            
            le.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS,3*20*level,1));

            p.sendMessage("Weakened!");

            if(e.getEntity() instanceof Player)
            {
                ((Player) e.getEntity()).sendMessage("You are weakened!");
            } 
            
            return true;
        }
        return false;
    }
}