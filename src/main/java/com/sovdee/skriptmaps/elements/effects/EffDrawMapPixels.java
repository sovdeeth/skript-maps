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
import com.sovdee.skriptmaps.utils.LayerEffect;
import org.bukkit.event.Event;
import org.bukkit.map.MapCanvas;
import org.checkerframework.checker.nullness.qual.Nullable;

@Name("Draw Map Pixels")
@Description("Sets the color of one or more pixels on a map. Must be used in a map layer section.")
@Examples({
        "set {_layer} to a new map layer:",
            "\tcolor the pixel at 0, 0 on the map as red"
})
@Since("1.0.0")
public class EffDrawMapPixels extends LayerEffect {

    static {
        Skript.registerEffect(EffDrawMapPixels.class,
                "colo[u]r %mappixels% on the map [as] %color%"
        );
    }

    Expression<MapPixel> pixels;
    Expression<Color> color;

    @Override
    protected boolean init(Expression<?>[] expressions, int matchedPattern, SkriptParser.ParseResult parseResult) {
        pixels = (Expression<MapPixel>) expressions[0];
        color = (Expression<Color>) expressions[1];
        return true;
    }

    @Override
    protected void execute(LayerRenderEvent event) {
        @Nullable Color color = this.color.getSingle(event);
        if (color == null)
            return;

        java.awt.Color awtColor = AWTColorUtils.fromSkriptColor(color);
        MapPixel[] pixels = this.pixels.getArray(event);
        MapCanvas canvas = event.getCanvas();
        for (MapPixel pixel : pixels)
            canvas.setPixelColor(pixel.x(), pixel.y(), awtColor);
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "color " + pixels.toString(event, debug) + " on the map as " + color.toString(event, debug);
    }
}
