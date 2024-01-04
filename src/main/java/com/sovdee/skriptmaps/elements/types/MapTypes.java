package com.sovdee.skriptmaps.elements.types;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.registrations.Classes;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import java.awt.*;

public class MapTypes {
    static {

        Classes.registerClass(new ClassInfo<>(MapRenderer.class, "maplayer")
                .user("map ?layers?")
                .name("Map Layer")
                .description("A map layer.")
                .since("1.0.0"));

        Classes.registerClass(new ClassInfo<>(MapView.class, "mapview")
                .user("map ?views?")
                .name("Map View")
                .description("A map view.")
                .since("1.0.0"));
        if (Classes.getExactClassInfo(Image.class) == null)
            Classes.registerClass(new ClassInfo<>(Image.class, "image")
                    .user("images?")
                    .name("Image")
                    .description("An image.")
                    .since("1.0.0"));
    }
}
