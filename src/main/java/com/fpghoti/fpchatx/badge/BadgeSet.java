package com.fpghoti.fpchatx.badge;

import com.fpghoti.fpchatx.FPChat;
import com.fpghoti.fpchatx.player.FPlayer;
import com.fpghoti.fpchatx.util.Util;

public class BadgeSet {

	public static void setBadge(FPlayer p, int slot, int badgeId){
		if(slot > 3){
			slot = 3;
		}else if(slot < 1){
			slot = 1;
		}
		String uuid = p.getUniqueId().toString();
		Util.connect();
		if(!FPChat.getPlugin().getMySQLConnection().itemExists("player_uuid", uuid, FPChat.getPlugin().getMainConfig().getChatFeatureTable())){
			createPlayer(p);
		}
		FPChat.getPlugin().getMySQLConnection().set("badge_slot" + String.valueOf(slot), badgeId, "player_uuid", "=", uuid,  FPChat.getPlugin().getMainConfig().getChatFeatureTable());
		BadgeGetter.getBadges(p);
	}

	public static void createPlayer(FPlayer p){
		String uuid = p.getUniqueId().toString();
		Util.connect();
		if(!FPChat.getPlugin().getMySQLConnection().itemExists("player_uuid", uuid, FPChat.getPlugin().getMainConfig().getChatFeatureTable())){
			FPChat.getPlugin().getMySQLConnection().insertInto("player_uuid, badge_slot1, badge_slot2, badge_slot3", " '" + uuid + "', '0', '0', '0' ",  FPChat.getPlugin().getMainConfig().getChatFeatureTable());
		}
	}

}
