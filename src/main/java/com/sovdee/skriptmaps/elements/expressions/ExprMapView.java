package com.sovdee.skriptmaps.elements.expressions;

import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;
import org.checkerframework.checker.nullness.qual.Nullable;

@Name("Map View")
@Description({
        "The map view of an item stack. This can be used the change the properties of a displayed map, or have layers " +
        "added and removed to change the content of the map.",
        "If a map item does not yet have a map view, this will return nothing. You can use the \"new map view\" expression instead.",
        "Be warned, resetting this property may clear the map, or it may cause the map to be completely regenerated as " +
        "an entirely new map, depending on your server jar."
})
@Examples({
        "set {_map} to a new map",
        "set {_view} to {_map}'s map view",
        "set {_view}'s map scale to 2",
        "set {_map}'s map view to {_view}"
})
@Since("1.0.0")
public class ExprMapView extends SimplePropertyExpression<ItemStack, MapView> {

    static {
        register(ExprMapView.class, MapView.class, "map view", "itemstacks");
    }

    @Override
    public @Nullable MapView convert(ItemStack itemStack) {
        if (itemStack.getItemMeta() instanceof MapMeta mapMeta && mapMeta.hasMapView())
            return mapMeta.getMapView();
        return null;
    }

    @Override
    public  Class<?> @Nullable [] acceptChange(ChangeMode mode) {
        return switch (mode) {
            case SET -> new Class[]{MapView.class};
            case RESET -> new Class[]{};
            default -> null;
        };
    }

    @Override
    public void change(Event event, @Nullable Object[] delta, ChangeMode mode) {
        @Nullable ItemStack[] itemStacks = getExpr().getArray(event);
        for (ItemStack itemStack : itemStacks) {
            if (itemStack.getItemMeta() instanceof MapMeta mapMeta) {
                switch (mode) {
                    case SET -> mapMeta.setMapView((MapView) delta[0]);
                    case RESET -> mapMeta.setMapView(null);
                    default -> {
                    }
                }
                itemStack.setItemMeta(mapMeta);
            }
        }
        getExpr().change(event, itemStacks, ChangeMode.SET);
    }

    @Override
    public Class<? extends MapView> getReturnType() {
        return MapView.class;
    }

    @Override
    protected String getPropertyName() {
        return "map view";
    }

}
