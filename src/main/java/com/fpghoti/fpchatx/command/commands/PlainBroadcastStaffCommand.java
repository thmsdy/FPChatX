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


public class PlainBroadcastStaffCommand extends Commands {

	public PlainBroadcastStaffCommand(FPChat plugin) {
		super(plugin);
		name = "Plain Broadcast Staff";
		description = "Sends a raw text broadcast to all players with permissions fpchat.playerbroadcast.hearstaff and gbc.staff";
		syntax = ChatColor.GRAY + "/plainbroadcaststaff " + ChatColor.GOLD + "<message>";
		minArgs = 0;
		maxArgs = 100000;
		labels.add("plainbroadcaststaff");
		labels.add("fpc plainbroadcaststaff");
		labels.add("fpchat plainbroadcaststaff");
		labels.add("ch plainbroadcaststaff");
		labels.add("pbcs");
		labels.add("fpc pbcs");
		labels.add("fpchat pbcs");
		labels.add("ch pbcs");
		labels.add("gbcs");
		labels.add("fpc gbcs");
		labels.add("fpchat gbcs");
		labels.add("ch gbcs");
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
			if(Permission.canHearPlainStaffBroadcast(p)) {
				p.sendMessage(msg);	
			}
		}
		
		plugin.log(Level.INFO, "Plain Broadcast Staff: " + msg);
	}	

}
