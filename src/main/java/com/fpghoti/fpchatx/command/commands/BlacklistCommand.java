package com.fpghoti.fpchatx.command.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
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

public class BlacklistCommand extends Commands {

	public BlacklistCommand(FPChat plugin) {
		super(plugin);
		name = "Blacklist";
		description = "Toggles blacklisted status of a player in a specific channel";
		syntax = ChatColor.GRAY + "/fpc blacklist " + ChatColor.GOLD + "<channel> <player>";
		minArgs = 1;
		maxArgs = 2;
		labels.add("fpc blacklist");
		labels.add("fpchat blacklist");
		labels.add("ch blacklist");
		labels.add("fpc ban");
		labels.add("fpchat ban");
		labels.add("ch ban");
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
			FPlayer.errMsg(p, "You cannot blacklist a player from the default/fallback channel. If you wish to prevent someone from speaking in default, try muting instead.");
			return;
		}

		if (c != null) {
			if (args.length == 1) {

				String msg = "There are no blacklisted players in this channel.";
				if (!c.getBanned().isEmpty()) {
					msg = c.getName() + " Blacklist: ";
					for (UUID uuid : c.getBanned()) {
						FPlayer bp = FPlayer.getPlayer(Bukkit.getOfflinePlayer(uuid));
						String name = ChatColor.RED + bp.getName();
						if(bp.isOnline()) {
							name = ChatColor.GREEN + bp.getName();
						}
						msg = msg + name + ChatColor.WHITE + ", ";
					}
					msg = msg.substring(0, msg.length() - 2);
				}
				FPlayer.dualMsg(p, msg);

			} else {
				if( p == null || (Permission.isAdmin(p) || Permission.canBan(p) || (c instanceof TempChannel && ((TempChannel)c).getOwner().equals(p)))) {
					FPlayer banned = FPlayer.getPlayer(args[1]);
					if(banned == null) {
						banned = FPlayer.getOfflinePlayer(args[1]);
					}
					if(banned != null) {
						String name = banned.getName();
						if(p == null || !(Permission.isAdmin(banned) || Permission.canBan(banned))) {
							if(c.getBanned().contains(banned.getUniqueId())) {
								c.removeBanned(banned);
								FPlayer.goodMsg(p, ChatColor.YELLOW + name + ChatColor.GREEN + " has been removed from the blacklist of channel " + ChatColor.YELLOW + c.getName() + ChatColor.GREEN + ".");
							}else{
								if(banned.getChannels().contains(c.getName()) || banned.getTempChannels().contains(c.getName())) {
									FPlayer.errMsg(banned, "You have been blacklisted from " + ChatColor.YELLOW + c.getName() + ChatColor.RED + ".");
								}
								c.addBanned(banned);
								c.kick(banned);
								FPlayer.goodMsg(p, ChatColor.YELLOW + name + ChatColor.GREEN + " has been blacklisted from " + ChatColor.YELLOW + c.getName() + ChatColor.GREEN + ".");
								banned.leaveChannel(c.getName());
								if(c instanceof StandardChannel) {
									banned.setPrimaryChannel(ChatChannel.getDefault().getName());
								}
								if(c instanceof TempChannel) {
									banned.stopTempSpeak();
									banned.setPrimaryTempChannel("");
								}
							}
						} else {
							FPlayer.errMsg(p, "Player cannot be removed from the channel.");
						}
					} else {
						FPlayer.errMsg(p, "Cannot find specified player.");
					}
				} else {
					FPlayer.errMsg(p, Permission.noPerm);
				}
			}
		} else {
			FPlayer.errMsg(p,  "That channel does not exist.");
		}
	}

}
