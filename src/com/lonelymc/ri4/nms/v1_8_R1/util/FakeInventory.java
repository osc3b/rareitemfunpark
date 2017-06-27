package com.lonelymc.ri4.nms.v1_8_R1.util;

import net.minecraft.server.v1_10_R1.EntityPlayer;
import net.minecraft.server.v1_10_R1.PacketPlayOutSetSlot;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_10_R1.inventory.CraftItemStack;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class FakeInventory {
    public static void fakeClientInventorySlot(JavaPlugin plugin,List<HumanEntity> viewers, final ItemStack is, final int slot) {
    // Generally it's just the one player
    for(final HumanEntity viewer : viewers) {
        if (viewer instanceof CraftPlayer) {
            plugin.getServer().getScheduler().runTaskLater(plugin, new Runnable() {
                @Override
                public void run() {
                    EntityPlayer handle = ((CraftPlayer) viewer).getHandle();

                    if (handle.activeContainer != null) {
                        handle.playerConnection.sendPacket(
                                new PacketPlayOutSetSlot(
                                        handle.activeContainer.windowId,
                                        slot,
                                        CraftItemStack.asNMSCopy(is)
                                )
                        );
                    }
                }
            }, 1);
        }
    }
}
}
