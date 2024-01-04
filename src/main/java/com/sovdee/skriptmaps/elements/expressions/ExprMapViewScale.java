package com.sovdee.skriptmaps.elements.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import org.bukkit.event.Event;
import org.bukkit.map.MapView;
import org.checkerframework.checker.nullness.qual.Nullable;

@Name("Map View Scale")
@Description({
        "The scale of a map view. This is the zoom level of the map, with 0 being the smallest and 4 being the largest.",
        "This does not affect the pixel size of the map, only the zoom level. The pixel size of the map is always 128x128."
})
@Examples({
        "set {_map} to a filled map",
        "set {_view} to a new map view",
        "set {_view}'s map scale to 2",
        "set {_map}'s map view to {_view}"
})
@Since("1.0.0")
public class ExprMapViewScale extends SimplePropertyExpression<MapView, Number> {

    static {
        register(ExprMapViewScale.class, Number.class, "map scale", "mapviews");
    }

    @Override
    public @Nullable Number convert(MapView from) {
        return from.getScale().ordinal();
    }

    @Override
    public Class<?> @Nullable [] acceptChange(Changer.ChangeMode mode) {
        return switch (mode) {
            case SET, RESET -> new Class[]{Number.class};
            default -> null;
        };
    }

    @Override
    public void change(Event event, Object @Nullable [] delta, Changer.ChangeMode mode) {
        @Nullable MapView mapView = getExpr().getSingle(event);
        if (mapView == null)
            return;
        if (mode == Changer.ChangeMode.SET && delta == null)
            return;
        switch (mode) {
            case SET -> mapView.setScale(MapView.Scale.values()[((Number) delta[0]).intValue()]);
            case RESET -> mapView.setScale(MapView.Scale.NORMAL);
        }
    }

    @Override
    public Class<? extends Number> getReturnType() {
        return Number.class;
    }

    @Override
    protected String getPropertyName() {
        return "map scale";
    }

}
