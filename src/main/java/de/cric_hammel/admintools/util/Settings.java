package de.cric_hammel.admintools.util;

import java.util.Arrays;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;

import de.cric_hammel.admintools.Main;

public class Settings {

	private FileConfiguration config;
	private Main main;
	private int mobClearerRadius;
	private List<EntityType> mobClearerExcludedTypes;
	
	public Settings(FileConfiguration config, Main main) {
		this.config = config;
		this.main = main;
		config.addDefault("settings.MobClearer.radius", 5);
		config.addDefault("settings.MobClearer.excludedTypes", Arrays.asList(EntityType.PLAYER.toString(), EntityType.SPLASH_POTION.toString()));
		
		config.options().copyDefaults(true);
		main.saveConfig();
		loadConfig();
	}
	
	public void loadConfig() {
		mobClearerRadius = config.getInt("settings.MobClearer.radius");
		mobClearerExcludedTypes = config.getStringList("settings.MobClearer.excludedTypes").stream().map(type -> {
			try {
				return EntityType.valueOf(type);
			} catch (IllegalArgumentException e) {
				return null;
			}
		}).toList();
	}
	
	private void saveConfig() {
		config.set("settings.MobClearer.radius", mobClearerRadius);
		config.set("settings.MobClearer.excludedTypes", mobClearerExcludedTypes);
		main.saveConfig();
		loadConfig();
	}

	public int getMobClearerRadius() {
		return mobClearerRadius;
	}

	public void setMobClearerRadius(int mobClearerRadius) {
		this.mobClearerRadius = mobClearerRadius;
		saveConfig();
	}

	public List<EntityType> getMobClearerExcludedTypes() {
		return mobClearerExcludedTypes;
	}

	public void setMobClearerExcludedTypes(List<EntityType> mobClearerExcludedTypes) {
		this.mobClearerExcludedTypes = mobClearerExcludedTypes;
		saveConfig();
	}
}
