package com.sovdee.skriptmaps.utils;

import org.jetbrains.annotations.Contract;

import java.awt.*;

/**
 * Contains utility methods for converting between AWT colours, Bukkit/Skript colours and colour strings.
 */
public class AWTColorUtils {

    /**
     * Converts a Bukkit colour to an AWT colour.
     * @param color The Bukkit colour to convert.
     * @return The AWT colour.
     */
    @Contract(value = "_ -> new", pure = true)
    public static Color fromBukkitColor(org.bukkit.Color color) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue());
    }

    /**
     * Converts an AWT colour to a Bukkit colour.
     * @param color The AWT colour to convert.
     * @return The Bukkit colour.
     */
    @Contract(value = "_ -> new", pure = true)
    public static org.bukkit.Color toBukkitColor(Color color) {
        return org.bukkit.Color.fromRGB(color.getRed(), color.getGreen(), color.getBlue());
    }

    /**
     * Converts a Skript colour to an AWT colour.
     * @param color The Skript colour to convert.
     * @return The AWT colour.
     */
    @Contract(value = "_ -> new", pure = true)
    public static Color fromSkriptColor(ch.njol.skript.util.Color color) {
        return fromBukkitColor(color.asBukkitColor());
    }

    /**
     * Converts an AWT colour to a Skript colour.
     * @param color The AWT colour to convert.
     * @return The Skript colour.
     */
    @Contract(value = "_ -> new", pure = true)
    public static ch.njol.skript.util.Color toSkriptColor(Color color) {
        return new ch.njol.skript.util.ColorRGB(color.getRed(), color.getGreen(), color.getBlue());
    }

    /**
     * Converts an AWT colour to a map colour string.
     * @param color The AWT colour to convert.
     * @return The map colour string, in the format §x; where x is the map colour code.
     */
    @Contract(value = "_ -> new", pure = true)
    public static String toMapColorString(Color color) {
        return String.format("§%d;", org.bukkit.map.MapPalette.matchColor(color));
    }

    /**
     * Converts a Bukkit colour to a map colour string.
     * @param color The Bukkit colour to convert.
     * @return The map colour string, in the format §x; where x is the map colour code.
     */
    @Contract(value = "_ -> new", pure = true)
    public static String toMapColorString(org.bukkit.Color color) {
        return toMapColorString(fromBukkitColor(color));
    }

    /**
     * Converts a Skript colour to a map colour string.
     * @param color The Skript colour to convert.
     * @return The map colour string, in the format §x; where x is the map colour code.
     */
    @Contract(value = "_ -> new", pure = true)
    public static String toMapColorString(ch.njol.skript.util.Color color) {
        return toMapColorString(color.asBukkitColor());
    }

    /**
     * Converts a red, green, and blue value to a map colour string.
     * @param red The red value.
     * @param green The green value.
     * @param blue The blue value.
     * @return The map colour string, in the format §x; where x is the map colour code.
     */
    @Contract(value = "_, _, _ -> new", pure = true)
    public static String toMapColorString(int red, int green, int blue) {
        return toMapColorString(new Color(red, green, blue));
    }
}
