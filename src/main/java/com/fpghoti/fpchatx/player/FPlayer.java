package com.fpghoti.fpchatx.player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.fpghoti.fpchatx.FPChat;
import com.fpghoti.fpchatx.badge.BadgeData;
import com.fpghoti.fpchatx.chat.BubbleCode;
import com.fpghoti.fpchatx.chat.ChatChannel;
import com.fpghoti.fpchatx.chat.ChatFilter;
import com.fpghoti.fpchatx.chat.Codify;
import com.fpghoti.fpchatx.chat.PrepareChat;
import com.fpghoti.fpchatx.chat.StandardChannel;
import com.fpghoti.fpchatx.chat.TempChannel;
import com.fpghoti.fpchatx.config.PlayerFile;
import com.fpghoti.fpchatx.permission.Permission;
import com.fpghoti.fpchatx.util.Util;
import com.fpghoti.fpchatx.util.VaultUtil;

public class FPlayer {

	private static HashMap<UUID,FPlayer> players = new HashMap<UUID,FPlayer>();

	public static void purge() {
		players = new HashMap<UUID,FPlayer>();
	}

	public static ArrayList<FPlayer> getPlayers(){
		ArrayList<FPlayer> temp = new ArrayList<FPlayer>(players.values());
		return temp;
	}

	public static FPlayer getPlayer(OfflinePlayer p) {
		return getPlayer(p,false);
	}

	@SuppressWarnings("deprecation") // Needed in order to run command using name when player offline. Most people can't remember UUIDs!!
	public static FPlayer getOfflinePlayer(String name) {
		return getPlayer(Bukkit.getOfflinePlayer(name));
	}

	public static void dualMsg(FPlayer p, String msg) { // Messages FPlayer. If FPlayer NULL msges CONSOLE instead
		if(p == null) {
			FPChat.getPlugin().log(Level.INFO, msg);
		}else {
			p.sendMessage(FPChat.logo() + ChatColor.YELLOW + " " + msg);
		}
	}

	public static void goodMsg(FPlayer p, String msg) { // Messages FPlayer. If FPlayer NULL msges CONSOLE instead
		if(p == null) {
			FPChat.getPlugin().log(Level.INFO, msg);
		}else {
			p.sendMessage(FPChat.logo() + ChatColor.YELLOW + " " + msg);
		}
	}

	public static void errMsg(FPlayer p, String msg) { // Messages FPlayer. If FPlayer NULL msges CONSOLE instead
		if(p == null) {
			FPChat.getPlugin().log(Level.INFO, "" + ChatColor.RED + msg);
		}else {
			p.sendMessage(FPChat.logo() + ChatColor.RED + " " + msg);
		}
	}

	public static void plainMsg(FPlayer p, String msg) { // Messages FPlayer. If FPlayer NULL msges CONSOLE instead
		if(p == null) {
			FPChat.getPlugin().log(Level.INFO, msg);
		}else {
			p.sendMessage(msg);
		}
	}


	public static FPlayer getPlayer(Player p) {
		return getPlayer(Bukkit.getOfflinePlayer(p.getUniqueId()));
	}

	public static FPlayer getPlayer(String playername) {
		return getPlayer(Util.getEP(playername));
	}

	public static FPlayer getPlayer(OfflinePlayer p, Boolean temp) {
		UUID uuid = p.getUniqueId();
		if(players.containsKey(uuid)) {
			return players.get(uuid);
		}else {
			FPlayer fplayer = new FPlayer(p);
			if(!temp) {
				players.put(uuid, fplayer);
			}
			return fplayer;
		}
	}

	private String name;
	private UUID uuid;
	private PlayerFile pfile;
	private OfflinePlayer offlinePlayer;
	private ArrayList<UUID> ignored;
	private ArrayList<String> channels;
	private ArrayList<String> tempchannels;
	private boolean tempspeak;
	private String primaryChannel;
	private String primaryTempChannel;
	private int shoutCooldown;
	private UUID lastMsg;
	private boolean spy;
	private boolean shoutVisible;
	private boolean hushed;
	private boolean toShout;
	private boolean toTalk;
	private ChatChannel talkChannel;
	private BadgeData badgeData;

