package com.minecraftfunpark.rareitemhunter.command;

import com.minecraftfunpark.rareitemhunter.RareItemHunter;
import com.minecraftfunpark.rareitemhunter.boss.BossEgg;
import com.minecraftfunpark.rareitemhunter.boss.BossEggSpawnPoint;
import com.minecraftfunpark.rareitemhunter.property.ItemProperty;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class RareItemHunterCommandExecutor implements CommandExecutor
{
    private final RareItemHunter plugin;

    public RareItemHunterCommandExecutor(RareItemHunter plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String alias, String[] args)
    {
        if(args.length == 0)
        {        
            cs.sendMessage(ChatColor.DARK_GREEN+"------  RareItemHunter  ------");
        
            cs.sendMessage("Here are the commands you have access to:");
            cs.sendMessage("");
            
            cs.sendMessage("- /ri whatis - Describes a rare item property");
            
            if(cs.hasPermission("rareitemhunter.admin.spawnpoint"))
            {
                cs.sendMessage("- /ri spawnpoint - Manage spawn points");
            }
            if(cs.hasPermission("rareitemhunter.admin.egg"))
            {
                cs.sendMessage("- /ri egg - Egg commands");
            }
            if(cs.hasPermission("rareitemhunter.admin.boss"))
            {
                cs.sendMessage("- /ri boss - Boss commands");
            }
            if(cs.hasPermission("rareitemhunter.admin.compass"))
            {
                cs.sendMessage("- /ri compass - Give you or another a "+plugin.recipeManager.getCompass().getItemMeta().getDisplayName());
            }  
            if(cs.hasPermission("rareitemhunter.admin.essence"))
            {
                cs.sendMessage("- /ri essence - Give you or another a "+plugin.recipeManager.getEssenceItem().getItemMeta().getDisplayName());
            }
            if(cs.hasPermission("rareitemhunter.admin.crafter"))
            {
                cs.sendMessage("- /ri craft - Give you a specific "+plugin.recipeManager.getEssenceItem().getItemMeta().getDisplayName());
            }
                
            return true;
        }
        else if((args[0].equalsIgnoreCase("whatis") || args[0].equalsIgnoreCase("wi")))
        {
            return this._whatIs(cs,args);
        }
        else if((args[0].equalsIgnoreCase("spawnpoint") || args[0].equalsIgnoreCase("sp")) 
        && this.hasCommandPermission(cs,"rareitemhunter.admin.spawnpoint","spawn point commands"))
        {
            return this._spawnPoint(cs,args);
        }
        else if((args[0].equalsIgnoreCase("boss") || args[0].equalsIgnoreCase("b"))
        && this.hasCommandPermission(cs,"rareitemhunter.admin.boss","spawn boss commands"))
        {
            return this._spawnBoss(cs,args);
        }
        else if(args[0].equalsIgnoreCase("egg")
        && this.hasCommandPermission(cs,"rareitemhunter.admin.egg","spawn egg commands"))
        {
            return this._spawnEgg(cs,args);
        }
        else if((args[0].equalsIgnoreCase("compass") || args[0].equalsIgnoreCase("c"))
        && this.hasCommandPermission(cs,"rareitemhunter.admin.compass","compass command"))
        {
            return this._compass(cs,args);
        }
        else if(args[0].equalsIgnoreCase("essence")
        && this.hasCommandPermission(cs,"rareitemhunter.admin.essence","essence command"))
        {
            return this._essence(cs,args);
        }
        else if((args[0].equalsIgnoreCase("craft") || args[0].equalsIgnoreCase("cr"))
        && this.hasCommandPermission(cs,"rareitemhunter.admin.craft","craft command"))
        {
            return this._craft(cs,args);
        }
        else if(args[0].equalsIgnoreCase("reload") 
        && this.hasCommandPermission(cs,"rareitemhunter.admin.reload","reload command"))
        {
            return this._reload(cs,args);
        }
        
        return false;
    }

    private boolean hasCommandPermission(CommandSender cs, String sPerm,String sAction)
    {
        if(cs.hasPermission(sPerm))
        {
            return true;
        }
        
        cs.sendMessage(ChatColor.RED+"You do not have permission to use "+ChatColor.WHITE+sAction+ChatColor.RED+".");
        cs.sendMessage(ChatColor.RED+"Permission node: "+ChatColor.WHITE+sPerm);
        
        return false;
    }
    
    private boolean sentFromConsole(CommandSender cs)
    {
        if(cs instanceof Player)
        { 
            return false;
        }
                   
        cs.sendMessage(ChatColor.RED+"This command cannot be run from the console in this way.");
        
        return true;
    }

    public String getHeader(String sHeader)
    {
        return ChatColor.DARK_GREEN + "-----  " + ChatColor.WHITE + sHeader + ChatColor.DARK_GREEN + " -----";
    }
    
    private boolean _spawnPoint(CommandSender cs, String[] args)
    {
        if(args.length == 1)
        {
            cs.sendMessage(ChatColor.DARK_GREEN+"------  /ri spawnpoint|sp  ------");
            cs.sendMessage("/ri spawnpoint add  - add a spawn point");
            cs.sendMessage("/ri spawnpoint del  - delete a spawn point");
            cs.sendMessage("/ri spawnpoint list - List spawn points");
        }
        else if(args[1].equalsIgnoreCase("add"))
        {
            return _spawn_add(cs, args);
        }
        else if(args[1].equalsIgnoreCase("del"))
        {
            return _spawn_del(cs, args);
        }
        else if(args[1].equalsIgnoreCase("list"))
        {
            return _spawn_list(cs, args);
        }
        
        return false;
    }

    private boolean _spawn_add(CommandSender cs, String[] args)
    {       
        if(sentFromConsole(cs))
        {
            return true;
        }
        
        if(args.length < 4 || args[2].equalsIgnoreCase("?"))
        {
            cs.sendMessage(ChatColor.DARK_GREEN+"------  /ri spawnpoint|sp add <name> <radius> ------");
            cs.sendMessage("Creates a spawn point monsters can spawn from. Radius determines how far from the spawn point a legendary boss egg can appear.");
            cs.sendMessage("");
            cs.sendMessage(ChatColor.DARK_GREEN+"Example:"+ChatColor.WHITE+" /ri spawnpoint add somePoint 50");
            
            return true;
        }
        else
        {
            String sPointName = args[2];
            String sRadius = args[3];
            
            int iRadius = 0;
            
            try
            {
                iRadius = Integer.parseInt(sRadius);
            }
            catch(Exception e)
            {
                cs.sendMessage(ChatColor.RED+sRadius+" is not a valid number!");
                
                return true;
            }

            if(!plugin.bossManager.isSpawnPoint(sPointName))
            {
                plugin.bossManager.addSpawnPoint(sPointName,((Player) cs).getLocation(),iRadius);
                
                cs.sendMessage(ChatColor.DARK_GREEN+"-------------- RareItemHunter ----------------");
                cs.sendMessage("Added "+sPointName+" at your location with a radius of "+iRadius+" blocks!");
            }
            else
            {
                cs.sendMessage(ChatColor.RED+sPointName+" already exists!");
            }
        }
        
        return false;
    }


    private boolean _spawn_del(CommandSender cs, String[] args)
    {
        if(args.length < 3 || args[2].equalsIgnoreCase("?"))
        {
            cs.sendMessage(ChatColor.DARK_GREEN+"------  /ri spawnpoint|sp del <name>  ------");
            cs.sendMessage("Deletes a spawn point by name.");
            cs.sendMessage("");
            cs.sendMessage(ChatColor.DARK_GREEN+"Example:"+ChatColor.WHITE+" /ri spawnpoint del somePoint");
            
            return true;
        }
        else
        {
            String sPointName = args[2];
            
            if(plugin.bossManager.isSpawnPoint(sPointName))
            {
                plugin.bossManager.delSpawnPoint(sPointName);

                cs.sendMessage(ChatColor.DARK_GREEN+sPointName+" was removed!");
                
                return true;
            }
            else
            {
                cs.sendMessage(ChatColor.RED+sPointName+" is not a valid spawn point!");
            }
        }
        
        return true;
    }

    private boolean _spawn_list(CommandSender cs, String[] args)
    {
        cs.sendMessage(ChatColor.DARK_GREEN+"------  Boss Spawn Points  ------");
        
        for(BossEggSpawnPoint point : plugin.bossManager.getSpawnPoints())
        {
            Location l = point.getLocation();
            
            cs.sendMessage(point.getName()+" (z:"+l.getBlockX()+",y:"+l.getBlockY()+",z:"+l.getBlockZ()+",radius:"+point.getRadius()+")");
        }
        
        return true;
    }    
    
    private boolean _spawnEgg(CommandSender cs, String[] args)
    {

        if(args.length < 3 || args[1].equalsIgnoreCase("?"))
        {
            cs.sendMessage(ChatColor.DARK_GREEN+"------  /ri egg <bossName> <pointName> | here  ------");
            cs.sendMessage("Spawns a boss egg at a spawn point, or at your current location.");
            cs.sendMessage("");
            cs.sendMessage(ChatColor.DARK_GREEN+"Available bosses: "+ChatColor.RESET+plugin.bossManager.getBossesString());
            cs.sendMessage("");
            cs.sendMessage(ChatColor.DARK_GREEN+"Available spawn points: "+ChatColor.RESET+plugin.bossManager.getSpawnPointsString());
            cs.sendMessage("");
            cs.sendMessage(ChatColor.DARK_GREEN+"Example:"+ChatColor.WHITE+" /ri egg boss1 somePoint");
            cs.sendMessage(ChatColor.DARK_GREEN+"Example:"+ChatColor.WHITE+" /ri egg boss1 here");
            
            return true;
        }
        else
        {
            String sBossName = args[1];      
            String sPointName = args[2];

            if(!plugin.bossManager.isValidLocation(sPointName) && !sPointName.equalsIgnoreCase("here"))
            {
                cs.sendMessage(ChatColor.RED+"Invalid spawn point name!");
                
                return true;
            }
            
            if(!plugin.bossManager.isValidBossName(sBossName))
            {
                cs.sendMessage(ChatColor.RED+"Invalid boss name");
                
                return true;
            }
            
            if(sPointName.equalsIgnoreCase("here"))
            {
                if(cs instanceof Player)
                {
                    plugin.bossManager.spawnBossEgg(sBossName,((Player) cs).getLocation().getBlock(), false);
                            
                    cs.sendMessage("Spawned a "+sBossName+" egg at your location!");
                }
                else
                {
                    cs.sendMessage("You cannot use 'here' from the console.");
                }
            }
            else
            {
                BossEgg bossEgg = plugin.bossManager.spawnBossEgg(sBossName, sPointName, true);

                cs.sendMessage("Spawned a "+sBossName+" egg near "+sPointName + " ("+bossEgg.getLocation().getBlockX()+","+bossEgg.getLocation().getBlockY()+","+bossEgg.getLocation().getBlockZ()+")!");
                
                for(Player p : bossEgg.getLocation().getWorld().getPlayers())
                {
                    p.sendMessage(ChatColor.GREEN+"A legendary boss egg has appeared!");
                }
            }
                
            return true;
        }
    }

    private boolean _spawnBoss(CommandSender cs, String[] args)
    {

        if(args.length < 3 || args[1].equalsIgnoreCase("?"))
        {
            cs.sendMessage(ChatColor.DARK_GREEN+"------  /ri boss|b <bossName> <pointName> | here  ------");
            cs.sendMessage("Spawns a boss at a spawn point, or at your current location.");
            cs.sendMessage("");
            cs.sendMessage(ChatColor.DARK_GREEN+"Available bosses: "+ChatColor.RESET+plugin.bossManager.getBossesString());
            cs.sendMessage("");
            cs.sendMessage(ChatColor.DARK_GREEN+"Available spawn points: "+ChatColor.RESET+plugin.bossManager.getSpawnPointsString());
            cs.sendMessage("");
            cs.sendMessage(ChatColor.DARK_GREEN+"Example:"+ChatColor.WHITE+" /ri boss boss1 somePoint");
            cs.sendMessage(ChatColor.DARK_GREEN+"Example:"+ChatColor.WHITE+" /ri b boss1 here");
            
            return true;
        }
        else
        {
            String sBossName = args[1];      
            String sPointName = args[2];

            if(!plugin.bossManager.isValidLocation(sPointName) && !sPointName.equalsIgnoreCase("here"))
            {
                cs.sendMessage(ChatColor.RED+"Invalid spawn point name!");
            }
            
            if(!plugin.bossManager.isValidBossName(sBossName))
            {
                cs.sendMessage(ChatColor.RED+"Invalid boss name");
                
                return true;
            }
            
            if(sPointName.equalsIgnoreCase("here"))
            {
                if(cs instanceof Player)
                {
                    plugin.bossManager.spawnBoss(sBossName,((Player) cs).getLocation());
                            
                    cs.sendMessage("Spawned a "+sBossName+" at your location!");
                }
                else
                {
                    cs.sendMessage("You cannot use 'here' from the console.");
                }
            }
            else
            {
                BossEgg bossEgg = plugin.bossManager.spawnBoss(sBossName, sPointName);
                    
                cs.sendMessage("Spawned a "+sBossName+" near "+sPointName + " ("+bossEgg.getLocation().getBlockX()+","+bossEgg.getLocation().getBlockY()+","+bossEgg.getLocation().getBlockZ()+")!");
            }
                
            return true;
        }
    }

    private boolean _compass(CommandSender cs, String[] args)
    {        
        if(args.length < 2)
        {
            if(this.sentFromConsole(cs))
            {
                cs.sendMessage("You ca/ri n use /ri compass|c <player>");
                
                return true;
            }
            
            Player player = (Player) cs;
            
            player.getWorld().dropItemNaturally(player.getLocation(), plugin.recipeManager.getCompass());
            
            player.sendMessage("Giving you a "+plugin.recipeManager.getCompass().getItemMeta().getDisplayName()+"!");
        }
        else if(args[1].equalsIgnoreCase("?"))
        {
            cs.sendMessage(ChatColor.DARK_GREEN+"------ /ri compass|c <player> ------");
            cs.sendMessage("Gives you or a specified player a compass");
            cs.sendMessage("");
            cs.sendMessage(ChatColor.DARK_GREEN+"Example:"+ChatColor.WHITE+" /ri compass");
            cs.sendMessage(ChatColor.DARK_GREEN+"Example:"+ChatColor.WHITE+" /ri compass <player>");
        }
        else if(plugin.getServer().getPlayer(args[1]) != null)
        {
            Player player = plugin.getServer().getPlayer(args[1]);

            player.getWorld().dropItemNaturally(player.getLocation(), plugin.recipeManager.getCompass());
            
            String sCompassName = plugin.recipeManager.getCompass().getItemMeta().getDisplayName();
            
            cs.sendMessage("Giving "+player.getName()+" a "+sCompassName+"!");
            player.sendMessage("You just got a "+sCompassName+"!");
        }
        else
        {
            cs.sendMessage(ChatColor.RED+args[1]+" is not a valid player!");
        }
        
        return true;
    }

    private boolean _reload(CommandSender cs, String[] args)
    {
        cs.sendMessage(ChatColor.GREEN+"Reloading RareItemHunter...");
        
        plugin.reload();
        
        cs.sendMessage(ChatColor.GREEN+"RareItemHunter Reloaded!");
        
        return true;
    }

    private boolean _essence(CommandSender cs, String[] args)
    {     
        if(args.length < 2)
        {
            if(this.sentFromConsole(cs))
            {
                cs.sendMessage("You can use /ri essence <player>");
                
                return true;
            }
            
            Player player = (Player) cs;
            
            player.getWorld().dropItemNaturally(player.getLocation(), plugin.recipeManager.getEssenceItem());
            
            player.sendMessage("Giving you a "+plugin.recipeManager.getEssenceItem().getItemMeta().getDisplayName()+"!");
        }
        else if(args[1].equalsIgnoreCase("?"))
        {
            cs.sendMessage(ChatColor.DARK_GREEN+"------  /ri essence <player>  ------");
            cs.sendMessage("Gives you or a specified player a essence");
            cs.sendMessage("");
            cs.sendMessage(ChatColor.DARK_GREEN+"Example:"+ChatColor.WHITE+" /ri essence");
            cs.sendMessage(ChatColor.DARK_GREEN+"Example:"+ChatColor.WHITE+" /ri essence <player>");
        }
        else if(plugin.getServer().getPlayer(args[1]) != null)
        {
            Player player = plugin.getServer().getPlayer(args[1]);

            player.getWorld().dropItemNaturally(player.getLocation(), plugin.recipeManager.getEssenceItem());
            
            String sEssenceName = plugin.recipeManager.getEssenceItem().getItemMeta().getDisplayName();
            
            cs.sendMessage("Giving "+player.getName()+" a "+sEssenceName+"!");
            
            player.sendMessage("You just got a "+sEssenceName+"!");
        }
        else
        {
            cs.sendMessage(ChatColor.RED+args[1]+" is not a valid player!");
        }
        
        return true;
    }
    
    private boolean _craft(CommandSender cs, String[] args)
    {     
        if(args.length < 3)
        {
            cs.sendMessage(ChatColor.DARK_GREEN+"------  /ri craft <essence name> <level> ------");
            cs.sendMessage("Gives you a specific essence");
            cs.sendMessage("Adds the essence property to the item in your hand.");
            cs.sendMessage(ChatColor.DARK_GREEN+"Example:"+ChatColor.WHITE+" /ri craft Durability 1");
            
            return true;
        }

        if(!(cs instanceof Player)) {
            cs.sendMessage("Not from console, sorry!");
            
            return true;
        }
        
        Player player = (Player) cs;
        
        String sPropertyName = "";

        for(int i=1;i<args.length-1;i++)
        {
            sPropertyName += " "+args[i];
        }
        
        sPropertyName = sPropertyName.substring(1);
        
        ItemProperty ip = plugin.propertyManager.getProperty(sPropertyName);

        if(ip == null)
        {
            cs.sendMessage(ChatColor.RED+"Invalid essence type!");

            return true;
        }

        int ipLevel = 1;

        try
        {
            ipLevel = Integer.parseInt(args[args.length-1]);
        }
        catch(Exception e)
        {
            cs.sendMessage(ChatColor.RED+"Invalid item property level!");

            return true;
        }

        if(ip.getMaxLevel() < ipLevel)
        {
            cs.sendMessage(ChatColor.RED+"The max level for this item property is "+ip.getMaxLevel()+"!");

            return true;
        }

        ItemStack playerItemInHand = player.getItemInHand();

        if(!plugin.recipeManager.canPropertyGoOnItemStack(ip,playerItemInHand))
        {
            cs.sendMessage(ChatColor.RED+"this item cannot have that property!");

            return true;
        }

        ItemMeta itemMeta = playerItemInHand.getItemMeta();
        
        List<String> lore = itemMeta.getLore();

        if(lore == null)
        {
            lore = new ArrayList<>();
            
            lore.add(plugin.RAREITEM_HEADER_STRING);
        }

        lore.add(plugin.propertyManager.getPropertyString(ip,ipLevel));
        
        itemMeta.setLore(lore);

        playerItemInHand.setItemMeta(itemMeta);

        player.sendMessage("Added "+ip.getName()+" to the item in your hand!");
        
        return true;
    }

    private boolean _whatIs(CommandSender cs, String[] args)
    {
        if(args.length < 2 || args[1].equalsIgnoreCase("?"))
        {
            cs.sendMessage(ChatColor.DARK_GREEN+"------  /ri whatis | wi <property name>  ------");
            cs.sendMessage("Describes a rare item property to you.");
            cs.sendMessage("");

            
            String sProperties = "";
            
            for(ItemProperty ip : plugin.propertyManager.getAllProperties())
            {
                sProperties += ", "+ip.getName();
            }
            
            cs.sendMessage(ChatColor.GRAY+"Available properties: "+ChatColor.WHITE+sProperties.substring(2));
            cs.sendMessage(ChatColor.DARK_GREEN+"---------------------------------");
        }
        else
        {
            String sPropertyName = "";
            for(int i=1;i<args.length;i++)
            {
                sPropertyName += " "+args[i];
            }
            sPropertyName = sPropertyName.substring(1);
            
            ItemProperty property = plugin.propertyManager.getProperty(sPropertyName);
            
            if(property == null)
            {
                cs.sendMessage(ChatColor.RED+"'"+sPropertyName+"' is not a valid rare item property!");
                
                return true;
            }
            
            cs.sendMessage(ChatColor.DARK_GREEN+"------  "+property.getName()+"  ------");
            cs.sendMessage(property.getDescription());
            cs.sendMessage("");
            
            cs.sendMessage(ChatColor.GRAY+"Recipe:");
            
            if(property.getRecipeLines() == null)
            {
                cs.sendMessage(ChatColor.RED+"No recipe found.");
            }
            else
            {
                for(String sLine : property.getRecipeLines())
                {
                    cs.sendMessage(sLine);
                }
            }
            
            cs.sendMessage("");
            
            cs.sendMessage(ChatColor.GRAY+"Can be put on:");
            
            String sAllowedItems = "";
            
            for(int iItemId : plugin.recipeManager.getPropertyRecipeItemList(property))
            {
                sAllowedItems += ", "+Material.getMaterial(iItemId);
            }
            
            cs.sendMessage(sAllowedItems.substring(2));
            
            cs.sendMessage(ChatColor.GRAY+"Max level: "+ChatColor.WHITE+property.getMaxLevel());          
            cs.sendMessage(ChatColor.GRAY+"Cost: "+ChatColor.WHITE+property.getCost(1));
            
/* TODO : figure out what's up with cost increments
            for(int i=1;i<=property.getMaxLevel();i++)
            {
                cs.sendMessage(ChatColor.WHITE+"lv"+i+" - "
                        +ChatColor.RESET+((property.getCost(i) - plugin.COST_LEVEL_INCREMENT) * plugin.COST_MULTIPLIER)
                        +" "+plugin.COST_TYPE.name().toLowerCase());
            }*/
            
            cs.sendMessage("");
            cs.sendMessage(ChatColor.DARK_GREEN+"-------------------------");
        }
        
        return true;
    }
}
