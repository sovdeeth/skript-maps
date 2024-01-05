package com.sovdee.skriptmaps.utils;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import com.sovdee.skriptmaps.SkriptMaps;
import com.sovdee.skriptmaps.maps.LayerRenderEvent;
import org.bukkit.event.Event;
import org.checkerframework.checker.nullness.qual.Nullable;

public abstract class LayerEffect extends Effect {

    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        // check that we're in a layer section
        if (!getParser().isCurrentEvent(LayerRenderEvent.class)) {
            Skript.error("Maps can only be visually edited within a map layer section!");
            return false;
        }

        return init(expressions, matchedPattern, parseResult);
    }

    protected abstract boolean init(Expression<?>[] expressions, int matchedPattern, ParseResult parseResult);

    @Override
    protected void execute(Event event) {
        // auto convert to LayerRenderEvent
        if (event instanceof LayerRenderEvent) {
            execute((LayerRenderEvent) event);
        } else {
            SkriptMaps.warning("Tried to execute a layer effect without a layer render event!");
        }

    }

    protected abstract void execute(LayerRenderEvent event);

    @Override
    public abstract String toString(@Nullable Event event, boolean debug);

}
