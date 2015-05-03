package com.minecraftfunpark.rareitemhunter.recipe;

import com.minecraftfunpark.rareitemhunter.RareItemHunter;
import com.minecraftfunpark.rareitemhunter.property.ItemProperty;
import com.minecraftfunpark.rareitemhunter.property.ItemPropertyTypes;
import java.io.File;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.Potion;

public class RecipeManager
{
    private final RareItemHunter plugin;

    private final ItemStack compass;

    private final HashMap<ItemStack,ItemStack[]> componentRecipes;
    
    private final EnumMap<ItemPropertyTypes, List<Integer>> TYPE_MATERIALS;
    private final ArrayList<Integer> ALL_TYPE_MATERIALS;
    private final ItemStack DEFAULT_IS;
    
    public RecipeManager(RareItemHunter plugin)
    {
        this.plugin = plugin;

        this.compass = new ItemStack(Material.COMPASS);
        ItemMeta itemMeta = compass.getItemMeta();
        itemMeta.setDisplayName(ChatColor.DARK_GREEN+"Legendary Compass");
        
        List<String> lore = new ArrayList<>();
        
        lore.add(ChatColor.DARK_GRAY+"When tapped against the ground");
        lore.add(ChatColor.DARK_GRAY+"this compass will attune itself");
        lore.add(ChatColor.DARK_GRAY+"to the nearest legendary boss egg.");
        
        itemMeta.setLore(lore);
        
        compass.getData().setData((byte) -41);
        
        compass.setItemMeta(itemMeta);
        
        ShapelessRecipe compassRecipe = new ShapelessRecipe(compass);
        
        compassRecipe.addIngredient(Material.COMPASS);
        compassRecipe.addIngredient(Material.GOLD_INGOT);
        compassRecipe.addIngredient(Material.EMERALD);
        compassRecipe.addIngredient(Material.IRON_INGOT);
        compassRecipe.addIngredient(Material.DIAMOND);
        
        plugin.getServer().addRecipe(compassRecipe);

        //Remove any existing RI recipes
        Iterator<Recipe> iter = plugin.getServer().recipeIterator();
        
        while (iter.hasNext())
        {
            Recipe r = iter.next();

            if(r.getResult().equals(this.DEFAULT_IS))
            {
                iter.remove();
            }
        }

        this.componentRecipes = new HashMap<>();
        
        TYPE_MATERIALS = new EnumMap<>(ItemPropertyTypes.class);

        List<Integer> BOW_MATERIALS = new ArrayList<>();
        BOW_MATERIALS.add(Material.BOW.getId());
        
        TYPE_MATERIALS.put(ItemPropertyTypes.BOW, BOW_MATERIALS);
        
        List<Integer> SKILL_MATERIALS = new ArrayList<>();
        SKILL_MATERIALS.add(Material.BOW.getId());
        
        SKILL_MATERIALS.add(Material.WOOD_AXE.getId());
        SKILL_MATERIALS.add(Material.STONE_AXE.getId());
        SKILL_MATERIALS.add(Material.GOLD_AXE.getId());
        SKILL_MATERIALS.add(Material.DIAMOND_AXE.getId());
        SKILL_MATERIALS.add(Material.IRON_AXE.getId());
        
        SKILL_MATERIALS.add(Material.STONE_SWORD.getId());
        SKILL_MATERIALS.add(Material.WOOD_SWORD.getId());
        SKILL_MATERIALS.add(Material.GOLD_SWORD.getId());
        SKILL_MATERIALS.add(Material.DIAMOND_SWORD.getId());
        SKILL_MATERIALS.add(Material.IRON_SWORD.getId());
        
        TYPE_MATERIALS.put(ItemPropertyTypes.SKILL, SKILL_MATERIALS);
        
        
        List<Integer> ENCHANTMENT_MATERIALS = new ArrayList<>();
        ENCHANTMENT_MATERIALS.add(Material.DIAMOND_PICKAXE.getId());
        ENCHANTMENT_MATERIALS.add(Material.WOOD_PICKAXE.getId());
        ENCHANTMENT_MATERIALS.add(Material.STONE_PICKAXE.getId());
        ENCHANTMENT_MATERIALS.add(Material.GOLD_PICKAXE.getId());
        
        ENCHANTMENT_MATERIALS.add(Material.DIAMOND_HOE.getId());
        ENCHANTMENT_MATERIALS.add(Material.WOOD_HOE.getId());
        ENCHANTMENT_MATERIALS.add(Material.STONE_HOE.getId());
        ENCHANTMENT_MATERIALS.add(Material.GOLD_HOE.getId());
        
        ENCHANTMENT_MATERIALS.add(Material.DIAMOND_SPADE.getId());
        ENCHANTMENT_MATERIALS.add(Material.WOOD_SPADE.getId());
        ENCHANTMENT_MATERIALS.add(Material.STONE_SPADE.getId());
        ENCHANTMENT_MATERIALS.add(Material.GOLD_SPADE.getId());
        
        TYPE_MATERIALS.put(ItemPropertyTypes.ENCHANTMENT, ENCHANTMENT_MATERIALS);
        
        
        List<Integer> SPELL_MATERIALS = new ArrayList<>();
        
        SPELL_MATERIALS.add(Material.BOOK.getId());

        TYPE_MATERIALS.put(ItemPropertyTypes.SPELL, SPELL_MATERIALS);

        List<Integer> ABILITY_MATERIALS = new ArrayList<>();
        
        ABILITY_MATERIALS.add(Material.SKULL_ITEM.getId());
        
        ABILITY_MATERIALS.add(Material.DIAMOND_HELMET.getId());
        ABILITY_MATERIALS.add(Material.GOLD_HELMET.getId());
        ABILITY_MATERIALS.add(Material.IRON_HELMET.getId());
        ABILITY_MATERIALS.add(Material.LEATHER_HELMET.getId());
        ABILITY_MATERIALS.add(Material.CHAINMAIL_HELMET.getId());
        
        ABILITY_MATERIALS.add(Material.DIAMOND_BOOTS.getId());
        ABILITY_MATERIALS.add(Material.GOLD_BOOTS.getId());
        ABILITY_MATERIALS.add(Material.IRON_BOOTS.getId());
        ABILITY_MATERIALS.add(Material.LEATHER_BOOTS.getId());
        ABILITY_MATERIALS.add(Material.CHAINMAIL_BOOTS.getId());
        
        ABILITY_MATERIALS.add(Material.DIAMOND_LEGGINGS.getId());
        ABILITY_MATERIALS.add(Material.GOLD_LEGGINGS.getId());
        ABILITY_MATERIALS.add(Material.IRON_LEGGINGS.getId());
        ABILITY_MATERIALS.add(Material.LEATHER_LEGGINGS.getId());
        ABILITY_MATERIALS.add(Material.CHAINMAIL_LEGGINGS.getId());
        
        ABILITY_MATERIALS.add(Material.DIAMOND_CHESTPLATE.getId());
        ABILITY_MATERIALS.add(Material.GOLD_CHESTPLATE.getId());
        ABILITY_MATERIALS.add(Material.IRON_CHESTPLATE.getId());
        ABILITY_MATERIALS.add(Material.LEATHER_CHESTPLATE.getId());
        ABILITY_MATERIALS.add(Material.CHAINMAIL_CHESTPLATE.getId());

        TYPE_MATERIALS.put(ItemPropertyTypes.ABILITY, ABILITY_MATERIALS);
        
        
        List<Integer> VISUAL_MATERIALS = new ArrayList<>();
        
        VISUAL_MATERIALS.add(Material.SKULL_ITEM.getId());
        VISUAL_MATERIALS.add(Material.DIAMOND_HELMET.getId());
        VISUAL_MATERIALS.add(Material.GOLD_HELMET.getId());
        VISUAL_MATERIALS.add(Material.IRON_HELMET.getId());
        VISUAL_MATERIALS.add(Material.LEATHER_HELMET.getId());
        VISUAL_MATERIALS.add(Material.CHAINMAIL_HELMET.getId());

        TYPE_MATERIALS.put(ItemPropertyTypes.VISUAL, VISUAL_MATERIALS);
        
        
        ALL_TYPE_MATERIALS = new ArrayList<>();
        
        for(List<Integer> materials : this.TYPE_MATERIALS.values())
        {
            for(int i : materials)
            {
                if(!ALL_TYPE_MATERIALS.contains(i))
                {
                    ALL_TYPE_MATERIALS.add(i);
                }
            }
        }
        
        DEFAULT_IS = new ItemStack(Material.STICK);

        DEFAULT_IS.getItemMeta().setDisplayName(ChatColor.RED+"Invalid Component(s)");
        
        for(int iMaterialId : ALL_TYPE_MATERIALS)
        {
            for(int i = 1;i<9;i++)
            {
                ShapelessRecipe recipe = new ShapelessRecipe(DEFAULT_IS);
                
                recipe.addIngredient(Material.getMaterial(iMaterialId));
                
                recipe.addIngredient(i, Material.MAGMA_CREAM);
                
                plugin.getServer().addRecipe(recipe);
            }
        }
        
        String componentsFilename = "component_recipes.yml";
        
        File componentsFile = new File(plugin.getDataFolder(),componentsFilename);
        
        if(!componentsFile.exists())
        {
            plugin.copy(plugin.getResource(componentsFilename),componentsFile);
        }
        
        YamlConfiguration componentsYml = YamlConfiguration.loadConfiguration(componentsFile);
        
        for(String sProperty : componentsYml.getKeys(false))
        {
            ItemProperty ip = plugin.propertyManager.getProperty(sProperty);
            
            if(ip != null)
            {
                ItemStack isComponentResult = new ItemStack(Material.MAGMA_CREAM);
                
                ItemMeta componentMeta = isComponentResult.getItemMeta();

                componentMeta.setDisplayName(plugin.COMPONENT_STRING);

                List<String> componentLore = new ArrayList<String>();

                componentLore.add(plugin.propertyManager.getPropertyComponentString(ip));

                componentMeta.setLore(componentLore);

                isComponentResult.setItemMeta(componentMeta); 
                
                ShapedRecipe componentRecipe = new ShapedRecipe(isComponentResult);
                
                ArrayList propertyRecipeParts = (ArrayList) componentsYml.get(sProperty);
                
//Compile a list of components for this recipe
                Map<String,Character> sIngredients = new HashMap<String,Character>();

                ItemStack[] ingredientsStorage = new ItemStack[9];
                
                int iUsed = 1;
                int iMatrixCounter = 0;
                
                String sShapeKey = "";
                 
                List<String> recipeStringLines = new ArrayList<String>();
                    
                for(Object oLine : propertyRecipeParts)
                {
                    ArrayList line = (ArrayList) oLine;
                    
                    String sRecipeStringLine = "";
                    
                    for(Object oIngredient : line)
                    {
                        String sIngredient = oIngredient.toString();
                        
                        
                        if(sIngredient.startsWith("POTION:"))
                        {
                            String sPotionID = sIngredient.substring(sIngredient.lastIndexOf(":")+1);
                            
                            int potionID = Integer.parseInt(sPotionID);
                            
                            sRecipeStringLine += ", "+this.getPotionName(potionID);
                        }
                        else
                        {
                            sRecipeStringLine += ", "+sIngredient;
                        }
                        
                        if(sIngredient.equalsIgnoreCase("AIR"))
                        {
                            sShapeKey += " ";
                        }
                        else if(!sIngredients.containsKey(sIngredient))
                        {
                            sIngredients.put(sIngredient,(char) iUsed);
                            sShapeKey += (char) iUsed;
                            iUsed++;
                        }
                        else
                        {
                            sShapeKey += sIngredients.get(sIngredient);
                        }
                        
                        if(sIngredient.contains(":"))
                        {
                            String sPrefix = sIngredient.substring(0,sIngredient.lastIndexOf(":"));
                            String sSuffix = sIngredient.substring(sIngredient.lastIndexOf(":")+1);
                            
                            ingredientsStorage[iMatrixCounter] = 
                                    new ItemStack(Material.matchMaterial(sPrefix),1,Short.parseShort(sSuffix));
                        }
                        else if(sIngredient.equalsIgnoreCase("RARE_ESSENCE"))
                        {
                            ingredientsStorage[iMatrixCounter] = this.getEssenceItem();
                        }
                        else if(!sIngredient.equalsIgnoreCase("AIR"))
                        {
                            ingredientsStorage[iMatrixCounter] = new ItemStack(Material.matchMaterial(sIngredient));
                        }
                        
                        iMatrixCounter++;
                    }
                    
                    recipeStringLines.add(sRecipeStringLine.substring(2));
                }
                
                ip.setRecipeLines(recipeStringLines);

                componentRecipe.shape(
                        sShapeKey.substring(0,3),
                        sShapeKey.substring(3,6),
                        sShapeKey.substring(6,9)                        
                        );
                
                for(String sIngredient : sIngredients.keySet())
                {
                    if(!sIngredient.contains(":"))
                    {
                        if(sIngredient.equalsIgnoreCase("rare_essence"))
                        {
                            componentRecipe.setIngredient(
                                    sIngredients.get(sIngredient),
                                    Material.MAGMA_CREAM);
                        }
                        else
                        {
                            componentRecipe.setIngredient(
                                    sIngredients.get(sIngredient),
                                    Material.matchMaterial(sIngredient));
                        }
                    }
                    else
                    {
                        String sPrefix = sIngredient.substring(0,sIngredient.lastIndexOf(":"));
                        String sSuffix = sIngredient.substring(sIngredient.lastIndexOf(":")+1);
                        
                        componentRecipe.setIngredient(
                                sIngredients.get(sIngredient),
                                Material.matchMaterial(sPrefix),
                                Integer.parseInt(sSuffix));
                    }
                }
                
                plugin.getServer().addRecipe(componentRecipe); 
                
                this.componentRecipes.put(componentRecipe.getResult(), ingredientsStorage);
            }
        }
    }
    
