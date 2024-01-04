package com.sovdee.skriptmaps.maps;

import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapView;
import org.checkerframework.checker.nullness.qual.NonNull;

public class LayerRenderEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final MapView mapView;
    private final MapCanvas canvas;
    private final Player player;

    static {
        EventValues.registerEventValue(LayerRenderEvent.class, MapView.class, new Getter<>() {
            @Override
            public MapView get(LayerRenderEvent event) {
                return event.getMapView();
            }
        }, EventValues.TIME_NOW);
        EventValues.registerEventValue(LayerRenderEvent.class, Player.class, new Getter<>() {
            @Override
            public Player get(LayerRenderEvent event) {
                return event.getPlayer();
            }
        }, EventValues.TIME_NOW);
    }

    public LayerRenderEvent(MapView mapView, MapCanvas canvas, Player player) {
        super();
        this.mapView = mapView;
        this.canvas = canvas;
        this.player = player;
    }

    public MapView getMapView() {
        return mapView;
    }

    public MapCanvas getCanvas() {
        return canvas;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    @NonNull
    public HandlerList getHandlers() {
        return handlers;
    }
}
