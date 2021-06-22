package com.fpghoti.fpchatx.chat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.fpghoti.fpchatx.FPChat;
import com.fpghoti.fpchatx.event.TempChannelChatEvent;
import com.fpghoti.fpchatx.player.FPlayer;

public class TempChannel extends ChatChannel{

	private UUID owner;

	public TempChannel(FPChat plugin, FPlayer owner, String name) {
		super(plugin);
		this.name = name;
		this.owner = owner.getUniqueId();
		this.chatFormat = plugin.getMainConfig().getTempChannelFormat();
		this.distinguishedChatFormat = plugin.getMainConfig().getTempChannelFormat();
		this.banned = new ArrayList<UUID>();
	}

	public FPlayer getOwner() {
		OfflinePlayer p = Bukkit.getOfflinePlayer(owner);
		return FPlayer.getPlayer(p, !p.isOnline());
	}

	public boolean isOwner(FPlayer p) {
		return p.getUniqueId() == this.owner;
	}

	public void setOwner(FPlayer p) {
		this.owner = p.getUniqueId();
	}

	@Override
	public boolean isTemp() {
		return true;
	}

	@Override
	public boolean isWhitelisted(FPlayer p) {
		if(!whitelistEnabled()) {
			return true;
		}
		if(whitelist.contains(p.getUniqueId())) {
			return true;
		}
		return false;		
	}

	@Override
	public void enableRadius() {
		return;
	}

	@Override
	public void disableRadius() {
		return;
	}

	@Override
	public boolean hasRadius() {
		return false;
	}

	@Override
	public void setRadius(int radius) {
		return;
	}

	@Override
	public int getRadius() {
		return 0;
	}

	@Override
	public boolean isDefault() {
		return false;
	}

	@Override
	public void kick(FPlayer p) {
		p.leaveTempChannel(getName());
	}

	@Override
	public void setShortcut(String shortcut) {
		this.shortcut = shortcut;
	}

	@Override
	public void enableWhitelist() {
		this.isWhitelisted = true;
	}

	@Override
	public void disableWhitelist() {
		this.isWhitelisted = false;
	}

	@Override
	public void whitelistAdd(UUID uuid){
		this.whitelist.add(uuid);
	}

	@Override
	public void whitelistRemove(UUID uuid) {
		this.whitelist.remove(uuid);
	}

	@Override
	public void addBanned(FPlayer p) {
		this.banned.add(p.getUniqueId());
	}


	@Override
	public void removeBanned(FPlayer p) {
		this.banned.remove(p.getUniqueId());
	}

	@Override
	public void setChatFormat(String chatFormat) {
		this.chatFormat = chatFormat;
	}

	@Override
	public void setDistinguishedChatFormat(String chatFormat) {
		this.distinguishedChatFormat = chatFormat;
	}

	@Override
	public ArrayList<FPlayer> getPlayers(){
		ArrayList<FPlayer> players = new ArrayList<FPlayer>();
		for(FPlayer p : FPlayer.getPlayers()) {
			if(p.getTempChannels().contains(name)) {
				players.add(p);
			}
		}
		return players;
	}

	@Override
	public void sendMessage(String msg, FPlayer from) {
		CompletableFuture.runAsync(() -> {
			plugin.log(Level.INFO, "[TC] " + name + ": " + msg);
			Set<Player> recipients = new HashSet<Player>();
			for(FPlayer p : FPlayer.getPlayers()) {
				if(p.getTempChannels().contains(name) && !p.isIgnoring(from)) {
					recipients.add(p.getPlayer());
				}
			}
			TempChannelChatEvent event = new TempChannelChatEvent(true, from.getPlayer(), msg, recipients, this);
			Bukkit.getPluginManager().callEvent(event);
			if(!event.isCancelled()) {
				for(Player recipient : recipients) {
					recipient.sendMessage(msg);
				}
			}
		});
	}

}
