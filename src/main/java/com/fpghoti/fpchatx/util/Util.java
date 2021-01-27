package com.fpghoti.fpchatx.util;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Util {

	public static String getGameVersion() {
		String version = Bukkit.getVersion().split("MC: ")[1].replace(")", "");
		return version;
	}
	
	public static Player playerGet(String uuid){
		UUID id = UUID.fromString(uuid);
		Player p = Bukkit.getPlayer(id);
		return p;
	}

	public static String fixCodes(String s){
		return s.replaceAll("&", "§");
	}

	public static Player getEP(String playername){
		Player p = null;
		for(Player item : Bukkit.getOnlinePlayers()){
			if(playername.equalsIgnoreCase(item.getName())){
				p = item;
			}
		}
		return p;
	}

	public static boolean isDigit(String s){
		return s.matches("[0-9]+");
	}

	public static String stripLast(String s) {
		if (s.length() != 0) {
			s = s.substring(0, s.length()-1);
		}
		return s;
	}

	public static Boolean contains(String[] list, String s) {
		Boolean check = false;
		for(String item: list){
			if(item.equals(s)){
				check = true;
			}
		}

		return check;
	}

}
