package com.fpghoti.fpchatx.command.commands;

import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.fpghoti.fpchatx.FPChat;
import com.fpghoti.fpchatx.command.Commands;
import com.fpghoti.fpchatx.permission.Permission;
import com.fpghoti.fpchatx.player.FPlayer;
import com.fpghoti.fpchatx.util.Util;

public class MessageCommand extends Commands {

	public MessageCommand(FPChat plugin) {
		super(plugin);
		name = "Message";
		description = "Sends a private message to another player";
		syntax = ChatColor.GRAY + "/msg " + ChatColor.GOLD + "<player> <message>";
		minArgs = 2;
		maxArgs = 100000;
		labels.add("msg");
		labels.add("fpc msg");
		labels.add("fpchat msg");
		labels.add("ch msg");
		labels.add("tell");
		labels.add("ch tell");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(sender instanceof Player) {
			FPlayer p = FPlayer.getPlayer((Player)sender);
			if(Permission.canPrivateMessage(p)) {
				Player tp = Util.getEP(args[0]);
				if(tp != null) {
					FPlayer target = FPlayer.getPlayer(tp);
					String msg ="";
		            String last = args[args.length - 1];
		            for(int i = 1; i < args.length - 1; i++) {
		            	msg = msg + args[i] + " ";
		            }
		            msg = msg + last;
		            p.sendPM(target, msg);
				}else {
					FPlayer.errMsg(p, "Target player is not online.");
				}
			}else {
				FPlayer.errMsg(p, Permission.noPerm);
			}
		}else {
			String header = ChatColor.GREEN + "PM from CONSOLE: " + ChatColor.GRAY;
			String msg = "";
            String last = args[args.length - 1];
            for(int i = 1; i < args.length - 1; i++) {
            	msg = msg + args[i] + " ";
            }
            msg = msg + last;
            
            Player target = Util.getEP(args[0]);
            if(target == null) {
            	FPlayer.errMsg(null,"Invalid player.");
            }else {
            	target.sendMessage(header + msg);
            	plugin.log(Level.INFO, ChatColor.GREEN + "PM to " + target.getName() + ": " + ChatColor.GRAY + msg);
            }
		}

	}	

}
