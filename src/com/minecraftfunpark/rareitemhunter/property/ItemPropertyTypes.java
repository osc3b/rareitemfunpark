package com.minecraftfunpark.rareitemhunter.property;

public enum ItemPropertyTypes
{
    ABILITY(0),
    ENCHANTMENT(1),
    SKILL(2),
    SPELL(3),
    VISUAL(4),
    BOW(5),
    ANY(6);

    public int id;
    public String name;

    ItemPropertyTypes(int id, String name){
        this.id = id;
        this.name = name;
    }

    ItemPropertyTypes(int id){
        this.id = id;
        this.name = this.name().toLowerCase();
    }

    public int getId(){
        return id;
    }
}
