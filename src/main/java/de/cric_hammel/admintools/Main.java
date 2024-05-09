package de.cric_hammel.admintools;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.cric_hammel.admintools.commands.AdminToolsCommand;
import de.cric_hammel.admintools.commands.GetItemsCommand;
import de.cric_hammel.admintools.items.CustomItem;
import de.cric_hammel.admintools.items.MobClearer;
import de.cric_hammel.admintools.util.InventoryManager;
import de.cric_hammel.admintools.util.Settings;

public class Main extends JavaPlugin {
	
	private static Main plugin;
	private static FileConfiguration config;
	
	private static Settings settings;
	
	private static List<CustomItem> items = new ArrayList<CustomItem>();
	public static final String LORE_ID = ChatColor.MAGIC + "admintools";
	
	@Override
	public void onEnable() {
		plugin = this;
		config = getConfig();
		
		settings = new Settings(config, plugin);
		
		getCommand("getitems").setExecutor(new GetItemsCommand());
		getCommand("admintools").setExecutor(new AdminToolsCommand());
		
		PluginManager pluginManager = Bukkit.getPluginManager();
		pluginManager.registerEvents(new InventoryManager.Listeners(), plugin);
		pluginManager.registerEvents(new MobClearer.Listeners(), plugin);
		
		items.add(new MobClearer());
	}
	
	public static Main getPlugin() {
		return plugin;
	}
	
	public static FileConfiguration getConfiguration() {
		return config;
	}
	
	public static void saveConfiguration() {
		plugin.saveConfig();
	}

	public static List<CustomItem> getItems() {
		return items;
	}
	
	public static Settings getSettings() {
		return settings;
	}
}
