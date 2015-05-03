package com.minecraftfunpark.rareitemhunter.boss.skill;

import com.minecraftfunpark.rareitemhunter.boss.Boss;
import com.minecraftfunpark.rareitemhunter.boss.BossSkill;
import java.util.Random;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class Disarm extends BossSkill
{
    public Disarm()
    {
        super("Disarm");
    }
    
    @Override
    public boolean activateSkill(Boss boss,EntityDamageByEntityEvent e, Entity eAttacker, int level)
    {       
        if(eAttacker instanceof Player)
        {
            Player pAttacker = (Player) eAttacker;

            if(pAttacker.getItemInHand() != null)
            {
                int iRandomSlot = (new Random()).nextInt(44)+9;

                ItemStack swapOut = pAttacker.getInventory().getItem(pAttacker.getInventory().getHeldItemSlot());
                ItemStack swapIn = pAttacker.getInventory().getItem(iRandomSlot);

                pAttacker.getInventory().setItem(pAttacker.getInventory().getHeldItemSlot(), swapIn);
                pAttacker.getInventory().setItem(iRandomSlot, swapOut);

                pAttacker.sendMessage("You have been disarmed!");

                return true;
            }
        }
        
        return false;
    }
}
