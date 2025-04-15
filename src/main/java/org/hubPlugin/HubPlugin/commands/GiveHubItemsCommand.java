package org.hubPlugin.HubPlugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.hubPlugin.HubPlugin.managers.ItemManager;

public class GiveHubItemsCommand implements CommandExecutor {

    private final ItemManager itemManager;

    public GiveHubItemsCommand(ItemManager itemManager) {
        this.itemManager = itemManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // Usage: /givehubitems <player>
        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Usage: /givehubitems <player>");
            return true;
        }

        Player target = Bukkit.getPlayerExact(args[0]);

        if (target == null || !target.isOnline()) {
            sender.sendMessage(ChatColor.RED + "That player is not online.");
            return true;
        }

        itemManager.giveLobbyItems(target);
        sender.sendMessage(ChatColor.GREEN + "Lobby items given to " + target.getName());
        target.sendMessage(ChatColor.YELLOW + "You received lobby items.");

        return true;
    }
}
