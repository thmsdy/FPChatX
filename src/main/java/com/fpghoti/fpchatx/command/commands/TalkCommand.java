package com.fpghoti.fpchatx.command.commands;

import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.fpghoti.fpchatx.FPChat;
import com.fpghoti.fpchatx.chat.ChatChannel;
import com.fpghoti.fpchatx.chat.StandardChannel;
import com.fpghoti.fpchatx.chat.TempChannel;
import com.fpghoti.fpchatx.command.Commands;
import com.fpghoti.fpchatx.player.FPlayer;
import com.fpghoti.fpchatx.util.Util;

public class TalkCommand extends Commands {

	public TalkCommand(FPChat plugin) {
		super(plugin);
		name = "Talk";
		description = "Talk in a specific channel";
		syntax = ChatColor.GRAY + "/talk " + ChatColor.GOLD + "channel [msg]";
		minArgs = 1;
		maxArgs = 100000;
		labels.add("fpc talk");
		labels.add("fpchat talk");
		labels.add("ch talk");
		labels.add("fpc tk");
		labels.add("fpchat tk");
		labels.add("ch tk");
		labels.add("talk");
		labels.add("tk");
		misc.add("Helpful alias: /tk");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (sender instanceof Player) {
			FPlayer p = FPlayer.getPlayer((Player)sender);
			ChatChannel channel = ChatChannel.getChannel(args[0]);
			if(channel == null) {
				channel = ChatChannel.getTempChannel(args[0]);
			}
			if(channel == null) {
				FPlayer.errMsg(p, "The channel you are trying to talk in does not exist.");
				return;
			}
			if(!p.getChannels().contains(channel.getName()) && !p.getTempChannels().contains(channel.getName())) {
				FPlayer.errMsg(p, "You are not currently in that channel.");
				return;
			}
			if(args.length == 1) {
				if(channel instanceof StandardChannel) {
					p.setPrimaryChannel(channel.getName());
					p.stopTempSpeak();
				}else if(channel instanceof TempChannel) {
					p.setPrimaryTempChannel(channel.getName());
					p.speakInTemp();
				}
				FPlayer.dualMsg(p,ChatColor.YELLOW + "Now speaking in " + ChatColor.GREEN + channel.getName() + ChatColor.YELLOW + ".");
			}else {
				String msg = "";
	            String last = args[args.length - 1];
	            for(int i = 1; i < args.length - 1; i++) {
	            	msg = msg + args[i] + " ";
	            }
	            msg = msg + last;
	            p.setTalk(true);
	            p.setTalkChannel(channel);
	            Player pl = Util.getEP(p.getName());
	            AsyncPlayerChatEvent event = new AsyncPlayerChatEvent(false, pl, msg, new HashSet<Player>(Bukkit.getOnlinePlayers()));
				Bukkit.getPluginManager().callEvent(event);
			}
		}else{
			FPlayer.errMsg(null, "This command is for players only.");
		}
	}

}
