package com.fpghoti.fpchatx.command.commands;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.fpghoti.fpchatx.FPChat;
import com.fpghoti.fpchatx.chat.ChatChannel;
import com.fpghoti.fpchatx.chat.ChatFilter;
import com.fpghoti.fpchatx.chat.StandardChannel;
import com.fpghoti.fpchatx.command.Commands;
import com.fpghoti.fpchatx.permission.Permission;
import com.fpghoti.fpchatx.player.FPlayer;
import com.fpghoti.fpchatx.util.Util;

public class CreateCommand extends Commands {

	public CreateCommand(FPChat plugin) {
		super(plugin);
		name = "Create";
		description = "Makes a new channel";
		syntax = ChatColor.GRAY + "/fpc create " + ChatColor.GOLD + "<temp> name <flags>";
		minArgs = 1;
		maxArgs = 2 + validflags.length;
		labels.add("fpc create");
		labels.add("fpchat create");
		labels.add("ch create");
		misc.add("Possible Flags:");
		misc.add("-w : Create with whitelist enabled");
		misc.add("-a : Broadcast alert to all players when channel is made");
		misc.add("-p : Set your primary channel to the new channel upon creation");
	}
	
	private static String[] validflags = {"-w", "-a", "-p"};

	@Override
	public void execute(CommandSender sender, String[] args) {
		FPlayer p = null;
		if (sender instanceof Player) {
			p = FPlayer.getPlayer((Player)sender);
		}

		boolean canTemp = false;
		boolean canStandard = false;
		boolean canAlert = false;
		boolean isTemp = false;
		
		boolean whitelist = false;
		boolean alert = false;
		boolean primary = false;

		String name;
		ArrayList<String> flags = new ArrayList<String>();

		ChatChannel channel;

		if(p == null) {
			canStandard = true;
			canAlert = true;
		}else {
			canTemp = Permission.canMakeTempChannel(p);
			canStandard = Permission.canMakeChannel(p);
			canAlert = Permission.canAlertMakeChannel(p);
		}

		if(args[0].equalsIgnoreCase("temp")) {
			isTemp = true;
		}

		if(isTemp && args.length  == 1) {
			FPlayer.errMsg(p, syntax);
			return;
		}

		if(isTemp && !canTemp) {
			if(p == null) {
				FPlayer.errMsg(p,"Temporary channels are designed for player use only.");
			}else {
				FPlayer.errMsg(p, "You do not have permission to create temporary channels.");
			}
			return;
		}

		if(!isTemp && !canStandard) {
			FPlayer.errMsg(p, "You do not have permission to create permanent channels.");
			return;
		}

		if(isTemp) {
			name = args[1];
			if(args.length > 2) {
				for(int i = 2; i < args.length; i++) {
					flags.add(args[i]);
				}
			}
		}else {
			name = args[0];
			if(args.length > 1) {
				for(int i = 1; i < args.length; i++) {
					flags.add(args[i]);
				}
			}
		}
		
		if(FPChat.getPlugin().getMainConfig().chatFilterEnabled()) {
			if(!ChatFilter.filterWord(name).equals(name)) {
				FPlayer.errMsg(p, "This channel name is not allowed on this server.");
				return;
			}
		}
		
		for(String s : flags) {
			if(!Util.contains(validflags, s.toLowerCase())) {
				FPlayer.errMsg(p, "You tried use an invalid flag.");
				return;
			}
			if(p == null && s.equalsIgnoreCase("-p")) {
				FPlayer.errMsg(p, "Console cannot set a primary chat channel.");
				return;
			}
			if(s.equalsIgnoreCase("-w")) {
				whitelist = true;
			}
			if(s.equalsIgnoreCase("-a")) {
				alert = true;
			}
			if(s.equalsIgnoreCase("-p")) {
				primary = true;
			}
		}

		if(ChatChannel.isReserved(name)) {
			FPlayer.errMsg(p, "This name is unavailable. Please try something else.");
			return;
		}

		if(isTemp) {
			channel = ChatChannel.makeTempChannel(p, name);
			p.joinChannel(name);
			if(primary) {
				p.speakingInTemp();
				p.setPrimaryTempChannel(name);
			}
		}else {
			channel = ChatChannel.makeChannel(name);
			StandardChannel loaded = ChatChannel.loadChannel(name);
			if(loaded != null) {
				ChatChannel.addChannel(loaded);
			}
			if(p != null) {
				p.joinChannel(name);
				if(primary) {
					p.stopTempSpeak();
					p.setPrimaryChannel(name);
				}
			}
		}
		
		if(whitelist) {
			channel.enableWhitelist();
		}
		if(alert) {
			if(canAlert) {
				String  creator = "CONSOLE";
				if(p != null) {
					creator = p.getName();
				}
				for(FPlayer player : FPlayer.getPlayers()) {
					FPlayer.plainMsg(player, ChatColor.YELLOW + creator + ChatColor.AQUA + " has created a new channel: " + ChatColor.WHITE + name);
				}
			}else {
				FPlayer.errMsg(p, "You do not have permission to send an alert.");
				return;
			}
		}
		
		FPlayer.goodMsg(p, "Channel created!");
	}

}
