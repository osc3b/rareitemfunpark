package com.lonelymc.ri4.bukkit.rareitems.properties;

import com.lonelymc.ri4.bukkit.rareitems.RareItemProperty;
import com.lonelymc.ri4.api.ItemPropertyRarity;
import com.lonelymc.ri4.api.PropertyCostType;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public class GrowTree extends RareItemProperty {
    public GrowTree() {
        super(
                "Grow Tree",
                "Grows a tree from a clicked sapling",
                ItemPropertyRarity.UNCOMMON,
                PropertyCostType.EXPERIENCE,
                1.0D,
                60,
                new String[]{
                        "type=DYE;dura=15;", 
                        "type=SAPLING;dura=1;", 
                        "type=DYE;dura=15;", 
                        "type=SAPLING;dura=2;",
                        "!UNCOMMON_ESSENCE",
                        "type=IRON_INGOT;", 
                        "type=SAPLING;dura=3;",
                        "type=STONE;",
                        "type=SAPLING;dura=4;"
                }
        );
    }

    @Override
    public boolean onInteracted(Player pInteracted, PlayerInteractEvent e, int level) {
        if (e.getClickedBlock() != null) {
            if (e.getClickedBlock().getType() == Material.SAPLING) {
                TreeType tt = getTree(e.getClickedBlock());

                e.getClickedBlock().setType(Material.AIR);

                e.getClickedBlock().getWorld().generateTree(e.getClickedBlock().getLocation(), tt);

                return true;
            }
        }
        return false;
    }

    public TreeType getTree(Block sappling) {
        switch (sappling.getData()) {
            case 0:
                if ((int) (Math.random() * 100.0D) > 90) {
                    return TreeType.TREE;
                }
                return TreeType.BIG_TREE;
            case 1:
                if ((int) (Math.random() * 100.0D) > 90) {
                    return TreeType.REDWOOD;
                }
                return TreeType.TALL_REDWOOD;
            case 2:
                return TreeType.BIRCH;
        }
        return TreeType.TREE;
    }
}