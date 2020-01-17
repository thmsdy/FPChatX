package com.fpghoti.fpchatx.command.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.fpghoti.fpchatx.FPChat;
import com.fpghoti.fpchatx.command.Commands;
import com.fpghoti.fpchatx.permission.Permission;
import com.fpghoti.fpchatx.player.FPlayer;

public class SpyCommand extends Commands{

	FPChat plugin;

	public SpyCommand(FPChat plugin) {
		super(plugin);
		name = "Spy";
		description = "Toggles Spy mode";
		syntax = ChatColor.GRAY + "/spy " + ChatColor.GOLD + "on/off";
		minArgs = 1;
		maxArgs = 1;
		labels.add("spy");
		labels.add("fpc spy");
		labels.add("fpchat spy");
		labels.add("ch spy");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (sender instanceof Player) {
			FPlayer p = FPlayer.getPlayer((Player) sender);
			if(Permission.canSpy(p)){
				if(args.length == 1){
					if(args[0].equalsIgnoreCase("true") || args[0].equalsIgnoreCase("on")){
						p.enableSpy();
						FPlayer.goodMsg(p,"PM Spy Enabled!");
					}else if(args[0].equalsIgnoreCase("false") || args[0].equalsIgnoreCase("off")){
						p.disableSpy();
						FPlayer.goodMsg(p,"PM Spy Disabled!");
					}
				}
			}else{
				FPlayer.errMsg(p, "You do not have permission to use this command!");
			}
		}else{
            FPlayer.errMsg(null, "This command is for players only.");
        }
	}
	
}
