package com.fpghoti.fpchatx.chat;

import java.util.ArrayList;

import org.bukkit.ChatColor;

import com.fpghoti.fpchatx.FPChat;

public class ChatFilter {

	public static ArrayList<String> filtered = null;

	//CHAT FILTER

	public static String filter(String sentence){  // THIS FUNCTION TAKES THE RAW CHAT MESSAGE AND SENDS EACH 
		String msg = sentence;                     // INDIVIDUAL WORD INTO THE FILTER WORD FUNCTION BELOW
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

	public static String filterWord(String word){             // THIS TAKES THE WORD, LOWERCASES IT,
		String wordp = "";                                // AND REPLACES CHARACTERS "1" and "!" with "i", "5" with "s", "6" with "g", and
		if(FPChat.getPlugin().getMainConfig().chatFilterEnabled()){    // "3" with "e". It then compares it to the list of
			String word2 = word.toLowerCase();                // Naughty words and replaces it with "Frank" or "bleep" if a match is found
			String color = ChatColor.getLastColors(word2);
			if(word2.length() >= 2 && word2.charAt(word2.length() -1) == '!'){
				for(int i = 0; i < word2.length() -1; i++ ){
					wordp += word2.charAt(i);
				}
				word2 = wordp;
			}
			wordp = "";
			for(int i = 0; i < word2.length(); i++ ){
				if(word2.charAt(i) != '!'){
					wordp += word2.charAt(i);
				}else{
					wordp += 'i';
				}
			}
			word2 = wordp;
			word2 = ChatColor.translateAlternateColorCodes('§', word2);
			word2 = ChatColor.translateAlternateColorCodes('&', word2);
			word2 = ChatColor.stripColor(word2);
			word2 = word2.replaceAll("\\p{Punct}+", "").replaceAll("1", "i").replaceAll("5", "s").replaceAll("6", "g").replaceAll("3", "e");
			if(filtered == null) {
				filtered = new ArrayList<String>();
				for(String s : FPChat.getPlugin().getMainConfig().getNaughtyWords().split(",")) {
					filtered.add(s);
				}
			}

			for(String item : filtered){
				if(ChatColor.stripColor(word2).equalsIgnoreCase(item) || ChatColor.stripColor(word2).equalsIgnoreCase(item + "s")){
					if (word2.length() > 2) {
						if(word2.substring(word2.length() - 3).equalsIgnoreCase("ing")){
							word = "bleeping";
							if(FPChat.getPlugin().getMainConfig().frankModeEnabled()){
								word = "Franking";
							}
						}else{
							word = "bleep";
							if(FPChat.getPlugin().getMainConfig().frankModeEnabled()){
								word = "Frank";
							}
						}
					}
				}
				word = color + word;
			}
			word2 = null;
		}
		return word;
	}


	// By the end of the loop, the sentence is reconstructed with the naughty words replaced

}
