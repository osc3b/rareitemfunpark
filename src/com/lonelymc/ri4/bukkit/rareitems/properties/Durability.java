package com.lonelymc.ri4.bukkit.rareitems.properties;

import com.lonelymc.ri4.api.ItemPropertyRarity;
import com.lonelymc.ri4.api.PropertyCostType;
import com.lonelymc.ri4.bukkit.rareitems.RareItemProperty;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Durability extends RareItemProperty {
    public Durability() {
        super(
                "Durability",
                "Automagically repairs equipped armor and held weapons by 2% per level every 10 seconds",
                ItemPropertyRarity.RARE,
                PropertyCostType.AUTOMATIC,
                10.0D,
                8,
                new String[]{
                        "type=POTION;dura=8257;",
                        "type=IRON_BLOCK;",
                        "type=POTION;dura=8257;",
                        "type=IRON_BLOCK;",
                        "!RARE_ESSENCE",
                        "type=IRON_BLOCK;",
                        "type=POTION;dura=8257;",
                        "type=IRON_BLOCK;",
                        "type=POTION;dura=8257;"
                }
        );
    }

    @Override
    public void applyEffectToPlayer(Player player, int level) {
        ItemStack[] armor = player.getInventory().getArmorContents();
        for (int i = 0; i < armor.length; i++) {
            repairItem(armor[i], level);
        }
        ItemStack heldItem = player.getItemInHand();
        if (heldItem != null) {
            repairItem(heldItem, level);
        }
    }

    public void repairItem(ItemStack is, int level) {
        if ((is.getType().getMaxDurability() > 20) && (is.getDurability() > 0) && (!is.getType().equals(Material.FISHING_ROD))) {
            int maxDurability = is.getType().getMaxDurability();
            int repairAmount;
            if (maxDurability < 100) {
                repairAmount = level * maxDurability / 16;
            } else {
                repairAmount = maxDurability / 100 * level * 2;
            }
            int newDurability = is.getDurability() - repairAmount;
            if (newDurability < 0) {
                newDurability = 0;
            }
            is.setDurability((short) newDurability);
        }
    }
}