package com.fpghoti.fpchatx.customcodes;

public class Codify {

	public static String changeFormatSign(String str) {
		String nstr = str;
		nstr = nstr.replace("&0", "§0");
		nstr = nstr.replace("&1", "§1");
		nstr = nstr.replace("&2", "§2");
		nstr = nstr.replace("&3", "§3");
		nstr = nstr.replace("&4", "§4");
		nstr = nstr.replace("&5", "§5");
		nstr = nstr.replace("&6", "§6");
		nstr = nstr.replace("&7", "§7");
		nstr = nstr.replace("&8", "§8");
		nstr = nstr.replace("&9", "§9");
		nstr = nstr.replace("&a", "§a");
		nstr = nstr.replace("&b", "§b");
		nstr = nstr.replace("&c", "§c");
		nstr = nstr.replace("&d", "§d");
		nstr = nstr.replace("&e", "§e");
		nstr = nstr.replace("&f", "§f");
		nstr = nstr.replace("&k", "§k");
		nstr = nstr.replace("&l", "§l");
		nstr = nstr.replace("&m", "§m");
		nstr = nstr.replace("&n", "§n");
		nstr = nstr.replace("&o", "§o");
		nstr = nstr.replace("&r", "§r");
		//nstr = nstr.replace("&w", "§w");
		//nstr = nstr.replace("&x", "§x");
		return nstr;
	}
	
	public static String removeBubbles(String str) {
		String nstr = str;
		nstr = nstr.replace('ⓐ', 'a');
		nstr = nstr.replace('ⓑ', 'b');
		nstr = nstr.replace('ⓒ', 'c');
		nstr = nstr.replace('ⓓ', 'd');
		nstr = nstr.replace('ⓔ', 'e');
		nstr = nstr.replace('ⓕ', 'f');
		nstr = nstr.replace('ⓖ', 'g');
		nstr = nstr.replace('ⓗ', 'h');
		nstr = nstr.replace('ⓘ', 'i');
		nstr = nstr.replace('ⓙ', 'j');
		nstr = nstr.replace('ⓚ', 'k');
		nstr = nstr.replace('ⓛ', 'l');
		nstr = nstr.replace('ⓜ', 'm');
		nstr = nstr.replace('ⓝ', 'n');
		nstr = nstr.replace('ⓞ', 'o');
		nstr = nstr.replace('ⓟ', 'p');
		nstr = nstr.replace('ⓠ', 'q');
		nstr = nstr.replace('ⓡ', 'r');
		nstr = nstr.replace('ⓢ', 's');
		nstr = nstr.replace('ⓣ', 't');
		nstr = nstr.replace('ⓤ', 'u');
		nstr = nstr.replace('ⓥ', 'v');
		nstr = nstr.replace('ⓦ', 'w');
		nstr = nstr.replace('ⓧ', 'x');
		nstr = nstr.replace('ⓨ', 'y');
		nstr = nstr.replace('ⓩ', 'z');
		nstr = nstr.replace('Ⓐ', 'A');
		nstr = nstr.replace('Ⓑ', 'B');
		nstr = nstr.replace('Ⓒ', 'C');
		nstr = nstr.replace('Ⓓ', 'D');
		nstr = nstr.replace('Ⓔ', 'E');
		nstr = nstr.replace('Ⓕ', 'F');
		nstr = nstr.replace('Ⓖ', 'G');
		nstr = nstr.replace('Ⓗ', 'H');
		nstr = nstr.replace('Ⓘ', 'I');
		nstr = nstr.replace('Ⓙ', 'J');
		nstr = nstr.replace('Ⓚ', 'K');
		nstr = nstr.replace('Ⓛ', 'L');
		nstr = nstr.replace('Ⓜ', 'M');
		nstr = nstr.replace('Ⓝ', 'N');
		nstr = nstr.replace('Ⓞ', 'O');
		nstr = nstr.replace('Ⓟ', 'P');
		nstr = nstr.replace('Ⓠ', 'Q');
		nstr = nstr.replace('Ⓡ', 'R');
		nstr = nstr.replace('Ⓢ', 'S');
		nstr = nstr.replace('Ⓣ', 'T');
		nstr = nstr.replace('Ⓤ', 'U');
		nstr = nstr.replace('Ⓥ', 'V');
		nstr = nstr.replace('Ⓦ', 'W');
		nstr = nstr.replace('Ⓧ', 'X');
		nstr = nstr.replace('Ⓨ', 'Y');
		nstr = nstr.replace('Ⓩ', 'Z');
		nstr = nstr.replace('①', '1');
		nstr = nstr.replace('②', '2');
		nstr = nstr.replace('③', '3');
		nstr = nstr.replace('④', '4');
		nstr = nstr.replace('⑤', '5');
		nstr = nstr.replace('⑥', '6');
		nstr = nstr.replace('⑦', '7');
		nstr = nstr.replace('⑧', '8');
		nstr = nstr.replace('⑨', '9');
		nstr = nstr.replace('⓪', '0');
		return nstr;
	}
	
	public static Boolean isCode(String c) {
		String code = "0,1,2,3,4,5,6,7,8,9,a,b,c,d,e,f,k,l,m,n,o,r,w,x";
		Boolean found = false;
		String[] codes = code.split(",");
		int size = codes.length;
		for(int i = 0; i < size; i++) {
			if(c.contains(codes[i])) {
				found = true;
			}
		}
		return found;
	}
	
}
