package com.fpghoti.fpchatx.command.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.fpghoti.fpchatx.FPChat;
import com.fpghoti.fpchatx.chat.ChatFilter;
import com.fpghoti.fpchatx.command.Commands;
import com.fpghoti.fpchatx.permission.Permission;
import com.fpghoti.fpchatx.player.FPlayer;

public class PrefixCommand extends Commands {

	public PrefixCommand(FPChat plugin) {
		super(plugin);
		name = "Prefix";
		description = "Sets your own prefix";
		syntax = ChatColor.GRAY + "/fpc prefix " + ChatColor.GOLD + "<prefix>";
		minArgs = 1;
		maxArgs = 1;
		labels.add("fpc prefix");
		labels.add("fpchat prefix");
		labels.add("ch prefix");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		FPlayer p = null;
		Boolean console = true;
		if(sender instanceof Player){
			console = false;
			p = FPlayer.getPlayer((Player) sender);
		}
		
		if(console) {
			FPlayer.errMsg(p, "This command cannot be used by console.");
			return;
		}

		if(Permission.canChangePrefix(p)){
			String prefix = ChatColor.translateAlternateColorCodes('&', ChatFilter.filterWord(args[0]));
			if(Permission.canChangePrefix(p)) {
				p.setPrefix(prefix);
				FPlayer.goodMsg(p, "Set prefix to: " + prefix);
			}else {
				FPlayer.errMsg(p, Permission.noPerm);
			}
		}
	}

}
