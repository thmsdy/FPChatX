package com.fpghoti.fpchatx.command.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.fpghoti.fpchatx.FPChat;
import com.fpghoti.fpchatx.command.Commands;
import com.fpghoti.fpchatx.permission.Permission;
import com.fpghoti.fpchatx.player.FPlayer;

public class ShoutToggleCommand extends Commands {

    public ShoutToggleCommand(FPChat plugin) {
        super(plugin);
        name = "ShoutToggle";
        description = "Temporarily enables or disables shout";
        syntax = ChatColor.GRAY + "/shouttoggle";
        minArgs = 0;
        maxArgs = 0;
        labels.add("fpc shouttoggle");
        labels.add("fpchat shouttoggle");
        labels.add("ch shouttoggle");
        labels.add("shouttoggle");
        labels.add("frankoffshout");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
		if (sender instanceof Player) {
			FPlayer p = FPlayer.getPlayer((Player) sender);
			if(Permission.canToggleShout(p)){
				if(args.length == 0){
					if(!p.isShoutVisible()){
						p.showShout();
						FPlayer.goodMsg(p,"Now showing shout!");
					}else if(p.isShoutVisible()){
						p.hideShout();
						FPlayer.goodMsg(p,"Now hiding shout!");
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
