package com.sovdee.skriptmaps.elements.functions;

import ch.njol.skript.lang.function.Functions;
import ch.njol.skript.lang.function.Parameter;
import ch.njol.skript.lang.function.SimpleJavaFunction;
import ch.njol.skript.registrations.DefaultClasses;
import ch.njol.skript.util.ColorRGB;
import com.sovdee.skriptmaps.utils.AWTColorUtils;
import org.checkerframework.checker.nullness.qual.Nullable;

public class FuncMapColour extends SimpleJavaFunction<String> {
    static {
        Functions.registerFunction(new FuncMapColour()
                .description("Returns a map colour string from a color, for use in colouring text on maps. This colour " +
                        "will not be exact, as there is a limited number of colours available on maps.")
                .examples("draw \"%mapColor(blue)%\" at 0, 0 on the map")
                .since("1.0.0")
        );
    }

    public FuncMapColour() {
        super("mapColor", new Parameter[] {
                new Parameter<>("color", DefaultClasses.COLOR, true, null)
        }, DefaultClasses.STRING, true);
    }

    @Override
    public @Nullable String[] executeSimple(Object[][] params) {
        return new String[]{
                AWTColorUtils.toMapColorString((ColorRGB) params[0][0])
        };
    }
}
