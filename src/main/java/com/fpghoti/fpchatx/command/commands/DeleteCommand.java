package com.fpghoti.fpchatx.command.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.fpghoti.fpchatx.FPChat;
import com.fpghoti.fpchatx.chat.ChatChannel;
import com.fpghoti.fpchatx.chat.TempChannel;
import com.fpghoti.fpchatx.command.Commands;
import com.fpghoti.fpchatx.permission.Permission;
import com.fpghoti.fpchatx.player.FPlayer;

public class DeleteCommand extends Commands {

	public DeleteCommand(FPChat plugin) {
		super(plugin);
		name = "Delete";
		description = "Deletes a channel";
		syntax = ChatColor.GRAY + "/fpc delete " + ChatColor.GOLD + "channel";
		minArgs = 1;
		maxArgs = 1;
		labels.add("fpc delete");
		labels.add("fpchat delete");
		labels.add("ch delete");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {

		FPlayer p = null;
		if(sender instanceof Player) {
			p = FPlayer.getPlayer((Player)sender);
		}

		ChatChannel c = ChatChannel.getChannel(args[0]);

		if(c == null) {
			c = ChatChannel.getTempChannel(args[0]);
		}

		boolean allowed = false;

		if(p == null) {
			allowed = true;
		}else {
			if(c instanceof TempChannel) {
				TempChannel temp = (TempChannel)c;
				if(temp.getOwner().equals(p)) {
					allowed = true;
				}

			}
			if(Permission.canDeleteChannel(p)) {
				allowed = true;
			}
		}
		
		if(!allowed) {
			FPlayer.errMsg(p, Permission.noPerm);
		}

		if(c == ChatChannel.getDefault()) {
			FPlayer.errMsg(p, "You cannot delete the default/fallback channel. If you wish to delete this channel, try setting a different default channel first.");
			return;
		}

		if (c != null) {
			c.delete();
			FPlayer.goodMsg(p,"Channel deleted.");
		} else {
			FPlayer.errMsg(p,  "That channel does not exist.");
		}
	}

}
