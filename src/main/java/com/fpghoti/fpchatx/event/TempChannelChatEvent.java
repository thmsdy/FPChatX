package com.fpghoti.fpchatx.event;

import java.util.Set;

import org.bukkit.entity.Player;

import com.fpghoti.fpchatx.chat.TempChannel;

public class TempChannelChatEvent extends FPChatEvent {
	
	private TempChannel channel;

	public TempChannelChatEvent(boolean async, Player who, String message, Set<Player> players, TempChannel channel) {
		super(async, who, message, players, channel);
		this.channel = channel;
	}
	
	public TempChannel getChannel() {
		return channel;
	}

}
