package de.cric_hammel.admintools.items;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.projectiles.ProjectileSource;

import de.cric_hammel.admintools.Main;
import de.cric_hammel.admintools.util.MessageSender;

public class MobClearer extends CustomItem {

	public MobClearer() {
		super(Material.SPLASH_POTION, ChatColor.RED + "Entity Clearer", "Clears all entities within a set radius on impact");
	}
	
	public static class Listeners implements Listener {
		
		@EventHandler
		public void onPotionSplash(PotionSplashEvent event) {
			ThrownPotion potion = event.getPotion();
			ProjectileSource shooter = potion.getShooter();
			
			if (!(shooter instanceof Player) || !(new MobClearer().isItem(potion.getItem()))) {
				return;
			}
			
			Player p = (Player) shooter;
			int r = Main.settings.getMobClearerRadius();
			List<EntityType> excludedTypes = Main.settings.getMobClearerExcludedTypes();
			Collection<Entity> entities = potion.getWorld().getNearbyEntities(potion.getLocation(), r, r, r, e -> !excludedTypes.contains(e.getType()));
			Map<EntityType, Integer> counts = new HashMap<EntityType, Integer>();
			entities.forEach(e -> {
				EntityType type = e.getType();
				Integer count = counts.get(type);
				
				if (count == null) {
					count = 0;
				}
				
				counts.put(type, count + 1);
				e.remove();
			});
			
			if (counts.size() == 0) {
				MessageSender.errorPlayer(p, "Removed nothing");
			} else {
				MessageSender.infoPlayer(p, "Removed: ");
				
				counts.forEach((type, count) -> MessageSender.infoPlayer(p, count + "x " + type.getKey().getKey()));
			}
		}
	}
}
