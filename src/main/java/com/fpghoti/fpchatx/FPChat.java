package com.fpghoti.fpchatx;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.fpghoti.fpchatx.badge.BadgeList;
import com.fpghoti.fpchatx.chat.ChatChannel;
import com.fpghoti.fpchatx.chat.ShoutChannel;
import com.fpghoti.fpchatx.command.Commands;
import com.fpghoti.fpchatx.command.commands.BadgeClearCommand;
import com.fpghoti.fpchatx.command.commands.BadgeEquipCommand;
import com.fpghoti.fpchatx.command.commands.BadgeListCommand;
import com.fpghoti.fpchatx.command.commands.BlacklistCommand;
import com.fpghoti.fpchatx.command.commands.DeleteCommand;
import com.fpghoti.fpchatx.command.commands.JoinCommand;
import com.fpghoti.fpchatx.command.commands.TalkCommand;
import com.fpghoti.fpchatx.command.commands.GiveBadgeCommand;
import com.fpghoti.fpchatx.command.commands.HelpCommand;
import com.fpghoti.fpchatx.command.commands.HushCommand;
import com.fpghoti.fpchatx.command.commands.IgnoreCommand;
import com.fpghoti.fpchatx.command.commands.IgnoreListCommand;
import com.fpghoti.fpchatx.command.commands.KickCommand;
import com.fpghoti.fpchatx.command.commands.LeaveCommand;
import com.fpghoti.fpchatx.command.commands.ChannelsCommand;
import com.fpghoti.fpchatx.command.commands.CreateCommand;
import com.fpghoti.fpchatx.command.commands.MessageCommand;
import com.fpghoti.fpchatx.command.commands.ReloadCommand;
import com.fpghoti.fpchatx.command.commands.ReplyCommand;
import com.fpghoti.fpchatx.command.commands.RevokeBadgeCommand;
import com.fpghoti.fpchatx.command.commands.ShoutCommand;
import com.fpghoti.fpchatx.command.commands.ShoutToggleCommand;
import com.fpghoti.fpchatx.command.commands.SpyCommand;
import com.fpghoti.fpchatx.command.commands.UnHushCommand;
import com.fpghoti.fpchatx.command.commands.UnignoreCommand;
import com.fpghoti.fpchatx.command.commands.WhoCommand;
import com.fpghoti.fpchatx.config.MainConfig;
import com.fpghoti.fpchatx.config.PlayerCache;
import com.fpghoti.fpchatx.listener.PlayerListener;
import com.fpghoti.fpchatx.mysql.MySQLConnection;
import com.fpghoti.fpchatx.player.FPlayer;
import com.fpghoti.fpchatx.util.VaultUtil;

public class FPChat extends JavaPlugin {

	private static Logger log = Logger.getLogger("Minecraft");
	private static Logger chatLog = Logger.getLogger("FPChat");
	private MainConfig config;
	private MySQLConnection sql;
	private static FPChat plugin;
	private int mysqlTimer = 1140;
	private BukkitTask refresh = null;
	private PlayerListener listener = null;
	private PlayerCache cache;
	
	public void onEnable() {
		plugin = this;
		VaultUtil vault = new VaultUtil(this);
		vault.setupChat();
		vault.setupEconomy();
		vault.setupPermissions();
		config = new MainConfig(this);
		if(config.mySQLEnabled()) {
			sql = new MySQLConnection(this);
			sql.generate();
			startTimers();
		}
		registerEvents();
		registerCommands();

		PluginDescriptionFile desc = getDescription();

		ChatChannel.loadChannels();
		ChatChannel.setShout(new ShoutChannel(this));
		ChatChannel.setDefault(config.getDefaultChannel());
		BadgeList.setupBadges();
		for(int i : BadgeList.badgelist.keySet()) {
			BadgeList.badgelist.put(i, BadgeList.badgelist.get(i).replace("&", "§"));
		}
		cache = new PlayerCache(this);
		for(Player bp : Bukkit.getOnlinePlayers()){
			FPlayer.getPlayer(bp);
		}
		log(Level.INFO, desc.getName() + " version " + desc.getVersion() + " enabled.");
	}

	public void onDisable() {
		for(FPlayer p : FPlayer.getPlayers()) {
			p.cleanup();
		}
		if(config.mySQLEnabled()) {
			sql.disconnect();
		}
	}

