package com.sovdee.skriptmaps.elements.effects;

import ch.njol.skript.Skript;
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

public class EffRenderLine extends GraphicsEffect {

    static {
        Skript.registerEffect(EffRenderLine.class,
                "colo[u]r [the] pixels (between|from) %mappixel% (and|to) %mappixel% on the map [as] %color%",
                "(draw|render) [a] line (between|from) %mappixel% (and|to) %mappixel% on the map [with|in|colo[u]red] %color%"
        );
    }

    Expression<MapPixel> start, end;
    Expression<Color> color;

    @Override
    protected boolean init(Expression<?>[] expressions, int matchedPattern, SkriptParser.ParseResult parseResult) {
        start = (Expression<MapPixel>) expressions[0];
        end = (Expression<MapPixel>) expressions[1];
        color = (Expression<Color>) expressions[2];
        return true;
    }

    @Override
    protected void execute(LayerRenderEvent event, Graphics graphics) {
        @Nullable Color color = this.color.getSingle(event);
        @Nullable MapPixel start = this.start.getSingle(event);
        @Nullable MapPixel end = this.end.getSingle(event);
        if (color == null || start == null || end == null) return;

        graphics.setColor(AWTColorUtils.fromSkriptColor(color));
        graphics.drawLine(start.x(), start.y(), end.x(), end.y());
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "render line from " + start.toString(event, debug) + " to " + end.toString(event, debug) + " on the map with color " + color.toString(event, debug);
    }
}