    public ItemStack getCompass()
    {
        return this.compass;
    }
    
    public ItemStack getEssenceItem()
    {
        ItemStack essence = new ItemStack(Material.MAGMA_CREAM);
        
        ItemMeta itemMeta = essence.getItemMeta();
        
        itemMeta.setDisplayName(ChatColor.DARK_GREEN+"Rare Essence");
        
        List<String> lore = new ArrayList<String>();
        //TODO: Better way to identify essences/compasses uniquely, allowing the name to change
        lore.add(ChatColor.DARK_GRAY+"This is the rare essence of");
        lore.add(ChatColor.DARK_GRAY+"a legendary boss. It can be used");
        lore.add(ChatColor.DARK_GRAY+"to craft Rare Items.");
        
        itemMeta.setLore(lore);

        essence.getData().setData((byte) -41);
        
        essence.setItemMeta(itemMeta);
        
        return essence;
    }
    

    public ItemStack getRecipeResult(PrepareItemCraftEvent e)
    {
        ItemStack result = e.getRecipe().getResult();
        
        if(result.equals(this.DEFAULT_IS))
        {            
            Map<ItemProperty,Integer> properties = new HashMap<ItemProperty,Integer>();
            ItemStack isResult = null;
            ItemPropertyTypes resultType =  null;
                  
            // Find the item to be upgraded
            for(ItemStack isIngredient : e.getInventory().getMatrix())
            {
                if(isIngredient != null 
                && isIngredient.getType() != Material.AIR 
                && this.ALL_TYPE_MATERIALS.contains(isIngredient.getTypeId()))
                {
                    ItemStack isNew = isIngredient.clone();
                    
                    isNew.setAmount(1);
                    
                    isResult = new ItemStack(isNew);
                }
            }
            
            // Find the category of that item            
            for(ItemPropertyTypes availableType : this.TYPE_MATERIALS.keySet())
            {
                if(this.TYPE_MATERIALS.get(availableType).contains(isResult.getTypeId()))
                {
                    resultType = availableType;
                }
            }
            
            if(resultType == null)
            {
                return new ItemStack(Material.AIR);
            }
            
            // Find the properties
            for(ItemStack isIngredient : e.getInventory().getMatrix())
            {
                if(isIngredient != null && isIngredient.getType() != Material.AIR)
                {
                    if(isIngredient.getType() == Material.MAGMA_CREAM)
                    {
                        ItemProperty p = plugin.propertyManager.getPropertyFromComponent(isIngredient);

                        if(p != null && p.getType() == resultType)
                        {
                            if(properties.containsKey(p))
                            {
                                properties.put(p,properties.get(p)+1);
                            }
                            else
                            {
                                properties.put(p,1);
                            }
                        }
                        else
                        {
                            return new ItemStack(Material.AIR);
                        }
                    }
                    else if(this.ALL_TYPE_MATERIALS.contains(isIngredient.getTypeId()))
                    {
                        continue;
                    }
                    else //blank magma cream or possibly an invalid property
                    {
                        return new ItemStack(Material.AIR);
                    }
                }
            }

            // Apply the properties
            if(isResult != null && !properties.isEmpty())
            {
                ItemMeta itemMeta = isResult.getItemMeta();
                
                List<String> lore = new ArrayList<>();
                
                lore.add(plugin.RAREITEM_HEADER_STRING);
                
                for(ItemProperty icp: properties.keySet())
                {
                    if(properties.get(icp) <= icp.getMaxLevel())
                    {
                        lore.add(plugin.propertyManager.getPropertyString(icp,properties.get(icp)));
                    }
                    else
                    {
                        for(HumanEntity he : e.getViewers())
                        {
                            Player p = (Player) he;
                            
                            p.sendMessage(ChatColor.RED+"The max level for "+icp.getName()+" is "+icp.getMaxLevel()+"!");
                        }
                        
                        return new ItemStack(Material.AIR);
                    }
                }
                
                if(isResult.getType() == Material.BOOK)
                {
                    itemMeta.setDisplayName("Spellbook");
                }
                
                itemMeta.setLore(lore);
                
                isResult.setItemMeta(itemMeta);
                
                return isResult;
            }
        }
        else if(this.componentRecipes.containsKey(result))
        {
            try
            {
                ItemStack[] storedComponents = this.componentRecipes.get(result);
                //Admit it, people just like using the term "matrix"
                ItemStack[] isMatrix = e.getInventory().getMatrix();

                for(int i=0;i<storedComponents.length;i++)
                {
                    if(storedComponents[i] != null)
                    {
                        if(storedComponents[i].getType() == Material.POTION)
                        {     
                            //Because water bottles don't have levels! -.-
                            byte componentsData = storedComponents[i].getData().getData();
                            byte matrixData = isMatrix[i].getData().getData();

                            if(componentsData == 0 && matrixData == 0)
                            {
                                continue;
                            }

                            if(componentsData == 0 || matrixData == 0)
                            {
                                return new ItemStack(Material.AIR);
                            }

                            if(!Potion.fromItemStack(storedComponents[i]).equals(Potion.fromItemStack(isMatrix[i])))
                            {
                                return new ItemStack(Material.AIR);
                            }
                        }
                        else if(!storedComponents[i].isSimilar(isMatrix[i]))
                        {
                            return new ItemStack(Material.AIR);
                        }
                    }
                }

                return result;
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
                
                return new ItemStack(Material.AIR);
            }
        }
        
        return null;
    }

    
    public boolean isCompassItem(ItemStack is)
    {
        return is.equals(this.compass);
    }

