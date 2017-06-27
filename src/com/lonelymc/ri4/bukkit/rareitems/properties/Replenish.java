package com.lonelymc.ri4.bukkit.rareitems.properties;

import com.lonelymc.ri4.bukkit.rareitems.RareItemProperty;
import com.lonelymc.ri4.api.ItemPropertyRarity;
import com.lonelymc.ri4.api.PropertyCostType;
import org.bukkit.entity.Player;

public class Replenish extends RareItemProperty {
    public Replenish() {
        super(
                "Replenish",
                "Adds 1 food per level every 20 seconds, if food is full adds 1 saturation per level",
                ItemPropertyRarity.LEGENDARY,
                PropertyCostType.AUTOMATIC,
                20,//Cost
                2//Max level
        );
    }

    @Override
    public void applyEffectToPlayer(Player player, int level) {
        if (!player.isSprinting()) {
            int currentFood = player.getFoodLevel();

            if (currentFood == 20) {
                float newSaturation = player.getSaturation() + level;

                if (newSaturation > currentFood) {
                    newSaturation = currentFood;
                }
                player.setSaturation(newSaturation);
            }
            else {
                int newFood = currentFood + level;

                if (newFood >= 20) {
                    newFood = 20;
                }

                player.setFoodLevel(newFood);
            }
        }
    }
}