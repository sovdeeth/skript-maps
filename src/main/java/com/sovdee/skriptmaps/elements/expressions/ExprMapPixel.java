package com.sovdee.skriptmaps.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.sovdee.skriptmaps.maps.MapPixel;
import org.bukkit.event.Event;
import org.checkerframework.checker.nullness.qual.Nullable;

@Name("Map Pixel")
@Description("Represents the coordinates of a pixel on a map.")
@Examples({
        "set {_pixel} to the map pixel at 0, 0",
        "set {_layer} to a new map layer:",
            "\tcolour the pixel at {_pixel} on the map blue",
            "\tdraw a rectangle from pixel 100, 100 to {_pixel} on the map using blue",
})
@Since("1.0.0")
public class ExprMapPixel extends SimpleExpression<MapPixel> {

    static {
        Skript.registerExpression(ExprMapPixel.class, MapPixel.class, ExpressionType.SIMPLE,
                "[the] [map] pixel [at] %number%,[ ]%number%");
    }

    private Expression<Number> x, y;

    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        x = (Expression<Number>) expressions[0];
        y = (Expression<Number>) expressions[1];
        return true;
    }

    @Override
    protected MapPixel @Nullable [] get(Event event) {
        @Nullable Number x = this.x.getSingle(event);
        @Nullable Number y = this.y.getSingle(event);
        if (x == null || y == null)
            return null;
        return new MapPixel[] {new MapPixel(x.intValue(), y.intValue())};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends MapPixel> getReturnType() {
        return MapPixel.class;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "the [map] pixel at " + x.toString(event, debug) + ", " + y.toString(event, debug);
    }
}
