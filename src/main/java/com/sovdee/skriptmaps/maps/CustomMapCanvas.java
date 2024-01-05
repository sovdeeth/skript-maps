package com.sovdee.skriptmaps.maps;

import com.sovdee.skriptmaps.utils.ImageUtils;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapCursorCollection;
import org.bukkit.map.MapFont;
import org.bukkit.map.MapPalette;
import org.bukkit.map.MapView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.awt.image.BufferedImage;

public class CustomMapCanvas implements MapCanvas {

    private BufferedImage image;
    private final MapCanvas canvas;

    public CustomMapCanvas(MapCanvas canvas) {
        this.canvas = canvas;
        this.image = new BufferedImage(128, 128, BufferedImage.TYPE_INT_ARGB);
        readFromCanvas();
    }

    public void render() {
        writeToCanvas();
    }

    private void writeToCanvas() {
        canvas.drawImage(0, 0, image);
    }

    private void readFromCanvas() {
        ImageUtils.getImage(canvas, image);
    }

    public Graphics2D getGraphics() {
        return image.createGraphics();
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
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
    public void setPixel(int x, int y, byte color) {
        Color awtColor = MapPalette.getColor(color);
        setPixelColor(x, y, awtColor);
    }

    @Override
    public byte getPixel(int x, int y) {
        @Nullable Color color = getPixelColor(x, y);
        if (color == null)
            return 0;
        return MapPalette.matchColor(color);
    }

    @Override
    public byte getBasePixel(int x, int y) {
        return canvas.getBasePixel(x, y);
    }

    @Override
    public void drawImage(int x, int y, @NotNull Image image) {
        this.image = ImageUtils.copyImage(image);
    }

    @Override
    public void drawText(int x, int y, @NotNull MapFont font, @NotNull String text) {
        writeToCanvas();
        canvas.drawText(x, y, font, text);
        readFromCanvas();
    }
}
