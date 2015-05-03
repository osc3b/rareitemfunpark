package com.minecraftfunpark.rareitemhunter.property.spell;

import com.minecraftfunpark.rareitemhunter.property.ItemProperty;
import com.minecraftfunpark.rareitemhunter.property.ItemPropertyTypes;
import java.util.Random;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Slime;
import org.bukkit.event.player.PlayerInteractEvent;

public class SummonSlime extends ItemProperty
{
    public SummonSlime()
    {
        super(ItemPropertyTypes.SPELL,"Summon Slime","Creates one slime / level",3,16);
    }
    
    @Override
    public boolean onInteract(PlayerInteractEvent e,int level)
    {
        for(int i=0;i<level;i++)
        {
            Slime slime = (Slime)  e.getPlayer().getWorld().spawnEntity(e.getPlayer().getLocation(),EntityType.SLIME);
            slime.setSize(new Random().nextInt(5)+1);
        }

        return true;
    }
}