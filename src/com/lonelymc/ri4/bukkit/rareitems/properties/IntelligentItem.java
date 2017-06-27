package com.lonelymc.ri4.bukkit.rareitems.properties;

import com.lonelymc.ri4.api.ItemPropertyRarity;
import com.lonelymc.ri4.api.PropertyCostType;
import com.lonelymc.ri4.bukkit.rareitems.RareItemProperty;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class IntelligentItem extends RareItemProperty {
    private final HashMap<UUID, Long> lastUse;
    private final Random random;

    public IntelligentItem() {
        super(
                "Intelligent Item", //Name (.toLowerCase() used as ID)
                "An item that's never afraid to tell you its opinion",// Description
                ItemPropertyRarity.STRANGE,
                PropertyCostType.COOLDOWN, //Cost type
                30, // Default cost
                1,   // Max level
                null
        );

        this.lastUse = new HashMap<UUID, Long>();
        this.random = new Random();
    }
/*
    @Override
    public void refreshCooldowns() {
        long now = System.currentTimeMillis();
        Iterator it = this.lastUse.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<String, Long> next = (Map.Entry<String, Long>) it.next();

            if (next.getValue() < now) {
                it.remove();
            }
        }
    }

    @Override
    public void takeCost(Player player, int level) {
        this.lastUse.put(player.getUniqueId(), System.currentTimeMillis() + ((random.nextInt((int) this.getCost()) + 1) * 1000));
    }

    // Basically, it's a randomized 1-30 second cooldown with no wait message
    @Override
    public boolean hasCost(Player player, int level) {
        Long cooldown = this.lastUse.get(player.getUniqueId());

        if (cooldown != null) {
            if (System.currentTimeMillis() < cooldown) {
                return false;
            }
        }

        return true;
    }

    String[] onInteracted_misc = new String[]{
            "I can't help but think there's something prettier we can build with.",
            "I'm not saying it's ugly, but it's not pretty.",
            "Look all I'm saying is can we see what other things are around to use?"
    };

    String[] onInteracted_noBlock = new String[]{
            "Can we do something interesting now?",
            "This isn't really what you do all day is it?",
            "Have you ever looked in a mirror and - oh what am I saying you don't know how to craft a mirror.",
            "*yawn* Let's go kill something."
    };

    String[] onInteracted_clickedPlant = new String[]{
            "I once met this birch I really liked, so I gave her some bone meal. Birches love bone meal.",
            "If plants come from seeds, and seeds come from plants... Where'd the first plant come from?",
            "Thinking about becoming a vegetarian?"
    };

    private String r(String[] msgs) {
        return msgs[random.nextInt(msgs.length)];
    }

    @Override
    public boolean onInteracted(Player pInteracted, PlayerInteractEvent e, int level) {
        String msg = "";

        if (e.hasBlock()) {
            switch (e.getClickedBlock().getType()) {
                default:
                    msg = r(onInteracted_misc);
                    break;
                case MELON_BLOCK:
                case MELON_STEM:
                    msg = "When the world gives you melons, make melonaid!";
                    break;

                case CARROT:
                case POTATO:
                case WATER_LILY:
                case PUMPKIN_STEM:
                case HUGE_MUSHROOM_1:
                case HUGE_MUSHROOM_2:
                case PUMPKIN:
                case CACTUS:
                case CROPS:
                case YELLOW_FLOWER:
                case RED_ROSE:
                case BROWN_MUSHROOM:
                case RED_MUSHROOM:
                case LEAVES:
                case SAPLING:
                    msg = r(onInteracted_clickedPlant);
                    break;

                case VINE:
                    msg = "Vines are like an upside down trees that grow a ladder.";
                    break;

                case JACK_O_LANTERN:
                case DEAD_BUSH:
                    msg = "That's a little spooky.";
                    break;

                case COCOA:
                    msg = "I love chocolate. I mean I've never tried it myself, but people say such good things...";
                    break;

                case CAKE_BLOCK:
                    msg = "Is it someone's birthday?";
                    break;

                case BED_BLOCK:
                case BED:
                    msg = "You know I could use a nap.";
                    break;

                case SPRUCE_FENCE_GATE:
                case BIRCH_FENCE_GATE:
                case JUNGLE_FENCE_GATE:
                case DARK_OAK_FENCE_GATE:
                case ACACIA_FENCE_GATE:
                case SPRUCE_FENCE:
                case BIRCH_FENCE:
                case JUNGLE_FENCE:
                case DARK_OAK_FENCE:
                case ACACIA_FENCE:


                case BEDROCK:
                    msg = "I suspect you wont be able to break this stuff.";
                    break;

                case DIODE:
                case REDSTONE_COMPARATOR:
                case REDSTONE_COMPARATOR_OFF:
                case REDSTONE_COMPARATOR_ON:
                case REDSTONE_TORCH_OFF:
                case REDSTONE_TORCH_ON:
                case TRIPWIRE_HOOK:
                case TRIPWIRE:
                case DIODE_BLOCK_OFF:
                case DIODE_BLOCK_ON:
                case RAILS:
                case REDSTONE_WIRE:
                case PISTON_BASE:
                case PISTON_STICKY_BASE:
                case PISTON_EXTENSION:
                case POWERED_RAIL:
                case DETECTOR_RAIL:
                    msg = "Never took you for an engineer.";
                    break;

                case DRAGON_EGG:
                    msg = "Think we can hatch it?";
                    break;

                case STONE_SLAB2:
                case SPRUCE_DOOR:
                case BIRCH_DOOR:
                case JUNGLE_DOOR:
                case ACACIA_DOOR:
                case DARK_OAK_DOOR:
                case TRAP_DOOR:
                case WOOD_DOOR:
                case IRON_DOOR:
                    msg = "Not the most terrible place I've ever been.";
                    break;

                case REDSTONE_LAMP_OFF:
                    msg = "Try clapping let's see if it turns on";
                    break;

                case REDSTONE_LAMP_ON:
                    msg = "Try clapping let's see if it turns off";
                    break;


                case GLOWING_REDSTONE_ORE:
                    msg = "What a creepy glow.";
                    break;

                case REDSTONE_ORE:
                case GOLD_ORE:
                case IRON_ORE:
                case LAPIS_ORE:
                case DIAMOND_ORE:
                case EMERALD_ORE:
                    msg = "We're gonna share that right?";
                    break;

                case WEB:
                    msg = "Ewww you're getting me all gummed up.";
                    break;

                // TODO: add messages for these
                /*
                case COAL_ORE:
                case EMERALD_BLOCK:
                case DIAMOND_BLOCK:
                case LAPIS_BLOCK:
                case WOOD:
                case WATER:
                case STATIONARY_WATER:
                case LAVA:
                case STATIONARY_LAVA:
                case SAND:
                case GRAVEL:
                case LOG:
                case SPONGE:
                case GLASS:
                case DISPENSER:
                case SANDSTONE:
                case NOTE_BLOCK:
                case WOOL:
                case PISTON_MOVING_PIECE:
                case GOLD_BLOCK:
                case IRON_BLOCK:
                case DOUBLE_STEP:
                case STEP:
                case BRICK:
                case TNT:
                case BOOKSHELF:
                case MOSSY_COBBLESTONE:
                case OBSIDIAN:
                case TORCH:
                case FIRE:
                case MOB_SPAWNER:
                case WOOD_STAIRS:
                case CHEST:
                case WORKBENCH:
                case SOIL:
                case FURNACE:
                case BURNING_FURNACE:
                case SIGN_POST:
                case WOODEN_DOOR:
                case LADDER:
                case COBBLESTONE_STAIRS:
                case WALL_SIGN:
                case LEVER:
                case STONE_PLATE:
                case IRON_DOOR_BLOCK:
                case WOOD_PLATE:
                case STONE_BUTTON:
                case SNOW:
                case ICE:
                case SNOW_BLOCK:
                case CLAY:
                case SUGAR_CANE_BLOCK:
                case JUKEBOX:
                case FENCE:
                case NETHERRACK:
                case SOUL_SAND:
                case GLOWSTONE:
                case PORTAL:
                case STAINED_GLASS:
                case MONSTER_EGGS:
                case SMOOTH_BRICK:
                case IRON_FENCE:
                case THIN_GLASS:
                case FENCE_GATE:
                case BRICK_STAIRS:
                case SMOOTH_STAIRS:
                case MYCEL:
                case NETHER_BRICK:
                case NETHER_FENCE:
                case NETHER_BRICK_STAIRS:
                case NETHER_WARTS:
                case ENCHANTMENT_TABLE:
                case BREWING_STAND:
                case CAULDRON:
                case ENDER_PORTAL:
                case ENDER_PORTAL_FRAME:
                case ENDER_STONE:
                case WOOD_DOUBLE_STEP:
                case WOOD_STEP:
                case SANDSTONE_STAIRS:
                case ENDER_CHEST:
                case SPRUCE_WOOD_STAIRS:
                case BIRCH_WOOD_STAIRS:
                case JUNGLE_WOOD_STAIRS:
                case COMMAND:
                case BEACON:
                case COBBLE_WALL:
                case FLOWER_POT:
                case WOOD_BUTTON:
                case SKULL:
                case ANVIL:
                case TRAPPED_CHEST:
                case GOLD_PLATE:
                case IRON_PLATE:
                case DAYLIGHT_DETECTOR:
                case REDSTONE_BLOCK:
                case QUARTZ_ORE:
                case HOPPER:
                case QUARTZ_BLOCK:
                case QUARTZ_STAIRS:
                case ACTIVATOR_RAIL:
                case DROPPER:
                case STAINED_CLAY:
                case STAINED_GLASS_PANE:
                case LEAVES_2:
                case LOG_2:
                case ACACIA_STAIRS:
                case DARK_OAK_STAIRS:
                case SLIME_BLOCK:
                case BARRIER:
                case IRON_TRAPDOOR:
                case PRISMARINE:
                case SEA_LANTERN:
                case HAY_BLOCK:
                case CARPET:
                case HARD_CLAY:
                case COAL_BLOCK:
                case PACKED_ICE:
                case DOUBLE_PLANT:
                case STANDING_BANNER:
                case WALL_BANNER:
                case DAYLIGHT_DETECTOR_INVERTED:
                case RED_SANDSTONE:
                case RED_SANDSTONE_STAIRS:
                case DOUBLE_STONE_SLAB2:
                case SIGN:
                case QUARTZ:
                    break;
                *//*
            }
        } else {
            msg = r(onInteracted_noBlock);
        }

        return tryMsg(pInteracted, msg);
    }

    @Override
    public boolean onDamaged(Player pDamaged, EntityDamageEvent e, int level) {
        double remainingHpPercent = pDamaged.getHealth() / pDamaged.getMaxHealth() * 100;


    }

    @Override
    public boolean onDamagedOther(Player pAttacker, EntityDamageByEntityEvent e, int level) {

    }

    @Override
    public boolean onInteractEntity(Player pInteracted, PlayerInteractEntityEvent e, int level) {
        e.getRightClicked().getType()
    }

    @Override
    public boolean onArrowHitEntity(Player shooter, EntityDamageByEntityEvent e, int level) {
        String msg = "";

        if (e.getEntity() instanceof LivingEntity) {
            LivingEntity le = (LivingEntity) e.getEntity();

            if (random.nextBoolean()) {
                double remainingHpPercent = le.getHealth() / le.getMaxHealth() * 100;

                if (remainingHpPercent < 25) {
                    msg = r(onArrowHitEntity_almostDead);
                } else if (remainingHpPercent < 50) {
                    msg = r(onArrowHitEntity_lowHp);
                } else if (remainingHpPercent < 75) {
                    msg = r(onArrowHitEntity_decentHP);
                } else {
                    msg = r(onArrowHitEntity_highHp);
                }
            } else {
                msg = this.getEntityTypeMsg(le);
            }
        } else {
            if (random.nextBoolean()) {
                msg = "Nice! You uh... Hit it, I guess.";
            } else {
                msg = "Did you mean to do that?";
            }
        }

        return tryMsg(shooter, msg);
    }

    private String getEntityTypeMsg(LivingEntity le){
        String msg = "";

        switch (le.getType()) {
            default:
                break;
            case CREEPER:
            case SKELETON:
            case SPIDER:
            case GIANT:
            case ZOMBIE:
            case SLIME:
            case GHAST:
            case PIG_ZOMBIE:
            case ENDERMAN:
            case CAVE_SPIDER:
            case SILVERFISH:
            case BLAZE:
            case MAGMA_CUBE:
            case ENDER_DRAGON:
            case WITHER:
            case BAT:
            case WITCH:
            case ENDERMITE:
            case GUARDIAN:
            case PIG:
            case SHEEP:
            case COW:
            case CHICKEN:
            case SQUID:
            case WOLF:
            case MUSHROOM_COW:
            case SNOWMAN:
            case OCELOT:
            case IRON_GOLEM:
            case HORSE:
            case RABBIT:
            case VILLAGER:
            case ENDER_CRYSTAL:
            case PLAYER:

        }

        return msg;
    }

    @Override
    public boolean onArrowHitGround(Player shooter, ProjectileHitEvent e, int level) {
        return tryMsg(shooter, r(onArrowHitGroundMsgs));
    }

    public boolean tryMsg(Player player, String msg) {
        this.takeCost(player, 1);

        // occasionally inject a location/time-based message
        // always do inject if msg is ""
        int inject;

        if (msg.equals("")) {
            inject = random.nextInt(8);
        } else {
            inject = random.nextInt(100);
        }

        if (inject < 5) {
            World world = player.getLocation().getWorld();

            if (random.nextInt(4) < 1 && world.getWorldType().equals(WorldType.FLAT)) {
                msg = msg_flat[random.nextInt(msg_flat.length)];
            } else if (random.nextBoolean() && world.isThundering()) {
                msg = msg_storm[random.nextInt(msg_storm.length)];
            } else {
                switch (world.getEnvironment()) {
                    case NORMAL:
                        switch (player.getLocation().getBlock().getBiome()) {

                            case FROZEN_OCEAN:
                            case FROZEN_RIVER:
                            case ICE_PLAINS:
                            case ICE_MOUNTAINS:
                            case COLD_TAIGA:
                            case COLD_TAIGA_HILLS:
                            case COLD_BEACH:
                            case TAIGA_HILLS:
                            case TAIGA_MOUNTAINS:
                            case MEGA_SPRUCE_TAIGA_HILLS:
                            case COLD_TAIGA_MOUNTAINS:
                            case ICE_PLAINS_SPIKES:
                            case TAIGA:
                            case MEGA_TAIGA:
                            case MEGA_TAIGA_HILLS:
                            case MEGA_SPRUCE_TAIGA:
                                msg = msg_cold[random.nextInt(msg_cold.length)];
                                break;

                            case DESERT_HILLS:
                            case DESERT:
                            case DESERT_MOUNTAINS:
                                msg = msg_desert[random.nextInt(msg_desert.length)];
                                break;

                            case MESA:
                            case MESA_PLATEAU_FOREST:
                            case MESA_PLATEAU:
                            case MESA_BRYCE:
                            case MESA_PLATEAU_FOREST_MOUNTAINS:
                            case MESA_PLATEAU_MOUNTAINS:

                            case BEACH:

                                break;
                            case OCEAN:
                                break;

                            case SWAMPLAND:
                            case FOREST:
                            case PLAINS:
                            case SKY:
                            case RIVER:
                            case EXTREME_HILLS:
                            case MUSHROOM_ISLAND:
                            case MUSHROOM_SHORE:
                            case FOREST_HILLS:
                            case SMALL_MOUNTAINS:
                            case JUNGLE:
                            case JUNGLE_HILLS:
                            case JUNGLE_EDGE:
                            case DEEP_OCEAN:
                            case STONE_BEACH:
                            case BIRCH_FOREST:
                            case BIRCH_FOREST_HILLS:
                            case ROOFED_FOREST:
                            case EXTREME_HILLS_PLUS:
                            case SAVANNA:
                            case SAVANNA_PLATEAU:
                            case SUNFLOWER_PLAINS:
                            case FLOWER_FOREST:
                            case SWAMPLAND_MOUNTAINS:
                            case JUNGLE_MOUNTAINS:
                            case JUNGLE_EDGE_MOUNTAINS:
                            case SAVANNA_MOUNTAINS:
                            case SAVANNA_PLATEAU_MOUNTAINS:
                            case BIRCH_FOREST_MOUNTAINS:
                            case BIRCH_FOREST_HILLS_MOUNTAINS:
                            case ROOFED_FOREST_MOUNTAINS:
                            case EXTREME_HILLS_MOUNTAINS:
                            case EXTREME_HILLS_PLUS_MOUNTAINS:

                        }
                        break;
                    case NETHER:
                        msg = msg_nether[random.nextInt(msg_nether.length)];
                        break;
                    case THE_END:
                        msg = msg_the_end[random.nextInt(msg_the_end.length)];
                        break;
                }
            }
        } else if (inject < 8) {
            long time = player.getWorld().getFullTime();

            if (time > 18000) {//midnight - 6am
                msg = r(msg_time_early_morning);
            } else if (time > 12000) {//6pm - midnight
                msg = r(msg_time_evening);
            } else if (time > 6000) {//noon - 6pm
                msg = r(msg_time_afternoon);
            } else {//6am - noon
                msg = r(msg_time_morning);
            }
        }

        ItemStack isHand = player.getItemInHand();
        String sItem = isHand.getType().name().toLowerCase().replace("_", " ");

        if (isHand.hasItemMeta()) {
            ItemMeta meta = isHand.getItemMeta();

            if (meta.hasDisplayName()) {
                sItem = meta.getDisplayName();
            }
        }

        player.sendMessage(ChatColor.GOLD + sItem + ChatColor.RESET + ": " + msg);

        String pMsg = player.getDisplayName() + ChatColor.GOLD + "'s " + sItem + ChatColor.RESET + ": " + msg;

        int d2 = 15 * 15;

        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            if (player != p && player.getWorld().equals(p.getWorld()) && player.getLocation().distanceSquared(p.getLocation()) < d2) {
                p.sendMessage(pMsg);
            }
        }

        return true;
    }*/
}