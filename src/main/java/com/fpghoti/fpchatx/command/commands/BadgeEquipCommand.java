package com.fpghoti.fpchatx.command.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.fpghoti.fpchatx.FPChat;
import com.fpghoti.fpchatx.badge.BadgeGetter;
import com.fpghoti.fpchatx.badge.BadgeList;
import com.fpghoti.fpchatx.badge.BadgeSet;
import com.fpghoti.fpchatx.command.Commands;
import com.fpghoti.fpchatx.player.FPlayer;
import com.fpghoti.fpchatx.util.Util;

public class BadgeEquipCommand extends Commands {

	public BadgeEquipCommand(FPChat plugin) {
		super(plugin);
		name = "BadgeEquip";
		description = "Equips badges for use in chat";
		syntax = ChatColor.GRAY + "/equip " + ChatColor.GOLD + "slot# badge#";
		minArgs = 2;
		maxArgs = 2;
		labels.add("fpc badgeequip");
		labels.add("fpchat badgeequip");
		labels.add("ch badgeequip");
		labels.add("badgeequip");
		labels.add("equip");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(!(sender instanceof Player)) {
			FPlayer.errMsg(null, "This command is for players only.");
			return;
		}
		
		FPlayer p = FPlayer.getPlayer((Player)sender);
		if(Util.isDigit(args[0]) && Util.isDigit(args[1])){

			Integer slot = Integer.parseInt(args[0]), badgeId = Integer.parseInt(args[1]);
			if(BadgeGetter.canUseSlot(p, slot)){
				if(BadgeList.badgelist.containsKey(badgeId) && BadgeGetter.hasBadge(p, badgeId)){
					BadgeSet.setBadge(p, slot, badgeId);
					p.updateBadges(slot, badgeId);
					p.sendMessage( FPChat.logo() + ChatColor.YELLOW + " You have equipped a badge!");
				}else{
					p.sendMessage(FPChat.logo() + ChatColor.YELLOW + " You do not have permission to equip this badge!");
				}
			}else{
				p.sendMessage(FPChat.logo() + ChatColor.YELLOW + " You do not have permission to equip a badge in this slot!");
			}
		}
	}
	
}
