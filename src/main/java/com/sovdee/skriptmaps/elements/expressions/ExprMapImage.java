package com.sovdee.skriptmaps.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.sovdee.skriptmaps.SkriptMaps;
import org.bukkit.event.Event;
import org.bukkit.map.MapPalette;
import org.checkerframework.checker.nullness.qual.Nullable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

@Name("Map Image")
@Description({
        "Loads an image from a file. The image must be in the plugins/skript-maps/images folder. Take care not to do this too often, as it can cause lag.",
        "If `resized` is used, the image will be resized to 128x128 pixels, which is the size of a whole map."
})
@Examples({
        "set {_img} to the resized map image from file \"myimage.png\"",
        "set {_layer} to a new map layer:",
            "\tdraw {_img} on the map at 0, 0"
})
@Since("1.0.0")
public class ExprMapImage extends SimpleExpression<Image> {

    static {
        Skript.registerExpression(ExprMapImage.class, Image.class, ExpressionType.COMBINED, "[the] [:resized] map image [from file|named] %string%");
    }

    Expression<String> file;
    boolean resize;

    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        file = (Expression<String>) expressions[0];
        resize = parseResult.hasTag("resized");
        return true;
    }

    @Override
    protected Image @Nullable [] get(Event event) {
        @Nullable String file = this.file.getSingle(event);
        if (file == null)
            return null;

        Image img;
        try {
            img = ImageIO.read(new File(SkriptMaps.IMAGE_FOLDER + file));
        } catch (IOException e) {
            SkriptMaps.warning("Failed to load image from file " + SkriptMaps.IMAGE_FOLDER + file);
            return null;
        }
        if (img == null)
            return null;
        if (resize)
            img = MapPalette.resizeImage(img);
        return new Image[]{img};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Image> getReturnType() {
        return Image.class;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "image from file " + file.toString(event, debug);
    }
}
