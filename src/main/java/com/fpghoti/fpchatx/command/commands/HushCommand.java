package com.fpghoti.fpchatx.command.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.fpghoti.fpchatx.FPChat;
import com.fpghoti.fpchatx.command.Commands;
import com.fpghoti.fpchatx.permission.Permission;
import com.fpghoti.fpchatx.player.FPlayer;

public class HushCommand extends Commands {

	public HushCommand(FPChat plugin) {
		super(plugin);
		name = "Hush";
		description = "Prevents a player from shouting";
		syntax = ChatColor.GRAY + "/hush " + ChatColor.GOLD + "<player>";
		minArgs = 1;
		maxArgs = 1;
		labels.add("fpc hush");
		labels.add("fpchat hush");
		labels.add("ch hush");
		labels.add("hush");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		FPlayer p = null;
		Boolean console = true;
		if(sender instanceof Player){
			console = false;
			p = FPlayer.getPlayer((Player) sender);
		}

		if(console || Permission.canHush(p)){
			FPlayer hush = FPlayer.getPlayer(args[0]);
			if(hush == null) {
				hush = FPlayer.getPlayer(args[0]);
			}
			if(hush == null) {
				FPlayer.errMsg(p,"That player cannot be found.");
			}else{
				hush.hush();
				FPlayer.dualMsg(hush, "You have been hushed. Your shouts will no longer send.");
				FPlayer.goodMsg(p, "Player can no longer use shout.");
			}
		}
	}

}
