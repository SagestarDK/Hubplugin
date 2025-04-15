package org.hubPlugin.HubPlugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.hubPlugin.HubPlugin.managers.GuiManager;

public class HubEditCommand implements CommandExecutor {

    private final GuiManager guiManager;

    public HubEditCommand(GuiManager guiManager) {
        this.guiManager = guiManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            // Show the GUI
            guiManager.openEditorGUI(player);

            player.sendMessage(ChatColor.GREEN + "Lobby item editor opened!");
            return true;
        }

        sender.sendMessage(ChatColor.RED + "Only players can use this command.");
        return true;
    }
}
