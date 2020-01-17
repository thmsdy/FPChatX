package com.fpghoti.fpchatx.command.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.fpghoti.fpchatx.FPChat;
import com.fpghoti.fpchatx.command.Commands;
import com.fpghoti.fpchatx.permission.Permission;
import com.fpghoti.fpchatx.player.FPlayer;

public class UnHushCommand extends Commands {

	public UnHushCommand(FPChat plugin) {
		super(plugin);
		name = "UnHush";
		description = "Unhushes a player";
		syntax = ChatColor.GRAY + "/unhush " + ChatColor.GOLD + "player";
		minArgs = 1;
		maxArgs = 1;
		labels.add("fpc unhush");
		labels.add("fpchat unhush");
		labels.add("ch unhush");
		labels.add("unhush");
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
				hush.unhush();
				FPlayer.dualMsg(hush, "You have been unhushed. Your shouts will now send.");
				FPlayer.goodMsg(p, "Player can now use shout.");
			}
		}
	}

}
