package com.fpghoti.fpchatx.badge;

import com.fpghoti.fpchatx.FPChat;
import com.fpghoti.fpchatx.player.FPlayer;
import com.fpghoti.fpchatx.util.Util;

public class SyncSet {

	public static void update(FPlayer p) {
		update(p, true);
	}

	public static void update(FPlayer p, boolean create){
		String uuid = p.getUniqueId().toString();
		Util.connect();
		if(create && !FPChat.getPlugin().getMySQLConnection().itemExists("player_uuid", uuid, FPChat.getPlugin().getMainConfig().getPermSyncTable())){
			createPlayer(p);
		}
		SyncGetter.syncBadges(p, false);
		String nl = "";
		String nl2 = "";
		String permstring = getBadgeString(p);
		String[] permids = permstring.split(",");
		for(String item : permids){
			if(Util.isDigit(item)) {
				int id = Integer.parseInt(item);
				if(!p.isSynced(id)){
					nl = nl + item + ",";
				}
			}
		}
		for(int id : p.syncedList()){
			String item = Integer.toString(id);
			nl2 = nl2 + item + ",";
		}
		nl = nl + nl2;
		FPChat.getPlugin().getMySQLConnection().set("badges", nl, "player_uuid", "=", uuid, FPChat.getPlugin().getMainConfig().getPermSyncTable());
		SyncGetter.syncBadges(p,false);
	}
	
	public static void revoke(FPlayer p) {
		revoke(p,true);
	}

	public static void revoke(FPlayer p, boolean create) {
		String uuid = p.getUniqueId().toString();
		Util.connect();
		if(create && !FPChat.getPlugin().getMySQLConnection().itemExists("player_uuid", uuid, FPChat.getPlugin().getMainConfig().getPermSyncTable())){
			createPlayer(p);
		}
		SyncGetter.syncBadges(p, false);
		String nl = "";
		FPChat.getPlugin().getMySQLConnection().set("badges", nl, "player_uuid", "=", uuid, FPChat.getPlugin().getMainConfig().getPermSyncTable());
		SyncGetter.syncBadges(p, false);
	}
	public static void revoke(FPlayer p, int badgeId){
		revoke(p, badgeId, true);
	}

	public static void revoke(FPlayer p, int badgeId, boolean create){
		String uuid = p.getUniqueId().toString();

		Util.connect();
		if(create && !FPChat.getPlugin().getMySQLConnection().itemExists("player_uuid", uuid, FPChat.getPlugin().getMainConfig().getPermSyncTable())){
			createPlayer(p);
		}
		SyncGetter.syncBadges(p,  false);
		String nl = "";
		String nl2 = "";
		for(String item : revokeBadgeString(p, badgeId).split(",")){
			if(Util.isDigit(item)) {
				int id = Integer.parseInt(item);
				if(p.isSynced(id)){
					nl = nl + item + ",";
				}
			}
		}
		for(int id : p.syncedList()){
			String item = Integer.toString(id);
			if(id != badgeId){
				nl2 = nl2 + item + ",";
			}
		}
		nl = nl + nl2;
		FPChat.getPlugin().getMySQLConnection().set("badges", nl, "player_uuid", "=", uuid, FPChat.getPlugin().getMainConfig().getPermSyncTable());
		SyncGetter.syncBadges(p, false);
	}

	public static String getBadgeString(FPlayer p){
		String list = "";
		for(int i = 1; i < BadgeList.badgeperm.size(); i++){
			if(p.hasPermission("fpchat.badge" + BadgeList.badgeperm.get(i))){
				String add = Integer.toString(i) + ",";
				list = list + add;
			}else if(p.getBadgeQueue().contains(i)) {
				String add = Integer.toString(i) + ",";
				list = list + add;
				p.unqueueBadge(i);
			}
		}

		return list;
	}

	public static String revokeBadgeString(FPlayer p, int badgeId){
		String list = "";
		for(int i = 1; i < BadgeList.badgeperm.size(); i++){
			if(p.hasPermission("fpchat.badge" + BadgeList.badgeperm.get(i))){
				if(i != badgeId){
					String add = Integer.toString(i) + ",";
					list = list + add;
				}
			}
		}

		return list;
	}

	public static void createPlayer(FPlayer p){
		String uuid = p.getUniqueId().toString();
		Util.connect();
		if(!FPChat.getPlugin().getMySQLConnection().itemExists("player_uuid", uuid, FPChat.getPlugin().getMainConfig().getPermSyncTable())){
			FPChat.getPlugin().getMySQLConnection().insertInto("player_uuid, badges", " '" + uuid + "', '' ", FPChat.getPlugin().getMainConfig().getPermSyncTable());
		}
	}
}
