
package com.minecraftfunpark.rareitemhunter.boss;

public class BossEvent
{
    BossEventType type;
    int triggerValue;
    BossSkill skill;
    int level;
    
    public BossEvent(BossEventType type, int triggerValue, BossSkill skill, int level)
    {
        this.type = type;
        this.triggerValue = triggerValue;
        this.skill = skill;
        this.level = level;
    }
}
