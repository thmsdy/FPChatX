package com.fpghoti.fpchatx.command.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.fpghoti.fpchatx.FPChat;
import com.fpghoti.fpchatx.chat.ChatChannel;
import com.fpghoti.fpchatx.command.Commands;
import com.fpghoti.fpchatx.player.FPlayer;

public class JoinCommand extends Commands {

	public JoinCommand(FPChat plugin) {
		super(plugin);
		name = "Join";
		description = "Enters a channel";
		syntax = ChatColor.GRAY + "/fpc join " + ChatColor.GOLD + "<channel>";
		minArgs = 1;
		maxArgs = 1;
		labels.add("fpc join");
		labels.add("fpchat join");
		labels.add("ch join");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (sender instanceof Player) {
			FPlayer p = FPlayer.getPlayer((Player)sender);
			ChatChannel channel = ChatChannel.getChannel(args[0]);
			if(channel == null) {
				channel = ChatChannel.getTempChannel(args[0]);
			}
			if(channel == null) {
				FPlayer.errMsg(p, "The channel you are trying to join does not exist.");
				return;
			}
			p.joinChannel(channel.getName());
		}
	}

}
