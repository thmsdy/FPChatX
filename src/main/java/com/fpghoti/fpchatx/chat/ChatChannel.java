package com.fpghoti.fpchatx.chat;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.fpghoti.fpchatx.FPChat;
import com.fpghoti.fpchatx.config.ChannelFile;
import com.fpghoti.fpchatx.permission.Permission;
import com.fpghoti.fpchatx.player.FPlayer;

public abstract class ChatChannel {

	private static ChatChannel defaultChannel = null;
	private static ArrayList<StandardChannel> channels;
	private static ArrayList<TempChannel> tempchannels;
	private static ChatChannel shout;
	private static final String[] reserved = {"shout"};

	private static ArrayList<String> loadChannelNames() {
		ArrayList<String> cnames = new ArrayList<String>();
		for(File f : ChannelFile.getChannelFiles()) {
			String fname = f.getName();
			cnames.add(fname.substring(0, fname.length()-4));
		}
		return cnames;
	}
	

	public static StandardChannel loadChannel(String channelname) {
		if(ChannelFile.channelExists(channelname)) {
			return makeChannel(channelname);
		}
		return null;
	}

	public static StandardChannel makeChannel(String channelname) {
		ChannelFile file = new ChannelFile(FPChat.getPlugin(), channelname);
		StandardChannel c = new StandardChannel(FPChat.getPlugin(), file);
		return c;
	}

	public static TempChannel makeTempChannel(FPlayer owner, String channelname) {
		TempChannel t = new TempChannel(FPChat.getPlugin(), owner, channelname);
		t.setShortcut(channelname);
		tempchannels.add(t);
		return t;
	}
	
	public static void setShout(ShoutChannel shoutchannel) {
		shout = shoutchannel;
	}

	public static void deleteChannel(String channelname) {
		ChatChannel c = getChannel(channelname);
		boolean temp = false;
		if(c == null) {
			c = getTempChannel(channelname);
			temp = true;
		}
		if(c != null) {
			String cname = c.getName();
			for(FPlayer pl : c.getPlayers()) {
				pl.leaveChannel(cname);
				FPlayer.dualMsg(pl, ChatColor.YELLOW + "The channel you were in " + ChatColor.WHITE + c.getName() + ChatColor.YELLOW + " was deleted.");
			}
			tempchannels.remove(c);
			channels.remove(c);
			if(!temp) {
				File cdir = new File(FPChat.getPlugin().getDataFolder() + File.separator + "channels");
				cdir.mkdirs();
				File configFile = new File(cdir, cname + ".yml");
				if(configFile.delete()) {
					FPChat.getPlugin().log(Level.INFO, "Channel file for " + cname + " successfully deleted.");
				} else {
					FPChat.getPlugin().log(Level.SEVERE, "Channel not properly removed. File deletion error.");
					return;
				}
			}else {
				FPChat.getPlugin().log(Level.INFO, "Temp Channel " + cname + " was deleted.");
			}
		}
	}

	public static ChatChannel getDefault() {
		return defaultChannel;
	}
	
	public static void setDefault(String channel) {
		defaultChannel = getChannel(channel);
	}

	public static ArrayList<ChatChannel> getChannels(){
		ArrayList<ChatChannel> c = new ArrayList<ChatChannel>();
		for(StandardChannel ch : channels) {
			c.add(ch);
		}
		for(TempChannel ch : tempchannels) {
			c.add(ch);
		}
		return c;
	}

	public static ChatChannel getChannel(String name) {
		for(ChatChannel c : channels) {
			if(c.getName().equalsIgnoreCase(name)) {
				return c;
			}
		}
		return null;
	}

	public static ChatChannel getTempChannel(String name) {
		for(ChatChannel c : tempchannels) {
			if(c.getName().equalsIgnoreCase(name)) {
				return c;
			}
		}
		return null;
	}

	public static void loadChannels() {
		shout = (ChatChannel)(new ShoutChannel(FPChat.getPlugin()));
		channels = new ArrayList<StandardChannel>();
		tempchannels = new ArrayList<TempChannel>();
		for(String c : loadChannelNames()) {
			StandardChannel chan = loadChannel(c);
			if(chan != null) {
				channels.add(chan);
			}
		}
	}

	public static ChatChannel getShout() {
		return shout;
	}