	public void reload() {
		Commands.blockCommands();
		if(listener != null) {
			listener.disable();
		}
		if(refresh != null) {
			refresh.cancel();
		}

		sql.disconnect();
		for(FPlayer p : FPlayer.getPlayers()) {
			p.cleanup();
		}
		BadgeList.purge();
		FPlayer.purge();
		config = new MainConfig(this);
		if(config.mySQLEnabled()) {
			sql = new MySQLConnection(this);
			sql.generate();
			startTimers();
		}
		ChatChannel.loadChannels();
		ChatChannel.setShout(new ShoutChannel(this));
		ChatChannel.setDefault(config.getDefaultChannel());
		BadgeList.setupBadges();
		for(int i : BadgeList.badgelist.keySet()) {
			BadgeList.badgelist.put(i, BadgeList.badgelist.get(i).replace("&", "§"));
		}
		cache = new PlayerCache(this);
		for(Player bp : Bukkit.getOnlinePlayers()){
			FPlayer.getPlayer(bp);
		}
		if(listener != null) {
			listener.enable();
		}
		Commands.allowCommands();
		log(Level.INFO, "FPChatX reloaded.");
	}

	private void registerEvents() {
		this.listener = new PlayerListener();
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(listener, this);
	}

	private void registerCommands() {
		Commands.register(new ReloadCommand(this));
		Commands.register(new ShoutCommand(this));
		Commands.register(new ShoutToggleCommand(this));
		Commands.register(new MessageCommand(this));
		Commands.register(new ReplyCommand(this));
		Commands.register(new IgnoreCommand(this));
		Commands.register(new UnignoreCommand(this));
		Commands.register(new IgnoreListCommand(this));
		Commands.register(new BadgeListCommand(this));
		Commands.register(new BadgeEquipCommand(this));
		Commands.register(new BadgeClearCommand(this));
		Commands.register(new SpyCommand(this));
		Commands.register(new ChannelsCommand(this));
		Commands.register(new WhoCommand(this));
		Commands.register(new TalkCommand(this));
		Commands.register(new JoinCommand(this));
		Commands.register(new LeaveCommand(this));;
		Commands.register(new CreateCommand(this));
		Commands.register(new DeleteCommand(this));
		Commands.register(new HushCommand(this));
		Commands.register(new UnHushCommand(this));
		Commands.register(new KickCommand(this));
		Commands.register(new BlacklistCommand(this));
		Commands.register(new HelpCommand(this));
		Commands.register(new RevokeBadgeCommand(this));
		Commands.register(new GiveBadgeCommand(this));

	}

	public MainConfig getMainConfig() {
		return this.config;
	}
	
	public PlayerCache getPlayerCache() {
		return this.cache;
	}

	public MySQLConnection getMySQLConnection() {
		return this.sql;
	}

	public void log(Level level, String msg) {
		log.log(level, "[FPChatX] " + msg.replaceAll("§[0-9A-FK-OR]", ""));
	}


	public void logChat(String msg) {
		chatLog.info(msg.replaceAll("§[0-9A-FK-OR]", ""));
	}

	public static FPChat getPlugin(){
		return plugin;
	}

	public static String logo() {
		return "" + ChatColor.DARK_RED + "[" + ChatColor.GREEN + ChatColor.BOLD + "FPChatX" + ChatColor.RESET + ChatColor.DARK_RED + "]" + ChatColor.RESET ;

	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		return Commands.performCommand(sender, command, label, args);
	}


	public void startTimers() {
		refresh = new BukkitRunnable(){
			public void run() {
				if(config.shoutCooldownEnabled()) {      //shout cooldown
					for(FPlayer p : FPlayer.getPlayers()) {
						if(p != null) {
							int time = p.getShoutCooldown();
							if(time > 0){
								p.setShoutCooldown(time - 1);
							}
						}
					}
				}
				if(config.mySQLEnabled()){     // mysql auto reconnect
					if(mysqlTimer >= 1200){
						sql.reconnect();
						mysqlTimer = 0;
					}else{
						mysqlTimer++;
					}
				}
			}
		}.runTaskTimerAsynchronously(this, 1*20, 1*20);
	}

}
