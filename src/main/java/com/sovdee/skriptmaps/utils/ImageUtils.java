package com.sovdee.skriptmaps.utils;

import org.bukkit.map.MapCanvas;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.Contract;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Utility class for dealing with {@link Image}s
 */
public class ImageUtils {

    /**
     * Converts a given {@link MapCanvas} into an {@link Image}
     *
     * @param canvas The {@link MapCanvas} to be converted
     * @return The converted {@link Image}
     */
    @Contract(value = "_ -> new", pure = true)
    public static Image getImage(MapCanvas canvas) {
        BufferedImage image = new BufferedImage(128, 128, BufferedImage.TYPE_INT_ARGB);
        return getImage(canvas, image);
    }

    /**
     * Converts a given {@link MapCanvas} into an {@link Image}, writing it to a given {@link Image}
     *
     * @param canvas The {@link MapCanvas} to be converted
     * @param image The {@link Image} to be written to
     * @return The converted {@link Image}
     */
    @Contract(value = "_, _ -> param2", pure = true)
    public static Image getImage(MapCanvas canvas, BufferedImage image) {
        @Nullable Color color;
        for (int x = 0; x < 128; x++) {
            for (int y = 0; y < 128; y++) {
                color = canvas.getPixelColor(x, y);
                if (color == null)
                    color = new Color(0,0,0,0);
                image.setRGB(x, y, color.getRGB());
            }
        }
        return image;
    }

    /**
     * Copies a given {@link Image}
     *
     * @param source The {@link Image} to be copied
     * @return The copied {@link Image}
     */
    @Contract(value = "_ -> new", pure = true)
    public static BufferedImage copyImage(Image source){
        BufferedImage b = new BufferedImage(source.getWidth(null), source.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics g = b.getGraphics();
        g.drawImage(source, 0, 0, null);
        g.dispose();
        return b;
    }

    /**
     * Converts a given Image into a BufferedImage. If the given Image is already a BufferedImage, it is returned.
     *
     * @param img The Image to be converted
     * @return The converted BufferedImage
     */
    public static BufferedImage toBufferedImage(Image img)
    {
        if (img instanceof BufferedImage)
            return (BufferedImage) img;
        return copyImage(img);
    }
}
