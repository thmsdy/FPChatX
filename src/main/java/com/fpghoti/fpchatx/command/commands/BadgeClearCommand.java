package com.fpghoti.fpchatx.command.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.fpghoti.fpchatx.FPChat;
import com.fpghoti.fpchatx.command.Commands;
import com.fpghoti.fpchatx.player.FPlayer;

public class BadgeClearCommand extends Commands {

	public BadgeClearCommand(FPChat plugin) {
		super(plugin);
		name = "BadgeClear";
		description = "Unequips all badges";
		syntax = ChatColor.GRAY + "/badgeclear";
		minArgs = 0;
		maxArgs = 0;
		labels.add("fpc badgeclear");
		labels.add("fpchat badgeclear");
		labels.add("ch badgeclear");
		labels.add("badgeclear");
		labels.add("badgesclear");
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
		p.setBadge(1, 0);
		p.updateBadges(1, 0);
		p.setBadge(2, 0);
		p.updateBadges(2, 0);
		p.setBadge(3, 0);
		p.updateBadges(3, 0);
		p.sendMessage(FPChat.logo() + ChatColor.YELLOW + " Unequipped all badges!");
	}

}
