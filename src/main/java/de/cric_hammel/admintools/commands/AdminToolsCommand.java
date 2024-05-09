package de.cric_hammel.admintools.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import de.cric_hammel.admintools.Main;
import de.cric_hammel.admintools.util.MessageSender;

public class AdminToolsCommand implements TabExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (sender instanceof Player) {	
			Player player = (Player) sender;
			
			if (player.hasPermission("admintools.getitems")) {
				
				if (args.length == 0) {
					String version = Main.getPlugin().getDescription().getVersion();
					
					MessageSender.infoPlayer(player, "§7-------<§4AdminTools §cv" + version + "§7>-------");
					MessageSender.infoPlayer(player, "");
					MessageSender.infoPlayer(player, "Commands:");
					MessageSender.infoPlayer(player, "/admintools: Get this message");
					MessageSender.infoPlayer(player, "/admintools reload: Reload the config");
					MessageSender.infoPlayer(player, "/getitems: Open the menu with all the AdminTools");
					MessageSender.infoPlayer(player, "");
					MessageSender.infoPlayer(player, "§7-------<§4AdminTools §cv" + version + "§7>-------");
					
					return true;
				} else if (args.length == 1) {
					
					if (args[0].equalsIgnoreCase("reload")) {
						boolean success = Main.getSettings().loadConfig();
						
						if (success) {
							MessageSender.successPlayer(player, "Successfully reloaded config!");
						} else {
							MessageSender.errorPlayer(player, "Could not reload config!");
						}
					} else
						player.sendMessage("§cWrong arguments! Use §6/admintools [version]§c!");
				} else
					player.sendMessage("§cToo many arguments! Use §6/admintools [version]§c!");
			} else
				player.sendMessage("§cYou don't have the rights to execute this command.");
		} else
			sender.sendMessage("This command can only be executed by a player!");
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		List<String> completions = new ArrayList<>();

		if (args.length == 1) {
			completions.add("reload");
		}
		
		return completions;
	}
}
