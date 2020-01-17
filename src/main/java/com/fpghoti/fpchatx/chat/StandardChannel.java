package com.fpghoti.fpchatx.chat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Level;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.fpghoti.fpchatx.FPChat;
import com.fpghoti.fpchatx.config.ChannelFile;
import com.fpghoti.fpchatx.player.FPlayer;
import com.fpghoti.fpchatx.util.Util;

public class StandardChannel extends ChatChannel{

	private boolean hasRadius;
	private int chatRadius;
	private String permission;
	private ChannelFile file;

	public StandardChannel(FPChat plugin, ChannelFile file) {
		super(plugin);
		this.file = file;
		setName(file.getName());
		this.hasRadius = file.hasRadius();
		this.permission = file.getWhitelistedPermissionNode();
		this.banned = new ArrayList<UUID>();
		if(!file.getBannedUUIDs().equals("")) {
			for(String s : file.getBannedUUIDs().split(",")) {
				UUID u = UUID.fromString(s);
				banned.add(u);
			}
		}
		this.whitelist = new ArrayList<UUID>();
		if(!file.getWhitelistedUUIDs().equals("")) {
			for(String s : file.getWhitelistedUUIDs().split(",")) {
				UUID u = UUID.fromString(s);
				whitelist.add(u);
			}
		}
		this.shortcut = file.getShortcut();
		this.distinguishedChatFormat = file.getDistinguishedChatFormat();
		this.chatFormat = file.getChatFormat();
		this.chatRadius = file.getRadius();
		this.isWhitelisted = file.isWhitelisted();
	}

	@Override
	public boolean isTemp() {
		return false;
	}

