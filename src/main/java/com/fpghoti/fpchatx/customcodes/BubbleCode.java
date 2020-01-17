package com.fpghoti.fpchatx.customcodes;

public class BubbleCode {

	public static String bubblecode(Boolean hasPerm, String msg) {
		String newmsg = msg;
		if(hasPerm) {
			newmsg = replaceChar(msg);
		}
		return newmsg;
	}

	private static String replaceChar(String msg) {
		Boolean on = false;
		String newmsg = "";
		int i = 0;
		
		while(i < msg.length()) {
			if(msg.substring(i, i + 1).equals("§")) {
				String code = msg.substring(i + 1, i + 2);
				if(code.equals("r")) {
					on = false;
				}
				String wcode = "§" + code;
				newmsg = newmsg + wcode;
				i++;
			}else if(msg.substring(i, i + 1).equals("&")) {
				if(msg.substring(i + 1, i + 2).equals("w")) {
					on = true;
					i++;
				}else {
					newmsg = newmsg + "&";
				}
				
			}else{
				String sub = msg.substring(i, i + 1);
				if(on) {				
					sub = sub.replace('a', 'ⓐ');
					sub = sub.replace('b', 'ⓑ');
					sub = sub.replace('c', 'ⓒ');
					sub = sub.replace('d', 'ⓓ');
					sub = sub.replace('e', 'ⓔ');
					sub = sub.replace('f', 'ⓕ');
					sub = sub.replace('g', 'ⓖ');
					sub = sub.replace('h', 'ⓗ');
					sub = sub.replace('i', 'ⓘ');
					sub = sub.replace('j', 'ⓙ');
					sub = sub.replace('k', 'ⓚ');
					sub = sub.replace('l', 'ⓛ');
					sub = sub.replace('m', 'ⓜ');
					sub = sub.replace('n', 'ⓝ');
					sub = sub.replace('o', 'ⓞ');
					sub = sub.replace('p', 'ⓟ');
					sub = sub.replace('q', 'ⓠ');
					sub = sub.replace('r', 'ⓡ');
					sub = sub.replace('s', 'ⓢ');
					sub = sub.replace('t', 'ⓣ');
					sub = sub.replace('u', 'ⓤ');
					sub = sub.replace('v', 'ⓥ');
					sub = sub.replace('w', 'ⓦ');
					sub = sub.replace('x', 'ⓧ');
					sub = sub.replace('y', 'ⓨ');
					sub = sub.replace('z', 'ⓩ');
					sub = sub.replace('A', 'Ⓐ');
					sub = sub.replace('B', 'Ⓑ');
					sub = sub.replace('C', 'Ⓒ');
					sub = sub.replace('D', 'Ⓓ');
					sub = sub.replace('E', 'Ⓔ');
					sub = sub.replace('F', 'Ⓕ');
					sub = sub.replace('G', 'Ⓖ');
					sub = sub.replace('H', 'Ⓗ');
					sub = sub.replace('I', 'Ⓘ');
					sub = sub.replace('J', 'Ⓙ');
					sub = sub.replace('K', 'Ⓚ');
					sub = sub.replace('L', 'Ⓛ');
					sub = sub.replace('M', 'Ⓜ');
					sub = sub.replace('N', 'Ⓝ');
					sub = sub.replace('O', 'Ⓞ');
					sub = sub.replace('P', 'Ⓟ');
					sub = sub.replace('Q', 'Ⓠ');
					sub = sub.replace('R', 'Ⓡ');
					sub = sub.replace('S', 'Ⓢ');
					sub = sub.replace('T', 'Ⓣ');
					sub = sub.replace('U', 'Ⓤ');
					sub = sub.replace('V', 'Ⓥ');
					sub = sub.replace('W', 'Ⓦ');
					sub = sub.replace('X', 'Ⓧ');
					sub = sub.replace('Y', 'Ⓨ');
					sub = sub.replace('Z', 'Ⓩ');
					sub = sub.replace('1', '①');
					sub = sub.replace('2', '②');
					sub = sub.replace('3', '③');
					sub = sub.replace('4', '④');
					sub = sub.replace('5', '⑤');
					sub = sub.replace('6', '⑥');
					sub = sub.replace('7', '⑦');
					sub = sub.replace('8', '⑧');
					sub = sub.replace('9', '⑨');
					sub = sub.replace('0', '⓪');
				}
				newmsg = newmsg + sub;
			}
			i++;
		}




		return newmsg;
	}



}
