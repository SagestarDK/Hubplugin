package org.hubPlugin.HubPlugin.data;

import org.bukkit.inventory.ItemStack;

public class LobbyItem {

    private final ItemStack itemStack;
    private final int slot;

    public LobbyItem(ItemStack itemStack, int slot) {
        this.itemStack = itemStack;
        this.slot = slot;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public int getSlot() {
        return slot;
    }
}
