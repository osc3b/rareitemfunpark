package com.lonelymc.ri4.bukkit.commands;

import com.lonelymc.ri4.api.*;
import com.lonelymc.ri4.bukkit.RareItems4Plugin;
import com.lonelymc.ri4.util.FakeInventory;
import com.lonelymc.ri4.util.ItemStackConvertor;
import com.lonelymc.ri4.util.MetaStringEncoder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class CommandRecipe extends BasicCommand {
    private final IRareItems4API api;
    private final RareItems4Plugin plugin;

    public CommandRecipe(RareItems4Plugin plugin) {
        super(
                RI4Strings.COM_RECIPE,
                RI4Strings.COM_RECIPE_USAGE,
                RI4Strings.COM_RECIPE_DESC,
                "ri4.admin.recipe"
        );

        this.plugin = plugin;
        this.api = plugin.getAPI();
    }

    @Override
    boolean execute(CommandSender cs, String[] args) {
        if (args.length < 1) {
            this.send(cs, this.getUsage());

            return true;
        }

        StringBuilder sb = new StringBuilder();

        for(String arg : args){
            sb.append(" "+arg);
        }

        String sPropertyName = sb.substring(1);

        IRareItemProperty rip = this.api.getItemProperty(sPropertyName);

        if(rip == null){
            this.sendError(cs,RI4Strings.COMMAND_INVALID_PROPERTY.replace("!property",sPropertyName));

            return true;
        }

        if (args.length > 1) {
            String action = args[1];

            if (action.equalsIgnoreCase("remove")) {
                rip.setRecipe(null);

                this.api.saveItemProperty(rip);

                this.send(cs, RI4Strings.COMMAND_RECIPE_REMOVED
                        .replace("!property", rip.getDisplayName()));

                return true;
            }
        }

        if (!(cs instanceof Player)) {
            this.sendError(cs, RI4Strings.COMMAND_NOT_FROM_CONSOLE);

            return true;
        }

        Player p = (Player) cs;

        Inventory inv = plugin.getServer().createInventory(null, InventoryType.WORKBENCH, RI4Strings.CRAFTING_RECIPE_EDITOR
                .replace("!propertyName", MetaStringEncoder.encodeHidden(rip.getName(),"re")));

        String[] recipe = rip.getRecipe();

        if(recipe == null){
            IEssence dummyEssence = this.api.generateDummyEssence(rip.getRarity());

            ItemStack isEssence = new ItemStack(Material.valueOf(dummyEssence.getMaterial()));

            ItemMeta meta = isEssence.getItemMeta();

            meta.setDisplayName(RI4Strings.getDisplayName(dummyEssence));

            meta.setLore(RI4Strings.getItemLore(dummyEssence));

            isEssence.setItemMeta(meta);

            inv.setItem(5,isEssence);
        }
        else{
            for(int i=0;i<recipe.length;i++){
                String sIngredient = recipe[i];

                switch (sIngredient) {
                    default:
                        ItemStack is = ItemStackConvertor.fromString(sIngredient);

                        inv.setItem(i+1,is);

                        break;
                    case "AIR":
                        break;
                    case "!COMMON_ESSENCE":
                    case "!UNCOMMON_ESSENCE":
                    case "!RARE_ESSENCE":
                    case "!LEGENDARY_ESSENCE":
                    case "!STRANGE_ESSENCE":
                        String sRarity = sIngredient.substring(1, sIngredient.indexOf("_"));

                        ItemPropertyRarity rarity = ItemPropertyRarity.valueOf(sRarity);

                        IEssence dummyEssence = this.api.generateDummyEssence(rarity);

                        ItemStack isEssence = new ItemStack(Material.valueOf(dummyEssence.getMaterial()));

                        ItemMeta meta = isEssence.getItemMeta();

                        meta.setDisplayName(RI4Strings.getDisplayName(dummyEssence));

                        meta.setLore(RI4Strings.getItemLore(dummyEssence));

                        isEssence.setItemMeta(meta);

                        inv.setItem(i+1,isEssence);

                        break;
                }
            }
        }

        p.openInventory(inv);

        ItemStack isResult = new ItemStack(Material.BOOK);

        ItemMeta meta = isResult.getItemMeta();

        meta.setDisplayName(ChatColor.BLUE+"Save Changes");

        List<String> lore = new ArrayList<>();

        lore.add("Editing:");
        lore.add(rip.getDisplayName());

        meta.setLore(lore);

        isResult.setItemMeta(meta);

        inv.setItem(0,isResult);

        FakeInventory.fakeClientInventorySlot(this.plugin, inv.getViewers(), isResult, 0);

        return true;

    }
}

