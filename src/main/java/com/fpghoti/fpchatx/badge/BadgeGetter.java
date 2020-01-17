package com.fpghoti.fpchatx.badge;

import java.util.UUID;

import com.fpghoti.fpchatx.FPChat;
import com.fpghoti.fpchatx.player.FPlayer;
import com.fpghoti.fpchatx.util.Util;

public class BadgeGetter {

	public static Integer[] getBadges(FPlayer p){
		UUID id = p.getUniqueId();
		String uuid = id.toString();
		Integer badge1 = 0, badge2 = 0, badge3 = 0;
		Util.connect();
		if(!FPChat.getPlugin().getMySQLConnection().itemExists("player_uuid", uuid, FPChat.getPlugin().getMainConfig().getChatFeatureTable())){
			BadgeSet.createPlayer(p);
		}
		badge1 = (Integer) FPChat.getPlugin().getMySQLConnection().get("badge_slot1", "player_uuid", "=", uuid, FPChat.getPlugin().getMainConfig().getChatFeatureTable());
		badge2 = (Integer)FPChat.getPlugin().getMySQLConnection().get("badge_slot2", "player_uuid", "=", uuid, FPChat.getPlugin().getMainConfig().getChatFeatureTable());
		badge3 = (Integer)FPChat.getPlugin().getMySQLConnection().get("badge_slot3", "player_uuid", "=", uuid, FPChat.getPlugin().getMainConfig().getChatFeatureTable());
		Integer[] badges = {badge1, badge2, badge3};
		return badges;
	}

	public static Boolean hasBadge(FPlayer p, int id){
		if(id == 0) {
			return true;
		}
		return p.hasPermission("fpchat.badge" + BadgeList.badgeperm.get(id)) || p.isSynced(id);
	}
	
	public static Boolean canUseSlot(FPlayer p, int slotid){
		if(slotid == 1){
			return p.hasPermission("fpchat.slot1");
		}else if(slotid == 2){
			if(hasBadge(p,33) || hasBadge(p,34) || hasBadge(p,35) || hasBadge(p,52)){
				return true;
			}
			return p.hasPermission("fpchat.slot2");
		}else if(slotid == 3){
			if(hasBadge(p,53)){
				return true;
			}
			return p.hasPermission("fpchat.slot3");
		}
		return false;
	}
}
