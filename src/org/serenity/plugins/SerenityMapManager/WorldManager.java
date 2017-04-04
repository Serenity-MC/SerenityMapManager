package org.serenity.plugins.SerenityMapManager;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class WorldManager {
    private static JavaPlugin plugin = JavaPlugin.getPlugin(SerenityMapManager.class);
    private static Logger logger = plugin.getLogger();
    private static FileConfiguration config = plugin.getConfig();

    // Loads a new world. Will create a new world if there is no savedata for the given name.
    public static World loadWorld(String worldName) {
        WorldCreator creator = new WorldCreator(worldName);
        World newWorld;

        logger.info("Loading World \"" + worldName + "\";");

        newWorld = Bukkit.createWorld(creator);
        newWorld.setAutoSave(false);

        return newWorld;
    }

    // Unloads a world without saving.
    public static void unloadWorld(World targetWorld) {
        logger.info("Unoading World \"" + targetWorld.getName() + "\";");

        Bukkit.unloadWorld(targetWorld, false);
    }

    // Unloads the target world without saving, then reloads it from save data.
    public static void restoreWorld(World targetWorld) {
        WorldCreator creator = new WorldCreator(targetWorld.getName());
        World newWorld;

        logger.info("Restoring World \"" + targetWorld.getName() + "\";");

        Bukkit.unloadWorld(targetWorld, false);

        newWorld = Bukkit.createWorld(creator);
        newWorld.setAutoSave(false);
    }
}
