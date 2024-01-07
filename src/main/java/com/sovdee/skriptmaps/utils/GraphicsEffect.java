package com.sovdee.skriptmaps.utils;

import com.sovdee.skriptmaps.maps.LayerRenderEvent;

import java.awt.*;

public abstract class GraphicsEffect extends LayerEffect {

    @Override
    protected void execute(LayerRenderEvent event) {
        Graphics graphics = event.getCanvas().getGraphics();
        execute(event, graphics);
        graphics.dispose();
    }

    protected abstract void execute(LayerRenderEvent event, Graphics graphics);
}
