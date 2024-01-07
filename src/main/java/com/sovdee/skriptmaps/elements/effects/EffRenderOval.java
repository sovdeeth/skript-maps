package com.sovdee.skriptmaps.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.util.Color;
import com.sovdee.skriptmaps.maps.LayerRenderEvent;
import com.sovdee.skriptmaps.maps.MapPixel;
import com.sovdee.skriptmaps.utils.AWTColorUtils;
import com.sovdee.skriptmaps.utils.GraphicsEffect;
import org.bukkit.event.Event;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.awt.*;

@Name("Draw Map Oval")
@Description("Draws an oval or a circle on a map. Must be used in a map layer section.")
@Examples({
        "set {_layer} to a new map layer:",
            "\tcolour the pixels in radius 25 of pixel 127, 127 on the map red",
            "\tdraw an oval of width 20 and height 30 around pixel 40, 20 on the map using red",
})
@Since("1.0.0")
public class EffRenderOval extends GraphicsEffect {

    static {
        Skript.registerEffect(EffRenderOval.class,
                "colo[u]r [the] pixels in radius %number% [of|around] %mappixel% on the map [as] %color%",
                "(draw|render) [a[n]] [:solid] circle (with|of) radius %number% (at|around) %mappixel% on the map [with|in|colo[u]red] %color%",
                "(draw|render) [a[n]] [:solid] oval (with|of) width %number% and height %number% (at|around) %mappixel% on the map [with|in|using] %color%"
        );
    }

    Expression<MapPixel> center;
    Expression<Number> radius;
    Expression<Number> width, height;
    Expression<Color> color;

    int matchedPattern;
    boolean fill;

    @Override
    protected boolean init(Expression<?>[] expressions, int matchedPattern, SkriptParser.ParseResult parseResult) {
        switch (matchedPattern) {
            case 0, 1 -> {
                radius = (Expression<Number>) expressions[0];
                center = (Expression<MapPixel>) expressions[1];
            }
            case 2 -> {
                width = (Expression<Number>) expressions[0];
                height = (Expression<Number>) expressions[1];
                center = (Expression<MapPixel>) expressions[2];
            }
        }
        color = (Expression<Color>) expressions[expressions.length - 1];
        this.matchedPattern = matchedPattern;
        fill = matchedPattern == 0 || parseResult.hasTag("solid");
        return true;
    }

    @Override
    protected void execute(LayerRenderEvent event, Graphics graphics) {
        @Nullable Color color = this.color.getSingle(event);
        @Nullable MapPixel center = this.center.getSingle(event);
        if (color == null || center == null) return;

        graphics.setColor(AWTColorUtils.fromSkriptColor(color));

        @Nullable Number width = null, height = null;
        switch (matchedPattern) {
            case 0, 1 -> width = height = radius.getSingle(event);
            case 2 -> {
                width = this.width.getSingle(event);
                height = this.height.getSingle(event);
            }
        }
        if (width == null || height == null) return;
        if (matchedPattern <= 1)
            width = height = width.intValue() * 2;

        if (fill) {
            graphics.fillOval(center.x(), center.y(), width.intValue(), height.intValue());
        } else {
            graphics.drawOval(center.x(), center.y(), width.intValue(), height.intValue());
        }

    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        if (matchedPattern <= 1)
            return "render " + (fill ? "solid" : "") + " circle with radius " + radius.toString(event, debug) + " at " + center.toString(event, debug) + " on the map with color " + color.toString(event, debug);
        return "render " + (fill ? "solid" : "") + " oval with width " + width.toString(event, debug) + " and height " + height.toString(event, debug) + " at " + center.toString(event, debug) + " on the map with color " + color.toString(event, debug);
    }
}
