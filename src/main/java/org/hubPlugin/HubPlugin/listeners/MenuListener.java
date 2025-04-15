package org.hubPlugin.HubPlugin.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.hubPlugin.HubPlugin.data.LobbyItem;
import org.hubPlugin.HubPlugin.managers.ItemManager;

import java.util.ArrayList;
import java.util.List;

public class MenuListener implements Listener {

    private final ItemManager itemManager;

    public MenuListener(ItemManager itemManager) {
        this.itemManager = itemManager;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        // Check if this is the editor GUI
        String title = ChatColor.stripColor(e.getView().getTitle());
        if (title == null || !title.equalsIgnoreCase("Lobby Item Editor")) return;

        // Cancel all interactions by default
        e.setCancelled(true);

        Player player = (Player) e.getWhoClicked();
        Inventory clickedInventory = e.getClickedInventory();
        int slot = e.getRawSlot();

        // Allow editing top inventory slots 0–35
        if (slot >= 0 && slot < 36 && clickedInventory.equals(e.getView().getTopInventory())) {
            e.setCancelled(false);
            return;
        }

        // Allow shift-click from player inventory to fill editable slots
        if (e.isShiftClick() && clickedInventory != null && clickedInventory.equals(player.getInventory())) {
            int firstEmpty = e.getView().getTopInventory().firstEmpty();
            if (firstEmpty >= 0 && firstEmpty < 36) {
                e.setCancelled(false); // allow shift-click to GUI
            }
            return;
        }

        // Handle control buttons
        switch (slot) {
            case 45: // Save
                Inventory top = e.getView().getTopInventory();
                List<LobbyItem> newItems = new ArrayList<>();

                for (int i = 0; i < 36; i++) {
                    ItemStack item = top.getItem(i);
                    if (item != null && item.getType() != Material.AIR) {
                        newItems.add(new LobbyItem(item.clone(), i));
                    }
                }

                itemManager.setLobbyItems(newItems);
                itemManager.saveItemsToConfig();
                player.sendMessage(ChatColor.GREEN + "Lobby items saved successfully.");
                player.closeInventory();
                break;

            case 49: // Test
                player.sendMessage(ChatColor.AQUA + "Giving you the configured lobby items...");
                itemManager.giveLobbyItems(player);
                break;

            case 53: // Close
                player.sendMessage(ChatColor.YELLOW + "Editor closed without saving.");
                player.closeInventory();
                break;

            default:
                // Clicked a slot outside of editable area or a non-control button
                break;
        }
    }
}
