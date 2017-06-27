package com.lonelymc.ri4.bukkit.rareitems.properties;

import com.lonelymc.ri4.api.ItemPropertyRarity;
import com.lonelymc.ri4.api.PropertyCostType;
import com.lonelymc.ri4.bukkit.rareitems.RareItemProperty;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class RepairItem extends RareItemProperty {
    private String ITEM_REPAIRED = "Item repaired by !amount%!";
    private String ITEM_NOT_REPAIRABLE = "Item in slot #1 is not repairable!";
    private String REPAIRED_OTHER_PLAYER = "Item in !player's hand repaired by !amount%!";
    private String CANNOT_REPAIR_PLAYERS_ITEM = "Item in !player's hand is not repairable!";

    public RepairItem() {
        super(
                "Repair Item",
                "Repairs the #1 hotbar slot item by %20 / level or the item held by a clicked player",
                ItemPropertyRarity.RARE,
                PropertyCostType.EXPERIENCE,
                100,// xp cost
                5,// max level
                new String[]{
                        "type=POTION;dura=8225;",
                        "type=ANVIL;",
                        "type=POTION;dura=8225;",
                        "type=ANVIL;",
                        "!RARE_ESSENCE",
                        "type=ANVIL;",
                        "type=POTION;dura=8225;",
                        "type=IRON_BLOCK;",
                        "type=POTION;dura=8225;"
                }
        );
    }

    @Override
    public YamlConfiguration getConfig(YamlConfiguration config) {
        this.getDefaultConfig(config);

        config.set("messages.itemRepaired", this.ITEM_REPAIRED);
        config.set("messages.itemNotRepairable", this.ITEM_NOT_REPAIRABLE);
        config.set("messages.repairedOtherPlayer", this.REPAIRED_OTHER_PLAYER);
        config.set("messages.cannotRepairPlayersItem", this.CANNOT_REPAIR_PLAYERS_ITEM);

        return config;
    }

    @Override
    public void setConfig(YamlConfiguration config) {
        this.setDefaultConfig(config);

        this.ITEM_REPAIRED = config.getString("messages.itemRepaired", this.ITEM_REPAIRED);
        this.ITEM_NOT_REPAIRABLE = config.getString("messages.itemNotRepairable", this.ITEM_NOT_REPAIRABLE);
        this.REPAIRED_OTHER_PLAYER = config.getString("messages.repairedOtherPlayer", this.REPAIRED_OTHER_PLAYER);
        this.CANNOT_REPAIR_PLAYERS_ITEM = config.getString("messages.cannotRepairPlayersItem", this.CANNOT_REPAIR_PLAYERS_ITEM);
    }

    @Override
    public boolean onInteracted(Player pInteracted, PlayerInteractEvent e, int level) {
        ItemStack isSlotOne = e.getPlayer().getInventory().getItem(0);
        if ((isSlotOne != null) && (isSlotOne.getType().getMaxDurability() > 20) && (isSlotOne.getDurability() > 0)) {
            int iRepairAmount = isSlotOne.getType().getMaxDurability() / 5 * level - isSlotOne.getType().getMaxDurability() / 10;

            short sDurability = (short) (isSlotOne.getDurability() - iRepairAmount);
            if (sDurability < 0) {
                sDurability = 0;
            }
            isSlotOne.setDurability(sDurability);

            e.getPlayer().sendMessage(this.ITEM_REPAIRED.replace("!amount", String.valueOf(level * 20)));

            return true;
        }
        e.getPlayer().sendMessage(this.ITEM_NOT_REPAIRABLE);


        return false;
    }

    @Override
    public boolean onInteractEntity(Player pInteracted, PlayerInteractEntityEvent e, int level) {
        if ((e.getRightClicked() instanceof Player)) {
            Player pClicked = (Player) e.getRightClicked();
            ItemStack isSlotOne = pClicked.getItemInHand();
            if (isSlotOne.getType().getMaxDurability() > 20) {
                int iRepairAmount = isSlotOne.getType().getMaxDurability() / 5 * level - isSlotOne.getType().getMaxDurability() / 10;

                short sDurability = (short) (isSlotOne.getDurability() - iRepairAmount);
                if (sDurability < 0) {
                    sDurability = 0;
                }
                isSlotOne.setDurability(sDurability);

                e.getPlayer().sendMessage(this.REPAIRED_OTHER_PLAYER
                        .replace("!player", pClicked.getName())
                        .replace("!amount", String.valueOf(level * 20)));

                return true;
            }

            e.getPlayer().sendMessage(this.CANNOT_REPAIR_PLAYERS_ITEM
                    .replace("!amount", String.valueOf(level * 20)));

            return true;
        }
        return false;
    }
}