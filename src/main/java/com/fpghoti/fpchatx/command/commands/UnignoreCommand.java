package com.fpghoti.fpchatx.command.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.fpghoti.fpchatx.FPChat;
import com.fpghoti.fpchatx.command.Commands;
import com.fpghoti.fpchatx.player.FPlayer;

public class UnignoreCommand extends Commands {

	public UnignoreCommand(FPChat plugin) {
		super(plugin);
		name = "Unignore";
		description = "Unignores all messages from a player";
		syntax = ChatColor.GRAY + "/unignore " + ChatColor.GOLD + "<player>";
		minArgs = 1;
		maxArgs = 1;
		labels.add("fpc unignore");
		labels.add("fpchat unignore");
		labels.add("ch unignore");
		labels.add("unignore");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(sender instanceof Player) {
			FPlayer p = FPlayer.getPlayer((Player)sender);
			UUID u = plugin.getPlayerCache().getUUID(args[0]);
			if(u == null) {
				FPlayer.errMsg(p, "Invalid player.");
				return;
			}
			OfflinePlayer o = Bukkit.getOfflinePlayer(u);
			if(o != null) {
				FPlayer unignored = FPlayer.getPlayer(o, true);
				p.unignore(unignored);
				FPlayer.goodMsg(p, "Player unignored.");
			} else {
				FPlayer.errMsg(p, "Invalid player.");
			}
		} else {
			FPlayer.errMsg(null, "You must be a player to use unignore.");
		}
	}

}
