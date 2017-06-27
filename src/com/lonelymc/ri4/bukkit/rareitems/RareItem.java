package com.lonelymc.ri4.bukkit.rareitems;

import com.lonelymc.ri4.api.IRareItem;
import com.lonelymc.ri4.api.IRareItemProperty;
import com.lonelymc.ri4.api.RareItemStatus;

import java.util.HashMap;
import java.util.Map;

public class RareItem implements IRareItem {
    private final int id;
    private Map<IRareItemProperty, Integer> properties;
    private RareItemStatus status;

    public RareItem(int id, Map<IRareItemProperty, Integer> properties, RareItemStatus status) {
        this.id = id;
        this.properties = properties;
        this.status = status;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Map<IRareItemProperty, Integer> getProperties() {
        Map<IRareItemProperty, Integer> temp = new HashMap<>();

        temp.putAll(this.properties);

        return temp;
    }

    @Override
    public void setProperties(Map<IRareItemProperty, Integer> properties) {
        this.properties = properties;
    }

    @Override
    public RareItemStatus getStatus() {
        return this.status;
    }

    @Override
    public void setStatus(RareItemStatus status) {
        this.status = status;
    }
}
