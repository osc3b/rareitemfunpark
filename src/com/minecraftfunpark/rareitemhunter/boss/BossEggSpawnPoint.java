package com.minecraftfunpark.rareitemhunter.boss;

import org.bukkit.Location;

public class BossEggSpawnPoint
{
    String name;
    Location location;
    int radius;
    
    public BossEggSpawnPoint(String name,Location location,int radius)
    {
        this.name = name;
        this.location = location;
        this.radius = radius;
    }

    public Location getLocation()
    {
        return this.location;
    }
    
    public String getName()
    {
        return this.name;
    }

    public int getRadius()
    {
        return this.radius;
    }
}
