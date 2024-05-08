package de.cric_hammel.admintools.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import de.cric_hammel.admintools.util.InventoryManager;

public class GetItemsCommand implements CommandExecutor {

	private static final String INV_TAG = "admintools_getitems";
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (sender instanceof Player) {	
			Player player = (Player) sender;
			
			if (player.hasPermission("admintools.getitems")) {
				
				if (args.length == 0) {
					Inventory inv = InventoryManager.createInventory(player, INV_TAG);
					player.openInventory(inv);
					return true;
				} else
					player.sendMessage("§cToo many arguments! Use §6/getitems§c!");
			} else
				player.sendMessage("§cYou don't have the rights to execute this command.");
		} else
			sender.sendMessage("This command can only be executed by a player!");
		return false;
	}

}