	private FPlayer(OfflinePlayer p) {
		this.toShout = false;
		this.toTalk = false;
		this.talkChannel = null;
		this.offlinePlayer = p;
		this.name = p.getName();
		this.uuid = p.getUniqueId();
		this.pfile = new PlayerFile(FPChat.getPlugin(),p);
		this.ignored = new ArrayList<UUID>();
		this.shoutCooldown = 0;
		this.lastMsg = null;
		this.spy = pfile.isSpy();
		this.shoutVisible = pfile.shoutVisible();
		this.hushed = pfile.isHushed();
		String rawignore = pfile.getIgnore();
		if(!rawignore.equals("")) {
			for(String s : rawignore.split(",")) {
				ignored.add(UUID.fromString(s));
			}
		}
		String rawchannel = pfile.getCurrentChannel();
		if(rawchannel.equals("default")) {
			this.primaryChannel = ChatChannel.getDefault().getName();
		}else if(ChatChannel.getChannel(rawchannel) != null) {
			this.primaryChannel = rawchannel;
		}else {
			this.primaryChannel = ChatChannel.getDefault().getName();
		}
		this.channels = new ArrayList<String>();
		this.tempchannels = new ArrayList<String>();
		channels.add(ChatChannel.getDefault().getName());
		channels.add(primaryChannel);
		String rawchannels = pfile.getChannels();
		if(!rawchannels.equals("")) {
			for(String s : rawchannels.split(",")) {
				ChatChannel chan = ChatChannel.getChannel(s);
				if(chan != null && !chan.getName().equals(ChatChannel.getDefault().getName()) && !chan.getName().equals(primaryChannel)) {
					this.channels.add(chan.getName());
				}
			}
		}
		if(FPChat.getPlugin().getMainConfig().mySQLEnabled()) {
			this.badgeData = new BadgeData(this);
		}
	}

	public boolean equals(FPlayer p) {
		return this.uuid == p.getUniqueId();
	}

	public String getName() {
		return this.name;
	}
	
	public int getShoutCooldown() {
		return this.shoutCooldown;
	}

	public void setShoutCooldown(int time) {
		this.shoutCooldown = time;
	}

	public UUID getUniqueId() {
		return this.uuid;
	}

	public PlayerFile getFile() {
		return this.pfile;
	}

	public boolean isOnline() {
		return offlinePlayer.isOnline();
	}

	public boolean isSpy() {
		return this.spy && Permission.canSpy(this);
	}

	public boolean enableSpy() {
		if(Permission.canSpy(this)) {
			this.spy = true;
			return true;
		}
		return false;
	}

	public void disableSpy() {
		this.spy = false;
	}

	public boolean isShoutVisible() {
		return this.shoutVisible;
	}

	public void showShout() {
		this.shoutVisible = true;
	}

	public void hideShout() {
		this.shoutVisible = false;
	}
	
	public BadgeData getBadgeData() {
		return this.badgeData;
	}

	public boolean setPrefix(String prefix) {
		if(isOnline() && getPlayer() != null) {
			World world = null;
			VaultUtil.chat.setPlayerPrefix(world, name, prefix);
			return true;
		}
		return false;
	}

	public boolean setSuffix(String suffix) {
		if(isOnline() && getPlayer() != null) {
			VaultUtil.chat.setPlayerSuffix(getPlayer(), suffix);
			return true;
		}
		return false;
	}

	public boolean isHushed() {
		return this.hushed;
	}

	public void hush() {
		this.hushed = true;
	}

	public void unhush() {
		this.hushed = false;
	}

	public FPlayer getLastMessage() {
		if(this.lastMsg == null) {
			return null;
		}
		if(Bukkit.getOfflinePlayer(this.lastMsg).isOnline()) {
			return FPlayer.getPlayer(Bukkit.getPlayer(this.lastMsg));
		}
		return null;
	}

