package com.fpghoti.fpchatx.command.commands;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.fpghoti.fpchatx.FPChat;
import com.fpghoti.fpchatx.command.Commands;
import com.fpghoti.fpchatx.player.FPlayer;
import com.fpghoti.fpchatx.util.Util;

public class IgnoreListCommand extends Commands {

	public IgnoreListCommand(FPChat plugin) {
		super(plugin);
		name = "Ignore List";
		description = "Lists all players you have ignored";
		syntax = ChatColor.GRAY + "/ignorelist " + ChatColor.GOLD + "[page number]";
		minArgs = 0;
		maxArgs = 1;
		labels.add("fpc ignorelist");
		labels.add("fpchat ignorelist");
		labels.add("ch ignorelist");
		labels.add("ignorelist");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (sender instanceof Player) {
			FPlayer p = FPlayer.getPlayer((Player)sender);

			int pg = 1;
			if (args.length > 0) {
				if(Util.isDigit(args[0])) {
					pg = Integer.parseInt(args[0]);
				}else {
					FPlayer.errMsg(p, syntax);
				}
			}

			ArrayList<UUID> players = p.getIgnored();
			
			if(players.size() == 0) {
				FPlayer.dualMsg(p, "Your ignore list is currently empty.");
				return;
			}

			int pageCount = (int) Math.ceil((double) players.size() / 8);
			if (pg > pageCount) {
				pg = pageCount;
			}

			FPlayer.plainMsg(p, "");
			FPlayer.plainMsg(p, ChatColor.GOLD + "[" + Integer.toString(pg) + "/" + Integer.toString(pageCount) + "] "  + ChatColor.YELLOW + "Ignored Players:");
			for (int i = 0; i < 8; i++) {
				int index = (pg - 1) * 8 + i;
				if (index < players.size()) {
					OfflinePlayer o = Bukkit.getOfflinePlayer(players.get(index));
					String msg = ChatColor.GREEN + "- " + ChatColor.YELLOW + o.getName();
					FPlayer.plainMsg(p, msg);
				}
			}

		}else{
			FPlayer.errMsg(null, "This command is for players only.");
		}
	}

}
