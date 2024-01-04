package com.sovdee.skriptmaps;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.logging.Logger;

public class SkriptMaps extends JavaPlugin {

    public static final String IMAGE_FOLDER = "plugins/skript-maps/images/";
    private static SkriptMaps instance;
    private static SkriptAddon addon;
    private static Logger logger;

    public static SkriptMaps getInstance() {
        return instance;
    }

    public static SkriptAddon getAddonInstance() {
        return addon;
    }

    public static void info(String message) {
        logger.info(message);
    }

    public static void warning(String message) {
        logger.warning(message);
    }

    public static void severe(String message) {
        logger.severe(message);
    }

    public static void debug(String message) {
        if (Skript.debug())
            logger.info(message);
    }

    @Override
    public void onEnable() {
        instance = this;
        addon = Skript.registerAddon(this);
        logger = this.getLogger();
        try {
            addon.loadClasses("com.sovdee.skriptmaps");
        } catch (IOException e) {
            e.printStackTrace();
        }

        SkriptMaps.info("skript-maps has been enabled.");

    }
}
