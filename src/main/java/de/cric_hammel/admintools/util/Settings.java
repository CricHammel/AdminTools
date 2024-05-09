package de.cric_hammel.admintools.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.stream.Collectors;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;

import de.cric_hammel.admintools.Main;

public class Settings {

	private static final String SETTINGS_KEY = "settings";
	private static final String MOB_CLEARER_KEY = "MobClearer.";
	private static final String MOB_CLEARER_RADIUS_KEY = MOB_CLEARER_KEY + "radius";
	private static final String MOB_CLEARER_EXCLUDED_TYPES_KEY = MOB_CLEARER_KEY + "excludedTypes";
	private FileConfiguration config;
	private Main main;
	private Map<String, Object> settings = new HashMap<String, Object>();
	private List<EntityType> mobClearerExcludedTypes;
	
	public Settings(FileConfiguration config, Main main) {
		this.config = config;
		this.main = main;
		config.addDefault(SETTINGS_KEY + "." + MOB_CLEARER_RADIUS_KEY, 5);
		config.addDefault(SETTINGS_KEY + "." + MOB_CLEARER_EXCLUDED_TYPES_KEY, Arrays.asList(EntityType.PLAYER.toString(), EntityType.SPLASH_POTION.toString()));
		
		config.options().copyDefaults(true);
		main.saveConfig();
		loadConfig();
	}
	
	public boolean loadConfig() {
		main.reloadConfig();
		config = main.getConfig();
		ConfigurationSection section = config.getConfigurationSection(SETTINGS_KEY);
		
		if (section != null) {
			settings = section.getValues(true);
		} else {
			return false;
		}
		
		@SuppressWarnings("unchecked")
		List<String> excludedTypes = (List<String>) settings.getOrDefault(MOB_CLEARER_EXCLUDED_TYPES_KEY, List.of());
		mobClearerExcludedTypes = excludedTypes.stream().map(s -> {
			try {
				return EntityType.valueOf(s);
			} catch (IllegalArgumentException e) {
				main.getLogger().log(Level.WARNING, "Could not read value '" + s + "' from config, skipping...");
				return null;
			}
		}).filter(out -> out != null).collect(Collectors.toList());
		
		return true;
	}

	public int getMobClearerRadius() {
		return (int) settings.getOrDefault(MOB_CLEARER_RADIUS_KEY, 5);
	}

	public List<EntityType> getMobClearerExcludedTypes() {
		return mobClearerExcludedTypes;
	}
}
