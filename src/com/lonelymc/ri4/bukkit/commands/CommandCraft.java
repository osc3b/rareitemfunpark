package com.lonelymc.ri4.bukkit.commands;

import com.lonelymc.ri4.api.IRareItem;
import com.lonelymc.ri4.api.IRareItemProperty;
import com.lonelymc.ri4.api.IRareItems4API;
import com.lonelymc.ri4.api.RI4Strings;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;

public class CommandCraft extends BasicCommand {
    private final IRareItems4API api;

    public CommandCraft(IRareItems4API api) {
        super(
            RI4Strings.COM_CRAFT, 
            RI4Strings.COM_CRAFT_USAGE,
            RI4Strings.COM_CRAFT_DESC,
            "ri4.admin.craft"
        );
        
        this.api = api;
    }	
    
    @Override
    boolean execute(CommandSender cs, String[] args){
        if(args.length < 1){
            this.send(cs,this.getUsage());

            return true;
        }

        if(!(cs instanceof Player)){
            this.sendError(cs,RI4Strings.COMMAND_NOT_FROM_CONSOLE);

            return true;
        }

        String sLevel = args[args.length - 1];

        int level = 1;

        try{
            level = Integer.parseInt(sLevel);

            if(args.length > 1) {
                String[] tempArgs = new String[args.length - 1];

                for (int i = 0; i < args.length - 1; i++) {
                    tempArgs[i] = args[i];
                }

                args = tempArgs;
            }
        }
        catch(NumberFormatException ex){
            cs.sendMessage(RI4Strings.COMMAND_ASSUMING_LEVEL_1.replace("!level", sLevel));
        }

        StringBuilder sb = new StringBuilder();

        for(int i=0;i<args.length;i++){
            sb.append(" "+args[i]);
        }

        String propertyName = sb.substring(1);

        IRareItemProperty property = this.api.getItemPropertyByDisplayName(propertyName);

        if(property == null){
            this.sendError(cs,RI4Strings.COMMAND_VALUE_NOT_FOUND.replace("!value",propertyName));

            return true;
        }

        if(level > property.getMaxLevel()){
            this.sendError(cs,RI4Strings.COMMAND_MAX_LEVEL
            .replace("!level",String.valueOf(property.getMaxLevel()))
            .replace("!property", property.getDisplayName()));

            return true;
        }

        Player player = (Player) cs;

        ItemStack isInHand = player.getInventory().getItemInMainHand();

        if(isInHand == null || isInHand.getType().equals(Material.AIR)){
            this.sendError(cs,RI4Strings.COMMAND_MUST_HOLD_ITEM);

            return true;
        }

        IRareItem ri = this.api.getRareItem(isInHand);

        if(ri == null){
            Map<IRareItemProperty,Integer> properties = new HashMap<>();

            properties.put(property,level);

            ri = this.api.createRareItem(properties);
        }
        else{
            Map<IRareItemProperty,Integer> properties = ri.getProperties();

            if(level == 0){
                properties.remove(property);
            }
            else {
                properties.put(property, level);
            }

            ri.setProperties(properties);

            this.api.saveRareItem(ri);
        }

        ItemMeta meta = isInHand.getItemMeta();

        meta.setLore(RI4Strings.getLore(ri));

        isInHand.setItemMeta(meta);

        this.send(cs,RI4Strings.COMMAND_ADDED_PROPERTY_TO_ITEM
                .replace("!property", property.getDisplayName()));

        return true;
    }
}
