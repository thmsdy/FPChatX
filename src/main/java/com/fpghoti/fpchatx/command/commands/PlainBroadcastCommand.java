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


public class PlainBroadcastCommand extends Commands {

	public PlainBroadcastCommand(FPChat plugin) {
		super(plugin);
		name = "Plain Broadcast";
		description = "Sends a raw text broadcast to all players";
		syntax = ChatColor.GRAY + "/plainbroadcast " + ChatColor.GOLD + "<message>";
		minArgs = 0;
		maxArgs = 100000;
		labels.add("plainbroadcast");
		labels.add("fpc plainbroadcast");
		labels.add("fpchat plainbroadcast");
		labels.add("ch plainbroadcast");
		labels.add("pbc");
		labels.add("fpc pbc");
		labels.add("fpchat pbc");
		labels.add("ch pbc");
		labels.add("gbc");
		labels.add("fpc gbc");
		labels.add("fpchat gbc");
		labels.add("ch gbc");
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
		
		if(args.length > 0) {
			msg = args[0];
		}
		
		if(args.length > 1) {
			for(int i = 1; i < args.length; i++) {
				msg = msg + " " + args[i];
			}
		}
		
		msg = BubbleCode.bubblecode(true, ChatColor.translateAlternateColorCodes('&', HexColor.formatHex(msg)));
		
		for(FPlayer p : FPlayer.getPlayers()) {
			p.sendMessage(msg);
		}
		
		plugin.log(Level.INFO, "Plain Broadcast: " + msg);
	}	

}
