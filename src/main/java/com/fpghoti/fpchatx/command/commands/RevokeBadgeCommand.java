package com.fpghoti.fpchatx.command.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.fpghoti.fpchatx.FPChat;
import com.fpghoti.fpchatx.badge.Badge;
import com.fpghoti.fpchatx.command.Commands;
import com.fpghoti.fpchatx.permission.Permission;
import com.fpghoti.fpchatx.player.FPlayer;
import com.fpghoti.fpchatx.util.Util;

public class RevokeBadgeCommand extends Commands {

	public RevokeBadgeCommand(FPChat plugin) {
		super(plugin);
		name = "RevokeBadge";
		description = "Revokes badge from player";
		syntax = ChatColor.GRAY + "/revokebadge " + ChatColor.GOLD + "<player> <badge number>";
		minArgs = 2;
		maxArgs = 2;
		labels.add("fpc revokebadge");
		labels.add("fpchat revokebadge");
		labels.add("ch revokebadge");
		labels.add("revokebadge");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		FPlayer p = null;
		Boolean console = true;
		if(sender instanceof Player){
			console = false;
			p = FPlayer.getPlayer((Player) sender);
		}

		if(console || Permission.isAdmin(p)){
			UUID u = plugin.getPlayerCache().getUUID(args[0]);
			if(u == null) {
				FPlayer.errMsg(null, "Invalid player.");
				return;
			}
			OfflinePlayer o = Bukkit.getOfflinePlayer(u);
			if(o != null) {
				FPlayer toRevoke = FPlayer.getPlayer(o,true);
				if(Util.isDigit(args[1]) || args[1].equals("*")){

					if(args[1].equals("*")) {
						toRevoke.getBadgeData().clearBadgeList();
						FPlayer.goodMsg(p, "All badges revoked from the specified player.");
					}else {

						if(Badge.getList().containsId(Integer.parseInt(args[1]))) {
							int id = Integer.parseInt(args[1]);
							
							toRevoke.getBadgeData().getBadgeList().remove(id);
							toRevoke.getBadgeData().savePlayerData();

							FPlayer.goodMsg(p, "Badge revoked from the specified player.");
						}else {
							FPlayer.errMsg(p, "Invalid badge.");
						}
					}
				}
			}else {
				FPlayer.errMsg(p, "Invalid player.");
			}
		}
	}


}
