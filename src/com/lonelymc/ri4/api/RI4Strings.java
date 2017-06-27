package com.lonelymc.ri4.api;

import com.avaje.ebean.enhance.ant.StringReplace;
import com.lonelymc.ri4.util.MetaStringEncoder;
import com.lonelymc.ri4.util.RomanNumeral;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// Utility class to route all strings through
public class RI4Strings {
    // Note: color codes in the format &DARK_RED will be translated automatically,
    // code using the strings is responsible for translating any variables
    // which should be in the format !variable

    public static String RAREITEM_HEADER = "&DARK_GRAYRare Item &GRAY#!id";
    public static String RAREITEM_COMMON = "&AQUA!property &DARK_AQUA!level";
    public static String RAREITEM_UNCOMMON = "&GREEN!property &DARK_GREEN!level";
    public static String RAREITEM_RARE = "&RED!property &DARK_RED!level";
    public static String RAREITEM_LEGENDARY = "&GOLD!property &DARK_RED!level";
    public static String RAREITEM_STRANGE = "&LIGHT_PURPLE!property &DARK_PURPLE!level";

    public static String ESSENCE_BLANK = "&DARK_GRAY(Empty)";
    public static String ESSENCE_FILLED = "&DARK_GRAY(Filled with !property&DARK_GRAY)";
    public static String ESSENCE_COMMON = "&GRAYCommon Essence";
    public static String ESSENCE_UNCOMMON = "&AQUAUncommon Essence";
    public static String ESSENCE_RARE = "&GREENRare Essence";
    public static String ESSENCE_LEGENDARY = "&GOLDLegendary Essence";
    public static String ESSENCE_STRANGE = "&LIGHT_PURPLEStrange Essence";
    public static String ESSENCE_FOOTER_1 = "&DARK_GRAYAn essence of power";
    public static String ESSENCE_FOOTER_2 = "&GRAY/ri wi &DARK_GRAYfor more info";

    public static String COMMAND_NOT_FROM_CONSOLE = "&REDCannot be used from the console";
    public static String COMMAND_HAT_EQUIPPED = "&GREENEnjoy your new hat!";
    public static String COMMAND_INVALID_HAT = "&REDYou can't wear that! It would look horrible and clash with your outfit";
    public static String COMMAND_MUST_HOLD_ITEM = "&REDYou must be holding an item!";
    public static String COMMAND_ADDED_PROPERTY_TO_ITEM = "&GREENAdded !property to the item in your hand";
    public static String COMMAND_INVALID_LEVEL = "&RED'!value' is not a valid level!";
    public static String COMMAND_VALUE_NOT_FOUND = "&RED'!value' not found!";
    public static String COMMAND_PLAYER_NOT_FOUND = "Player '!player' not found!";
    public static String COMMAND_IF_CONSOLE_REQUIRES_PLAYER = "If used from console you must specify a player!";
    public static String COMMAND_VALID_TYPES_ARE = "Valid types are: !types";
    public static String COMMAND_INVALID_ESSENCE_OR_PROPERTY = "!property is not a valid essence or item property type!";
    public static String COMMAND_INVALID_PROPERTY = "!property is not a valid item property type!";
    public static String COMMAND_GAVE_ESSENCE_SENDER = "&GREENGave &RESET!player &GREENa &RESET!essence &GREENessence!";
    public static String COMMAND_GAVE_ESSENCE_RECEIVER = "&GREENYou got a &RESET!essence &GREENessence!";
    public static String COMMAND_INVENTORY_WAS_FULL_ON_GROUND_NOW = "Tried to give you !item but your inventory was full so it was dropped on the ground";
    public static String COMMAND_YOU_CAN_USE = "Commands you have access to:";
    public static String MAX_PROPERTIES_EXCEEDED = "&REDYou can put at most !max properties on an item, you put !amount!";

    public static String COM_CRAFT = "craft";
    public static String COM_CRAFT_USAGE = "<property_name> [level]";
    public static String COM_CRAFT_DESC = "Add a rare item property to the item in your hand";
    
    public static String COM_ESSENCE = "essence";
    public static String COM_ESSENCE_USAGE = "<essence_type> [playerName]";
    public static String COM_ESSENCE_DESC = "create a rare essence";
    
    public static String COM_WI = "wi";
    public static String COM_WI_USAGE = "[property_name]";
    public static String COM_WI_DESC = "List or view available properties";

    public static String COM_RECIPE = "recipe";
    public static String COM_RECIPE_USAGE = "<property_name> [remove]";
    public static String COM_RECIPE_DESC = "Edit or remove the recipe for a property";

