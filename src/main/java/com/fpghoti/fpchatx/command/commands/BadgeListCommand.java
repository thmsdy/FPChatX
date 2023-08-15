package com.fpghoti.fpchatx.command.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.fpghoti.fpchatx.FPChat;
import com.fpghoti.fpchatx.badge.Badge;
import com.fpghoti.fpchatx.badge.BadgeList;
import com.fpghoti.fpchatx.command.Commands;
import com.fpghoti.fpchatx.permission.Permission;
import com.fpghoti.fpchatx.player.FPlayer;
import com.fpghoti.fpchatx.util.Util;

public class BadgeListCommand extends Commands {

	public BadgeListCommand(FPChat plugin) {
		super(plugin);
		name = "BadgeList";
		description = "Pulls up a list of badges you own";
		syntax = ChatColor.GRAY + "/badgelist " + ChatColor.GOLD + "[page number]";
		minArgs = 0;
		maxArgs = 1;
		labels.add("fpc badgelist");
		labels.add("fpchat badgelist");
		labels.add("ch badgelist");
		labels.add("badges");
		labels.add("badgelist");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		FPlayer p = null;
		if(sender instanceof Player) {
			p = FPlayer.getPlayer((Player)sender);
		}

		if(!plugin.getMainConfig().isSQLEnabled()) {
			FPlayer.errMsg(null, "MySQL is not enabled.");
			return;
		}

		int pg = 1;
		if (args.length > 0) {
			if(Util.isDigit(args[0])) {
				pg = Integer.parseInt(args[0]);
			}else {
				FPlayer.errMsg(p, syntax);
			}
		}

		BadgeList list = p.getBadgeData().getBadgeList();

		int pageCount = (int) Math.ceil((double) list.getListSize() / 8);
		if (pg > pageCount) {
			pg = pageCount;
		}

		FPlayer.plainMsg(p, "");

		FPlayer.plainMsg(p, ChatColor.GREEN + "Equip badges with " + ChatColor.DARK_AQUA + "/equip <slot id> <badge id>" + ChatColor.GREEN + ".");
		FPlayer.plainMsg(p, ChatColor.GREEN + "Unequip all badges with " + ChatColor.DARK_AQUA + "/badgesclear" + ChatColor.GREEN + ".");
		FPlayer.plainMsg(p, ChatColor.GOLD + "[" + Integer.toString(pg) + "/" + Integer.toString(pageCount) + "] "  + ChatColor.YELLOW + "Badges:");
		FPlayer.plainMsg(p, ChatColor.GOLD + "======================");
		for (int i = 0; i < 8; i++) {
			int index = (pg - 1) * 8 + i;
			if (index < list.getListSize()) {
				Badge badge = list.getIndex(index);
				if(badge != null) {
					int id = badge.getId();
					String name = badge.getName();
					String contents = badge.getContents();
					String perm = badge.getPerm();
					String msg = ChatColor.DARK_AQUA + "ID: " + ChatColor.AQUA + id + ChatColor.DARK_AQUA + " - " + ChatColor.RESET + contents +
							ChatColor.RESET + ChatColor.DARK_AQUA + " - " + ChatColor.WHITE + ChatColor.BOLD + name;
					if(Permission.isAdmin(p)) {
						msg = msg + " " + ChatColor.YELLOW + perm;
					}
					FPlayer.plainMsg(p, msg);
				}
			}
		}

	}

}
