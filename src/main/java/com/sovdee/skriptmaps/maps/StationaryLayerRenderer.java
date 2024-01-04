package com.sovdee.skriptmaps.maps;

import ch.njol.skript.lang.Trigger;
import ch.njol.skript.util.Date;
import com.sovdee.skriptmaps.SkriptMaps;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapView;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.Map;

public class StationaryLayerRenderer extends CustomLayerRenderer  {

    private static final LogoutListener logoutListener = new LogoutListener();
    private final Map<Player, Long> rendered;

    public StationaryLayerRenderer(Trigger trigger, boolean contextual) {
        super(trigger, contextual);
        rendered = new java.util.HashMap<>();
        logoutListener.addRenderer(this);
    }


    @Override
    public void render(@NonNull MapView map, @NonNull MapCanvas canvas, @NonNull Player player) {
        if (!rendered.containsKey(player)) {
            rendered.put(player, Date.now().getTimestamp());
            super.render(map, canvas, player);
        }
    }

    private static class LogoutListener implements Listener {

        public LogoutListener() {
            super();
            SkriptMaps.getInstance().getServer().getPluginManager().registerEvents(this, SkriptMaps.getInstance());
        }

        private final ArrayList<StationaryLayerRenderer> renderers = new ArrayList<>();

        @EventHandler
        public void onPlayerQuit(PlayerQuitEvent event) {
            for (StationaryLayerRenderer renderer : renderers)
                renderer.rendered.remove(event.getPlayer());
        }

        public void addRenderer(StationaryLayerRenderer renderer) {
            renderers.add(renderer);
        }

        public void removeRenderer(StationaryLayerRenderer renderer) {
            renderers.remove(renderer);
        }

        public void clearRenderers() {
            renderers.clear();
        }
    }
}
