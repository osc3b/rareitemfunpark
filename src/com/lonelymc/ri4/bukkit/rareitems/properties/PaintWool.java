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
                    "type=WOOL;dura=4;",
                    "type=WOOL;dura=1;",
                    "type=WOOL;dura=14;",
                    "type=WOOL;dura=5;",
                    "!COMMON_ESSENCE",
                    "type=WOOL;dura=2;",
                    "type=WOOL;dura=9;",
                    "type=WOOL;dura=3;",
                    "type=WOOL;dura=6;"
                }
        );
    }

    @Override
    public boolean onInteracted(Player pInteracted, PlayerInteractEvent e, int level) {
        /*if (e.getClickedBlock() != null) {
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
        }*/
        //Funciona diferente desde la 1.13, ahora no va por numeros, es mas complicado hacer esto ahora
        return false;
    }
}