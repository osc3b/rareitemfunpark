package com.lonelymc.ri4.bukkit.commands;

import com.lonelymc.ri4.api.RI4Strings;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.HashMap;

public class BasicCommandExecutor implements CommandExecutor {
    private final HashMap<String, BasicCommand> commands;
    private final String pluginName;

    public BasicCommandExecutor(JavaPlugin plugin) {
        this.commands = new HashMap<>();

        this.pluginName = plugin.getName();
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String label, String[] args) {
        if ((args.length == 0) || (args[0].equals("?"))) {
            sendUsage(cs);

            return true;
        }

        BasicCommand command = (BasicCommand) this.commands.get(args[0]);

        if (command != null) {
            if (cs.hasPermission(command.getPermissionNode())) {
                args = (String[]) Arrays.copyOfRange(args, 1, args.length);

                return command.execute(cs, args);
            }

            command.send(cs, new String[]{
                    ChatColor.RED + "You do not have permission to " + command.getAction(), ChatColor.RED + "Required node: " + ChatColor.WHITE + command.getPermissionNode()
            });
            
            return true;
        }

        cs.sendMessage(ChatColor.RED + "Invalid subcommand: " + args[0]);

        return false;
    }

    private void sendUsage(CommandSender cs) {
        cs.sendMessage(ChatColor.GRAY + "---" + ChatColor.DARK_GREEN + " " + this.pluginName + " " + ChatColor.GRAY + "---");

        cs.sendMessage(RI4Strings.COMMAND_YOU_CAN_USE);

        for (BasicCommand lc : this.commands.values()) {
            if ((cs.hasPermission(lc.getPermissionNode())) && (!lc.getName().equals("hat"))) {
                cs.sendMessage(lc.getUsage());
            }
        }
    }

    public void registerSubCommand(BasicCommand command) {
        this.commands.put(command.getName().toLowerCase(), command);
    }
}
