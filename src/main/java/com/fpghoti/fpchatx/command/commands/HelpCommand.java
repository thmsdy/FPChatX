package com.fpghoti.fpchatx.command.commands;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.fpghoti.fpchatx.FPChat;
import com.fpghoti.fpchatx.command.Commands;
import com.fpghoti.fpchatx.permission.Permission;
import com.fpghoti.fpchatx.player.FPlayer;
import com.fpghoti.fpchatx.util.Util;

public class HelpCommand extends Commands {

	public HelpCommand(FPChat plugin) {
		super(plugin);
		name = "Help";
		description = "Displays the help menu";
		syntax = ChatColor.GRAY + "/fpc help " + ChatColor.GOLD + "[page numer]";
		minArgs = 0;
		maxArgs = 1;
		labels.add("fpc help");
		labels.add("fpchat help");
		labels.add("ch help");
		labels.add("fpc");
		labels.add("fpchat");
		labels.add("ch");
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

		ArrayList<Commands> commands = Commands.getCommands();

		int pageCount = (int) Math.ceil((double) commands.size() / 8);
		if (pg > pageCount) {
			pg = pageCount;
		}
		
		FPlayer.plainMsg(p, "");
		String name = "";
		String ctype = "Normal";
		if(Permission.isAdmin(p)) {
			name = name + ChatColor.AQUA + "[ADMIN] ";
		}else if(Permission.isStaff(p)) {
			name = name + ChatColor.BLUE + "[STAFF] ";
		}
		name = ChatColor.DARK_AQUA + "Player: " + name + ChatColor.GREEN + p.getName();
		String cname = p.getPrimaryChannel().getName();
		String ccount = Integer.toString(p.getPrimaryChannel().playerCount());
		if(p.speakingInTemp() && p.getPrimaryTempChannel() != null) {
			ccount = Integer.toString(p.getPrimaryTempChannel().playerCount());
			cname = p.getPrimaryTempChannel().getName();
			ctype = "Temporary";
		}
		String primary = ChatColor.DARK_AQUA + "Current Channel: " + ChatColor.GREEN + cname;
		ctype = ChatColor.DARK_AQUA + "Channel Type: " + ChatColor.GREEN + ctype;
		String online = ChatColor.DARK_AQUA + "Members Online: " + ChatColor.GREEN + ccount;
		if(args.length == 0) {
			FPlayer.plainMsg(p, ChatColor.GOLD + "[INFO] "  + ChatColor.YELLOW + "FPChatX:");
			FPlayer.plainMsg(p,name);
			FPlayer.plainMsg(p,primary);
			FPlayer.plainMsg(p,ctype);
			FPlayer.plainMsg(p,online);
			FPlayer.plainMsg(p,ChatColor.GRAY + "Use " + ChatColor.WHITE + "/fpc who " + ChatColor.GRAY + "to see who is in this channel.");
			FPlayer.plainMsg(p, ChatColor.GRAY + "Use " + ChatColor.WHITE + "/fpc help <page number> " + ChatColor.GRAY + "for a list of commands.");
			return;
		}
		FPlayer.plainMsg(p, ChatColor.GOLD + "[" + Integer.toString(pg) + "/" + Integer.toString(pageCount) + "] "  + ChatColor.YELLOW + "FPChatX Commands:");
		for (int i = 0; i < 8; i++) {
			int index = (pg - 1) * 8 + i;
			if (index < commands.size()) {
				String msg = ChatColor.GREEN + "- " + commands.get(index).getSyntax();
				FPlayer.plainMsg(p, msg);
			}
		}

	}

}
