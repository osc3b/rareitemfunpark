package com.minecraftfunpark.rareitemhunter.listener;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import com.minecraftfunpark.rareitemhunter.RareItemHunter;
import com.minecraftfunpark.rareitemhunter.boss.Boss;
import com.minecraftfunpark.util.FireworkVisualEffect;

import java.util.Random;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.GameMode;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.SmallFireball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTameEvent;
import org.bukkit.projectiles.ProjectileSource;

public class RareItemHunterEntityListener implements Listener
{
    private final RareItemHunter plugin;
    private final Essentials essentials;

    public RareItemHunterEntityListener(RareItemHunter plugin)
    {
        this.plugin = plugin;
        
        this.essentials = plugin.essentials;
    }
    
    @EventHandler(priority=EventPriority.NORMAL)
    public void Damage(EntityDamageByEntityEvent e)
    {
        //TODO: Snowballs or eggs do extra damage to certain vulnerable bosses. each boss has a weakness
        Entity eAttacker = e.getDamager();
        
        if((eAttacker instanceof Arrow))
        {
            eAttacker = (Entity) ((Arrow) eAttacker).getShooter();
        }       
        if((eAttacker instanceof Fireball))
        {
            eAttacker = (Entity) ((Fireball) eAttacker).getShooter();
        }           
        if((eAttacker instanceof SmallFireball))
        {
            eAttacker = (Entity) ((SmallFireball) eAttacker).getShooter();
        }      
        
        Boss bossAttacker = null;
        
        //TODO: Optimize these conditionals
        if(eAttacker != null)
        {
            bossAttacker = plugin.bossManager.getBoss(e.getDamager());
        }
        
        Boss bossAttacked = plugin.bossManager.getBoss(e.getEntity());

        //boss on boss violence... A sad social problem.
        if(bossAttacker != null && bossAttacked != null)
        {
            e.setCancelled(true);
            
            return;
        }
        
        if(bossAttacker != null)
        {
            e.setDamage(bossAttacker.getAttackPower());
            
            return;
        }
        
        if(bossAttacked != null)
        {     
            // Disable god mode if they have turned it on. Suckers. :D
            if(eAttacker instanceof Player) {
                Player pAttacker = (Player) eAttacker;
                
                if(pAttacker.getGameMode().equals(GameMode.CREATIVE)) {
                    pAttacker.setGameMode(GameMode.SURVIVAL);
                }
                
                if(this.essentials != null) {
                    User user = essentials.getUser(pAttacker);
                    
                    if(user.isGodModeEnabled()) {
                        user.setGodModeEnabled(false);
                    }
                }
            }
            
            LivingEntity leBossAttacked = (LivingEntity) e.getEntity();

            double iRemainingHP = bossAttacked.takeDamage(e.getDamage());
            
            if(iRemainingHP > 0)
            {
                leBossAttacked.setHealth(leBossAttacked.getMaxHealth());

                if(eAttacker instanceof LivingEntity)
                {
                    if(!bossAttacked.activateEvent(e,eAttacker))
                    {
                        bossAttacked.activateRandomSkill(e,eAttacker);
                    }
                }

                if(eAttacker instanceof Player)
                {
                    Player pAttacker = (Player) eAttacker;

                    pAttacker.sendMessage(bossAttacked.getName()+" HP: "+iRemainingHP+"/"+bossAttacked.getMaxHP());
                }
                
                leBossAttacked.setCustomName(String.format("%s %sHP / %sHP",new Object[]{
                    bossAttacked.getName(),
                    iRemainingHP,
                    bossAttacked.getMaxHP()
                }));

                e.setDamage(1d);
            }
            else //Dead
            {
                try
                {
                    new FireworkVisualEffect().playFirework(
                        leBossAttacked.getWorld(), leBossAttacked.getLocation(),
                        FireworkEffect
                            .builder()
                            .with(FireworkEffect.Type.CREEPER)
                            .withColor(Color.RED)
                            .build()
                    );
                }
                catch (Exception ex)
                {
                    plugin.getLogger().log(Level.SEVERE, null, ex);
                }

                if(eAttacker instanceof Player)
                {
                    Player pAttacker = (Player) eAttacker;
                    
                    plugin.getServer().broadcastMessage(pAttacker.getName()+ChatColor.DARK_GREEN+" has defeated legendary boss "+ChatColor.WHITE+bossAttacked.getName()+ChatColor.GREEN+"!");
                }
                else
                {
                    plugin.getServer().broadcastMessage("A legendary boss has been defeated!");
                }
                
                Random random = new Random();
                
                for(int i=0;i<bossAttacked.getEssenceDropCount();i++)
                {
                    //TODO: randomize drop spots somewhat
                    
                    Item droppedItem = leBossAttacked.getWorld().dropItemNaturally(leBossAttacked.getLocation(), plugin.recipeManager.getEssenceItem());
                    
                    //droppedItem.setVelocity(new Vector(random.nextFloat(),random.nextFloat(),random.nextFloat()));
                }
                
                e.setCancelled(true);
                
                plugin.bossManager.destroyBoss(e.getEntity(),bossAttacked);
            }
        }
    }
    
    @EventHandler(priority = EventPriority.NORMAL,ignoreCancelled = true)
    public void onEntityDamage(EntityDamageEvent e)
    {
        if (e.getEntity() != null && plugin.bossManager.isBoss(e.getEntity().getUniqueId()))
        {
            if(e.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK 
            && e.getCause() != EntityDamageEvent.DamageCause.PROJECTILE 
            && e.getCause() != EntityDamageEvent.DamageCause.MAGIC)
            {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(priority=EventPriority.NORMAL,ignoreCancelled = true)
    public void onEntityCombust(EntityCombustEvent e)
    {
        //TODO: fire immunity, rather than every boss being immune
        if(plugin.bossManager.isBoss(e.getEntity().getUniqueId()))
        {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority=EventPriority.NORMAL,ignoreCancelled = true)
    public void onEntityTame(EntityTameEvent e)
    {
        if(plugin.bossManager.isBoss(e.getEntity().getUniqueId()))
        {
            e.setCancelled(true);
        }
    }
    
    
}
