package com.minecraftfunpark.rareitemhunter.property.skill;

import com.connorlinfoot.bountifulapi.BountifulAPI;
import com.minecraftfunpark.rareitemhunter.property.ItemProperty;
import com.minecraftfunpark.rareitemhunter.property.ItemPropertyTypes;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Backstab extends ItemProperty
{
    public Backstab()
    {
        super(ItemPropertyTypes.SKILL,"Backstab","Deal extra damage if attacking an enemy from behind (damage * level)",8,2);
    }
    
    @Override
    public boolean onDamageOther(final EntityDamageByEntityEvent e,Player p,int level)
    {
        if(e.getEntity().getLocation().getDirection().dot(e.getDamager().getLocation().getDirection()) > 0.0D)
        {
            e.setDamage(e.getDamage() * level);
            
            p.sendMessage("Backstab!");
            BountifulAPI.sendActionBar(p,ChatColor.GREEN+"Backstab");
            
            if(e.getEntity() instanceof Player)
            {
                ((Player) e.getEntity()).sendMessage("Te han backstabbed!");
            }
            
            return true;
        }
        return false;
    }
}