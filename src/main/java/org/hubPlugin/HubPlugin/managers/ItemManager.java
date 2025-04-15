package org.hubPlugin.HubPlugin.managers;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.NamespacedKey;
import org.hubPlugin.HubPlugin.data.LobbyItem;

import java.util.ArrayList;
import java.util.List;

public class ItemManager {

    private final JavaPlugin plugin;
    private final List<LobbyItem> lobbyItems = new ArrayList<>();
    private final NamespacedKey hubItemKey;

    public ItemManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.hubItemKey = new NamespacedKey(plugin, "hub_item");
    }

    /**
     * Loads the lobby items from config and stores them in memory.
     */
    public void loadItemsFromConfig() {
        FileConfiguration config = plugin.getConfig();

        lobbyItems.clear(); // Make sure we don't duplicate

        if (!config.isConfigurationSection("lobby_items")) return;

        for (String key : config.getConfigurationSection("lobby_items").getKeys(false)) {
            try {
                int slot = Integer.parseInt(key.replace("slot_", ""));
                ItemStack item = config.getItemStack("lobby_items." + key);

                if (item != null) {
                    lobbyItems.add(new LobbyItem(item, slot));
                }
            } catch (NumberFormatException e) {
                Bukkit.getLogger().warning("[HubPlugin] Invalid slot key in config: " + key);
            }
        }
    }

    /**
     * Saves the current lobby items to config.
     */
    public void saveItemsToConfig() {
        FileConfiguration config = plugin.getConfig();

        config.set("lobby_items", null); // Clear previous config section

        for (LobbyItem item : lobbyItems) {
            config.set("lobby_items.slot_" + item.getSlot(), item.getItemStack());
        }

        plugin.saveConfig();
    }

    /**
     * Returns the current list of lobby items with their inventory slots.
     */
    public List<LobbyItem> getLobbyItems() {
        return new ArrayList<>(lobbyItems);
    }

    /**
     * Sets and stores the new list of lobby items.
     */
    public void setLobbyItems(List<LobbyItem> newItems) {
        lobbyItems.clear();
        lobbyItems.addAll(newItems);
    }

    /**
     * Gives all configured lobby items to a player in the correct inventory slots.
     */
    public void giveLobbyItems(Player player) {
        PlayerInventory inv = player.getInventory();

        for (LobbyItem item : lobbyItems) {
            ItemStack copy = item.getItemStack().clone();
            markAsHubItem(copy); // Add NBT tag
            inv.setItem(item.getSlot(), copy);
        }
    }

    /**
     * Adds a custom NBT tag to an item to mark it as a hub item.
     */
    private void markAsHubItem(ItemStack item) {
        item.editMeta(meta -> {
            PersistentDataContainer container = meta.getPersistentDataContainer();
            container.set(hubItemKey, PersistentDataType.BYTE, (byte) 1);
        });
    }

    /**
     * Checks whether the given item is a marked hub item.
     */
    public boolean isHubItem(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return false;

        PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();
        return container.has(hubItemKey, PersistentDataType.BYTE);
    }
}
