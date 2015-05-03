package com.minecraftfunpark.rareitemhunter.property.enchantment;

import com.minecraftfunpark.rareitemhunter.property.ItemProperty;
import com.minecraftfunpark.rareitemhunter.property.ItemPropertyTypes;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;

public class Fertilize extends ItemProperty
{
    public Fertilize()
    {
        super(ItemPropertyTypes.ENCHANTMENT,"Fertilize","Turns clicked dirt blocks to grass",2,1);
    }
    
    @Override
    public boolean onInteract(PlayerInteractEvent e,int level)
    {
        if(e.getClickedBlock() != null && e.getClickedBlock().getType() == Material.DIRT)
        {
            e.getClickedBlock().setType(Material.GRASS);
            
            return true;
        }
        
        return false;
    }
}
