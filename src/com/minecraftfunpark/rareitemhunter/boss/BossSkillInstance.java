
package com.minecraftfunpark.rareitemhunter.boss;

public class BossSkillInstance
{
    int chance;
    int level;
    BossSkill bossSkill;
    
    public BossSkillInstance(BossSkill bossSkill,int level,int chance)
    {
        this.bossSkill = bossSkill;
        this.level = level;
        this.chance = chance;
    }
}
