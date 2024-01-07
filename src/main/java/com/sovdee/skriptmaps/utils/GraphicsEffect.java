package com.sovdee.skriptmaps.utils;

import com.sovdee.skriptmaps.maps.LayerRenderEvent;

import java.awt.*;

/**
 * A {@link LayerEffect} that should be used for effects that draw on the map.
 * Provides a {@link Graphics} object to draw on in the {@link #execute(LayerRenderEvent, Graphics)} method.
 */
public abstract class GraphicsEffect extends LayerEffect {

    @Override
    protected void execute(LayerRenderEvent event) {
        Graphics graphics = event.getCanvas().getGraphics();
        execute(event, graphics);
        graphics.dispose();
    }

    protected abstract void execute(LayerRenderEvent event, Graphics graphics);
}
