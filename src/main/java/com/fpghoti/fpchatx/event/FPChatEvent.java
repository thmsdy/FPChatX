package com.fpghoti.fpchatx.event;

import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.fpghoti.fpchatx.chat.ChatChannel;

public abstract class FPChatEvent extends AsyncPlayerChatEvent {
	
	private ChatChannel channel;

	public FPChatEvent(boolean async, Player who, String message, Set<Player> players, ChatChannel channel) {
		super(async, who, message, players);
		this.channel = channel;
	}

	
	public ChatChannel getChannel() {
		return channel;
	};
}
