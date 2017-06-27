package com.lonelymc.ri4.util;

import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

import java.lang.reflect.Method;

public class FireworkVisualEffect {
    private Method world_getHandle = null;
    private Method nms_world_broadcastEntityEffect = null;
    private Method firework_getHandle = null;

    public void playFirework(World world, Location loc, FireworkEffect fe)
            throws Exception {
        Firework fw = (Firework) world.spawnEntity(loc, EntityType.FIREWORK);


        Object nms_world = null;
        Object nms_firework = null;
        if (this.world_getHandle == null) {
            this.world_getHandle = getMethod(world.getClass(), "getHandle");
            this.firework_getHandle = getMethod(fw.getClass(), "getHandle");
        }
        nms_world = this.world_getHandle.invoke(world, (Object[]) null);
        nms_firework = this.firework_getHandle.invoke(fw, (Object[]) null);
        if (this.nms_world_broadcastEntityEffect == null) {
            this.nms_world_broadcastEntityEffect = getMethod(nms_world.getClass(), "broadcastEntityEffect");
        }
        FireworkMeta data = fw.getFireworkMeta();

        data.clearEffects();

        data.setPower(1);

        data.addEffect(fe);

        fw.setFireworkMeta(data);

        this.nms_world_broadcastEntityEffect.invoke(nms_world, nms_firework, Byte.valueOf((byte) 17));

        fw.remove();
    }

    private static Method getMethod(Class<?> cl, String method) {
        for (Method m : cl.getMethods()) {
            if (m.getName().equals(method)) {
                return m;
            }
        }
        return null;
    }
}