	public static boolean channelExists(String name) {
		for(ChatChannel c : getChannels()) {
			if(c.getName().equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isReserved(String name) {
		if(channelExists(name)) {
			return true;
		}
		for(String s : reserved) {
			if(s.equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}
	
	public static void addChannel(StandardChannel channel) {
		channels.add(channel);
	}

	protected FPChat plugin;
	protected String name;
	protected String shortcut;
	protected String chatFormat;
	protected String distinguishedChatFormat;
	protected boolean isWhitelisted;

	protected ArrayList<UUID> whitelist;
	protected ArrayList<UUID> banned;

	public ChatChannel(FPChat plugin) {
		this.plugin = plugin;
		this.isWhitelisted = false;
		this.whitelist = new ArrayList<UUID>();
	}

	public void makeDefault() {
		ChatChannel.defaultChannel = this;
	}

	public abstract boolean isTemp();

	public abstract boolean isDefault();

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public abstract void kick(FPlayer p);

	public abstract void setShortcut(String shortcut);

	public String getShortcut() {
		return this.shortcut;
	}

	public abstract void setChatFormat(String chatFormat);

	public String getChatFormat() {
		return this.chatFormat.replace("{channel}", name);
	}

	public abstract void setDistinguishedChatFormat(String chatFormat);

	public String getDistinguishedChatFormat() {
		return this.distinguishedChatFormat.replace("{channel}", name);
	}

	public abstract void enableWhitelist();

	public abstract void disableWhitelist();

	public boolean whitelistEnabled() {
		return this.isWhitelisted;
	}

	public void whitelistAdd(OfflinePlayer p) {
		whitelistAdd(p.getUniqueId());
	}

	public void whitelistAdd(Player p) {
		whitelistAdd(p.getUniqueId());
	}

	public void whitelistAdd(FPlayer p) {
		whitelistAdd(p.getUniqueId());
	}

	public abstract void whitelistAdd(UUID uuid);

	public void whitelistRemove(OfflinePlayer p) {
		whitelistRemove(p.getUniqueId());
	}

	public void whitelistRemove(Player p) {
		whitelistRemove(p.getUniqueId());
	}

	public void whitelistRemove(FPlayer p) {
		whitelistRemove(p.getUniqueId());
	}

	public abstract void whitelistRemove(UUID uuid);

	public abstract boolean isWhitelisted(FPlayer p);

	public void log(String str) {
		plugin.log(Level.INFO, str);
	}

	public abstract void enableRadius();

	public abstract void disableRadius();

	public abstract boolean hasRadius();

	public abstract void setRadius(int radius);

	public abstract int getRadius();

	public boolean isBanned(FPlayer p) {
		return banned.contains(p.getUniqueId());
	}

	public abstract void addBanned(FPlayer p);

	public abstract ArrayList<FPlayer> getPlayers();

	public abstract void removeBanned(FPlayer p);

	public ArrayList<UUID> getBanned(){
		return this.banned;
	}
	
	public int playerCount() {
		int count = 0;
		for(FPlayer p : FPlayer.getPlayers()) {
			if(p.getChannels().contains(name) || p.getTempChannels().contains(name)) {
				count++;
			}
		}
		return count;
	}

	public String format(FPlayer p, String msg) {
		String finalMessage = "", badges = "", stf = "", filler = "";
		String header = PrepareChat.swapPlaceholders(p, this, msg);
		if(Permission.isStaff(p)){
			stf = FPChat.getPlugin().getMainConfig().getStaffBadge();
		}
		if(plugin.getMainConfig().mySQLEnabled()){
			badges = p.getBadgeData().getAppearanceString();
		}
		if(plugin.getMainConfig().chatFilterEnabled()){
			filler = "word ";
		}
		if(Permission.canUseColor(p)){
			String last = ChatFilter.filter(filler + msg);
			last = BubbleCode.bubblecode(Permission.canBubbleCode(p), ChatColor.translateAlternateColorCodes('&', HexColor.formatHex(last)));
			finalMessage = HexColor.formatHex(stf + badges + header + last);
		}else{
			String newmsg = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('§', ChatFilter.filter(filler + msg)));
			finalMessage = stf + badges + Codify.removeBubbles(HexColor.formatHex(header) + newmsg);
		}
		return finalMessage;
	}

	public abstract void sendMessage(String msg, FPlayer from);
	
	public void delete() {
		ChatChannel.deleteChannel(name);
	}

}
