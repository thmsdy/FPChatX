package com.fpghoti.fpchatx.command.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.fpghoti.fpchatx.FPChat;
import com.fpghoti.fpchatx.command.Commands;
import com.fpghoti.fpchatx.permission.Permission;
import com.fpghoti.fpchatx.player.FPlayer;

public class ReloadCommand extends Commands {

	public ReloadCommand(FPChat plugin) {
		super(plugin);
		name = "Reload";
		description = "Reloads the plugin's configuration and data files.";
		syntax = "§7/fpc reload";
		minArgs = 0;
		maxArgs = 0;
		labels.add("fpc reload");
		labels.add("fpchat reload");
		labels.add("ch reload");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		FPlayer p = null;
		boolean allowed = true;
		if(sender instanceof Player) {
			p = FPlayer.getPlayer((Player)sender);
			allowed = Permission.isAdmin(p);
		}
		
		if(allowed) {
			
			FPlayer.dualMsg(p,"Reloading config files...");
			plugin.reload();
			FPlayer.goodMsg(p,"Config files reloaded.");
			
		}else {
			FPlayer.errMsg(p, Permission.noPerm);
		}
		
	}

}
