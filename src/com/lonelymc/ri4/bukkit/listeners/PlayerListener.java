package com.lonelymc.ri4.bukkit.listeners;

import com.lonelymc.ri4.api.IRareItem;
import com.lonelymc.ri4.api.IRareItemProperty;
import com.lonelymc.ri4.api.IRareItems4API;
import com.lonelymc.ri4.api.RareItemStatus;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.Metadatable;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.projectiles.ProjectileSource;

import java.util.List;
import java.util.Map;

public class PlayerListener implements Listener {
    private final IRareItems4API api;
    private final JavaPlugin plugin;

    public PlayerListener(JavaPlugin plugin, IRareItems4API api) {
        this.plugin = plugin;
        this.api = api;
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.hasItem()) {
            Player p = e.getPlayer();
            IRareItem ri = this.api.getRareItem(e.getItem());

            if (ri != null && ri.getStatus() != RareItemStatus.REVOKED) {
                for (Map.Entry<IRareItemProperty, Integer> entry : ri.getProperties().entrySet()) {
                    IRareItemProperty rip = entry.getKey();
                    int level = entry.getValue();

                    if (rip.hasCost(p, level)) {
                        if (rip.onInteracted(p, e, level)) {
                            rip.takeCost(p, level);
                        }
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerInteractWithEntityUsingRareItem(PlayerInteractEntityEvent e) {
        Player p = e.getPlayer();
        IRareItem ri = this.api.getRareItem(p.getItemInHand());

        if (ri != null && ri.getStatus() != RareItemStatus.REVOKED) {
            e.setCancelled(true);

            for (Map.Entry<IRareItemProperty, Integer> entry : ri.getProperties().entrySet()) {
                IRareItemProperty rip = entry.getKey();
                int level = entry.getValue();

                if (rip.hasCost(p, level)) {
                    if (rip.onInteractEntity(p, e, level)) {
                        rip.takeCost(p, level);
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerDamagedWearingRareItem(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player pDamaged = (Player) e.getEntity();

            Map<IRareItemProperty, Integer> activeEffects = this.api.getActiveEffects(pDamaged.getUniqueId());

            if (activeEffects != null) {
                for (Map.Entry<IRareItemProperty, Integer> entry : activeEffects.entrySet()) {
                    IRareItemProperty rip = entry.getKey();
                    int level = entry.getValue();

                    if (rip.hasCost(pDamaged, level)) {
                        if (rip.onDamaged(pDamaged, e, level)) {
                            rip.takeCost(pDamaged, level);
                        }
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEntityDamagedByPlayerUsingRareItem(EntityDamageByEntityEvent e) {
        Entity attacker = e.getDamager();

        if (attacker instanceof Player) {
            Player pAttacker = (Player) e.getDamager();

            ItemStack is = pAttacker.getItemInHand();

            IRareItem ri = this.api.getRareItem(is);

            if (ri != null && ri.getStatus() != RareItemStatus.REVOKED) {
                for (Map.Entry<IRareItemProperty, Integer> entry : ri.getProperties().entrySet()) {
                    IRareItemProperty rip = entry.getKey();
                    Integer level = entry.getValue();

                    if (rip.hasCost(pAttacker, level)) {
                        if (rip.onDamagedOther(pAttacker, e, level)) {
                            rip.takeCost(pAttacker, level);
                        }
                    }
                }
            }
        } else if (e.getDamager() instanceof Arrow) {
            Arrow arrow = (Arrow) e.getDamager();

            if (arrow.hasMetadata("bow")) {
                MetadataValue itemCraftMetaData = this.getItemCraftMetaData(arrow, "bow");

                if (itemCraftMetaData != null) {
                    ItemStack bow = (ItemStack) itemCraftMetaData.value();
                    ProjectileSource psShooter = arrow.getShooter();

                    if (bow != null && psShooter instanceof Player) {
                        Player shooter = (Player) psShooter;

                        IRareItem ri = this.api.getRareItem(bow);

                        for (Map.Entry<IRareItemProperty, Integer> entry : ri.getProperties().entrySet()) {
                            IRareItemProperty rip = entry.getKey();
                            Integer level = entry.getValue();

                            if (rip.hasCost(shooter, level)) {
                                if (rip.onArrowHitEntity(shooter, e, level)) {
                                    rip.takeCost(shooter, level);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
//TODO: item swapping equip/unequip RIs
    /*
    @EventHandler(ignoreCancelled = true)
    public void onPlayerSwappedRareItem(PlayerItemHeldEvent e){
        this.api.equipRareItem(e.getPlayer(), e.getPlayer().getInventory().getItem(e.getNewSlot()));
        this.api.unEquipRareItem(e.getPlayer(),e.getPlayer().getItemInHand());

        System.out.println("equipping: " +e.getPlayer().getInventory().getItem(e.getNewSlot()).toString());
        System.out.println("unequipping: " +e.getPlayer().getItemInHand().toString());
    }*/

    @EventHandler(ignoreCancelled = true)
    public void onPlayerShootRareItemBow(EntityShootBowEvent e) {
        if ((e.getEntity() instanceof Player)) {
            Player player = (Player) e.getEntity();
            ItemStack bow = e.getBow();

            if (bow != null) {
                IRareItem ri = this.api.getRareItem(bow);

                if (ri != null && ri.getStatus() != RareItemStatus.REVOKED) {
                    //Mark which bow shot the arrow
                    Arrow arrow = (Arrow) e.getProjectile();

                    arrow.setMetadata("bow", new FixedMetadataValue(plugin, player.getItemInHand()));

                    for (Map.Entry<IRareItemProperty, Integer> entry : ri.getProperties().entrySet()) {
                        IRareItemProperty rip = entry.getKey();
                        Integer level = entry.getValue();

                        if (rip.hasCost(player, level)) {
                            if (rip.onLaunchProjectile(player, e, level)) {
                                rip.takeCost(player, level);
                            }
                        }
                    }
                }
            }
        }
    }

    // Equip / unequip listeners
    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerEquipOrUnequipRareItem(InventoryClickEvent e) {
        if (e.getSlotType() == InventoryType.SlotType.ARMOR) {
            this.api.equipRareItem((Player) e.getWhoClicked(), e.getCursor());

            this.api.unEquipRareItem((Player) e.getWhoClicked(), e.getCurrentItem());
        }
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerQuit(PlayerQuitEvent e) {
        this.api.removeActiveEffects(e.getPlayer());
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerJoinWhileWearingRareItem(PlayerJoinEvent e) {
        for (ItemStack is : e.getPlayer().getInventory().getArmorContents()) {
            this.api.equipRareItem(e.getPlayer(), is);
        }
    }

    // Prevents players from placing blocks that are also rare items
    @EventHandler(ignoreCancelled = true)
    public void onPlayerPlaceRareItem(BlockPlaceEvent e) {
        IRareItem ri = this.api.getRareItem(e.getItemInHand());

        // Purposely allowing revoked here
        if (ri != null) {
            e.setCancelled(true);
        }
    }

    // Prevents rare items from breaking
    @EventHandler(ignoreCancelled = true)
    public void onPlayerBreakRareItem(PlayerItemBreakEvent e) {
        IRareItem ri = this.api.getRareItem(e.getBrokenItem());

        if (ri != null && ri.getStatus() != RareItemStatus.REVOKED) {
            e.getBrokenItem().setAmount(1);

            // repair the item but also disable the passive effects it has
            this.api.unEquipRareItem(e.getPlayer(), ri);
        }
    }

    private MetadataValue getItemCraftMetaData(Metadatable holder, String key) {
        List<MetadataValue> metadata = holder.getMetadata(key);

        for (MetadataValue mdv : metadata) {
            if (mdv.getOwningPlugin().equals(plugin)) {
                return mdv;
            }
        }

        return null;
    }
}