package de.cric_hammel.admintools.util;

import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class MessageSender {

	private static final String PREFIX = ChatColor.DARK_RED + "[AdminTools] ";
	
	public static void infoPlayer(Player p, String message) {
		p.sendMessage(PREFIX + ChatColor.AQUA + message);
	}
	
	public static void errorPlayer(Player p, String message) {
		p.sendMessage(PREFIX + ChatColor.RED + message);
	}
	
	public static void successPlayer(Player p, String message) {
		p.sendMessage(PREFIX + ChatColor.GREEN + message);
	}
}
