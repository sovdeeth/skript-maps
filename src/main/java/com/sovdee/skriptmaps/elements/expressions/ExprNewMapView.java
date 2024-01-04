package com.sovdee.skriptmaps.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.Event;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.checkerframework.checker.nullness.qual.Nullable;

@Name("New Map View")
@Description({
        "Creates a new empty map view. This is not a map item, but a map view that can be used to render a map.",
        "To assign this map view to a map item, use `set {_map}'s map view to {_view}`."
})
@Examples({
        "set {_view} to a new map view",
        "set {_layer} to a new map layer:",
            "\tcolor pixels within 0, 0 and 127, 127  on the map in radius 10 of 64, 64",
        "add {_layer} to map layers of {_view}",
        "set {_map}'s map view to {_view}"
})
public class ExprNewMapView extends SimpleExpression<MapView> {

    static {
        Skript.registerExpression(ExprNewMapView.class, MapView.class, ExpressionType.SIMPLE, "[a[n]] [new] [empty] map view [in [world] %world%]");
    }

    Expression<World> world;

    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        world = (Expression<World>) expressions[0];
        return true;
    }

    @Override
    protected MapView @Nullable [] get(Event event) {
        @Nullable World world = this.world.getSingle(event);
        if (world == null)
            return null;

        MapView view = Bukkit.createMap(world);

        for (MapRenderer renderer : view.getRenderers())
            view.removeRenderer(renderer);

        view.setCenterX(0);
        view.setCenterZ(0);
        view.setScale(MapView.Scale.NORMAL);

        view.setTrackingPosition(false);
        view.setUnlimitedTracking(false);

        return new MapView[] {view};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends MapView> getReturnType() {
        return MapView.class;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "a new map view in world " + world.toString(event, debug);
    }
}
