package com.sovdee.skriptmaps.elements.functions;

import ch.njol.skript.lang.function.Functions;
import ch.njol.skript.lang.function.Parameter;
import ch.njol.skript.lang.function.SimpleJavaFunction;
import ch.njol.skript.registrations.DefaultClasses;
import com.sovdee.skriptmaps.utils.AWTColorUtils;
import org.checkerframework.checker.nullness.qual.Nullable;

public class FuncMapColourRGB extends SimpleJavaFunction<String> {
    static {
        Functions.registerFunction(new FuncMapColourRGB()
                .description("Returns a map colour string from red, green, and blue values, for use in colouring text on maps. This colour " +
                        "will not be exact, as there is a limited number of colours available on maps.")
                .examples("draw \"%mapColorRGB(1, 30, 230)%\" at 0, 0 on the map")
                .since("1.0.0")
        );
    }

    public FuncMapColourRGB() {
        super("mapColorRGB", new Parameter[] {
                new Parameter<>("red", DefaultClasses.LONG, true, null),
                new Parameter<>("green", DefaultClasses.LONG, true, null),
                new Parameter<>("blue", DefaultClasses.LONG, true, null)
        }, DefaultClasses.STRING, true);
    }

    @Override
    public @Nullable String[] executeSimple(Object[][] params) {
        return new String[]{
                AWTColorUtils.toMapColorString(
                        ((Number) params[0][0]).intValue(),
                        ((Number) params[1][0]).intValue(),
                        ((Number) params[2][0]).intValue())
        };
    }
}
