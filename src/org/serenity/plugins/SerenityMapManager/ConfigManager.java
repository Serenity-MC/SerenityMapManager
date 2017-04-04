package org.serenity.plugins.SerenityMapManager;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class ConfigManager {
    private static JavaPlugin plugin = JavaPlugin.getPlugin(SerenityMapManager.class);
    private static Logger logger = plugin.getLogger();
    private static FileConfiguration config = plugin.getConfig();

    // Ensures that default configuration values are set.
    public static void ensureConfigDefaults() {
        config.addDefault("eventWorldName", "");
        config.options().copyDefaults(true);

        plugin.saveConfig();
    }

    // Checks whether the "eventWorldName" config property is set.
    public static Boolean checkConfigValidity() {
        String worldName = config.getString("eventWorldName");

        if (worldName.length() < 1) {
            logger.warning("No value set for property \"eventWorldName\";");
            logger.warning("No world will be loaded;");

            return false;
        }

        return true;
    }

}
