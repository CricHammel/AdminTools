package de.cric_hammel.admintools.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.cric_hammel.admintools.Main;

public class InventoryManager {

	private static final Map<String, Set<Inventory>> invs = new HashMap<String, Set<Inventory>>();
	private static final Material PLACEHOLDER = Material.GRAY_STAINED_GLASS_PANE;
	
	public static Inventory createInventory(Player p, String tag) {
		Inventory inv = Bukkit.createInventory(p, 9 * 4, "§cAdminTools");
		int size = inv.getSize();
		
		for (int slot = 0; slot < size; slot++) {
			
			if (slot < 9 || slot >= (size / 9 - 1) * 9 || slot % 9 == 0 || (slot + 1) % 9 == 0) {
				ItemStack pane = new ItemStack(PLACEHOLDER);
				ItemMeta paneMeta = pane.getItemMeta();
				paneMeta.setDisplayName(" ");
				pane.setItemMeta(paneMeta);
				inv.setItem(slot, pane);
			}
		}
		
		inv.addItem(Main.getItems().stream().map((item) -> item.getItem()).toArray(ItemStack[]::new));
		
		Set<Inventory> invGroup = invs.get(tag);
		
		if (invGroup == null) {
			invGroup = new HashSet<Inventory>();
			invs.put(tag, invGroup);
		}
		
		invGroup.add(inv);
		return inv;
	}
	
	public static boolean exists(Inventory inv) {
		
		for (Map.Entry<String, Set<Inventory>> invGroups : invs.entrySet()) {
			if (invGroups.getValue().contains(inv)) {
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean exists(Inventory inv, String tag) {
		Set<Inventory> invGroup = invs.get(tag);
		
		if (invGroup == null) {
			return false;
		}
		
		return invGroup.contains(inv);
	}
	
	private static void removeInv(Inventory inv) {
		
		for (Map.Entry<String, Set<Inventory>> invGroups : invs.entrySet()) {
			invGroups.getValue().remove(inv);
		}
	}
	
	public static class Listeners implements Listener {
		
		@EventHandler
		public void onInventoryClose(InventoryCloseEvent event) {
			removeInv(event.getInventory());
		}
		
		@EventHandler
		public void onInventoryClick(InventoryClickEvent event) {
			HumanEntity h = event.getWhoClicked();
			
			if (!(h instanceof Player)) {
				return;
			}
			
			if (!InventoryManager.exists(event.getClickedInventory())) {
				return;
			}
			
			ItemStack current = event.getCurrentItem();
			
			if (current != null && current.getType() == PLACEHOLDER) {
				event.setCancelled(true);
			}
		}
	}
}
