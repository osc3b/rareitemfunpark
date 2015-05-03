package com.minecraftfunpark.rareitemhunter.property.ability;

import com.minecraftfunpark.rareitemhunter.property.ItemPropertyRepeatingEffect;
import com.minecraftfunpark.rareitemhunter.property.ItemPropertyTypes;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Durability extends ItemPropertyRepeatingEffect
{
    
    public Durability()
    {
        super(ItemPropertyTypes.ABILITY,"Durability","Automagically repairs equipped armor over time",5,0);
        
        this.createRepeatingAppliedEffect(this, 20*10);
    }

    @Override
    public void applyEffectToPlayer(Player player, int level)
    {
        ItemStack[] armor = player.getInventory().getArmorContents();
        
        for(int i = 0; i < armor.length; i++)
        {
            if(armor[i].getDurability() > 0)
            {
                int iNewArmor = armor[i].getDurability() - 1 * level;
                
                if(iNewArmor < 0)
                {
                    iNewArmor = 0;
                }
                
                armor[i].setDurability((short) iNewArmor);
            }
        }
    }
}
