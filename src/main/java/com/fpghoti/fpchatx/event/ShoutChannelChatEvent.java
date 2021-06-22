package com.fpghoti.fpchatx.event;

import java.util.Set;

import org.bukkit.entity.Player;

import com.fpghoti.fpchatx.chat.ShoutChannel;

public class ShoutChannelChatEvent extends FPChatEvent {

	private ShoutChannel channel;
	
	public ShoutChannelChatEvent(boolean async, Player who, String message, Set<Player> players, ShoutChannel channel) {
		super(async, who, message, players, channel);
		this.channel = channel;
	}
	
	public ShoutChannel getChannel() {
		return channel;
	}

}
