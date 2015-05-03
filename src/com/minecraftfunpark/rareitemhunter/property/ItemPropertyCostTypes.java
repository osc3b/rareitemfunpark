package com.minecraftfunpark.rareitemhunter.property;

public enum ItemPropertyCostTypes
{
    FOOD(0),
    MONEY(1),
    XP(2);

    public int id;
    public String name;

    ItemPropertyCostTypes(int id, String name){
        this.id = id;
        this.name = name;
    }

    ItemPropertyCostTypes(int id){
        this.id = id;
        this.name = this.name().toLowerCase();
    }

    public int getId(){
        return id;
    }
}
