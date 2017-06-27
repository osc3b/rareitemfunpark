package com.lonelymc.ri4.bukkit.rareitems;

import com.lonelymc.ri4.api.EssenceStatus;
import com.lonelymc.ri4.api.IEssence;
import com.lonelymc.ri4.api.IRareItemProperty;
import com.lonelymc.ri4.api.ItemPropertyRarity;


public class Essence implements IEssence {
    private final ItemPropertyRarity rarity;
    private IRareItemProperty property;
    private final int id;
    private EssenceStatus status;

    public Essence(int id, EssenceStatus status, ItemPropertyRarity rarity) {
        this.id = id;
        this.status = status;
        this.rarity = rarity;
    }

    public Essence(int id, EssenceStatus status, IRareItemProperty property) {
        this.id = id;
        this.status = status;
        this.rarity = property.getRarity();
        this.property = property;
    }

    @Override
    public int getId() {
        return this.id;
    }


    @Override
    public ItemPropertyRarity getRarity() {
        return this.rarity;
    }

    @Override
    public boolean hasProperty() {
        return this.property != null;
    }

    @Override
    public IRareItemProperty getProperty() {
        return this.property;
    }

    @Override
    public void setProperty(IRareItemProperty rip) {
        if(rip != null) {
            this.property = rip;
            this.status = EssenceStatus.FILLED;
        }
        else{
            this.property = null;
            this.status = EssenceStatus.EMPTY;
        }
    }

    @Override
    public EssenceStatus getStatus() {
        return this.status;
    }

    @Override
    public void setStatus(EssenceStatus status) {
        this.status = status;
    }

    @Override
    public String getMaterial() {
        return Essence.getMaterialByRarity(this.getRarity());
    }

    public static String getMaterialByRarity(ItemPropertyRarity rarity) {
        switch (rarity) {
            default: //case COMMON:
                return "CLAY_BALL";
            case UNCOMMON:
                return "GLOWSTONE_DUST";
            case RARE:
                return "MAGMA_CREAM";
            case LEGENDARY:
                return "NETHER_STAR";
            case STRANGE:
                return "SLIME_BALL";
        }
    }
}
