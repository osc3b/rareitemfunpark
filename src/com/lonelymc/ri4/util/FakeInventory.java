package com.lonelymc.ri4.util;

import com.lonelymc.ri4.bukkit.RareItems4Plugin;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class FakeInventory {
    public static void fakeClientInventorySlot(RareItems4Plugin plugin,List<HumanEntity> viewers, final ItemStack is, final int slot) {
        com.lonelymc.ri4.nms.v1_8_R1.util.FakeInventory.fakeClientInventorySlot(plugin, viewers, is, slot);
    }
}
