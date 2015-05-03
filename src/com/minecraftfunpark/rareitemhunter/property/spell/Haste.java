package com.minecraftfunpark.rareitemhunter.property.spell;

import com.minecraftfunpark.rareitemhunter.property.ItemProperty;
import com.minecraftfunpark.rareitemhunter.property.ItemPropertyTypes;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Haste extends ItemProperty
{    
    public Haste()
    {
        super(ItemPropertyTypes.SPELL,"Haste","Allows you to run faster for 30 seconds / lvl ",6,4); 
    }
    
    @Override
    public boolean onInteract(PlayerInteractEvent e,int level)
    {
        e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED,20*30,level));
        
        e.getPlayer().sendMessage("You cast "+this.getName().toLowerCase()+" on yourself!");
        
        return true;
    }
    
    @Override
    public boolean onInteractEntity(PlayerInteractEntityEvent e, int level)
    {
        if(e.getRightClicked() instanceof LivingEntity)
        {
            LivingEntity le = (LivingEntity) e.getRightClicked();

            le.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,20*60,level));
                    
            if(le instanceof Player)
            {
                e.getPlayer().sendMessage("You cast "+this.getName().toLowerCase()+" on "+((Player) le).getName().toLowerCase()+"!");
                ((Player) le).sendMessage(e.getPlayer().getName()+" cast "+this.getName()+" on you!");
            }
            else
            {
                e.getPlayer().sendMessage("You cast "+this.getName().toLowerCase()+" on that thing!");
            }
            
            return true;
        }
        return false;
    }
}