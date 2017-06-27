package com.lonelymc.ri4.api;

import java.util.Date;
import java.util.UUID;

public interface IEssence {
    String getMaterial();

    int getId();

    boolean hasProperty();

    ItemPropertyRarity getRarity();

    IRareItemProperty getProperty();

    EssenceStatus getStatus();

    void setProperty(IRareItemProperty rip);

    void setStatus(EssenceStatus status);
}
