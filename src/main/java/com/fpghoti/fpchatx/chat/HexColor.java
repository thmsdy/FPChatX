package com.fpghoti.fpchatx.chat;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fpghoti.fpchatx.util.Util;

import net.md_5.bungee.api.ChatColor;

public class HexColor {

	private static final Pattern pattern = Pattern.compile("(?<!\\\\)(#[a-fA-F0-9]{6})");

	public static boolean allowHex() {
		String version = Util.getGameVersion().replace(".", "-");
		String[] vnums = version.split("-");
		int ver = Integer.parseInt(vnums[1]);
		if(Integer.parseInt(vnums[0]) > 1){
			return true;
		};
		return ver >= 16;
	}

	public static String formatHex(String message) {
		if(!allowHex()) {
			return message;
		}
		HashMap<String, String> toReplace = new HashMap<String, String>();
		Matcher matcher = pattern.matcher(message);
		while (matcher.find()) {
			String hex = message.substring(matcher.start(), matcher.end());
			if(isHexCode(hex)) {
				toReplace.put(hex, "" + ChatColor.of(hex));
			}
		}
		for(String hex : toReplace.keySet()) {
			message = message.replace(hex, toReplace.get(hex));
		}
		return message;
	}

	public static boolean isHexCode(String code) {
		Pattern pattern = Pattern.compile("#([a-fA-F0-9]{3}|[a-fA-F0-9]{6}|[a-fA-F0-9]{8})");
		Matcher matcher = pattern.matcher(code);
		return matcher.matches();
	}
}