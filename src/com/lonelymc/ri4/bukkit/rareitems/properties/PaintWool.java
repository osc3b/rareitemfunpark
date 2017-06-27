package com.lonelymc.ri4.bukkit.rareitems.properties;

import com.lonelymc.ri4.bukkit.rareitems.RareItemProperty;
import com.lonelymc.ri4.api.ItemPropertyRarity;
import com.lonelymc.ri4.api.PropertyCostType;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public class PaintWool extends RareItemProperty {
    public PaintWool() {
        super(
                "Paint Wool",
                "Changes the color of a clicked wool block",
                ItemPropertyRarity.COMMON,
                PropertyCostType.COOLDOWN,
                0.5D,
                1,
                new String[]{
                    "type=WOOL;dura=11;",
                    "type=WOOL;",
                    "type=WOOL;",
                    "type=WOOL;dura=11;",
                    "!COMMON_ESSENCE",
                    "type=WOOL;dura=14;",
                    "type=WOOL;dura=15;",
                    "type=WOOL;dura=15;",
                    "type=WOOL;dura=14;"
                }
        );
    }

    @Override
    public boolean onInteracted(Player pInteracted, PlayerInteractEvent e, int level) {
        if (e.getClickedBlock() != null) {
            if (e.getClickedBlock().getType() == Material.WOOL) {
                Block woolBlock = e.getClickedBlock();
                byte woolData = woolBlock.getData();
                if (woolData == 21) {
                    woolData = 0;
                } else {
                    woolData = (byte) (woolData + 1);
                }
                woolBlock.setData(woolData);

                return true;
            }
        }
        return false;
    }
}