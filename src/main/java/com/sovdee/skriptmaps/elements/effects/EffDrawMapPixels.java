package com.sovdee.skriptmaps.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.util.Color;
import com.sovdee.skriptmaps.MapLayerRenderEvent;
import com.sovdee.skriptmaps.utils.AWTColorUtils;
import com.sovdee.skriptmaps.utils.LayerEffect;
import org.bukkit.event.Event;
import org.checkerframework.checker.nullness.qual.Nullable;

@Name("Draw Map Pixels")
@Description("Sets the color of one or more pixels on a map. Must be used in a map layer section.")
@Examples({
        "set {_layer} to a new map layer:",
            "\tcolor the pixel at 0, 0 on the map as red",
            "\tcolor the pixels between 0, 0 and 127, 127 on the map red",
            "\tcolor the pixels in radius 10 of 64, 64 on the map rgb(30, 50, 80)",
})
@Since("1.0.0")
public class EffDrawMapPixels extends LayerEffect {

    static {
        Skript.registerEffect(EffDrawMapPixels.class,
                "colo[u]r [the] pixel [at] %number%,[ ]%number% on the map [as] %color%",
                "colo[u]r [the] pixels (between|:within) [[the] pixel [at]] %number%,[ ]%number% and [[the] pixel [at]] %number%,[ ]%number% on the map [as] %color%",
                "colo[u]r [the] pixels in radius %number% [of|around] [[the] pixel [at]] %number%,[ ]%number% on the map [as] %color%"
        );
    }

    Expression<Number> x1, y1;
    @Nullable Expression<Number> x2, y2, radius;
    Expression<Color> color;

    boolean fill;
    int matchedPattern;

    @Override
    protected boolean init(Expression<?>[] expressions, int matchedPattern, SkriptParser.ParseResult parseResult) {
        fill = false;
        this.matchedPattern = matchedPattern;
        switch (matchedPattern) {
            case 0 -> {
                x1 = (Expression<Number>) expressions[0];
                y1 = (Expression<Number>) expressions[1];
                color = (Expression<Color>) expressions[2];
            }
            case 1 -> {
                x1 = (Expression<Number>) expressions[0];
                y1 = (Expression<Number>) expressions[1];
                x2 = (Expression<Number>) expressions[2];
                y2 = (Expression<Number>) expressions[3];
                color = (Expression<Color>) expressions[4];
                fill = parseResult.hasTag("within");
            }
            case 2 -> {
                radius = (Expression<Number>) expressions[0];
                x1 = (Expression<Number>) expressions[1];
                y1 = (Expression<Number>) expressions[2];
                color = (Expression<Color>) expressions[3];
                fill = true;
            }
        }
        return true;
    }

    @Override
    protected void execute(MapLayerRenderEvent event) {
        @Nullable Number x1 = this.x1.getSingle(event);
        @Nullable Number y1 = this.y1.getSingle(event);
        @Nullable Color color = this.color.getSingle(event);
        if (x1 == null || y1 == null || color == null)
            return;

        java.awt.Color awtColor = AWTColorUtils.fromSkriptColor(color);

        switch (matchedPattern) {
            case 0 -> setPixelColor(event, x1.intValue(), y1.intValue(), awtColor);
            case 1 -> {
                assert x2 != null;
                assert y2 != null;
                @Nullable Number x2 = this.x2.getSingle(event);
                @Nullable Number y2 = this.y2.getSingle(event);
                if (x2 == null || y2 == null)
                    return;
                if (fill)
                    drawRect(event, x1.intValue(), y1.intValue(), x2.intValue(), y2.intValue(), awtColor);
                else
                    drawLine(event, x1.intValue(), y1.intValue(), x2.intValue(), y2.intValue(), awtColor);

            }
            case 2 -> {
                assert radius != null;
                @Nullable Number radius = this.radius.getSingle(event);
                if (radius == null)
                    return;
                drawCircle(event, x1.intValue(), y1.intValue(), radius.intValue(), awtColor);
            }
        }
    }

    private void drawRect(MapLayerRenderEvent event, int x1, int y1, int x2, int y2, java.awt.Color color) {
        int minX = Math.min(x1, x2);
        int maxX = Math.max(x1, x2);
        int minY = Math.min(y1, y2);
        int maxY = Math.max(y1, y2);
        for (int x = minX; x <= maxX; x++)
            for (int y = minY; y <= maxY; y++)
                setPixelColor(event, x, y, color);
    }

    private void drawLine(MapLayerRenderEvent event, int x1, int y1, int x2, int y2, java.awt.Color color) {
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = x1 < x2 ? 1 : -1;
        int sy = y1 < y2 ? 1 : -1;
        int err = dx - dy;
        while (true) {
            setPixelColor(event, x1, y1, color);
            if (x1 == x2 && y1 == y2)
                break;
            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x1 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y1 += sy;
            }
        }
    }

    // draws a solid disc around the center point
    private void drawCircle(MapLayerRenderEvent event, int x0, int y0, int radius, java.awt.Color color) {
        int x1 = x0 - radius;
        int y1 = y0 - radius;
        int x2 = x0 + radius;
        int y2 = y0 + radius;
        float radiusSquared = radius * radius;
        for (int x = x1; x <= x2; x++)
            for (int y = y1; y <= y2; y++)
                if (Math.pow(x - x0, 2) + Math.pow(y - y0, 2) <= radiusSquared)
                    setPixelColor(event, x, y, color);
    }

    private void setPixelColor(MapLayerRenderEvent event, int x, int y, java.awt.Color color) {
        if (x < 0 || x > 127 || y < 0 || y > 127)
            return;
        event.getCanvas().setPixelColor(x, y, color);
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return null;
    }
}
