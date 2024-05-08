package de.cric_hammel.admintools.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.cric_hammel.admintools.Main;

public abstract class CustomItem {

	private final Material m;
	private final String lore;
	private final ItemStack item;

	public CustomItem(Material m, String name, String lore) {
		this.m = m;
		this.lore = lore;
		item = createItem(m, name, lore);
	}

	private ItemStack createItem(Material m, String name, String lore) {
		ItemStack item = new ItemStack(m);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(name);
		ArrayList<String> loreList = new ArrayList<>();
		loreList.add(lore);
		loreList.add(Main.LORE_ID);
		itemMeta.setLore(loreList);
		item.setItemMeta(itemMeta);
		item.addUnsafeEnchantment(Enchantment.DIG_SPEED, 10);
		return item;
	}

	public boolean hasInHand(Player p) {
		return isItem(p.getInventory().getItemInMainHand());
	}
	
	public boolean isItem(ItemStack item) {
		
		if (item == null || !item.hasItemMeta()) {
			return false;
		}
		
		ItemMeta meta = item.getItemMeta();
		
		if (!meta.hasLore()) {
			return false;
		}
		
		List<String> loreList = meta.getLore();
		
		if (loreList.size() < 2) {
			return false;
		}

		if (loreList.get(1).equals(Main.LORE_ID) && loreList.get(0).equals(lore) && item.getType() == m) {
			return true;
		}

		return false;
	}
	
	public ItemStack getItem() {
		return item.clone();
	}
}
