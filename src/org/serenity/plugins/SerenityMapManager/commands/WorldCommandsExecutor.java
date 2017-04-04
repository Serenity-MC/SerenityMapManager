package org.serenity.plugins.SerenityMapManager.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.serenity.plugins.SerenityMapManager.WorldManager;

import java.util.ArrayList;
import java.util.List;

public class WorldCommandsExecutor extends SerenityCommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        switch (command.getName()) {
            case "worldlist":
                return executeWorldListCommand(commandSender, command, label, args);
            case "worldrestore":
                return executeWorldRestoreCommand(commandSender, command, label, args);
            default:
                return false;
        }
    }

    // Lists the currently loaded worlds.
    private boolean executeWorldListCommand(CommandSender commandSender, Command command, String label, String[] args) {
        List<World> loadedWorlds = Bukkit.getWorlds();
        ArrayList<String> loadedWorldNames = new ArrayList<String>();

        for (World world : loadedWorlds) {
            loadedWorldNames.add(world.getName());
        }

        commandSender.sendMessage( ChatColor.GREEN + "Loaded Worlds:");

        for (String worldName : loadedWorldNames) {
            commandSender.sendMessage(" - " + worldName);
        }

        return true;
    }

    // Restores a world to its save data state.
    private boolean executeWorldRestoreCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (args.length < 1) {
            return false;
        }

        String targetWorldName = args[0];

        World targetWorld = commandSender.getServer().getWorld(targetWorldName);
        if (targetWorld == null) {
            commandSender.sendMessage(generateInvalidArgumenMessage(targetWorldName));
            return false;
        }

        WorldManager.restoreWorld(targetWorld);

        return true;
    }
}
