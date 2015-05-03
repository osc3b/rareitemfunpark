package com.minecraftfunpark.rareitemhunter.property.spell;

import com.minecraftfunpark.rareitemhunter.property.ItemProperty;
import com.minecraftfunpark.rareitemhunter.property.ItemPropertyTypes;
import com.minecraftfunpark.rareitemhunter.property.PropertyManager;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CatsFeet extends ItemProperty
{
    private final PropertyManager propertyManager;
    public CatsFeet(PropertyManager propertyManager)
    {
        super(ItemPropertyTypes.SPELL,"Cat's Feet","Lets you or a clicked target jump much higher for 60 seconds / lvl",8,4);
        
        this.propertyManager = propertyManager;
    }
    
    @Override
    public boolean onInteract(PlayerInteractEvent e,int level)
    {
        e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.JUMP,20*60*level,level));
        
        e.getPlayer().sendMessage("You can jump higher!");
            
        propertyManager.addTemporaryEffect(e.getPlayer(),this,level,20*60*level);
        
        return true;
    }    
    
    @Override
    public boolean onInteractEntity(PlayerInteractEntityEvent e, int level)
    {
        if(e.getRightClicked() instanceof LivingEntity)
        {
            int duration = 20*60*level;
        
            LivingEntity le = (LivingEntity) e.getRightClicked();

            le.addPotionEffect(new PotionEffect(PotionEffectType.JUMP,duration,level));
                    
            if(le instanceof Player)
            {
                e.getPlayer().sendMessage("You cast Cat's Feet on "+((Player) le).getName()+"!");
                ((Player) le).sendMessage(e.getPlayer().getName()+" cast Cat's Feet on you!");
            }
            else
            {
                e.getPlayer().sendMessage("You cast Cat's Feet on that thing!");
            }
            
            this.propertyManager.addTemporaryEffect(((Player) le),this,level,duration);
            
            return true;
        }
        return false;
    }
}