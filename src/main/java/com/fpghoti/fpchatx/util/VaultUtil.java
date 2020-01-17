package com.fpghoti.fpchatx.util;

import org.bukkit.plugin.RegisteredServiceProvider;

import com.fpghoti.fpchatx.FPChat;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public class VaultUtil {

	public static Permission permission = null;
	public static Economy economy = null;
	public static Chat chat = null;
	private FPChat plugin;

	public VaultUtil(FPChat plugin) {
		this.plugin = plugin;
	}

	public boolean setupPermissions(){
		RegisteredServiceProvider<Permission> permissionProvider = plugin.getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
		if (permissionProvider != null) {
			permission = permissionProvider.getProvider();
		}
		return (permission != null);
	}

	public boolean setupChat(){
		RegisteredServiceProvider<Chat> chatProvider = plugin.getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
		if (chatProvider != null) {
			chat = chatProvider.getProvider();
		}

		return (chat != null);
	}

	public boolean setupEconomy(){
		RegisteredServiceProvider<Economy> economyProvider = plugin.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null) {
			economy = economyProvider.getProvider();
		}

		return (economy != null);
	}

	public Chat getChat() {
		return chat;
	}
	
}
