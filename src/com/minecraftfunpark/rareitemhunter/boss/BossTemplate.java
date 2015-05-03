package com.minecraftfunpark.rareitemhunter.boss;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public class BossTemplate
{
    String name;
    int attackPower;
    List<BossSkillInstance> skills;
    int maxHP;
    EntityType entityType;
    int essencesDropped;
    List<ItemStack> equipment;
    ItemStack weapon;
    List<BossEvent> events;
    
    public BossTemplate(String name,EntityType entityType,int maxHP,int attackPower,int essencesDropped,List<ItemStack> equipment,ItemStack weapon)
    {
        this.name = name;
        this.attackPower = attackPower;
        this.maxHP = maxHP;
        this.entityType = entityType;
        this.essencesDropped = essencesDropped;
        
        if(equipment != null && !equipment.isEmpty())
        {
            this.equipment = equipment;
        }
        
        if(weapon != null)
        {
            this.weapon = weapon;
        }
    }
    
    public void addSkill(BossSkill bs,int level,int chance)
    {
        if(this.skills == null)
        {
            this.skills = new ArrayList<BossSkillInstance>();
        }
        
        this.skills.add(new BossSkillInstance(bs,level,chance));
    }

    void addEvent(BossEvent bossEvent)
    {
        if(this.events == null)
        {
            this.events = new ArrayList<BossEvent>();
        }
        
        this.events.add(bossEvent);
    }
}