    public Iterable<Integer> getPropertyRecipeItemList(ItemProperty property)
    {
        return this.TYPE_MATERIALS.get(property.getType());
    }

    // Checks to see if the itemstack in question is the right type of material
    // to be used with the given item property by checking the item properties type
    // against the itemstack's material ID
    public boolean canPropertyGoOnItemStack(ItemProperty ip, ItemStack is)
    {
        return ip.getType() == ItemPropertyTypes.ANY || this.TYPE_MATERIALS.get(ip.getType()).contains(is.getType().getId());
    }

    private String getPotionName(int potionID) {
        switch(potionID) {
            case 0: 
                    return "Water Bottle";
            case 16: 
                    return "Awkward Potion";
            case 32: 
                    return "Thick Potion";
            case 64: 
                    return "Mundane Potion";
            case 8193: 
                    return "Regeneration Potion (0:45)";
            case 8194: 
                    return "Swiftness Potion (3:00)";
            case 8195: 
                    return "Fire Resistance Potion (3:00)";
            case 8196: 
                    return "Poison Potion (0:45)";
            case 8197: 
                    return "Healing Potion";
            case 8198: 
                    return "Night Vision Potion (3:00)";
            case 8200: 
                    return "Weakness Potion (1:30)";
            case 8201: 
                    return "Strength Potion (3:00)";
            case 8202: 
                    return "Slowness Potion (1:30)";
            case 8204: 
                    return "Harming Potion";
            case 8205: 
                    return "Water Breathing Potion (3:00)";
            case 8206: 
                    return "Invisibility Potion (3:00)";
            case 8225: 
                    return "Regeneration Potion II (0:22)";
            case 8226: 
                    return "Swiftness Potion II (1:30)";
            case 8228: 
                    return "Poison Potion II (0:22)";
            case 8229: 
                    return "Healing Potion II";
            case 8233: 
                    return "Strength Potion II (1:30)";
            case 8236: 
                    return "Harming Potion II";
            case 8257: 
                    return "Regeneration Potion (2:00)";
            case 8258: 
                    return "Swiftness Potion (8:00)";
            case 8259: 
                    return "Fire Resistance Potion (8:00)";
            case 8260: 
                    return "Poison Potion (2:00)";
            case 8262: 
                    return "Night Vision Potion (8:00)";
            case 8264: 
                    return "Weakness Potion (4:00)";
            case 8265: 
                    return "Strength Potion (8:00)";
            case 8266: 
                    return "Slowness Potion (4:00)";
            case 8269: 
                    return "Water Breathing Potion (8:00)";
            case 8270: 
                    return "Invisibility Potion (8:00)";
            case 8289: 
                    return "Regeneration Potion II (1:00)";
            case 8290: 
                    return "Swiftness Potion II (4:00)";
            case 8292: 
                    return "Poison Potion II (1:00)";
            case 8297: 
                    return "Strength Potion II (4:00)";
            case 16385: 
                    return "Regeneration Splash (0:33)";
            case 16386: 
                    return "Swiftness Splash (2:15)";
            case 16387: 
                    return "Fire Resistance Splash (2:15)";
            case 16388: 
                    return "Poison Splash (0:33)";
            case 16389: 
                    return "Healing Splash";
            case 16390: 
                    return "Night Vision Splash (2:15)";
            case 16392: 
                    return "Weakness Splash (1:07)";
            case 16393: 
                    return "Strength Splash (2:15)";
            case 16394: 
                    return "Slowness Splash (1:07)";
            case 16396: 
                    return "Harming Splash";
            case 16397: 
                    return "Breathing Splash (2:15)";
            case 16398: 
                    return "Invisibility Splash (2:15)";
            case 16417: 
                    return "Regeneration Splash II (0:16)";
            case 16418: 
                    return "Swiftness Splash II (1:07)";
            case 16420: 
                    return "Poison Splash II (0:16)";
            case 16421: 
                    return "Healing Splash II";
            case 16425: 
                    return "Strength Splash II (1:07)";
            case 16428: 
                    return "Harming Splash II";
            case 16449: 
                    return "Regeneration Splash (1:30)";
            case 16450: 
                    return "Swiftness Splash (6:00)";
            case 16451: 
                    return "Fire Resistance Splash (6:00)";
            case 16452: 
                    return "Poison Splash (1:30)";
            case 16454: 
                    return "Night Vision Splash (6:00)";
            case 16456: 
                    return "Weakness Splash (3:00)";
            case 16457: 
                    return "Strength Splash (6:00)";
            case 16458: 
                    return "Slowness Splash (3:00)";
            case 16461: 
                    return "Breathing Splash (6:00)";
            case 16462: 
                    return "Invisibility Splash (6:00)";
            case 16481: 
                    return "Regeneration Splash II (0:45)";
            case 16482: 
                    return "Swiftness Splash II (3:00)";
            case 16484: 
                    return "Poison Splash II (0:45)";
            case 16489: 
                    return "Strength Splash II (3:00)";
        }
        return "POTION:"+potionID;
    }
}
