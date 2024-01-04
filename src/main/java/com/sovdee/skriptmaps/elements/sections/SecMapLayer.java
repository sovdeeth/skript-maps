package com.sovdee.skriptmaps.elements.sections;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.config.SectionNode;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.Section;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.Trigger;
import ch.njol.skript.lang.TriggerItem;
import ch.njol.skript.lang.Variable;
import ch.njol.util.Kleenean;
import com.sovdee.skriptmaps.maps.LayerRenderEvent;
import com.sovdee.skriptmaps.maps.CustomLayerRenderer;
import com.sovdee.skriptmaps.maps.StationaryLayerRenderer;
import org.bukkit.event.Event;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Name("New Map Layer Section")
@Description({
        "Creates a new map layer. This is not a map item, but a map layer that can be used to render a map.",
        "The code inside this section will be run every time the map is rendered, which is every five ticks for " +
        "each map item in a player's inventory and approximately every 50 ticks for each map on an item frame.",
        "These can add up quickly, so keep your code as efficient as possible inside the section."
})
@Examples({
        "set {_layer} to a new map layer:",
            "\tcolor pixels within 0, 0 and 127, 127 on the map in radius 10 of 64, 64",
        "add {_layer} to map layers of {_view}"
})
@Since("1.0.0")
public class SecMapLayer extends Section {

    static {
        Skript.registerSection(SecMapLayer.class, "set %~object% to [a] [new] [:stationary] map layer");
    }

    Variable<?> variable;
    Trigger trigger;

    private boolean stationary = false;

    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult, SectionNode sectionNode, List<TriggerItem> triggerItems) {
        if (!(expressions[0] instanceof Variable)) {
            Skript.error("A map layer must be put into a variable.");
            return false;
        }
        variable = (Variable<?>) expressions[0];

        AtomicBoolean delayed = new AtomicBoolean(false);
        Runnable afterLoading = () -> delayed.set(!getParser().getHasDelayBefore().isFalse());
        trigger = loadCode(sectionNode, "map layer", afterLoading, LayerRenderEvent.class);
        if (delayed.get()) {
            Skript.error("Delays are not allowed when drawing a map layer!");
            return false;
        }
        stationary = parseResult.hasTag("stationary");

        return true;
    }

    @Override
    protected @Nullable TriggerItem walk(Event event) {
        CustomLayerRenderer layer = stationary ? new StationaryLayerRenderer(trigger, true) : new CustomLayerRenderer(trigger, true);
        variable.change(event, new Object[] {layer}, Changer.ChangeMode.SET);
        return super.walk(event, false);
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "set " + variable + " to a new map layer";
    }
}
