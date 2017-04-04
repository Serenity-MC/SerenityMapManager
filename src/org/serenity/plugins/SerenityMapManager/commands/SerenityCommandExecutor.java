package org.serenity.plugins.SerenityMapManager.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.serenity.plugins.SerenityMapManager.SerenityMapManager;

import java.util.logging.Logger;

public class SerenityCommandExecutor implements CommandExecutor {

    protected JavaPlugin plugin;
    protected Logger logger;

    public SerenityCommandExecutor() {
        this.plugin = JavaPlugin.getPlugin(SerenityMapManager.class);
        this.logger = plugin.getLogger();
    }

    protected JavaPlugin getPlugin() {
        return plugin;
    }

    protected Logger getLogger() {
        return logger;
    }

    // Used by subclasses to generate a message to send to the command invoker.
    protected String generateInvalidArgumenMessage(String value) {
        String message = ChatColor.DARK_RED + "Argument \"" + value + "\" is invalid;";
        return message;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        return false;
    }

}
