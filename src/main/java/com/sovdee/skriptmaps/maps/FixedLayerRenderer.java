package com.sovdee.skriptmaps.maps;

import ch.njol.skript.lang.Trigger;
import com.sovdee.skriptmaps.utils.ImageUtils;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapView;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.awt.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FixedLayerRenderer extends CustomLayerRenderer  {

    private final Map<Player, Image> rendered;

    public FixedLayerRenderer(Trigger trigger, boolean contextual) {
        super(trigger, contextual);
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