    public static String LOG_RAREITEM_DISABLED = "!property not loaded because enable=false in rareitems.yml";
    public static String LOG_UNABLE_TO_SAVE_FILE = "!file could not be saved!";
    public static String RIP_NEED_MORE_COOLDOWN = "&REDYou need to wait !seconds more seconds to use !property!";
    public static String RIP_NEED_MORE_HEALTH = "&REDYou need !health more health to use !property!";
    public static String RIP_NEED_MORE_EXPERIENCE = "&REDYou need !experience more experience to use !property!";
    public static String RIP_NEED_MORE_FOOD = "&REDYou need !food more food to use !property!";
    public static String CRAFTING_ESSENCE_ALREADY_USED = "!property essence has already been used!";
    public static String COMMAND_AVAILABLE_PROPERTIES = "Available properties:";
    public static String COMMAND_MAX_LEVEL = "&REDThe max level for !property is !level!";
    public static String COST_FOOD = "Costs !cost food / use";
    public static String COST_HEALTH = "Costs !cost HP / use";
    public static String COST_EXPERIENCE = "Costs !cost XP / use";
    public static String COST_AUTOMATIC = "Automatic while worn";
    public static String COST_PASSIVE = "Automatic while worn";
    public static String COST_COOLDOWN = "!cost second cooldown";
    public static String COMMAND_MULTILINE_RI_DESCRIPTION = "&GRAY!property (!rarity)\n&GRAYMax: &RESET!maxLevel\n&GRAYCost: &RESET!costMsg\n&GRAYRecipe: &RESET!hasRecipe\n!description";
    public static String COMMAND_NO_RECIPE = "No recipe";
    public static String COMMAND_HAS_RECIPE = "Has recipe";
    public static String COMMAND_NO_RIP_RECIPE_EXISTS = "No recipe exists for !property!";
    public static String COMMAND_ASSUMING_LEVEL_1 = "Invalid level '!level', assuming level 1";
    public static String COMMAND_RECIPE_REMOVED = "Recipe for !property was removed!";
    public static String RECIPE_UPDATED = "Recipe for !property was updated!";
    public static String CRAFTING_NEED_ESSENCE = "&REDEach recipe must have a single essence!";
    public static String NMS_CRAFTING_NOT_SUPPORTED = "Crafting is not fully supported under your server version (!version), please check for an update!";

    public static String API_UNABLE_TO_SAVE = "Unable to save !file!";

    // These aren't actually displayed as it turns out, but are necessary to statelessly identify certain GUI elements
    public final static String CRAFTING_VIEW_RARE_ITEM_RECIPE = "RIPVIEWER";
    public final static String CRAFTING_RECIPE_EDITOR = "RIPEDITTOR";
    public static String COMMAND_HAT_PERMISSION = "&REDYou do not have permission to use &WHITE/hat&RED, Required node: &WHITEri4.hat";

    public static String getDisplayName(IEssence essence) {
        switch (essence.getRarity()) {
            default: //case COMMON:
                return ESSENCE_COMMON;
            case UNCOMMON:
                return ESSENCE_UNCOMMON;
            case RARE:
                return ESSENCE_RARE;
            case LEGENDARY:
                return ESSENCE_LEGENDARY;
            case STRANGE:
                return ESSENCE_STRANGE;
        }
    }

    public static List<String> getItemLore(IEssence essence) {
        List<String> lore = new ArrayList<>();

        if (!essence.hasProperty()) {
            lore.add(ESSENCE_BLANK);
        } else {
            lore.add(
                    ESSENCE_FILLED.replace("!property", essence.getProperty().getDisplayName())
            );
        }

        lore.add(ESSENCE_FOOTER_1.replace("!id", essence.getId() + "") + MetaStringEncoder.encodeHidden(String.valueOf(essence.getId()), "es"));

        lore.add(ESSENCE_FOOTER_2);

        return lore;
    }

    public static List<String> getLore(IRareItem ri) {
        List<String> lore = new ArrayList<>();

        lore.add(RAREITEM_HEADER.replace("!id", ri.getId() + "") + MetaStringEncoder.encodeHidden(String.valueOf(ri.getId()), "ri"));

        for (Map.Entry<IRareItemProperty, Integer> entry : ri.getProperties().entrySet()) {
            lore.add(RI4Strings.getRareItemLoreString(entry.getKey(),entry.getValue()));
        }

        return lore;
    }

    public static String getRareItemLoreString(IRareItemProperty rip,int level){
        switch (rip.getRarity()) {
            default: //case COMMON:
                return RAREITEM_COMMON
                        .replace("!property", rip.getDisplayName())
                        .replace("!level", RomanNumeral.convertToRoman(level));
            case UNCOMMON:
                return RAREITEM_UNCOMMON
                        .replace("!property", rip.getDisplayName())
                        .replace("!level", RomanNumeral.convertToRoman(level));
            case RARE:
                return RAREITEM_RARE
                        .replace("!property", rip.getDisplayName())
                        .replace("!level", RomanNumeral.convertToRoman(level));
            case LEGENDARY:
                return RAREITEM_LEGENDARY
                        .replace("!property", rip.getDisplayName())
                        .replace("!level", RomanNumeral.convertToRoman(level));
            case STRANGE:
                return RAREITEM_STRANGE
                        .replace("!property", rip.getDisplayName())
                        .replace("!level", RomanNumeral.convertToRoman(level));
        }
    }

    public static String getCostMessage(double cost, PropertyCostType costType) {
        switch(costType){
            case FOOD:
                return RI4Strings.COST_FOOD.replace("!cost",String.valueOf(cost));
            case HEALTH:
                return RI4Strings.COST_HEALTH.replace("!cost", String.valueOf(cost));
            case EXPERIENCE:
                return RI4Strings.COST_EXPERIENCE.replace("!cost", String.valueOf(cost));
            case AUTOMATIC:
                return RI4Strings.COST_AUTOMATIC.replace("!cost", String.valueOf(cost));
            case PASSIVE:
                return RI4Strings.COST_PASSIVE.replace("!cost", String.valueOf(cost));
            case COOLDOWN:
                return RI4Strings.COST_COOLDOWN.replace("!cost", String.valueOf(cost));
        }

        return null;
    }

    public static String tc(String configString) {
        for (ChatColor cc : ChatColor.values()) {
            configString.replace("&" + cc.name().toUpperCase(), cc.toString());
        }

        return configString;
    }
}
