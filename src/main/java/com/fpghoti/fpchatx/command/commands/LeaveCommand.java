package com.fpghoti.fpchatx.command.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.fpghoti.fpchatx.FPChat;
import com.fpghoti.fpchatx.chat.ChatChannel;
import com.fpghoti.fpchatx.command.Commands;
import com.fpghoti.fpchatx.player.FPlayer;

public class LeaveCommand extends Commands {

	public LeaveCommand(FPChat plugin) {
		super(plugin);
		name = "Leave";
		description = "Leaves a channel";
		syntax = ChatColor.GRAY + "/fpc leave " + ChatColor.GOLD + "channel";
		minArgs = 1;
		maxArgs = 1;
		labels.add("fpc leave");
		labels.add("fpchat leave");
		labels.add("ch leave");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (sender instanceof Player) {
			FPlayer p = FPlayer.getPlayer((Player)sender);
			ChatChannel channel = ChatChannel.getChannel(args[0]);
			if(channel == null) {
				FPlayer.errMsg(p, "The channel you are trying to leave does not exist.");
				return;
			}
			p.leaveChannel(channel.getName());
			p.leaveTempChannel(channel.getName());
			FPlayer.goodMsg(p, "You left the channel.");
		}
	}

}
