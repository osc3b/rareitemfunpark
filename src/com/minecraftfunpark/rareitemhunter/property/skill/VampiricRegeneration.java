package com.minecraftfunpark.rareitemhunter.property.skill;

import com.minecraftfunpark.rareitemhunter.property.ItemProperty;
import com.minecraftfunpark.rareitemhunter.property.ItemPropertyTypes;
import java.util.Random;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class VampiricRegeneration extends ItemProperty
{
    public VampiricRegeneration()
    {
        super(ItemPropertyTypes.SKILL,"Vampiric Regeneration","20% chance to steal 1-(3*level) HP from an enemy",1,4);
    }
    
    @Override
    public boolean onDamageOther(final EntityDamageByEntityEvent e,Player p,int level)
    {
        if(new Random().nextInt(5) == 0
        && e.getEntity() instanceof LivingEntity && e.getDamager() instanceof LivingEntity)
        {
            LivingEntity attacked = (LivingEntity) e.getEntity();
            LivingEntity attacker = (LivingEntity) e.getDamager();
                    
            int iStolenHP = new Random().nextInt(3 * level)+1;

            double iNewAttackerHP = attacked.getHealth() - iStolenHP;
            
            if(iNewAttackerHP > 20)
            {
                iNewAttackerHP = 20;
            }
            
            double iNewAttackedHP = attacker.getHealth() + iStolenHP;
            
            if(iNewAttackedHP < 1)
            {
                iNewAttackerHP = 1;
            }
            
            attacked.setHealth(iNewAttackedHP);
            attacker.setHealth(iNewAttackerHP);
            
            p.sendMessage(ChatColor.RED+"You stole "+iStolenHP+"HP!");
            
            if(attacked instanceof Player)
            {
                Player pAttacked = (Player) attacked;
                pAttacked.sendMessage(ChatColor.RED+p.getName()+" stole "+iStolenHP+"HP from you!");
            }
            
            return true;
        }
        
        return false;
    }
}