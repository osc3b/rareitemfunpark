package com.lonelymc.ri4.bukkit.listeners;

import com.lonelymc.ri4.api.*;
import com.lonelymc.ri4.bukkit.RareItems4Plugin;
import com.lonelymc.ri4.util.FakeInventory;
import com.lonelymc.ri4.util.ItemStackConvertor;
import com.lonelymc.ri4.util.MetaStringEncoder;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CraftingListener implements Listener {
    private final IRareItems4API api;
    private final RareItems4Plugin plugin;
    private final int MAX_PROPERTIES;

    public CraftingListener(RareItems4Plugin plugin, IRareItems4API api) {
        this.plugin = plugin;
        this.api = api;

        this.MAX_PROPERTIES = plugin.getConfig().getInt("max-properties",8);
    }

    @EventHandler(ignoreCancelled = true)
    public void onCompletedEssenceRecipe(CraftItemEvent e) {
        ItemStack isResult = e.getInventory().getItem(0);

        if (isResult != null && isResult.getType().equals(Material.DIRT) && isResult.hasItemMeta()) {
            ItemMeta meta = isResult.getItemMeta();

            String propertyName = MetaStringEncoder.decodeHidden(meta.getDisplayName(), "rir");

            if (propertyName != null) {
                IRareItemProperty rip = this.api.getItemProperty(propertyName);

                if (rip != null) {
                    String[] recipe = rip.getRecipe();

                    if(recipe != null){
                        for(int i=1;i<10;i++){
                            if(!e.getInventory().getItem(i).equals(ItemStackConvertor.fromString(recipe[i-1]))){
                                e.getInventory().setResult(null);

                                return;
                            }
                        }

                        IEssence essence = this.api.generateDummyEssence(rip);

                        ItemStack isDummyEssence = new ItemStack(Material.valueOf(essence.getMaterial()));

                        ItemMeta dummyMeta = isDummyEssence.getItemMeta();

                        dummyMeta.setDisplayName(RI4Strings.getDisplayName(essence));
                        dummyMeta.setLore(RI4Strings.getItemLore(essence));

                        isDummyEssence.setItemMeta(meta);

                        FakeInventory.fakeClientInventorySlot(
                                this.plugin,
                                e.getViewers(),
                                isDummyEssence,
                                0
                        );

                        return;
                    }
                }
            }

            e.getInventory().setResult(null);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onCraftRareEssence(PrepareItemCraftEvent e) {
        ItemStack result = e.getRecipe().getResult();

        if (result.getType().equals(Material.DIRT) && result.hasItemMeta()) {
            ItemMeta meta = result.getItemMeta();

            String propertyName = MetaStringEncoder.decodeHidden(meta.getDisplayName(), "rir");

            if (propertyName != null) {
                IRareItemProperty rip = this.api.getItemProperty(propertyName);

                if (rip != null) {
                    IEssence essence = null;

                    for(int i=1;i<10;i++){
                        essence = this.api.getEssence(e.getInventory().getItem(i));

                        if(essence != null){
                            break;
                        }
                    }

                    if(essence != null){
                        essence.setProperty(rip);

                        this.api.saveEssence(essence);

                        ItemStack isEssence = new ItemStack(Material.valueOf(essence.getMaterial()));

                        ItemMeta essenceMeta = isEssence.getItemMeta();

                        essenceMeta.setDisplayName(RI4Strings.getDisplayName(essence));

                        essenceMeta.setLore(RI4Strings.getItemLore(essence));

                        isEssence.setItemMeta(essenceMeta);

                        e.getInventory().setResult(isEssence);

                        return;
                    }
                }
            }

            e.getInventory().setResult(null);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onCraftingEssenceClick(InventoryClickEvent e) {
        // Viewing a recipe in a read-only GUI
        if (e.getInventory().getTitle().equals(RI4Strings.CRAFTING_VIEW_RARE_ITEM_RECIPE)) {
            e.setCancelled(true);

            return;
        } else if (e.getInventory().getTitle().equals(RI4Strings.CRAFTING_RECIPE_EDITOR)) {
            if (e.getRawSlot() == 0) {
                ItemStack isSave = e.getInventory().getItem(0);

                ItemMeta meta = isSave.getItemMeta();

                List<String> lore = meta.getLore();

                String sPropertyName = lore.get(1);

                IRareItemProperty rip = this.api.getItemProperty(sPropertyName);

                String[] recipe = new String[9];

                boolean foundEssence = false;

                for (int i = 1; i < 10; i++) {
                    ItemStack is = e.getInventory().getItem(i);

                    if (is == null || is.getType().equals(Material.AIR)) {
                        recipe[i - 1] = "AIR";
                    } else if (this.api.isDummyEssence(is)) {
                        if(foundEssence){
                            e.getWhoClicked().sendMessage(RI4Strings.CRAFTING_NEED_ESSENCE);

                            return;
                        }

                        recipe[i - 1] = "!" + rip.getRarity().name() + "_ESSENCE";

                        foundEssence = true;
                    } else {
                        recipe[i - 1] = ItemStackConvertor.fromItemStack(is, false);
                    }
                }

                if(!foundEssence){
                    e.getWhoClicked().sendMessage(RI4Strings.CRAFTING_NEED_ESSENCE);

                    return;
                }

                e.getWhoClicked().closeInventory();

                e.getWhoClicked().sendMessage(RI4Strings.RECIPE_UPDATED.replace("!property", rip.getDisplayName()));

                rip.setRecipe(recipe);

                this.api.saveItemProperty(rip);
            } else {
                // refresh the save button
                FakeInventory.fakeClientInventorySlot(this.plugin, e.getViewers(), e.getInventory().getItem(0), 0);
            }

            return;
        }

        if (e.getInventory().getType().equals(InventoryType.WORKBENCH)) {
            if (e.getRawSlot() < 10) {
                ItemStack itemToAddTo = null;
                IRareItem riToAddTo = null;
                Map<IRareItemProperty, Integer> properties = new HashMap<>();
                List<IEssence> essences = new ArrayList<IEssence>();
                int totalProperties = 0;

                for (int i = 1; i < 10; i++) {
                    ItemStack is = e.getInventory().getItem(i);

                    // pretend the item is already in the recipe
                    if (i == e.getRawSlot()) {
                        is = e.getCursor();
                    }

                    if (is != null && !is.getType().equals(Material.AIR)) {
                        IEssence essence = this.api.getEssence(is);

                        // Allow essences and one item to add the essences to
                        if (essence != null) {
                            if (essence.getProperty() != null) {
                                // We know it's not empty because it has a property
                                if (essence.getStatus().equals(EssenceStatus.USED) || essence.getStatus().equals(EssenceStatus.REVOKED)) {
                                    e.getWhoClicked().sendMessage(RI4Strings.CRAFTING_ESSENCE_ALREADY_USED
                                            .replace("!property", essence.getProperty().getDisplayName()));

                                    return;
                                } else if (!essence.getStatus().equals(EssenceStatus.FILLED)) {
                                    return;
                                }

                                Integer level = properties.get(essence.getProperty());

                                if (level == null) {
                                    level = 1;
                                } else {
                                    level++;
                                }

                                totalProperties++;

                                properties.put(essence.getProperty(), level);
                            } else {
                                // can't create a dynamic recipe with a blank essence
                                return;
                            }

                            if(essences.contains(essence)){
                                // duped essence
                                return;
                            }

                            essences.add(essence);
                        } else if (itemToAddTo == null) {
                            itemToAddTo = is;

                            riToAddTo = this.api.getRareItem(is);

                            if(riToAddTo != null){
                                for(Map.Entry<IRareItemProperty,Integer> rips : riToAddTo.getProperties().entrySet()){
                                    Integer level = properties.get(rips.getKey());

                                    if (level == null) {
                                        level = rips.getValue();
                                        totalProperties += rips.getValue();
                                    } else {
                                        level++;
                                        totalProperties++;
                                    }

                                    properties.put(rips.getKey(), level);
                                }
                            }
                        } else {
                            return;
                        }
                    }
                }

                if (properties.isEmpty() || itemToAddTo == null) {
                    return;
                }

                if(totalProperties > this.MAX_PROPERTIES){
                    e.getWhoClicked().sendMessage(RI4Strings.MAX_PROPERTIES_EXCEEDED
                            .replace("!max",String.valueOf(this.MAX_PROPERTIES))
                            .replace("!amount",String.valueOf(totalProperties)));

                    return;
                }

                // Result click
                if (e.getRawSlot() == 0) {
                    for (int i = 1; i < 10; i++) {
                        ItemStack is = e.getInventory().getItem(i);

                        if (is != null && !is.getType().equals(Material.AIR)) {
                            ItemStack isAir = new ItemStack(Material.AIR);

                            e.getInventory().setItem(i, isAir);

                            FakeInventory.fakeClientInventorySlot(this.plugin, e.getViewers(), isAir, i);
                        }
                    }

                    ItemStack isRareItem = itemToAddTo.clone();

                    isRareItem.setAmount(1);

                    if(riToAddTo == null){
                        riToAddTo = this.api.createRareItem(properties);
                    }
                    else{
                        riToAddTo.setProperties(properties);

                        this.api.saveRareItem(riToAddTo);
                    }

                    ItemMeta meta = isRareItem.getItemMeta();

                    meta.setLore(RI4Strings.getLore(riToAddTo));

                    isRareItem.setItemMeta(meta);

                    for (IEssence essence : essences) {
                        essence.setStatus(EssenceStatus.USED);

                        this.api.saveEssence(essence);
                    }

                    e.setCurrentItem(isRareItem);

                    FakeInventory.fakeClientInventorySlot(this.plugin, e.getViewers(), isRareItem, 0);
                }
                // Recipe click
                else {
                    IRareItem dummyRi = this.api.generateDummyRareItem(properties);

                    ItemStack isDummyRareItem = itemToAddTo.clone();

                    isDummyRareItem.setAmount(1);

                    ItemMeta meta = isDummyRareItem.getItemMeta();

                    meta.setLore(RI4Strings.getLore(dummyRi));

                    isDummyRareItem.setItemMeta(meta);

                    FakeInventory.fakeClientInventorySlot(this.plugin, e.getViewers(), isDummyRareItem, 0);
                }
            }
        }
    }
}