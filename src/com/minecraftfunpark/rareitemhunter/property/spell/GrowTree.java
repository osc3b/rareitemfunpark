package com.minecraftfunpark.rareitemhunter.property.spell;

import com.minecraftfunpark.rareitemhunter.property.ItemProperty;
import com.minecraftfunpark.rareitemhunter.property.ItemPropertyTypes;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.block.Block;
import org.bukkit.event.player.PlayerInteractEvent;

public class GrowTree extends ItemProperty
{
    public GrowTree()
    {
        super(ItemPropertyTypes.SPELL,"Grow Tree","Grows a tree from a clicked sapling",1,8);
    }
    
    @Override
    public boolean onInteract(PlayerInteractEvent e,int level){
        if(e.getClickedBlock() != null)
        {
            if(e.getClickedBlock().getType() == Material.SAPLING)
            {
                TreeType tt = this.getTree(e.getClickedBlock());
                
                e.getClickedBlock().setType(Material.AIR);
                
                e.getClickedBlock().getWorld().generateTree(e.getClickedBlock().getLocation(), tt);
                
                return true;
            }
        }        
        return false;
    }
    
    public TreeType getTree(Block sappling) {
        switch (sappling.getData())
        {
            case 0:
                if((int)(Math.random() * 100.0D) > 90)
                {
                    return TreeType.TREE;
                }
                return TreeType.BIG_TREE;
            case 1:
                if((int)(Math.random() * 100.0D) > 90){
                    return TreeType.REDWOOD;
                }
                return TreeType.TALL_REDWOOD;
            case 2:
                return TreeType.BIRCH;
        }
        return TreeType.TREE;
    }
}