	public boolean hasPermission(String permission) {
		if(isOnline()) {
			Player p = offlinePlayer.getPlayer();
			return VaultUtil.permission.playerHas(p, permission);
		}
		return false;
	}

	public void setPrimaryChannel(String channel) {
		this.primaryChannel = channel;
	}

	public ChatChannel getPrimaryChannel() {
		ChatChannel c = ChatChannel.getChannel(this.primaryChannel);
		if(c != null) {
			return c;
		}
		c = ChatChannel.getDefault();
		this.primaryChannel = c.getName();
		return c;
	}

	public void setPrimaryTempChannel(String channel) {
		this.primaryTempChannel = channel;
	}

	public ChatChannel getPrimaryTempChannel() {
		ChatChannel c = ChatChannel.getTempChannel(this.primaryTempChannel);
		if(c != null) {
			return c;
		}
		this.tempspeak = false;
		return getPrimaryChannel();
	}

	public boolean speakingInTemp() {
		return this.tempspeak;
	}

	public void speakInTemp() {
		this.tempspeak = true;
	}

	public void stopTempSpeak() {
		this.tempspeak = false;
	}

	public ArrayList<String> getChannels(){
		return this.channels;
	}

	public ArrayList<String> getTempChannels(){
		return this.tempchannels;
	}

	public void sendMessage(String message) {
		if(isOnline()) {
			Player p = Bukkit.getPlayer(uuid);
			p.sendMessage(message);
		}
	}

	public Player getPlayer() {
		if(isOnline()) {
			return Util.getEP(name);
		}
		return null;
	}

	public boolean joinChannel(String channel) {
		ChatChannel cc = ChatChannel.getChannel(channel);
		if(cc == null) {
			cc = ChatChannel.getTempChannel(channel);
		}
		if(cc == null) {
			joinStandardChannel(ChatChannel.getDefault().getName());
			FPlayer.errMsg(this, "You tried to join a channel that no longer exists. You have been placed in the default channel instead.");
			return false;
		}
		if(cc.isBanned(this)) {
			FPlayer.errMsg(this, "Unable to join. You are blacklisted from this channel.");
			return false;
		}
		if(cc instanceof StandardChannel) {
			return joinStandardChannel(cc.getName());
		}else if(cc instanceof TempChannel) {
			return joinTempChannel(cc.getName());
		}
		FPlayer.errMsg(this, "An error has occurred. Unable to join channel.");
		return false;
	}

	public boolean joinStandardChannel(String channel) {
		ChatChannel cc = ChatChannel.getChannel(channel);
		if(cc == null || !(cc instanceof StandardChannel)) {
			joinStandardChannel(ChatChannel.getDefault().getName());
			FPlayer.errMsg(this, "You tried to join a channel that no longer exists. You have been placed in the default channel instead.");
			return false;
		}
		StandardChannel c = (StandardChannel)cc;
		if(!c.whitelistEnabled() || c.isWhitelisted(this)) {
			channels.add(channel);
			FPlayer.goodMsg(this, "You have joined channel " + ChatColor.YELLOW + cc.getName() + ChatColor.GREEN + ".");
			return true;
		}else {
			FPlayer.errMsg(this, "Unable to join. Channel is whitelisted.");
		}
		return false;
	}

	public void leaveChannel(String channel) {
		if(ChatChannel.getDefault().getName().equalsIgnoreCase(channel)) {
			FPlayer.errMsg(this, "You cannot leave the default channel.");
			return;
		}
		if(getPrimaryChannel().getName().equalsIgnoreCase(channel)) {
			setPrimaryChannel(ChatChannel.getDefault().getName());
		}
		channels.remove(channel);
	}

