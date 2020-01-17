package com.fpghoti.fpchatx.command.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.fpghoti.fpchatx.FPChat;
import com.fpghoti.fpchatx.command.Commands;
import com.fpghoti.fpchatx.permission.Permission;
import com.fpghoti.fpchatx.player.FPlayer;

public class IgnoreCommand extends Commands {

	public IgnoreCommand(FPChat plugin) {
		super(plugin);
		name = "Ignore";
		description = "Ignores all messages from a player";
		syntax = ChatColor.GRAY + "/ignore " + ChatColor.GOLD + "player";
		minArgs = 1;
		maxArgs = 1;
		labels.add("fpc ignore");
		labels.add("fpchat ignore");
		labels.add("ch ignore");
		labels.add("ignore");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(sender instanceof Player) {
			FPlayer p = FPlayer.getPlayer((Player)sender);
			if(Permission.canIgnore(p)) {
				UUID u = plugin.getPlayerCache().getUUID(args[0]);
				if(u == null) {
					FPlayer.errMsg(p, "Invalid player.");
					return;
				}
				OfflinePlayer o = Bukkit.getOfflinePlayer(u);
				
				if(o != null) {
					FPlayer ignored = FPlayer.getPlayer(o, true);
					if(p.getIgnored().contains(u)) {
						FPlayer.errMsg(p, "This player is already ignored.");
						return;
					}
					boolean result = p.ignore(ignored);
					if(result) {
						FPlayer.goodMsg(p, "Player ignored.");
					}else {
						FPlayer.errMsg(p, "This player cannot be ignored.");
					}
				} else {
					FPlayer.errMsg(p, "Invalid player.");
				}
			}
		} else {
			FPlayer.errMsg(null, "You must be a player to use ignore.");
		}
	}

}
