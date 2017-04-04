package org.serenity.plugins.SerenityMapManager;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.serenity.plugins.SerenityMapManager.commands.TeleportCommandsExecutor;
import org.serenity.plugins.SerenityMapManager.commands.WorldCommandsExecutor;

import java.util.logging.Logger;

public class SerenityMapManager extends JavaPlugin {

    private Logger logger;
    private FileConfiguration config;

    @Override
    public void onEnable() {
        this.logger = getLogger();
        this.config = getConfig();

        if (checkConfig()) {
            loadEventWorld();
        }

        registerCommandExecutors();
    }

    // Registers the command executors.
    private void registerCommandExecutors() {
        getCommand("teleport").setExecutor(new TeleportCommandsExecutor());
        getCommand("teleportall").setExecutor(new TeleportCommandsExecutor());
        getCommand("worldlist").setExecutor(new WorldCommandsExecutor());
        getCommand("worldrestore").setExecutor(new WorldCommandsExecutor());
    }

    // Checks that everything is correct with the configuration.
    private boolean checkConfig() {
        ConfigManager.ensureConfigDefaults();

        return ConfigManager.checkConfigValidity();
    }

    // Loads the world specified in the "eventWorldName" config property.
    private void loadEventWorld() {
        String eventWorldName = config.getString("eventWorldName");

        logger.warning("If the world \"" + eventWorldName + "\" is not found, a new survival world will be created in its place;");

        WorldManager.loadWorld(eventWorldName);
    }
}
