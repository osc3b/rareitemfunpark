package com.minecraftfunpark.rareitemhunter.property.spell;

import com.minecraftfunpark.rareitemhunter.property.ItemProperty;
import com.minecraftfunpark.rareitemhunter.property.ItemPropertyTypes;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class RepairItem extends ItemProperty
{
    public RepairItem()
    {
        super(ItemPropertyTypes.SPELL,"Repair Item","Repairs the first item in your inventory bar by 20% / level - 10%.",5,20);
    }
    
    @Override
    public boolean onInteract(PlayerInteractEvent e,int level)
    {
        ItemStack isSlotOne = e.getPlayer().getInventory().getItem(0);
        
        if(isSlotOne != null && isSlotOne.getType().getMaxDurability() > 20 && isSlotOne.getDurability() > 0)
        {
            int iRepairAmount = isSlotOne.getType().getMaxDurability() / 5 * level - (isSlotOne.getType().getMaxDurability()/10);
            
            short sDurability = (short) (isSlotOne.getDurability() - iRepairAmount);
            
            if(sDurability < 0)
            {
                sDurability = 0;
            }
            
            isSlotOne.setDurability(sDurability);
            
            e.getPlayer().sendMessage("Item repaired by "+(level*20-10)+"%!");
            
            return true;
        }
        else
        {
            e.getPlayer().sendMessage("Item in slot #1 is not repairable!");
        }
        
        return false;
    }
    
    @Override
    public boolean onInteractEntity(PlayerInteractEntityEvent e, int level)
    {
        if(e.getRightClicked() instanceof Player)
        {
            Player pClicked = (Player) e.getRightClicked();
            ItemStack isSlotOne = pClicked.getItemInHand();

            if(isSlotOne.getType().getMaxDurability() > 20)
            {
                short sDurability = (short) (isSlotOne.getDurability() - 8 * level);

                if(sDurability < 0)
                {
                    sDurability = 0;
                }

                isSlotOne.setDurability(sDurability);

                e.getPlayer().sendMessage("Item in "+pClicked.getName()+"'s hand repaired!");

                return true;
            }
            else
            {
                e.getPlayer().sendMessage("Item in "+pClicked.getName()+"'s hand is not repairable!");
            }
            
            return true;
        }
        return false;
    }
}