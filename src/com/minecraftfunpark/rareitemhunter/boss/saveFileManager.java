package com.minecraftfunpark.rareitemhunter.boss;

import com.minecraftfunpark.rareitemhunter.RareItemHunter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.metadata.FixedMetadataValue;

public class saveFileManager
{
    private final RareItemHunter plugin;
    private final BossManager bm;

    public saveFileManager(RareItemHunter plugin, BossManager bossManager)
    {
        this.plugin = plugin;
        this.bm = bossManager;
        
        File saveFile = new File(plugin.getDataFolder(),"save.yml");
        
        YamlConfiguration saveYml = YamlConfiguration.loadConfiguration(saveFile);
        
        if(saveYml.isSet("spawnPoints"))
        {
            for(String pointName : saveYml.getConfigurationSection("spawnPoints").getKeys(false))
            {                
                bm.spawnPoints.put(
                    pointName, 
                    new BossEggSpawnPoint(pointName,new Location(
                        Bukkit.getWorld(saveYml.getString("spawnPoints."+pointName+".world")),
                        saveYml.getInt("spawnPoints."+pointName+".x"),
                        saveYml.getInt("spawnPoints."+pointName+".y"),
                        saveYml.getInt("spawnPoints."+pointName+".z")
                    ),
                    saveYml.getInt("spawnPoints."+pointName+".radius")));
            }
        }
         
        if(saveYml.isSet("eggs"))
        {
            for(Map<String,Object> tempEgg : (List<Map<String,Object>>) saveYml.get("eggs"))
            {      
                Location lEgg = new Location(
                    Bukkit.getWorld(tempEgg.get("world").toString()),
                    Integer.parseInt(tempEgg.get("x").toString()),
                    Integer.parseInt(tempEgg.get("y").toString()),
                    Integer.parseInt(tempEgg.get("z").toString())
                );
                
                //Verify there's still an egg there
                if(!lEgg.getBlock().getType().equals(Material.DRAGON_EGG))
                {
                    if(lEgg.add(0, -1, 0).getBlock().getType().equals(Material.BEDROCK))
                    {
                        lEgg.add(0, -1, 0).getBlock().setType(Material.AIR);
                    }
                    
                    plugin.getLogger().log(Level.INFO, "Removing invalid egg at {0},{1},{2}", 
                            new Object[]{lEgg.getBlockX(), lEgg.getBlockY(), lEgg.getBlockZ()});
                }
                else
                {
                    bm.bossEggs.add(new BossEgg(
                        tempEgg.get("boss").toString(),
                        lEgg,
                        tempEgg.get("autospawn").toString().equalsIgnoreCase("true")
                    ));
                    
                    lEgg.getBlock().setMetadata("isBossEgg", new FixedMetadataValue(plugin,true));
                }
            }
        }
    }

    public void save()
    {
        final List<BossEgg> bossEggsToSave = new ArrayList<BossEgg>();
        
        for(BossEgg egg : this.bm.bossEggs) {
            if(!egg.getLocation().getBlock().getType().equals(Material.DRAGON_EGG))
            {
                if(egg.getLocation().add(0, -1, 0).getBlock().getType().equals(Material.BEDROCK))
                {
                    egg.getLocation().add(0, -1, 0).getBlock().setType(Material.AIR);
                }
                
                plugin.getLogger().log(Level.INFO, "Removing invalid egg at {0},{1},{2}", 
                        new Object[]{egg.getLocation().getBlockX(), egg.getLocation().getBlockY(), egg.getLocation().getBlockZ()});

            }
            else {
                bossEggsToSave.add(egg);
            }
        }
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, new SaveFileTask(plugin,bm,bossEggsToSave));
    }

    private static class SaveFileTask implements Runnable
    {
        private final RareItemHunter plugin;
        private final BossManager bm;
        private final List<BossEgg> bossEggsToSave;
        
        private SaveFileTask(RareItemHunter plugin, BossManager bm, List<BossEgg> bossEggsToSave) {
            this.plugin = plugin;
            this.bm = bm;
            this.bossEggsToSave = bossEggsToSave;
        }

        @Override
        public void run()
        {
            Map<String,Object> spawnPointsMap = new HashMap<>(); 

            for(BossEggSpawnPoint besp : bm.spawnPoints.values())
            {
                Map<String,Object> tempSP = new HashMap<>();

                Location l = besp.location;

                tempSP.put("world", l.getWorld().getName());
                tempSP.put("x", l.getBlockX());
                tempSP.put("y", l.getBlockY());
                tempSP.put("z", l.getBlockZ());
                tempSP.put("radius", besp.radius);

                spawnPointsMap.put(besp.name, tempSP);
            }

            List<Object> eggsMap = new ArrayList<>(); 

            for(BossEgg egg : bossEggsToSave)
            {
                Map<String,Object> tempEgg = new HashMap<>();
                
                if(egg != null) {
                    tempEgg.put("boss", egg.getName());                    
                    tempEgg.put("world", egg.getLocation().getWorld().getName());
                    tempEgg.put("x", egg.getLocation().getBlockX());
                    tempEgg.put("y", egg.getLocation().getBlockY());
                    tempEgg.put("z", egg.getLocation().getBlockZ());
                    tempEgg.put("autospawn",egg.getAutoSpawn());

                    eggsMap.add(tempEgg);
                }
            }

            YamlConfiguration saveYml = new YamlConfiguration();

            saveYml.set("eggs", eggsMap);
            saveYml.set("spawnPoints", spawnPointsMap);

            try
            {
                saveYml.save(new File(plugin.getDataFolder(),"save.yml"));
            }
            catch (IOException ex)
            {
                Logger.getLogger(saveFileManager.class.getName()).log(Level.SEVERE, null, ex);
                plugin.getLogger().log(Level.SEVERE,"Unable to write to save.yml!");
            }
        }
    }
}
