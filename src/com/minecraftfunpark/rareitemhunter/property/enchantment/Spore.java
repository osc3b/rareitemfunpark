package com.minecraftfunpark.rareitemhunter.property.enchantment;


import com.minecraftfunpark.rareitemhunter.property.ItemProperty;
import com.minecraftfunpark.rareitemhunter.property.ItemPropertyTypes;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;

public class Spore extends ItemProperty
{
    public Spore()
    {
        super(ItemPropertyTypes.ENCHANTMENT,"Spore","Turns clicked cobblestone into mossy cobblestone.",1,1);
    }
    
    @Override
    public boolean onInteract(PlayerInteractEvent e,int level)
    {
        if(e.getClickedBlock() != null && e.getClickedBlock().getType() == Material.COBBLESTONE)
        {
            e.getClickedBlock().setType(Material.MOSSY_COBBLESTONE);
            
            return true;
        }
        
        return false;
    }
}