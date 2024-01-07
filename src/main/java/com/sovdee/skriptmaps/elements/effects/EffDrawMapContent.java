package com.sovdee.skriptmaps.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import com.sovdee.skriptmaps.maps.LayerRenderEvent;
import com.sovdee.skriptmaps.maps.MapPixel;
import com.sovdee.skriptmaps.utils.LayerEffect;
import org.bukkit.event.Event;
import org.bukkit.map.MinecraftFont;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.awt.*;

@Name("Draw Map Content")
@Description("Draws text or images on a map. Must be used in a map layer section.")
@Examples({
        "set {_img} to the map image from file \"myimage.png\"",
        "set {_layer} to a new map layer:",
            "\tdraw {_img} on the map at pixel 0, 0",
            "\tdraw \"%mapColor(red)%%Hello, world!\" on the map at pixel 40, 60",
})
@Since("1.0.0")
public class EffDrawMapContent extends LayerEffect {

    static {
        Skript.registerEffect(EffDrawMapContent.class,
                "(draw|render) %image/string% on the map at %mappixel%"
        );
    }

    Expression<?> content;
    Expression<MapPixel> pixel;

    @Override
    protected boolean init(Expression<?>[] expressions, int matchedPattern, ParseResult parseResult) {
        content = expressions[0];
        pixel = (Expression<MapPixel>) expressions[1];
        return true;
    }

    @Override
    protected void execute(LayerRenderEvent event) {
        @Nullable Object content = this.content.getSingle(event);
        @Nullable MapPixel pixel = this.pixel.getSingle(event);
        if (content == null || pixel == null)
            return;

        if (content instanceof Image image)
            event.getCanvas().drawImage(pixel.x(), pixel.y(), image);
        else if (content instanceof String text)
            event.getCanvas().drawText(pixel.x(), pixel.y(), MinecraftFont.Font, text);
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "draw " + content.toString(event, debug) + " on the map at " + pixel.toString(event, debug);
    }
}
