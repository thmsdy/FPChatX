package com.fpghoti.fpchatx.chat;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.fpghoti.fpchatx.FPChat;
import com.fpghoti.fpchatx.customcodes.BubbleCode;
import com.fpghoti.fpchatx.customcodes.Codify;
import com.fpghoti.fpchatx.permission.Permission;
import com.fpghoti.fpchatx.player.FPlayer;
import com.fpghoti.fpchatx.util.VaultUtil;

public class PrepareChat {

	public static String swapPlaceholders(FPlayer p, ChatChannel channel, String msg) {
		return swapPlaceholders(p,channel,msg,"", false);
	}

	public static String swapPlaceholders(FPlayer p, String recipient, String msg, boolean in) {
		return swapPlaceholders(p,null,msg,recipient,in);
	}
	
	public static String swapPlaceholders(FPlayer p, String msg) {
		return swapPlaceholders(p,null,msg,"",false);
	}


	private static String swapPlaceholders(FPlayer p, ChatChannel channel, String msg, String recipient, boolean in) {
		if(!p.isOnline()) {
			return "";
		}
		Player sender = Bukkit.getPlayer(p.getUniqueId());
		String prefix = "";
		String suffix = "";
		String group = "";
		String groupPrefix = "";
		String groupSuffix = "";
		World world = sender.getWorld();
		prefix = VaultUtil.chat.getPlayerPrefix(sender);
		prefix = BubbleCode.bubblecode(Permission.canBubbleCode(p), Codify.changeFormatSign(prefix)).replaceAll("&([0-9A-FK-OR])", "§$1").replace("&", "§");
		suffix = VaultUtil.chat.getPlayerSuffix(sender);
		suffix = BubbleCode.bubblecode(Permission.canBubbleCode(p), Codify.changeFormatSign(suffix)).replaceAll("&([0-9A-FK-OR])", "§$1").replace("&", "§");
		group = VaultUtil.permission.getPrimaryGroup(sender);
		groupPrefix = VaultUtil.chat.getGroupPrefix(world, group).replace("&", "§");
		groupSuffix = VaultUtil.chat.getGroupSuffix(world, group).replace("&", "§");
		String displayName = sender.getDisplayName();
		displayName = BubbleCode.bubblecode(Permission.canBubbleCode(p), Codify.changeFormatSign(displayName)).replaceAll("&([0-9A-FK-OR])", "§$1").replace("&", "§");
		String format = "";
		if(channel != null) {
			if(Permission.isDistinguished(p)) {
				format = channel.getDistinguishedChatFormat();
			}else {
				format = channel.getChatFormat();
			}
		}else {
			if(recipient.equals("")) {
				format = FPChat.getPlugin().getMainConfig().getShoutFormat();
			}else {
				if(in) {
					format = FPChat.getPlugin().getMainConfig().getInMsgFormat();
				}else {
					format = FPChat.getPlugin().getMainConfig().getOutMsgFormat();
				}
			}
		}
		format = format.replaceAll("&([0-9a-fk-or])", "§$1");
		format = format.replace("{prefix}", prefix);
		format = format.replace("{prefix}", prefix);
		format = format.replace("{suffix}", suffix);
		format = format.replace("{group}", group);
		format = format.replace("{groupprefix}", groupPrefix);
		format = format.replace("{groupSuffix}", groupSuffix);
		if (channel != null) {
			format = format.replace("{shortcut}", channel.getShortcut());
			format = format.replace("{name}", channel.getName());
		}
		format = format.replace("{player}", displayName);
		format = format.replace("{recipient}", recipient);
		format = format.replace("{world}", world.getName());

		return format;
	}
}
