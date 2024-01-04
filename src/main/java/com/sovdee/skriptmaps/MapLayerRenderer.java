package com.sovdee.skriptmaps;

import ch.njol.skript.lang.Trigger;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.checkerframework.checker.nullness.qual.NonNull;

public class MapLayerRenderer extends MapRenderer {

    Trigger trigger;

    public MapLayerRenderer(Trigger trigger, boolean contextual) {
        super(contextual);
        this.trigger = trigger;
    }

    @Override
    public void render(@NonNull MapView map, @NonNull MapCanvas canvas, @NonNull Player player) {
        MapLayerRenderEvent event = new MapLayerRenderEvent(map, canvas, player);
        trigger.execute(event);
    }

}
