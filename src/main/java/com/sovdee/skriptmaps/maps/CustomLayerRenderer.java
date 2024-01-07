package com.sovdee.skriptmaps.maps;

import ch.njol.skript.lang.Trigger;
import ch.njol.skript.variables.Variables;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public class CustomLayerRenderer extends MapRenderer {

    Trigger trigger;
    @Nullable
    Object variablesMap;

    public CustomLayerRenderer(Trigger trigger, @Nullable Object variablesMap, boolean contextual) {
        super(contextual);
        this.trigger = trigger;
        this.variablesMap = variablesMap;
    }

    @Override
    public void render(@NonNull MapView map, @NonNull MapCanvas canvas, @NonNull Player player) {
        CustomMapCanvas customCanvas = new CustomMapCanvas(canvas);
        LayerRenderEvent event = new LayerRenderEvent(map, customCanvas, player);
        Variables.setLocalVariables(event, variablesMap);
        trigger.execute(event);
        customCanvas.render();
        variablesMap = Variables.removeLocals(event);
    }

}
