package de.cric_hammel.admintools.util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MessageSender {

	public static void infoPlayer(Player p, String message) {
		p.sendMessage(ChatColor.DARK_RED + "[AdminTools] " + ChatColor.AQUA + message);
	}
	
	public static void errorPlayer(Player p, String message) {
		p.sendMessage(ChatColor.DARK_RED + "[AdminTools] " + ChatColor.RED + message);
	}
	
	public static void successPlayer(Player p, String message) {
		p.sendMessage(ChatColor.DARK_RED + "[AdminTools] " + ChatColor.GREEN + message);
	}
}
