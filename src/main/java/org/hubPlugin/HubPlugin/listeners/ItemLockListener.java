package org.hubPlugin.HubPlugin.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.hubPlugin.HubPlugin.managers.ItemManager;

public class ItemLockListener implements Listener {

    private final ItemManager itemManager;

    public ItemLockListener(ItemManager itemManager) {
        this.itemManager = itemManager;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        ItemStack current = e.getCurrentItem();
        ItemStack cursor = e.getCursor();

        // Block moving or swapping hub items
        if (itemManager.isHubItem(current) || itemManager.isHubItem(cursor)) {
            e.setCancelled(true);
            e.getWhoClicked().sendMessage(ChatColor.RED + "You can't move or swap hub items.");
        }
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e) {
        ItemStack item = e.getItemDrop().getItemStack();
        if (itemManager.isHubItem(item)) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(ChatColor.RED + "You can't drop hub items.");
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        ItemStack item = e.getItemInHand();
        if (itemManager.isHubItem(item)) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(ChatColor.RED + "You can't place hub items.");
        }
    }

    @EventHandler
    public void onInventoryMove(InventoryMoveItemEvent e) {
        ItemStack item = e.getItem();
        if (itemManager.isHubItem(item)) {
            e.setCancelled(true); // Prevent hopper/chest movement
        }
    }

    @EventHandler
    public void onCraftAttempt(PrepareItemCraftEvent e) {
        for (ItemStack item : e.getInventory().getMatrix()) {
            if (itemManager.isHubItem(item)) {
                e.getInventory().setResult(null); // Cancel crafting
                break;
            }
        }
    }
}
