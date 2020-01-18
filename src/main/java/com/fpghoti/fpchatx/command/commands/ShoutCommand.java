package com.fpghoti.fpchatx.command.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.fpghoti.fpchatx.FPChat;
import com.fpghoti.fpchatx.command.Commands;
import com.fpghoti.fpchatx.player.FPlayer;

public class ShoutCommand extends Commands {

	public ShoutCommand(FPChat plugin) {
		super(plugin);
		name = "Shout";
		description = "Sends a chat message to every online player";
		syntax = ChatColor.GRAY + "/shout " + ChatColor.GOLD + "<message>";
		minArgs = 0;
		maxArgs = 100000;
		labels.add("fpc shout");
		labels.add("fpchat shout");
		labels.add("ch shout");
		labels.add("fshout");
		labels.add("fyell");
		labels.add("shout");
		labels.add("yell");
		labels.add("y");
	}


	@Override
	public void execute(CommandSender sender, String[] args) {
		if (sender instanceof Player) {
            FPlayer p = FPlayer.getPlayer((Player)sender);
            String msg = "";
            String last = args[args.length - 1];
            for(int i = 0; i < args.length - 1; i++) {
            	msg = msg + args[i] + " ";
            }
            msg = msg + last;
            p.shout(msg);
		}else{
            FPlayer.errMsg(null, "This command is for players only.");
        }
		
	}

}
