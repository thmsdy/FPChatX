package com.fpghoti.fpchatx.command.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.fpghoti.fpchatx.FPChat;
import com.fpghoti.fpchatx.badge.BadgeList;
import com.fpghoti.fpchatx.command.Commands;
import com.fpghoti.fpchatx.player.FPlayer;
import com.fpghoti.fpchatx.util.Util;

public class BadgeListCommand extends Commands {

	public BadgeListCommand(FPChat plugin) {
		super(plugin);
		name = "BadgeList";
		description = "Pulls up a list of badges you own";
		syntax = ChatColor.GRAY + "/badgelist " + ChatColor.GOLD + "[page number]";
		minArgs = 0;
		maxArgs = 1;
		labels.add("fpc badgelist");
		labels.add("fpchat badgelist");
		labels.add("ch badgelist");
		labels.add("badges");
		labels.add("badgelist");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(!(sender instanceof Player)) {
			FPlayer.errMsg(null, "This command is for players only.");
			return;
		}

		if(!plugin.getMainConfig().mySQLEnabled()) {
			FPlayer.errMsg(null, "MySQL is not enabled.");
			return;
		}
		
		FPlayer p = FPlayer.getPlayer((Player)sender);
		if(args.length == 0 || !Util.isDigit(args[0])){
			BadgeList.badgeList(p, 1);
		}else{
			BadgeList.badgeList(p,  Integer.parseInt(args[0]));
		}
	}

}
