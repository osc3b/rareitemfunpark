package com.minecraftfunpark.rareitemhunter.property;

import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ShapedRecipe;

public class ItemProperty
{
    private final String name;
    private final String description;
    private int cost;
    private final int maxLevel;
    private final ItemPropertyTypes type;
    private List<String> recipeLines;
    
    public ItemProperty(ItemPropertyTypes type,String name,String description,int maxLevel,int cost)
    {
        this.type = type;
        this.name = name;
        this.description = description;
        this.maxLevel = maxLevel;
        this.cost = cost;
    }    
    
    public ShapedRecipe getComponentRecipe(ShapedRecipe recipe){ return null; }
    
    public boolean onInteract(PlayerInteractEvent e, int level){return false;}

    public boolean onInteractEntity(PlayerInteractEntityEvent e, int level){return false;}
    
    public boolean onDamageOther(EntityDamageByEntityEvent e, Player attacker, int level){return false;}

// Defaults to the same action as a melee attack, but offers optional overwritting.
    public boolean onArrowHitEntity(EntityDamageByEntityEvent e, Player shooter, int level)
    {
        return this.onDamageOther(e, shooter, level);
    }

    public boolean onArrowHitGround(ProjectileHitEvent e, Player shooter, int level){return false;}

    public void onEquip(Player p, int level){}
    
    public void onUnequip(Player p, int level){}

    public ItemPropertyTypes getType()
    {
        return this.type;
    }

    public int getMaxLevel()
    {
        return this.maxLevel;
    }

    public String getName()
    {
        return this.name;
    }
    
    //Can be overwritten for level-based costs
    public int getCost(int level)
    {
        return this.cost;
    }

    void setCost(int cost)
    {
        this.cost = cost;
    }
    
    public String getDescription()
    {
        return this.description;
    }
    
    public void setRecipeLines(List<String> recipeLines)
    {
        this.recipeLines = recipeLines;
    }
    
    public List<String> getRecipeLines()
    {
        return this.recipeLines;
    }
}
