package com.minecraftfunpark.rareitemhunter;

import com.earth2me.essentials.Essentials;
import com.minecraftfunpark.rareitemhunter.boss.BossManager;
import com.minecraftfunpark.rareitemhunter.command.RareItemHunterCommandExecutor;
import com.minecraftfunpark.rareitemhunter.listener.*;
import com.minecraftfunpark.rareitemhunter.property.ItemPropertyCostTypes;
import com.minecraftfunpark.rareitemhunter.property.PropertyManager;
import com.minecraftfunpark.rareitemhunter.recipe.RecipeManager;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import net.h31ix.updater.Updater;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class RareItemHunter extends JavaPlugin
{
    public BossManager bossManager;
    public RecipeManager recipeManager;
    public Essentials essentials;
    
    public boolean NIGHT_CRAFTING_ONLY = true;
    
    public static RareItemHunter self;
    
    public PropertyManager propertyManager;

    public ItemPropertyCostTypes COST_TYPE;
    public int COST_MULTIPLIER;
    public int COST_LEVEL_INCREMENT;
    public final String COMPONENT_STRING = ChatColor.DARK_PURPLE+"RareItem Component";
    public final String RAREITEM_HEADER_STRING = ChatColor.DARK_PURPLE+"RareItem";
    
    public Economy economy;
    public boolean UPDATE_AVAILABLE = false;
    public String UPDATE_STRING;
    
    @Override
    public void onEnable()
    {
        RareItemHunter.self = this;
        
        loadConfig(false);
        
        this.essentials = ((Essentials) Bukkit.getPluginManager().getPlugin("Essentials"));
        
        loadManagers();
        
        getServer().getPluginManager().registerEvents(new RareItemHunterEntityListener(this), this);
        getServer().getPluginManager().registerEvents(new RareItemHunterPlayerListener(this), this);
        getServer().getPluginManager().registerEvents(new RareItemHunterBlockListener(this), this);
        
        getCommand("ri").setExecutor(new RareItemHunterCommandExecutor(this));
    }
    
    public void reload()
    {
        getServer().getScheduler().cancelTasks(this);
        
        loadConfig(true);
        
        loadManagers();
    }
    
    private void loadConfig(boolean reloadConfig)
    {
        getDataFolder().mkdirs();
        
        File configFile = new File(getDataFolder(),"config.yml");
        
        if(!configFile.exists())
        {
            copy(getResource("config.yml"), configFile);
        }
        
        if(reloadConfig)
        {
            this.reloadConfig();
        }

        if(getConfig().getString("costType").equalsIgnoreCase("food"))
        {
            COST_TYPE = ItemPropertyCostTypes.FOOD;
        }
        else if(getConfig().getString("costType").equalsIgnoreCase("xp"))
        {
            COST_TYPE = ItemPropertyCostTypes.XP;
        }
        else if(getConfig().getString("costType").equalsIgnoreCase("money"))
        {
            RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
            
            if(economyProvider != null)
            {
                economy = economyProvider.getProvider();
            }

            this.getLogger().log(Level.SEVERE,"You specified money as your cost type, however you don't have Vault! Disabling...");
            
            this.getPluginLoader().disablePlugin(this);
            
            COST_TYPE = ItemPropertyCostTypes.MONEY;
        }
        
        COST_MULTIPLIER = getConfig().getInt("costMultiplier");
        
        COST_LEVEL_INCREMENT = getConfig().getInt("costLevelIncrement");
        
        if(getConfig().getBoolean("update-notifications"))
        {
            Updater updater = new Updater(this, "rareitemhunter", this.getFile(), Updater.UpdateType.NO_DOWNLOAD, false); // Start Updater but just do a version check
            
            this.UPDATE_AVAILABLE = updater.getResult() == Updater.UpdateResult.UPDATE_AVAILABLE;
            this.UPDATE_STRING = updater.getLatestVersionString();
        }
    }

    private void loadManagers()
    {        
        this.propertyManager = new PropertyManager(this);
        
        this.bossManager = new BossManager(this);

        this.recipeManager = new RecipeManager(this);
    }

// Public helper methods
    
    public void copy(InputStream in, File file)
    {
        try
        {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while((len=in.read(buf))>0)
            {
                out.write(buf,0,len);
            }
            out.close();
            in.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
