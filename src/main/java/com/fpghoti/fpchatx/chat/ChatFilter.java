package com.fpghoti.fpchatx.chat;

import java.util.ArrayList;

import org.bukkit.ChatColor;

import com.fpghoti.fpchatx.FPChat;
import com.fpghoti.fpchatx.permission.Permission;
import com.fpghoti.fpchatx.player.FPlayer;

public class ChatFilter {

	public static ArrayList<String> filtered = null;
	public static ArrayList<String> prefixfilter = null;

	public static String[] suffixes = {"ing","s","ed","er","es","y","ers","ier","iest","ies","ys"};

	public static void loadFilter() {
		filtered = new ArrayList<String>();
		for(String s : FPChat.getPlugin().getMainConfig().getNaughtyWords().split(",")) {
			filtered.add(s);
		}
		prefixfilter = new ArrayList<String>();
		for(String s : FPChat.getPlugin().getMainConfig().getPrefixNaughtyWords().split(",")) {
			prefixfilter.add(s);
		}
	}

	public static String filter(String sentence){
		String msg = sentence;
		if(FPChat.getPlugin().getMainConfig().chatFilterEnabled()){
			msg = "";
			int i = 0;
			for(String s : sentence.split(" ")){
				if(i != 0){
					msg = msg + filterWord(s) + " ";
				}
				i++;
			}
		}
		return msg;
	}

	public static String filterWord(String word) {
		if(FPChat.getPlugin().getMainConfig().chatFilterEnabled()) {
			String color = ChatColor.getLastColors(word);
			String[] match = findMatchPair(word, filtered);
			if(match != null) {
				String suffix = match[1];
				if(suffix == null) {
					suffix = "";
				}
				return color + getReplacement() + suffix;
			}
		}
		return word;
	}

	public static String filterPrefix(FPlayer p, String prefix) {
		if(FPChat.getPlugin().getMainConfig().prefixFilterEnabled()) {
			String color = ChatColor.getLastColors(prefix);
			String[] match = findMatchPair(prefix, prefixfilter);
			if(match != null) {
				String found = match[0];
				String suffix = match[1];
				if(suffix == null) {
					suffix = "";
				}
				if(!Permission.canUseWordInPrefix(p, found)) {
					return color + getReplacement() + suffix;
				}
			}
		}
		return prefix;
	}

	public static String getReplacement() {
		if(FPChat.getPlugin().getMainConfig().frankModeEnabled()){
			return "Frank";
		}
		return "bleep";
	}

	public static String findMatch(String word, ArrayList<String> filter) {
		String[] match = findMatchPair(word,filter);
		if(match == null || match[0] == null) {
			return null;
		}
		return match[0];
	}

	public static String[] findMatchPair(String word, ArrayList<String> filter) {
		String cleaned = "";
		word = word.toLowerCase();
		if(word.length() >= 2 && word.charAt(word.length() -1) == '!'){
			for(int i = 0; i < word.length() -1; i++ ){
				cleaned += word.charAt(i);
			}
			word = cleaned;
		}
		cleaned = "";
		for(int i = 0; i < word.length(); i++ ){
			if(word.charAt(i) != '!'){
				cleaned += word.charAt(i);
			}else{
				cleaned += 'i';
			}
		}
		cleaned = ChatColor.translateAlternateColorCodes('§', cleaned);
		cleaned = ChatColor.translateAlternateColorCodes('&', cleaned);
		cleaned = ChatColor.stripColor(cleaned);
		cleaned = cleaned.replace(" ", "");
		cleaned = cleaned.replaceAll("\\p{Punct}+", "").replaceAll("1", "i").replaceAll("5", "s").replaceAll("6", "g").replaceAll("3", "e").replaceAll("0", "o").replaceAll("9", "g").replaceAll("8", "b");
		if(cleaned.equals("")) {
			return null;
		}
		String[] wordSuf = {null,null};
		for(String item : filter) {
			if(cleaned.equalsIgnoreCase(item)){
				wordSuf[0] = item;
				return wordSuf;
			}
			for(String suffix : suffixes) {
				if(cleaned.equalsIgnoreCase(item + suffix)){
					wordSuf[0] = item;
					wordSuf[1] = suffix;
					return wordSuf;
				}
				String last = item.substring(item.length() - 1);
				if(cleaned.equalsIgnoreCase(item + last + suffix)){
					wordSuf[0] = item;
					wordSuf[1] = last + suffix;
					return wordSuf;
				}
				if(cleaned.equalsIgnoreCase(item + last + last + suffix)){
					wordSuf[0] = item;
					wordSuf[1] = last + last + suffix;
					return wordSuf;
				}
			}

		}
		return null;
	}

}
