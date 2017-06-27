package com.lonelymc.ri4.bukkit.rareitems;

import com.lonelymc.ri4.api.ItemPropertyRarity;
import com.lonelymc.ri4.api.PropertyCostType;
import org.bukkit.entity.Player;

public class _Dummy extends RareItemProperty {
    public _Dummy(String name) {
        super(
                name, //Name (.toLowerCase() used as ID)
                "Placeholder property for when a property is missing",// Description
                ItemPropertyRarity.COMMON,
                PropertyCostType.PASSIVE, //Cost type
                0D, // Default cost
                1   // Max level
        );
    }

    public boolean hasCost(Player player, int level) {
        player.sendMessage(this.getName() + " is an invalid property! (Maybe an addon is missing?)");

        return false;
    }
}
