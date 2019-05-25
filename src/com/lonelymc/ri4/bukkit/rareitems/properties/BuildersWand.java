package com.lonelymc.ri4.bukkit.rareitems.properties;

import com.lonelymc.ri4.api.ItemPropertyRarity;
import com.lonelymc.ri4.api.PropertyCostType;
import com.lonelymc.ri4.bukkit.rareitems.RareItemProperty;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.HashMap;

public class BuildersWand extends RareItemProperty {
    public BuildersWand() {
        super(
                "Builder's Wand",
                "Clones block shapes using items from your inventory",
                ItemPropertyRarity.RARE,
                PropertyCostType.COOLDOWN,
                1,
                2
        );
    }

    @Override
    public boolean onInteracted(Player pInteracted, PlayerInteractEvent e, int level) {
        int maxCopied = level * 5;

        if (e.getClickedBlock() != null) {
            String sPlayerName = pInteracted.getName();
            String sWorldName = pInteracted.getLocation().getWorld().getName();

            Block clickedBlock = e.getClickedBlock();
            BlockFace baseFace = e.getBlockFace();
            Material type = clickedBlock.getType();

            ArrayList<Block> blocksToBuildOn = this.addBlocksToBuildOn(new ArrayList<Block>(), clickedBlock, baseFace, maxCopied);

            if (!blocksToBuildOn.contains(clickedBlock) && clickedBlock.getRelative(baseFace).getType().equals(Material.AIR)) {
                blocksToBuildOn.add(clickedBlock);
            }

            PlayerInventory inventory = pInteracted.getInventory();

            int changedBlocks = 0;

            for (Block b : blocksToBuildOn) {
                ItemStack is = new ItemStack(type, 1, (short) 0, b.getData());

                BlockPlaceEvent event = new BlockPlaceEvent(b, b.getState(), b.getRelative(baseFace),is, pInteracted, true, org.bukkit.inventory.EquipmentSlot.HAND);

                Bukkit.getServer().getPluginManager().callEvent(event);

                if (!event.isCancelled()) {
                    HashMap<Integer, ItemStack> leftovers = inventory.removeItem(is);

                    if (leftovers.isEmpty()) {
                        Block bFace = b.getRelative(baseFace);

                        bFace.setType(is.getType());
                        //bFace.setData(is.getData().getData());

                        changedBlocks++;
                    } else {
                        break;
                    }
                }
            }

            pInteracted.updateInventory();

            return changedBlocks > 0;
        }

        return false;
    }

    public ArrayList<Block> addBlocksToBuildOn(ArrayList<Block> blocksToChange, Block startingPoint, BlockFace baseFace, int maxCopied) {
        BlockFace[] affectedFaces = this.getAffectedBlockFaces(baseFace);
        Material baseMaterial = startingPoint.getType();

        for (BlockFace face : affectedFaces) {
            if (blocksToChange.size() >= maxCopied) {
                break;
            }

            Block b = startingPoint.getRelative(face);

            if (!blocksToChange.contains(b)
                    && b.getType().equals(baseMaterial)
                    && b.getRelative(baseFace).getType().equals(Material.AIR)) {
                blocksToChange.add(b);

                blocksToChange = this.addBlocksToBuildOn(blocksToChange, b, baseFace, maxCopied);
            }
        }

        return blocksToChange;
    }


    public BlockFace[] getAffectedBlockFaces(BlockFace bf) {
        switch (bf) {
            case DOWN:
            case UP:
                return new BlockFace[]{BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST};
            case NORTH:
            case SOUTH:
                return new BlockFace[]{BlockFace.UP, BlockFace.EAST, BlockFace.DOWN, BlockFace.WEST};
            case EAST:
            case WEST:
                return new BlockFace[]{BlockFace.UP, BlockFace.NORTH, BlockFace.DOWN, BlockFace.SOUTH};
        }
        return null;
    }
}