	@Override
	public void enableRadius() {
		this.hasRadius = true;
		new BukkitRunnable() {
			@Override
			public void run() {
				try {
					file.saveHasRadius(true);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.runTaskAsynchronously(plugin);
	}

	@Override
	public void disableRadius() {
		this.hasRadius = false;
		new BukkitRunnable() {
			@Override
			public void run() {
				try {
					file.saveHasRadius(false);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.runTaskAsynchronously(plugin);
	}

	@Override
	public boolean hasRadius() {
		return this.hasRadius;
	}

	@Override
	public void setRadius(int radius) {
		this.chatRadius = radius;
		new BukkitRunnable() {
			@Override
			public void run() {
				try {
					file.saveRadius(radius);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.runTaskAsynchronously(plugin);
	}

	@Override
	public int getRadius() {
		return this.chatRadius;
	}

	@Override
	public boolean isWhitelisted(FPlayer p)  {
		if(!whitelistEnabled()) {
			return true;
		}
		if(isDefault()){
			return true;
		}
		if(whitelist.contains(p.getUniqueId())) {
			return true;
		}
		if(p.hasPermission(permission)) {
			return true;
		}
		return false;		
	}

	public void setPermission(String perm) {
		this.permission = perm;
	}

	public String getPermission() {
		return this.permission;
	}

	@Override
	public boolean isDefault()  {
		return ChatChannel.getDefault() == this;
	}

	@Override
	public void kick(FPlayer p) {
		p.leaveChannel(getName());
	}

	@Override
	public void setShortcut(String shortcut) {
		this.shortcut = shortcut;
		new BukkitRunnable() {
			@Override
			public void run() {
				try {
					file.saveShortcut(shortcut);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.runTaskAsynchronously(plugin);
	}

	@Override
	public void enableWhitelist() {
		this.isWhitelisted = true;
		new BukkitRunnable() {
			@Override
			public void run() {
				try {
					file.saveWhitelist(true);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.runTaskAsynchronously(plugin);
	}

	@Override
	public void disableWhitelist() {
		this.isWhitelisted = false;
		new BukkitRunnable() {
			@Override
			public void run() {
				try {
					file.saveWhitelist(false);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.runTaskAsynchronously(plugin);
	}

	@Override
	public void whitelistAdd(UUID uuid) {
		this.whitelist.add(uuid);

		ArrayList<UUID> rl = this.whitelist;
		new BukkitRunnable() {
			@Override
			public void run() {
				try {
					String setting = "";
					for(UUID u : rl) {
						String su = u.toString();
						setting = setting + su + ",";
					}
					setting = StringUtils.chop(setting);
					file.saveWhitelistedUUIDs(setting);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.runTaskAsynchronously(plugin);


	}

	@Override
	public void whitelistRemove(UUID uuid) {
		this.whitelist.remove(uuid);
		ArrayList<UUID> rl = this.whitelist;
		new BukkitRunnable() {
			@Override
			public void run() {
				try {
					String setting = "";
					for(UUID u : rl) {
						String su = u.toString();
						setting = setting + su + ",";
					}
					setting = StringUtils.chop(setting);
					file.saveWhitelistedUUIDs(setting);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.runTaskAsynchronously(plugin);
	}

	@Override
	public void setChatFormat(String chatFormat) {
		this.chatFormat = chatFormat;
		new BukkitRunnable() {
			@Override
			public void run() {
				try {
					file.saveChatFormat(chatFormat);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.runTaskAsynchronously(plugin);
	}

	@Override
	public ArrayList<FPlayer> getPlayers(){
		ArrayList<FPlayer> players = new ArrayList<FPlayer>();
		for(FPlayer p : FPlayer.getPlayers()) {
			if(p.getChannels().contains(name)) {
				players.add(p);
			}
		}
		return players;
	}

	@Override
	public void addBanned(FPlayer p) {
		this.banned.add(p.getUniqueId());
		ArrayList<UUID> rl = this.banned;
		new BukkitRunnable() {
			@Override
			public void run() {
				try {
					String setting = "";
					for(UUID u : rl) {
						String su = u.toString();
						setting = setting + su + ",";
					}
					setting = StringUtils.chop(setting);
					file.saveBannedUUIDs(setting);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.runTaskAsynchronously(plugin);
	}

	@Override
	public void removeBanned(FPlayer p) {
		this.banned.remove(p.getUniqueId());
		ArrayList<UUID> rl = this.banned;
		new BukkitRunnable() {
			@Override
			public void run() {
				try {
					String setting = "";
					for(UUID u : rl) {
						String su = u.toString();
						setting = setting + su + ",";
					}
					setting = StringUtils.chop(setting);
					file.saveBannedUUIDs(setting);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.runTaskAsynchronously(plugin);
	}

	@Override
	public void setDistinguishedChatFormat(String chatFormat) {
		this.distinguishedChatFormat = chatFormat;
		new BukkitRunnable() {
			@Override
			public void run() {
				try {
					file.saveDistinguishedChatFormat(chatFormat);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.runTaskAsynchronously(plugin);
	}

	@Override
	public void sendMessage(String msg, FPlayer from) {
		Player pf = Util.getEP(from.getName());
		plugin.log(Level.INFO, name + ": " + msg);
		for(FPlayer p : FPlayer.getPlayers()) {
			if(p.getChannels().contains(name) && !p.isIgnoring(from)) {
				if(hasRadius) {
					Player pp = Util.getEP(p.getName());
					if(pp.getWorld() == pf.getWorld()) {
						if(pp.getLocation().distance(pf.getLocation()) < chatRadius){
							p.sendMessage(msg);
						}
					}
				}else {
					p.sendMessage(msg);
				}
			}
		}
	}

	public void update(ChannelFile file) {
		this.file = file;
		setName(file.getName());
		this.hasRadius = file.hasRadius();
		this.permission = file.getWhitelistedPermissionNode();
		this.banned = new ArrayList<UUID>();
		for(String s : file.getBannedUUIDs().split(",")) {
			UUID u = UUID.fromString(s);
			banned.add(u);
		}
		this.shortcut = file.getShortcut();
	}

}


