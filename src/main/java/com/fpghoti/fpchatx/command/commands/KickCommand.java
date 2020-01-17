package com.fpghoti.fpchatx.command.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.fpghoti.fpchatx.FPChat;
import com.fpghoti.fpchatx.chat.ChatChannel;
import com.fpghoti.fpchatx.chat.StandardChannel;
import com.fpghoti.fpchatx.chat.TempChannel;
import com.fpghoti.fpchatx.command.Commands;
import com.fpghoti.fpchatx.permission.Permission;
import com.fpghoti.fpchatx.player.FPlayer;

public class KickCommand extends Commands {

	public KickCommand(FPChat plugin) {
		super(plugin);
		name = "Kick";
		description = "Kicks a player from a channel";
		syntax = ChatColor.GRAY + "/fpc kick " + ChatColor.GOLD + "channel player";
		minArgs = 2;
		maxArgs = 2;
		labels.add("fpc kick");
		labels.add("fpchat kick");
		labels.add("ch kick");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {

		FPlayer p = null;
		if(sender instanceof Player) {
			p = FPlayer.getPlayer((Player)sender);
		}

		ChatChannel c = ChatChannel.getChannel(args[0]);

		if(c == null) {
			c = ChatChannel.getTempChannel(args[0]);
		}

		if(c == ChatChannel.getDefault()) {
			FPlayer.errMsg(p, "You cannot kick a player from the default/fallback channel. If you wish to prevent someone from speaking in default, try muting instead.");
			return;
		}

		if (c != null) {
			if( p == null || (Permission.isAdmin(p) || Permission.canBan(p))) {
				FPlayer kicked = FPlayer.getPlayer(args[1]);
				if(kicked == null) {
					kicked = FPlayer.getOfflinePlayer(args[1]);
				}
				if(kicked != null) {
					String name = kicked.getName();
					if(p == null || !(Permission.isAdmin(kicked) || Permission.canBan(kicked))) {
						if(kicked.getChannels().contains(c.getName())) {
							c.kick(kicked);
							FPlayer.goodMsg(p, ChatColor.YELLOW + name + ChatColor.GREEN + " has been kicked from " + c.getName());
							FPlayer.errMsg(kicked, "You have been kicked from " + ChatColor.YELLOW + c.getName());
							kicked.leaveChannel(c.getName());
							if(c instanceof StandardChannel) {
								kicked.setPrimaryChannel(ChatChannel.getDefault().getName());
							}
							if(c instanceof TempChannel) {
								kicked.stopTempSpeak();
								kicked.setPrimaryTempChannel("");
							}
						}
					} else {
						FPlayer.errMsg(p, "Player cannot be kicked from the channel.");
					}
				} else {
					FPlayer.errMsg(p, "Cannot find specified player.");
				}
			} else {
				FPlayer.errMsg(p, Permission.noPerm);
			}
		} else {
			FPlayer.errMsg(p,  "That channel does not exist.");
		}
	}

}
