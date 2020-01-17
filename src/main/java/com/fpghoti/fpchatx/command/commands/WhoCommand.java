package com.fpghoti.fpchatx.command.commands;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.fpghoti.fpchatx.FPChat;
import com.fpghoti.fpchatx.chat.ChatChannel;
import com.fpghoti.fpchatx.command.Commands;
import com.fpghoti.fpchatx.permission.Permission;
import com.fpghoti.fpchatx.player.FPlayer;
import com.fpghoti.fpchatx.util.Util;

public class WhoCommand extends Commands {

    public WhoCommand(FPChat plugin) {
        super(plugin);
        name = "Who";
        description = "Lists all players in your primary channel";
        syntax = ChatColor.GRAY + "/fpc who " + ChatColor.GOLD + "[page#]";
        minArgs = 0;
        maxArgs = 1;
        labels.add("fpc who");
        labels.add("fpchat who");
        labels.add("ch who");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            FPlayer p = FPlayer.getPlayer((Player)sender);
            ChatChannel c = p.getPrimaryChannel();
            if(p.speakingInTemp()) {
            	c = p.getPrimaryTempChannel();
            }
            if (c != null) {
            	
            	int pg = 1;
        		if (args.length > 0) {
        			if(Util.isDigit(args[0])) {
        				pg = Integer.parseInt(args[0]);
        			}else {
        				FPlayer.errMsg(p, syntax);
        			}
        		}
            	
                ArrayList<FPlayer> players = c.getPlayers();
                
                int pageCount = (int) Math.ceil((double) players.size() / 8);
        		if (pg > pageCount) {
        			pg = pageCount;
        		}
        		
        		FPlayer.plainMsg(p, "");
        		FPlayer.plainMsg(p, ChatColor.GOLD + "[" + Integer.toString(pg) + "/" + Integer.toString(pageCount) + "] "  + ChatColor.YELLOW + "Players in " + ChatColor.WHITE + c.getName() + ChatColor.YELLOW +":");
        		for (int i = 0; i < 8; i++) {
        			int index = (pg - 1) * 8 + i;
        			if (index < players.size()) {
        				FPlayer member = players.get(index);
        				String msg = ChatColor.GREEN + "- ";
        				if(Permission.isAdmin(member)) {
        					msg = msg + ChatColor.AQUA + "[ADMIN] ";
        				}else if(Permission.isStaff(member)) {
        					msg = msg + ChatColor.BLUE + "[STAFF] ";
        				}
        				msg = msg + ChatColor.YELLOW + member.getName();
        				FPlayer.plainMsg(p, msg);
        			}
        		}
                
                
            }
        }else{
            FPlayer.errMsg(null, "This command is for players only.");
        }
    }

}
