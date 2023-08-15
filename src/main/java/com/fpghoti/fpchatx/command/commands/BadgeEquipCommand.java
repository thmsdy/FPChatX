package com.fpghoti.fpchatx.command.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.fpghoti.fpchatx.FPChat;
import com.fpghoti.fpchatx.badge.Badge;
import com.fpghoti.fpchatx.badge.BadgeEquipResult;
import com.fpghoti.fpchatx.command.Commands;
import com.fpghoti.fpchatx.player.FPlayer;
import com.fpghoti.fpchatx.util.Util;

public class BadgeEquipCommand extends Commands {

	public BadgeEquipCommand(FPChat plugin) {
		super(plugin);
		name = "BadgeEquip";
		description = "Equips badges for use in chat";
		syntax = ChatColor.GRAY + "/equip " + ChatColor.GOLD + "<slot number> <badge number>";
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
		
		if(!plugin.getMainConfig().isSQLEnabled()) {
			FPlayer.errMsg(null, "MySQL is not enabled.");
			return;
		}
		
		FPlayer p = FPlayer.getPlayer((Player)sender);
		if(Util.isDigit(args[0]) && Util.isDigit(args[1])){

			Integer slot = Integer.parseInt(args[0]), badgeId = Integer.parseInt(args[1]);
			
			if(!Badge.getList().containsId(badgeId)) {
				p.sendMessage(FPChat.logo() + ChatColor.RED + " Invalid badge!");
				return;
			}
			
			Badge badge = Badge.getList().get(badgeId);
			BadgeEquipResult result = p.getBadgeData().equipBadge(slot, badge);
			
			switch(result) {
				
				case SUCCESS:
					p.sendMessage(FPChat.logo() + ChatColor.YELLOW + " You successfully equiped a badge!");
					break;
					
				default:
					p.sendMessage(FPChat.logo() + ChatColor.RED + " Could not equip badge: " + result.toString());
			
			}
		}
	}
	
}
