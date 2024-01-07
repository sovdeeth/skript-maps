package com.sovdee.skriptmaps.maps;

import com.sovdee.skriptmaps.utils.ImageUtils;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapCursorCollection;
import org.bukkit.map.MapFont;
import org.bukkit.map.MapPalette;
import org.bukkit.map.MapView;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * A custom map canvas, backed by a BufferedImage and a true MapCanvas.
 */
public class CustomMapCanvas implements MapCanvas {

    private final BufferedImage image;
    private final MapCanvas canvas;

    public CustomMapCanvas(MapCanvas canvas) {
        this.canvas = canvas;
        this.image = new BufferedImage(128, 128, BufferedImage.TYPE_INT_ARGB);
        readFromCanvas();
    }

    /**
     * Writes the image to the backing canvas.
     */
    public void render() {
        writeToCanvas();
    }

    /**
     * Draws the backing image to the canvas at (0, 0).
     */
    private void writeToCanvas() {
        canvas.drawImage(0, 0, image);
    }

    /**
     * Reads the current state of the backing canvas into the backing image.
     */
    private void readFromCanvas() {
        ImageUtils.getImage(canvas, image);
    }

    /**
     * Gets the Graphics2D object for the backing image. This can be used to draw on the image.
     * @return The Graphics2D object for the backing image.
     */
    @Contract(value = "-> new", pure = true)
    public Graphics2D getGraphics() {
        return image.createGraphics();
    }

    /**
     * Gets the backing image.
     * @return The backing image.
     */
    public BufferedImage getImage() {
        return image;
    }

    @Override
    public @NotNull MapView getMapView() {
        return canvas.getMapView();
    }

    @Override
    public @NotNull MapCursorCollection getCursors() {
        return canvas.getCursors();
    }

    @Override
    public void setCursors(@NotNull MapCursorCollection cursors) {
        canvas.setCursors(cursors);
    }

    @Override
    public void setPixelColor(int x, int y, @Nullable Color color) {
        if (x >= 0 && x < 128 && y >= 0 && y < 128) {
            if (color == null)
                color = new Color(0, 0, 0, 0);
            image.setRGB(x, y, color.getRGB());
        }
    }

    @Override
    public @Nullable Color getPixelColor(int x, int y) {
        if (x >= 0 && x < 128 && y >= 0 && y < 128)
            return new Color(image.getRGB(x, y));
        return null;
    }

    @Override
    public @NotNull Color getBasePixelColor(int x, int y) {
        return canvas.getBasePixelColor(x, y);
    }

    @Override
    @Deprecated
    public void setPixel(int x, int y, byte color) {
        Color awtColor = MapPalette.getColor(color);
        setPixelColor(x, y, awtColor);
    }

    @Override
    @Deprecated
    public byte getPixel(int x, int y) {
        @Nullable Color color = getPixelColor(x, y);
        if (color == null)
            return 0;
        return MapPalette.matchColor(color);
    }

    @Override
    @Deprecated
    public byte getBasePixel(int x, int y) {
        return canvas.getBasePixel(x, y);
    }

    @Override
    public void drawImage(int x, int y, @NotNull Image image) {
        getGraphics().drawImage(image, x, y, null);
    }

    @Override
    public void drawText(int x, int y, @NotNull MapFont font, @NotNull String text) {
        // ensure the canvas is up-to-date, so we can use the backing canvas to draw the text
        writeToCanvas();
        canvas.drawText(x, y, font, text);
        readFromCanvas();
    }
}
