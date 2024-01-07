package com.sovdee.skriptmaps.maps;

import ch.njol.skript.lang.Trigger;
import com.sovdee.skriptmaps.utils.ImageUtils;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapView;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.awt.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A custom map layer renderer.
 * Renders a map layer using a trigger.
 * This renderer is fixed, meaning the trigger is only executed once for each player and the result is cached.
 */
public class FixedLayerRenderer extends CustomLayerRenderer  {

    private final Map<Player, Image> rendered;

    public FixedLayerRenderer(Trigger trigger, @Nullable Object variablesMap, boolean contextual) {
        super(trigger, variablesMap, contextual);
        rendered = new ConcurrentHashMap<>();
    }

    @Override
    public void render(@NonNull MapView map, @NonNull MapCanvas canvas, @NonNull Player player) {
        if (rendered.containsKey(player)) {
            canvas.drawImage(0, 0, rendered.get(player));
        } else {
            super.render(map, canvas, player);
            rendered.put(player, ImageUtils.getImage(canvas));
        }
    }
}
