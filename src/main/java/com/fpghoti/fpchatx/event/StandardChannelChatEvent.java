package com.fpghoti.fpchatx.event;

import java.util.Set;

import org.bukkit.entity.Player;

import com.fpghoti.fpchatx.chat.StandardChannel;

public class StandardChannelChatEvent extends FPChatEvent {
	
	private StandardChannel channel;

	public StandardChannelChatEvent(boolean async, Player who, String message, Set<Player> players, StandardChannel channel) {
		super(async, who, message, players, channel);
		this.channel = channel;
	}
	
	public StandardChannel getChannel() {
		return this.channel;
	}

}
