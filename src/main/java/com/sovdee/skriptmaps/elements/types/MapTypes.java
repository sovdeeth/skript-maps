package com.sovdee.skriptmaps.elements.types;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import com.sovdee.skriptmaps.maps.MapPixel;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        Classes.registerClass(new ClassInfo<>(MapPixel.class, "mappixel")
                .user("map ?pixels?")
                .name("Map Pixel")
                .description("A pixel on a map.")
                .since("1.0.0")
                .parser(new Parser<>() {

                    final Pattern variablePattern = Pattern.compile("pixel:(\\d+),\\s?(\\d+)");

                    @Override
                    public @Nullable MapPixel parse(String s, ParseContext context) {
                        Matcher variableMatcher = variablePattern.matcher(s);
                        if (variableMatcher.matches())
                            return new MapPixel(Integer.parseInt(variableMatcher.group(1)), Integer.parseInt(variableMatcher.group(2)));
                        return null;
                    }

                    @Override
                    public boolean canParse(ParseContext context) {
                        return context == ParseContext.COMMAND;
                    }

                    @Override
                    public String toString(MapPixel o, int flags) {
                        return "the pixel at " + o.x() + ", " + o.y();
                    }

                    @Override
                    public String toVariableNameString(MapPixel o) {
                        return "pixel:" + o.x() + "," + o.y();
                    }
                }));


        if (Classes.getExactClassInfo(Image.class) == null)
            Classes.registerClass(new ClassInfo<>(Image.class, "image")
                    .user("images?")
                    .name("Image")
                    .description("An image.")
                    .since("1.0.0"));
    }
}
