package com.fpghoti.fpchatx.listener;

import java.io.IOException;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.fpghoti.fpchatx.FPChat;
import com.fpghoti.fpchatx.chat.ChatChannel;
import com.fpghoti.fpchatx.player.FPlayer;

public class PlayerListener implements Listener {

	private boolean enabled = true;

	@EventHandler (priority = EventPriority.HIGH)
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		if(enabled) {
			if (event.isCancelled()) {
				return;
			}
			Player sender = event.getPlayer();
			FPlayer p = FPlayer.getPlayer(sender);
			if(p.toTalk() && p.getTalkChannel() != null) {
				p.setTalk(false);
				ChatChannel c = p.getTalkChannel();
				p.setTalkChannel(null);
				p.chat(c, event.getMessage());
			}else {
				p.chat(event.getMessage());
			}
			event.setCancelled(true);
		}
	}

	@EventHandler (priority = EventPriority.NORMAL)
	public void onPlayerJoin(PlayerJoinEvent event) {
		if(enabled) {
			try {
				FPChat.getPlugin().getPlayerCache().saveUUID(event.getPlayer().getName(), event.getPlayer().getUniqueId());
			} catch (IOException e) {
				e.printStackTrace();
			}
			FPlayer.getPlayer(event.getPlayer());
		}
	}

	@EventHandler (priority = EventPriority.NORMAL)
	public void onPlayerQuit(PlayerQuitEvent event) {
		if(enabled) {
			FPlayer p = FPlayer.getPlayer(event.getPlayer());
			p.cleanup();
		}
	}

	@EventHandler (priority = EventPriority.NORMAL)
	public void onPlayerKick(PlayerKickEvent event) {
		if(enabled) {
			FPlayer p = FPlayer.getPlayer(event.getPlayer());
			p.cleanup();
		}
	}

	public void enable() {
		this.enabled = true;
	}

	public void disable() {
		this.enabled = false;
	}

}
