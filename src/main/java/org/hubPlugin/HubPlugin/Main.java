package org.hubPlugin.HubPlugin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.hubPlugin.HubPlugin.commands.GiveHubItemsCommand;
import org.hubPlugin.HubPlugin.commands.HubEditCommand;
import org.hubPlugin.HubPlugin.listeners.ItemLockListener;
import org.hubPlugin.HubPlugin.listeners.JoinListener;
import org.hubPlugin.HubPlugin.listeners.MenuListener;
import org.hubPlugin.HubPlugin.managers.GuiManager;
import org.hubPlugin.HubPlugin.managers.ItemManager;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        // Initialize ItemManager and GuiManager
        ItemManager itemManager = new ItemManager(this);
        itemManager.loadItemsFromConfig();
        GuiManager guiManager = new GuiManager(itemManager);

        // Register commands
        this.getCommand("hubedit").setExecutor(new HubEditCommand(guiManager));
        this.getCommand("givehubitems").setExecutor(new GiveHubItemsCommand(itemManager));

        // Register the listeners
        Bukkit.getPluginManager().registerEvents(new MenuListener(itemManager), this);
        getServer().getPluginManager().registerEvents(new JoinListener(itemManager), this);
        getServer().getPluginManager().registerEvents(new ItemLockListener(itemManager), this);
    }








    @Override
    public void onDisable() {

    }
}
