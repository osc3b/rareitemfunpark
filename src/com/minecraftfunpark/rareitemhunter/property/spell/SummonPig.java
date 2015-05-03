package com.minecraftfunpark.rareitemhunter.property.spell;

import com.minecraftfunpark.rareitemhunter.property.ItemProperty;
import com.minecraftfunpark.rareitemhunter.property.ItemPropertyTypes;
import org.bukkit.entity.EntityType;
import org.bukkit.event.player.PlayerInteractEvent;

public class SummonPig extends ItemProperty
{
    public SummonPig()
    {
        super(ItemPropertyTypes.SPELL,"Summon Pig","Creates one pig / level",1,12);
    }
    
    @Override
    public boolean onInteract(PlayerInteractEvent e,int level)
    {
        for(int i=0;i<level;i++)
        {
            e.getPlayer().getWorld().spawnEntity(e.getPlayer().getLocation(),EntityType.PIG);
        }
        
        return true;
    }
}