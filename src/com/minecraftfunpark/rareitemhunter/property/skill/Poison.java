package com.minecraftfunpark.rareitemhunter.property.skill;

import com.minecraftfunpark.rareitemhunter.property.ItemProperty;
import com.minecraftfunpark.rareitemhunter.property.ItemPropertyTypes;
import java.util.Random;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Poison extends ItemProperty
{
    public Poison()
    {
        super(
            ItemPropertyTypes.SKILL,
            "Poison",
            "Chance to poison an enemy onHit",
            4,
            4);
    }

    @Override
    public boolean onDamageOther(EntityDamageByEntityEvent e, Player attacker, int level)
    {
        if(new Random().nextInt(5) == 0
        && e.getEntity() instanceof LivingEntity)
        {
            LivingEntity le = (LivingEntity) e.getEntity();
            
            le.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 180, 1 * level));

            attacker.sendMessage("Poisoned!");
            
            if(e.getEntity() instanceof Player)
            {
                ((Player) e.getEntity()).sendMessage("You are poisoned!");
            }
            
            return true;
        }
        return false;
    }
}
