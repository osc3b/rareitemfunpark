package com.lonelymc.ri4.bukkit.commands;

import com.lonelymc.ri4.api.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CommandEssence extends BasicCommand {
    private final IRareItems4API api;
    
    public CommandEssence(IRareItems4API api) {
        super(
            RI4Strings.COM_ESSENCE,
            RI4Strings.COM_ESSENCE_USAGE,
            RI4Strings.COM_ESSENCE_DESC,
            "ri4.admin.essence"
        );
        
        this.api = api;
    }	
    
    @Override
    boolean execute(CommandSender cs, String[] args){
        if(args.length < 1){
            this.send(cs,this.getUsage());

            String sTypes = "";
            for(ItemPropertyRarity rarity : ItemPropertyRarity.values()){
                sTypes += ", "+rarity.name().toLowerCase();
            }
            sTypes = sTypes.substring(2);

            cs.sendMessage(RI4Strings.COMMAND_VALID_TYPES_ARE.replace("!types",sTypes));

            return true;
        }

        Player player;

        if(args.length > 1){
            String playerName = args[1];

            player = Bukkit.getPlayer(playerName);

            if(player == null){
                this.sendError(cs,RI4Strings.COMMAND_PLAYER_NOT_FOUND.replace("!player",playerName));

                return true;
            }
        }
        else if(cs instanceof Player){
            player = (Player) cs;
        }
        else {
            this.sendError(cs,RI4Strings.COMMAND_IF_CONSOLE_REQUIRES_PLAYER);

            return true;
        }

        StringBuilder sb = new StringBuilder();

        for(String arg : args){
            sb.append(" "+arg);
        }

        String essenceType = sb.substring(1);

        IEssence essence;

        // blank essences
        try{
            ItemPropertyRarity rarity = ItemPropertyRarity.valueOf(essenceType.toUpperCase());

            essence = this.api.createEssence(rarity);
        }
        catch (IllegalArgumentException e) {
            // essence with an item property
            IRareItemProperty rip = this.api.getItemPropertyByDisplayName(essenceType);

            if(rip != null){
                essence = this.api.createEssence(rip);
            }
            else {
                this.sendError(cs,RI4Strings.COMMAND_INVALID_ESSENCE_OR_PROPERTY.replace("!property",essenceType));

                return true;
            }
        }

        ItemStack isEssence = new ItemStack(Material.valueOf(essence.getMaterial()));

        ItemMeta meta = isEssence.getItemMeta();

        meta.setDisplayName(RI4Strings.getDisplayName(essence));

        meta.setLore(RI4Strings.getItemLore(essence));

        isEssence.setItemMeta(meta);

        if (!player.getInventory().addItem(new ItemStack[] { isEssence }).isEmpty()) {
            this.sendError(player,RI4Strings.COMMAND_INVENTORY_WAS_FULL_ON_GROUND_NOW
            .replace("!item",RI4Strings.getDisplayName(essence)));

            player.getWorld().dropItemNaturally(player.getLocation(), isEssence);
        }

        if(player == cs){
            this.send(cs,RI4Strings.COMMAND_GAVE_ESSENCE_RECEIVER
                    .replace("!essence", RI4Strings.getDisplayName(essence)));
        }
        else {
            this.send(cs,RI4Strings.COMMAND_GAVE_ESSENCE_RECEIVER
                    .replace("!essence",RI4Strings.getDisplayName(essence))
                    .replace("!player",player.getName()));

            this.send(cs,RI4Strings.COMMAND_GAVE_ESSENCE_RECEIVER
                    .replace("!essence",RI4Strings.getDisplayName(essence)));
        }

        return true;
    }
}