	public boolean joinTempChannel(String channel) {
		ChatChannel cc = ChatChannel.getTempChannel(channel);
		if(cc == null || !(cc instanceof TempChannel)) {
			joinStandardChannel(ChatChannel.getDefault().getName());
			FPlayer.errMsg(this, "You tried to join a channel that no longer exists. You have been placed in the default channel instead.");
			return false;
		}
		TempChannel c = (TempChannel)cc;
		if(!c.whitelistEnabled() || c.isWhitelisted(this)) {
			tempchannels.add(channel);
			FPlayer.goodMsg(this, "You have joined temporary channel " + ChatColor.YELLOW + cc.getName() + ChatColor.GREEN + ".");
			return true;
		}else {
			FPlayer.errMsg(this, "Unable to join. Channel is whitelisted.");
		}
		return false;
	}

	public boolean canJoin(ChatChannel channel) {
		if(channel.isBanned(this)) {
			return false;
		}
		if(channel.whitelistEnabled() && !channel.isWhitelisted(this)) {
			return false;
		}
		return true;
	}

	public void leaveTempChannel(String channel) {
		if(getPrimaryTempChannel().getName().equalsIgnoreCase(channel)) {
			stopTempSpeak();
			this.primaryTempChannel = null;
		}
		tempchannels.remove(channel);
	}

	public String formatChat(String msg) {
		if(tempspeak) {
			return formatChat(getPrimaryTempChannel(),msg);
		}
		return formatChat(getPrimaryChannel(), msg);
	}

	public String formatChat(ChatChannel channel, String msg) {
		return channel.format(this, msg);
	}

	public void chat(ChatChannel channel, String msg) {
		channel.sendMessage(formatChat(channel,msg), this);
	}

	public void chat(String msg) {
		if(toShout) {
			toShout = false;
			ChatChannel.getShout().sendMessage(formatChat(ChatChannel.getShout(),msg), this);
			if(FPChat.getPlugin().getMainConfig().shoutCooldownEnabled()) {
				shoutCooldown = FPChat.getPlugin().getMainConfig().getShoutSeconds();
			}
		}else if(tempspeak) {
			getPrimaryTempChannel().sendMessage(formatChat(getPrimaryTempChannel(),msg), this);
		}else {
			getPrimaryChannel().sendMessage(formatChat(msg), this);
		}
	}

	public boolean shout(String message) {
		if(!hushed && FPChat.getPlugin().getMainConfig().shoutCooldownEnabled() && shoutCooldown > 0) {
			int i = shoutCooldown;
			String time = " seconds ";
			if(i == 1) {
				time = " second ";
			}
			FPlayer.errMsg(this, "You must wait " + Integer.toString(i) + time + "before you can shout again.");
		}

		if(hushed) {
			FPlayer.errMsg(this, "You are unable to perform this action, because you have been hushed.");
		}

		if(isOnline() && (shoutCooldown == 0 || !FPChat.getPlugin().getMainConfig().shoutCooldownEnabled() ) && !isHushed()) {
			if(Permission.canShout(this)) {
				Player p = Bukkit.getPlayer(uuid);
				toShout = true;
				AsyncPlayerChatEvent event = new AsyncPlayerChatEvent(false, p, message, new HashSet<Player>(Bukkit.getOnlinePlayers()));
				Bukkit.getPluginManager().callEvent(event);
				return true;
			}else {
				FPlayer.errMsg(this, Permission.noPerm);
			}
		}
		return false;
	}

	public boolean ignore(FPlayer p) {
		if(Permission.isAdmin(p) || Permission.isStaff(p)) {
			return false;
		}
		ignored.add(p.getUniqueId());
		return true;
	}

	public void unignore(FPlayer p) {
		ignored.remove(p.getUniqueId());
	}

	public ArrayList<UUID> getIgnored(){
		return ignored;
	}

	public boolean isIgnoring(FPlayer p) {
		if(Permission.isAdmin(p) || Permission.isStaff(p)) {
			return false;
		}
		return this.ignored.contains(p.getUniqueId());
	}

	public boolean reply(String msg) {
		if(getLastMessage() == null) {
			sendMessage(FPChat.logo() + ChatColor.RED + "There is nobody currently online who you can reply to.");
			return false;
		}else {
			FPlayer p = getLastMessage();
			return sendPM(p,msg);
		}
	}

	public boolean toTalk() {
		return this.toTalk;
	}

