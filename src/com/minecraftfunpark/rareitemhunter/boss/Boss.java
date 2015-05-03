package com.minecraftfunpark.rareitemhunter.boss;

import java.util.Random;
import java.util.UUID;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Boss
{
    private double hp;
    private int kills = 0;
    BossTemplate template;
    UUID entityId;
    
    Boss(BossTemplate bossTemplate)
    {
        this.template = bossTemplate;
        this.hp = bossTemplate.maxHP;
    }
    
    public double takeDamage(double damage)
    {
        return this.hp = this.hp - damage; 
    }

    public String getName()
    {
        return this.template.name;
    }

    public int getMaxHP()
    {
        return this.template.maxHP;
    }

    public boolean activateEvent(EntityDamageByEntityEvent e, Entity eAttacker)
    {
        if(this.template.events != null)
        {
            for(BossEvent be : this.template.events)
            {       
                //Only one event type so far
                if(//be.type.equals(BossEventType.hpLessThan)
                /*&&*/ this.hp + e.getDamage() > be.triggerValue
                && this.hp < be.triggerValue)
                {
                    if(be.skill.activateSkill(this, e, eAttacker, be.level))
                    {
                        if(eAttacker instanceof Player)
                        {
                            ((Player) eAttacker).sendMessage(this.template.name+" uso "+be.skill.name+"!");
                        }
                    
                        return true;
                    }
                }
            }
        }
        
        return false;
    }
    
    public void activateRandomSkill(EntityDamageByEntityEvent e, Entity eAttacker)
    {
        if(this.template.skills != null)
        {
            Random random = new Random();

            for(BossSkillInstance bsi : this.template.skills)
            {
                if(random.nextInt(100) < bsi.chance)
                {
                    if(bsi.bossSkill.activateSkill(this, e, eAttacker, bsi.level))
                    {
                        if(eAttacker instanceof Player)
                        {
                            ((Player) eAttacker).sendMessage(this.template.name+" uso "+bsi.bossSkill.name+"!");
                        }

                        break;
                    }
                }
            }
        }
    }

    EntityType getEntityType()
    {
        return this.template.entityType;
    }

    public double getAttackPower()
    {
        return this.template.attackPower;
    }

    public int getEssenceDropCount()
    {
        return this.template.essencesDropped;
    }

    UUID getUniqueId()
    {
        return this.entityId;
    }

    void setEntity(Entity entity)
    {
        this.entityId = entity.getUniqueId();
    }

    public int getKills()
    {
        return this.kills;
    }

    public int addKill()
    {
        return this.kills = this.kills + 1;
    }
}
