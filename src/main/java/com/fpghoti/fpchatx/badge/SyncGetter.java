package com.fpghoti.fpchatx.badge;

import java.util.UUID;

import com.fpghoti.fpchatx.FPChat;
import com.fpghoti.fpchatx.player.FPlayer;
import com.fpghoti.fpchatx.util.Util;

public class SyncGetter {

	public static void syncBadges(FPlayer p, boolean create){
		UUID id = p.getUniqueId();
		String uuid = id.toString();
		Util.connect();
		if(create && !FPChat.getPlugin().getMySQLConnection().itemExists("player_uuid", uuid, FPChat.getPlugin().getMainConfig().getPermSyncTable())){
			SyncSet.createPlayer(p);
		}
		String raw = (String) FPChat.getPlugin().getMySQLConnection().get("badges", "player_uuid", "=", uuid, FPChat.getPlugin().getMainConfig().getPermSyncTable());
		if(raw != null && raw.length() > 0 && raw.charAt(0) == ','){
			raw = raw.substring(1);
		}
		if(raw != null && !raw.equals("")) {
			String list[] = Util.stripLast(raw).split(",");
			for(String item : list) {
				if(!item.equals("")) {
					Integer badgeId = Integer.parseInt(item);
					p.addSyncedBadge(badgeId);
				}
			}
		}
	}

	public static Boolean syncExists(FPlayer p){
		Boolean check = false;
		if(p == null) {
			return false;
		}
		String uuid = p.getUniqueId().toString();
		Util.connect();
		if(FPChat.getPlugin().getMySQLConnection().itemExists("player_uuid", uuid, FPChat.getPlugin().getMainConfig().getPermSyncTable())){
			check = true;
		}
		return check;
	}

}
