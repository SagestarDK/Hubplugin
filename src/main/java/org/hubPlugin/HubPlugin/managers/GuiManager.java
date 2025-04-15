package org.hubPlugin.HubPlugin.managers;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.hubPlugin.HubPlugin.data.LobbyItem;

import java.util.Arrays;
import java.util.List;

public class GuiManager {

    private final ItemManager itemManager;

    public GuiManager(ItemManager itemManager) {
        this.itemManager = itemManager;
    }

    /**
     * Opens the GUI for editing the lobby items.
     * Only accessible to players with permission (handled externally).
     */
    public void openEditorGUI(Player player) {
        Inventory gui = buildEditorInventory();
        player.openInventory(gui);
    }

    /**
     * Builds the full editor GUI inventory.
     * Includes editable slots (0–35) and control buttons (save/test/close).
     */
    private Inventory buildEditorInventory() {
        Inventory inv = Bukkit.createInventory(null, 54, "§9Lobby Item Editor");

        // Load existing lobby items into slots 0–35
        for (LobbyItem item : itemManager.getLobbyItems()) {
            if (item.getSlot() >= 0 && item.getSlot() < 36) {
                inv.setItem(item.getSlot(), item.getItemStack());
            }
        }

        // Save button
        ItemStack save = new ItemStack(Material.ANVIL);
        ItemMeta saveMeta = save.getItemMeta();
        saveMeta.setDisplayName("§aSave");
        saveMeta.setLore(Arrays.asList("§7Click to save changes."));
        save.setItemMeta(saveMeta);
        inv.setItem(45, save);

        // Test button
        ItemStack test = new ItemStack(Material.ENDER_PEARL);
        ItemMeta testMeta = test.getItemMeta();
        testMeta.setDisplayName("§bTest Items");
        testMeta.setLore(Arrays.asList("§7Gives you the items to test them."));
        test.setItemMeta(testMeta);
        inv.setItem(49, test);

        // Close button
        ItemStack close = new ItemStack(Material.BARRIER);
        ItemMeta closeMeta = close.getItemMeta();
        closeMeta.setDisplayName("§cClose");
        closeMeta.setLore(Arrays.asList("§7Exit without saving."));
        close.setItemMeta(closeMeta);
        inv.setItem(53, close);

        return inv;
    }


    /**
     * Handles clicks inside the editor GUI.
     * - Prevents control button clicks from being moved
     * - Handles save/test/close actions
     */
    public void handleInventoryClick(InventoryClickEvent event) {
        // TODO: Implement click logic
    }

    /**
     * Handles drag events to prevent dragging over control buttons.
     */
    public void handleInventoryDrag(InventoryDragEvent event) {
        // TODO: Implement drag blocking
    }

    /**
     * Extracts the lobby items from slots 0–35 of the editor GUI.
     * These are the items that will be saved and given to players.
     */
    private List<ItemStack> getEditedItems(Inventory guiInventory) {
        // TODO: Extract and return items
        return null;
    }

    /**
     * Checks whether the given inventory is the editor GUI.
     * Used for event filtering.
     */
    private boolean isEditorInventory(Inventory inventory) {
        // TODO: Match based on title or metadata
        return false;
    }
}
