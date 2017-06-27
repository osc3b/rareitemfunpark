package com.lonelymc.ri4.api;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

public interface IRareItem {
    int getId();

    Map<IRareItemProperty,Integer> getProperties();

    void setProperties(Map<IRareItemProperty,Integer> properties);

    RareItemStatus getStatus();

    void setStatus(RareItemStatus status);
}