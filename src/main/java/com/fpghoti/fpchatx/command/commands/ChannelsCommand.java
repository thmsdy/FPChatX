package com.fpghoti.fpchatx.command.commands;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.fpghoti.fpchatx.FPChat;
import com.fpghoti.fpchatx.chat.ChatChannel;
import com.fpghoti.fpchatx.chat.StandardChannel;
import com.fpghoti.fpchatx.chat.TempChannel;
import com.fpghoti.fpchatx.command.Commands;
import com.fpghoti.fpchatx.player.FPlayer;
import com.fpghoti.fpchatx.util.Util;

import net.md_5.bungee.api.ChatColor;

public class ChannelsCommand extends Commands {
	public ChannelsCommand(FPChat plugin) {
		super(plugin);
		name = "Channels";
		description = "Lists all open channels";
		syntax = ChatColor.GRAY + "/fpc channels " + ChatColor.GOLD + "[page#]";
		minArgs = 0;
		maxArgs = 1;
		labels.add("fpc channels");
		labels.add("fpchat channels");
		labels.add("ch channels");
		labels.add("fpc list");
		labels.add("fpchat list");
		labels.add("ch list");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		FPlayer p = null;
		if(sender instanceof Player) {
			p = FPlayer.getPlayer((Player)sender);
		}
		int pg = 1;
		if (args.length > 0) {
			if(Util.isDigit(args[0])) {
				pg = Integer.parseInt(args[0]);
			}else {
				FPlayer.errMsg(p, syntax);
			}
		}
		ArrayList<String> channels = new ArrayList<String>();
		for(ChatChannel c : ChatChannel.getChannels()) {
			if(p == null || p.canJoin(c)) {
				if(c instanceof StandardChannel) {
					String s = ChatColor.DARK_GREEN + "Channel: " + ChatColor.GREEN + c.getName();
					channels.add(s);
				}else if(c instanceof TempChannel) {
					String s = ChatColor.DARK_AQUA + "TempChannel: " + ChatColor.AQUA + c.getName();
					channels.add(s);
				}
			}
		}
		int pageCount = (int) Math.ceil((double) channels.size() / 8);
		if (pg > pageCount) {
			pg = pageCount;
		}

		FPlayer.plainMsg(p, ChatColor.GOLD + "[" + Integer.toString(pg) + "/" + Integer.toString(pageCount) + "] "  + ChatColor.YELLOW + "Open Channels:");
		for (int i = 0; i < 8; i++) {
			int index = (pg - 1) * 8 + i;
			if (index < channels.size()) {
				String msg = channels.get(index);
				FPlayer.plainMsg(p, msg);
			}
		}
	}

}
