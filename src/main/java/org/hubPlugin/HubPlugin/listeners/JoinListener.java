package org.hubPlugin.HubPlugin.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.hubPlugin.HubPlugin.managers.ItemManager;

public class JoinListener implements Listener {

    private final ItemManager itemManager;

    public JoinListener(ItemManager itemManager) {
        this.itemManager = itemManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // Automatically give lobby items when the player joins
        itemManager.giveLobbyItems(event.getPlayer());
    }
}
