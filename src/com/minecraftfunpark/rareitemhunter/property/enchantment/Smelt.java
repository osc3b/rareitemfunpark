package com.minecraftfunpark.rareitemhunter.property.enchantment;


import com.minecraftfunpark.rareitemhunter.property.ItemProperty;
import com.minecraftfunpark.rareitemhunter.property.ItemPropertyTypes;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;

public class Smelt extends ItemProperty
{
    public Smelt()
    {
        super(ItemPropertyTypes.ENCHANTMENT,"Smelt","Turns clicked cobblestone into stone",1,1);
    }

    @Override
    public boolean onInteract(PlayerInteractEvent e,int level)
    {
        if(e.getClickedBlock() != null && e.getClickedBlock().getType() == Material.COBBLESTONE)
        {
            e.getClickedBlock().setType(Material.STONE);
            
            return true;
        }
        return false;
    }
}