	public void setTalk(boolean b) {
		toTalk = b;
	}
	public ChatChannel getTalkChannel() {
		return this.talkChannel;
	}

	public void setTalkChannel(ChatChannel c) {
		talkChannel = c;
	}

	public boolean receivePM(FPlayer from, String msg) {
		if(!from.isIgnoring(this)) {
			String finalMessage = "";
			String stf = "";
			if (Permission.canPMColor(from)) {
				msg = msg.replaceAll("&([0-9a-fk-or])", "§$1");
			} else {
				msg = msg.replaceAll("§[0-9a-fk-or]", "");
			}
			String header = PrepareChat.swapPlaceholders(from, this.getName(), msg, true);
			if(Permission.isStaff(from)){
				stf = FPChat.getPlugin().getMainConfig().getStaffBadge();
			}
			String filler = "";
			if(FPChat.getPlugin().getMainConfig().chatFilterEnabled()){
				filler = "word ";
			}
			if(Permission.canPMColor(from)){
				String last = ChatFilter.filter(filler + msg);
				last = BubbleCode.bubblecode(Permission.canBubbleCode(from), ChatColor.translateAlternateColorCodes('&', last));
				finalMessage = stf + header + last;
			}else{
				String newmsg = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('§', ChatFilter.filter(filler + msg)));
				finalMessage = stf + Codify.removeBubbles(header + newmsg);
			}
			sendMessage(finalMessage);
			lastMsg = from.getUniqueId();
			return true;
		}
		return false;

	}

	public boolean sendPM(FPlayer to, String msg) {
		boolean received = to.receivePM(this, msg);
		if(to.isOnline() && received) {
			String finalMessage = "";
			String stf = "";
			if (Permission.canPMColor(this)) {
				msg = msg.replaceAll("&([0-9a-fk-or])", "§$1");
			} else {
				msg = msg.replaceAll("§[0-9a-fk-or]", "");
			}
			String header = PrepareChat.swapPlaceholders(this, to.getName(), msg, false);
			if(Permission.isStaff(this)){
				stf = FPChat.getPlugin().getMainConfig().getStaffBadge();
			}
			String filler = "";
			if(FPChat.getPlugin().getMainConfig().chatFilterEnabled()){
				filler = "word ";
			}
			if(Permission.canPMColor(this)){
				String last = ChatFilter.filter(filler + msg);
				last = BubbleCode.bubblecode(Permission.canBubbleCode(this), ChatColor.translateAlternateColorCodes('&', last));
				finalMessage = stf + header + last;
			}else{
				String newmsg = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('§', ChatFilter.filter(filler + msg)));
				finalMessage = stf + Codify.removeBubbles(header + newmsg);
			}
			lastMsg = to.getUniqueId();
			sendMessage(finalMessage);
			if(!Permission.isAdmin(this)) {
				for(FPlayer p: FPlayer.getPlayers()){
					if(p.isSpy()){
						p.sendMessage(ChatColor.DARK_GRAY + name + " --> " + to.getName() + ": " +  msg);
					}
				}
			}
			return true;
		}
		sendMessage(FPChat.logo() + ChatColor.RED + "The person you are trying to send a message to is either offline or ignoring you.");
		return false;
	}

	public void cleanup() {
		String ignorelist = "";
		for(UUID id : ignored) {
			ignorelist = id.toString() + ",";
		}
		ignorelist = Util.stripLast(ignorelist);
		String chanlist = "";
		for(String c : channels) {
			if(chanlist.equals("") || !Util.contains(Util.stripLast(chanlist).split(","), c)) {
				chanlist = chanlist + c + ",";
			}
		}
		chanlist = Util.stripLast(chanlist);
		try {
			pfile.saveIgnore(ignorelist);
			pfile.saveChannels(chanlist);
			pfile.saveCurrentChannel(getPrimaryChannel().getName());
			pfile.saveSpy(spy);
			pfile.saveHushed(hushed);
		} catch (IOException e) {
			e.printStackTrace();
		}
		players.remove(this.uuid);
	}

}
