package com.fpghoti.fpchatx.command.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.fpghoti.fpchatx.FPChat;
import com.fpghoti.fpchatx.badge.Badge;
import com.fpghoti.fpchatx.badge.Sync;
import com.fpghoti.fpchatx.command.Commands;
import com.fpghoti.fpchatx.permission.Permission;
import com.fpghoti.fpchatx.player.FPlayer;
import com.fpghoti.fpchatx.util.Util;

public class GiveBadgeCommand extends Commands {

	public GiveBadgeCommand(FPChat plugin) {
		super(plugin);
		name = "GiveBadge";
		description = "Give player a badge";
		syntax = ChatColor.GRAY + "/givebadge " + ChatColor.GOLD + "<player> <badge number>";
		minArgs = 2;
		maxArgs = 2;
		labels.add("fpc givebadge");
		labels.add("fpchat givebadge");
		labels.add("ch givebadge");
		labels.add("givebadge");
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
			FPlayer toGive = FPlayer.getPlayer(args[0]);
			if(toGive == null) {
				toGive = FPlayer.getPlayer(args[0]);
			}
			if(toGive == null) {
				FPlayer.errMsg(p,"That player cannot be found.");
				return;
			}
			if(Util.isDigit(args[1]) && Badge.getList().containsId(Integer.parseInt(args[1]))) {
				int id = Integer.parseInt(args[1]);
				toGive.queueBadgeAdd(id);
				Sync.update(toGive);
				toGive.addSyncedBadge(id);
				FPlayer.goodMsg(p, "Badge granted.");
			}else {
				 FPlayer.errMsg(p,"Wrong command usage.");
			}
		}
	}

}
