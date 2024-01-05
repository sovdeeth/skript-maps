package com.sovdee.skriptmaps.utils;

import org.bukkit.map.MapCanvas;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageUtils {

    public static Image getImage(MapCanvas canvas) {
        BufferedImage image = new BufferedImage(128, 128, BufferedImage.TYPE_INT_ARGB);
        return getImage(canvas, image);
    }

    public static Image getImage(MapCanvas canvas, BufferedImage image) {
        @Nullable Color color;
        for (int x = 0; x < 128; x++) {
            for (int y = 0; y < 128; y++) {
                color = canvas.getPixelColor(x, y);
                if (color == null)
                    color = canvas.getBasePixelColor(x, y);
                image.setRGB(x, y, color.getRGB());
            }
        }
        return image;
    }

    public static BufferedImage copyImage(Image source){
        BufferedImage b = new BufferedImage(source.getWidth(null), source.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics g = b.getGraphics();
        g.drawImage(source, 0, 0, null);
        g.dispose();
        return b;
    }

    /**
     * Converts a given Image into a BufferedImage
     *
     * @param img The Image to be converted
     * @return The converted BufferedImage
     */
    public static BufferedImage toBufferedImage(Image img)
    {
        if (img instanceof BufferedImage)
        {
            return (BufferedImage) img;
        }

        return copyImage(img);
    }
}
