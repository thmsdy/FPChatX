package com.fpghoti.fpchatx.command.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.fpghoti.fpchatx.FPChat;
import com.fpghoti.fpchatx.command.Commands;
import com.fpghoti.fpchatx.permission.Permission;
import com.fpghoti.fpchatx.player.FPlayer;

public class ReplyCommand extends Commands {

	public ReplyCommand(FPChat plugin) {
		super(plugin);
		name = "Reply";
		description = "Reply to a PM from another player";
		syntax = ChatColor.GRAY + "/r " + ChatColor.GOLD + "<message>";
		minArgs = 1;
		maxArgs = 10000;
		labels.add("reply");
		labels.add("fpc reply");
		labels.add("fpchat reply");
		labels.add("ch reply");
		labels.add("r");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (sender instanceof Player) {
			FPlayer p = FPlayer.getPlayer((Player)sender);
			if(Permission.canPrivateMessage(p)) {
				FPlayer target = p.getLastMessage();
				if(target != null) {
					String msg ="";
		            String last = args[args.length - 1];
		            for(int i = 0; i < args.length - 1; i++) {
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
		}
	}

}
