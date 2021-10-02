package com.fpghoti.fpchatx.command.commands;

import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.fpghoti.fpchatx.FPChat;
import com.fpghoti.fpchatx.chat.BubbleCode;
import com.fpghoti.fpchatx.chat.HexColor;
import com.fpghoti.fpchatx.command.Commands;
import com.fpghoti.fpchatx.permission.Permission;
import com.fpghoti.fpchatx.player.FPlayer;


public class PlainBroadcastPlayerCommand extends Commands {

	public PlainBroadcastPlayerCommand(FPChat plugin) {
		super(plugin);
		name = "Plain Broadcast Player";
		description = "Sends a raw text broadcast to specified player";
		syntax = ChatColor.GRAY + "/plainbroadcastplayer " + ChatColor.GOLD + "<Player> <message>";
		minArgs = 1;
		maxArgs = 100000;
		labels.add("plainbroadcast player");
		labels.add("fpc plainbroadcastplayer");
		labels.add("fpchat plainbroadcastplayer");
		labels.add("ch plainbroadcastplayer");
		labels.add("pbcp");
		labels.add("fpc pbcp");
		labels.add("fpchat pbcp");
		labels.add("ch pbcp");
		labels.add("gbcp");
		labels.add("fpc gbcp");
		labels.add("fpchat gbcp");
		labels.add("ch gbcp");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(sender instanceof Player) {
			FPlayer p = FPlayer.getPlayer((Player)sender);
			if(!Permission.canPlainBroadcast(p)) {
				FPlayer.errMsg(p, Permission.noPerm);
				return;
			}
		}
		
		String msg = "";
		
		if(args.length > 1) {
			msg = args[1];
		}
		
		if(args.length > 2) {
			for(int i = 2; i < args.length; i++) {
				msg = msg + " " + args[i];
			}
		}
		
		msg = BubbleCode.bubblecode(true, ChatColor.translateAlternateColorCodes('&', HexColor.formatHex(msg)));
		
		for(FPlayer p : FPlayer.getPlayers()) {
			if(p.getName().toLowerCase().equals(args[0].toLowerCase())) {
				p.sendMessage(msg);
				plugin.log(Level.INFO, "Plain Broadcast to " + p.getName() + ": "+  msg);
				return;
			}
		}
		
		if(sender instanceof Player) {
			FPlayer p = FPlayer.getPlayer((Player)sender);
			FPlayer.errMsg(p, args[0] + " is not an online player");
		}else {
			plugin.log(Level.INFO, args[0] + " is not an online player");
		}
		
		
	}	

}
