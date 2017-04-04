package org.serenity.plugins.SerenityMapManager.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.serenity.plugins.SerenityMapManager.SerenityMapManager;

import java.util.logging.Logger;

public class TeleportCommandsExecutor extends SerenityCommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        switch(command.getName()) {
            case "teleport":
                return executeTeleportCommand(commandSender, command, label, args);
            case "teleportall":
                return executeTeleportAllCommand(commandSender, command, label, args);
            default:
                return false;
        }
    }

    // Teleports either the specified player, the sender, or all the overworld players to the specified world.
    private boolean executeTeleportCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (args.length < 1) { // The command needs at least the worldName argument.
            return false;
        }

        // For command blocks. Sends the selected player to the specified world. (Selected via @p, @e, specific name, etc.)
        if (commandSender instanceof BlockCommandSender) {
            if (args.length < 2) {
                return false;
            }

            BlockCommandSender blockCommandSender = (BlockCommandSender) commandSender;

            String targetWorldName = args[0];
            Location targetLocation = blockCommandSender.getServer().getWorld(targetWorldName).getSpawnLocation();
            if (targetLocation == null) {

                blockCommandSender.sendMessage(generateInvalidArgumenMessage(targetWorldName));
                return false;
            }

            String targetPlayerName = args[1];
            Player targetPlayer = blockCommandSender.getServer().getPlayer(targetPlayerName);
            if (targetPlayer == null) {
                blockCommandSender.sendMessage(generateInvalidArgumenMessage(targetPlayerName));
                return false;
            }

            targetPlayer.teleport(targetLocation);
        }

        // For players. Sends the specified player or the player who executed the command to the specified world.
        if (commandSender instanceof Player) {
            Player playerCommandSender = (Player) commandSender;
            String targetWorldName = args[0];
            Location targetLocation;

            World targetWorld = playerCommandSender.getServer().getWorld(targetWorldName);
            if (targetWorld == null) {
                playerCommandSender.sendMessage(generateInvalidArgumenMessage(targetWorldName));
                return false;
            }

            targetLocation = targetWorld.getSpawnLocation();

            if (args.length == 2) {
                String targetPlayerName = args[1];
                Player targetPlayer = Bukkit.getPlayer(targetPlayerName);

                if (targetPlayer == null) {
                    playerCommandSender.sendMessage(generateInvalidArgumenMessage(targetPlayerName));
                    return false;
                }

                targetPlayer.teleport(targetLocation);
            } else {
                playerCommandSender.teleport(targetLocation);
            }
        }

        return true;
    }

    // Sends all online players to the specified server
    private boolean executeTeleportAllCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (args.length < 1) {
            return false;
        }

        String targetWorldName = args[0];
        Location targetLocation = commandSender.getServer().getWorld(targetWorldName).getSpawnLocation();
        if (targetLocation == null) {
            commandSender.sendMessage(generateInvalidArgumenMessage(targetWorldName));
            return false;
        }

        commandSender.getServer().getOnlinePlayers()
                .forEach(p -> p.teleport(targetLocation));

        return true;
    }
}
