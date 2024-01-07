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

@Name("Draw Map Rectangle")
@Description("Draws a rectangle on a map. Must be used in a map layer section.")
@Examples({
        "set {_layer} to a new map layer:",
            "\tcolour the pixels within pixel 0, 0 and pixel 127, 127 on the map red",
            "\tdraw a rectangle from pixel 88, 20 to pixel 40, 40 on the map using red",
})
@Since("1.0.0")
public class EffRenderRect extends GraphicsEffect {

    static {
        Skript.registerEffect(EffRenderRect.class,
                "colo[u]r [the] pixels witihin %mappixel% (and|to) %mappixel% on the map [as] %color%",
                "(draw|render) [a] [:solid] rect[angle] (within|from) %mappixel% (and|to) %mappixel% on the map [with|in|using] %color%"
        );
    }

    Expression<MapPixel> start, end;
    Expression<Color> color;

    boolean fill;

    @Override
    protected boolean init(Expression<?>[] expressions, int matchedPattern, SkriptParser.ParseResult parseResult) {
        start = (Expression<MapPixel>) expressions[0];
        end = (Expression<MapPixel>) expressions[1];
        color = (Expression<Color>) expressions[2];
        fill = matchedPattern == 0 || parseResult.hasTag("solid");
        return true;
    }

    @Override
    protected void execute(LayerRenderEvent event, Graphics graphics) {
        @Nullable Color color = this.color.getSingle(event);
        @Nullable MapPixel start = this.start.getSingle(event);
        @Nullable MapPixel end = this.end.getSingle(event);
        if (color == null || start == null || end == null) return;

        graphics.setColor(AWTColorUtils.fromSkriptColor(color));
        if (fill) {
            graphics.fillRect(start.x(), start.y(), end.x() - start.x(), end.y() - start.y());
        } else {
            graphics.drawRect(start.x(), start.y(), end.x() - start.x(), end.y() - start.y());
        }
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "render line from " + start.toString(event, debug) + " to " + end.toString(event, debug) + " on the map with color " + color.toString(event, debug);
    }
}
