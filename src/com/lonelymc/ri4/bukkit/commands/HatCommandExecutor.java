package com.lonelymc.ri4.bukkit.commands;

import com.lonelymc.ri4.api.IRareItems4API;
import com.lonelymc.ri4.api.RI4Strings;
import com.lonelymc.ri4.bukkit.RareItems4Plugin;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class HatCommandExecutor implements CommandExecutor{
    private final IRareItems4API api;

    public HatCommandExecutor(RareItems4Plugin plugin) {
        this.api = plugin.getAPI();
    }

    @Override
    public boolean onCommand(CommandSender cs, Command command, String label, String[] args) {
        if (!(cs instanceof Player)) {
            cs.sendMessage(RI4Strings.COMMAND_NOT_FROM_CONSOLE);

            return true;
        }

        if(!cs.hasPermission("ri4.hat")){
            cs.sendMessage( RI4Strings.COMMAND_HAT_PERMISSION);

            return true;
        }

        Player p = (Player)cs;

        ItemStack isToEquip = p.getInventory().getItemInMainHand();

        if ((isToEquip != null) && (isToEquip.getType() != Material.AIR) && !this.isBelowNeckArmor(isToEquip))
        {
            
            PlayerInventory inv = p.getInventory();

            ItemStack isToUnequip = inv.getHelmet();

            inv.removeItem(isToEquip);

            inv.setHelmet(isToEquip);

            inv.setItemInMainHand(isToUnequip);

            this.api.unEquipRareItem(p, isToUnequip);

            this.api.equipRareItem(p, isToEquip);

            p.sendMessage(RI4Strings.COMMAND_HAT_EQUIPPED);

            return true;
        }

        p.sendMessage(RI4Strings.COMMAND_INVALID_HAT);

        return true;
    }

    // Not concerned with gold & leather armor
    private boolean isBelowNeckArmor(ItemStack is) {
        switch(is.getType()){
            default:
                return false;
            case DIAMOND_LEGGINGS:
            case DIAMOND_CHESTPLATE:
            case DIAMOND_BOOTS:
            case IRON_LEGGINGS:
            case IRON_CHESTPLATE:
            case IRON_BOOTS:
            case CHAINMAIL_LEGGINGS:
            case CHAINMAIL_CHESTPLATE:
            case CHAINMAIL_BOOTS:
                return true;
        }
    }
}
