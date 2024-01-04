package com.sovdee.skriptmaps.elements.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.PropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.checkerframework.checker.nullness.qual.Nullable;

@Name("Map Layers")
@Description({
        "The layers of a map. Each layer can have its own code for drawing on the map, and their contents will be drawn to the map in order.",
        "This means the last layer will be drawn on top of the others.",
        "Layers can be added or removed at any time, and their order can be changed by removing and re-adding them."
})
@Examples({
        "set map layers of {_map-view} to reversed map layers of {_map-view}",
        "set map layers of {_map-view} to {_layer1}, {_layer2}, {_layer3}",
        "clear map layers of {_map-view}",
        "add {_layer} to map layers of {_map-view}",
        "remove {_layer} from map layers of {_map-view}"
})
@Since("1.0.0")
public class ExprMapLayers extends PropertyExpression<MapView, MapRenderer> {

    static {
        register(ExprMapLayers.class, MapRenderer.class, "map layers", "mapview");
    }

    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        setExpr((Expression<MapView>) expressions[0]);
        return true;
    }

    @Override
    protected MapRenderer[] get(Event event, MapView[] source) {
        if (source.length == 0)
            return new MapRenderer[0];
        return source[0].getRenderers().toArray(new MapRenderer[0]);
    }

    @Override
    public Class<?> @Nullable [] acceptChange(Changer.ChangeMode mode) {
        return switch (mode) {
            case ADD, REMOVE, SET -> new Class[]{MapRenderer[].class};
            case DELETE -> new Class[]{};
            default -> null;
        };
    }

    @Override
    public void change(Event event, @Nullable Object[] delta, Changer.ChangeMode mode) {
        @Nullable MapView view = getExpr().getSingle(event);
        if (view == null)
            return;
        switch (mode) {
            case ADD -> {
                for (MapRenderer renderer : (MapRenderer[]) delta)
                    view.addRenderer(renderer);
            }
            case REMOVE -> {
                for (MapRenderer renderer : (MapRenderer[]) delta)
                    view.removeRenderer(renderer);
            }
            case SET -> {
                for (MapRenderer renderer : view.getRenderers())
                    view.removeRenderer(renderer);
                for (MapRenderer renderer : (MapRenderer[]) delta)
                    view.addRenderer(renderer);
            }
            case DELETE -> {
                for (MapRenderer renderer : view.getRenderers())
                    view.removeRenderer(renderer);
            }
        }
    }

    @Override
    public Class<? extends MapRenderer> getReturnType() {
        return MapRenderer.class;
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "map layers of " + getExpr().toString(event, debug);
    }
}
