package com.fpghoti.fpchatx.chat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.fpghoti.fpchatx.FPChat;
import com.fpghoti.fpchatx.event.ShoutChannelChatEvent;
import com.fpghoti.fpchatx.player.FPlayer;

public class ShoutChannel extends ChatChannel{

	/*
	 * This is the shout channel
	 * 
	 * Shout is intended to act as a streamlined alternative
	 * to setting up a StandardChannel with the radius disabled.
	 * 
	 * It also provides the ability to restrict global message sending
	 * to a cooldown.
	 * 
	 * It also allows for a separate mute (hush) to be enforced.
	 * 
	 * Players can opt out of seeing shout.
	 * 
	 * Many functions here will do nothing. Shout extends ChatChannel
	 * for the sake of treating shouts as a form of chat message.
	 */

	public ShoutChannel(FPChat plugin) {
		super(plugin);
		this.name = "Shout";
		this.shortcut = "Shout";
		this.chatFormat = plugin.getMainConfig().getShoutFormat();
		this.distinguishedChatFormat = plugin.getMainConfig().getDistinguishedShout();
	}

	@Override
	public boolean isTemp() {
		// Shout will always be a registered channel while the plugin is enabled.
		return false;
	}

	@Override
	public boolean isDefault() {
		// Shout can only be spoken in by use of command, so cannot be default.
		return false;
	}

	@Override
	public void kick(FPlayer p) {
		// No real reason to use this. It will hush the player if called.
		// Leaving the shout channel is impossible. Standard channel should
		// be used if the ability to leave is required.

		p.hush();
	}

	@Override
	public void setShortcut(String shortcut) {
		// Shout has a dedicated command, so a shortcut is not needed.
	}

	@Override
	public void setChatFormat(String chatFormat) {
		this.chatFormat = plugin.getMainConfig().getShoutFormat();
	}

	@Override
	public void setDistinguishedChatFormat(String chatFormat) {
		this.distinguishedChatFormat = plugin.getMainConfig().getDistinguishedShout();
	}

	@Override
	public void enableWhitelist() {
		return;
	}

	@Override
	public void disableWhitelist() {
		return;
	}

	@Override
	public void whitelistAdd(UUID uuid) {
		return;
	}

	@Override
	public void whitelistRemove(UUID uuid) {
		return;
	}

	@Override
	public boolean isWhitelisted(FPlayer p) {
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
	public ArrayList<FPlayer> getPlayers(){
		return FPlayer.getPlayers();
	}

	@Override
	public void addBanned(FPlayer p) {
		// No real reason to use this. It will hush the player if called.
		// Leaving the shout channel is impossible. Standard channel should
		// be used if the ability to leave is required.

		p.hush();
	}

	@Override
	public void removeBanned(FPlayer p) {
		// No real reason to use this. It will hush the player if called.
		// Leaving the shout channel is impossible. Standard channel should
		// be used if the ability to leave is required.

		p.unhush();
	}

	@Override
	public void sendMessage(String msg, FPlayer from) {
		CompletableFuture.runAsync(() -> {
			plugin.log(Level.INFO, "Shout: " + msg);
			Set<Player> recipients = new HashSet<Player>();
			for(FPlayer p : FPlayer.getPlayers()) {
				if(p.isShoutVisible() && !p.isIgnoring(from)) {
					recipients.add(p.getPlayer());
				}
			}
			ShoutChannelChatEvent event = new ShoutChannelChatEvent(true, from.getPlayer(), msg, recipients, this);
			Bukkit.getPluginManager().callEvent(event);
			if(!event.isCancelled()) {
				for(Player recipient : recipients) {
					recipient.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&4&lS&r&8]&r") + msg);
				}
			}
		});
	}

}
