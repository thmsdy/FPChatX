package com.fpghoti.fpchatx.badge;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;

import com.fpghoti.fpchatx.FPChat;
import com.fpghoti.fpchatx.player.FPlayer;
import com.fpghoti.fpchatx.util.Util;

public class BadgeData {

	private FPlayer player;
	private HashMap<Integer, Badge> slots;
	private BadgeList badges;
	
	public BadgeData(FPlayer player) {
		this.player = player;
		this.slots = new HashMap<Integer, Badge>();
		badges = new BadgeList();
		CompletableFuture.runAsync(() -> {
			createPlayerData();
		}).thenRunAsync(() -> {
			getPlayerData();
			savePlayerData();
		});
	}

	public FPlayer getPlayer() {
		return player;
	}

	public Badge getBadge(int slot) {
		if(slot <= 0 || !FPChat.getPlugin().getMainConfig().mySQLEnabled() || slot > FPChat.getPlugin().getMainConfig().getMaxBadgeSlots()) {
			return null;
		}
		return slots.get(slot);
	}

	public BadgeList getBadgeList() {
		return this.badges;
	}

	public void clearLoadout() {
		this.slots = new HashMap<Integer, Badge>();
		savePlayerData();
	}

	public void clearBadgeList() {
		this.badges = new BadgeList();
		savePlayerData();
	}

	/**Will equip the badge regardless of the user's permissions.**/
	public BadgeEquipResult setBadge(int slot, Badge badge) {
		if(!FPChat.getPlugin().getMainConfig().mySQLEnabled()) {
			return BadgeEquipResult.NO_SQL;
		}
		if(slot <= 0 || slot > FPChat.getPlugin().getMainConfig().getMaxBadgeSlots()) {
			return BadgeEquipResult.INVALID_SLOT;
		}
		slots.put(slot, badge);
		savePlayerData();
		return BadgeEquipResult.SUCCESS;
	}

	/**Will not equip badge if user does not have permission
	 * to use the badge or slot.**/
	public BadgeEquipResult equipBadge(int slot, Badge badge) {
		if(!hasSlotPermission(slot)) {
			return BadgeEquipResult.NO_PERMISSION_SLOT;
		}
		if(!player.hasPermission(badge.getPerm()) && !badges.containsId(badge.getId())) {
			return BadgeEquipResult.NO_PERMISSION_BADGE;
		}
		return setBadge(slot, badge);
	}

	public String getAppearanceString() {
		String appearance = "";
		if(slots.isEmpty()) {
			return "";
		}
		for(int slot = 1; slot <= FPChat.getPlugin().getMainConfig().getMaxBadgeSlots(); slot++) {
			if(hasSlotPermission(slot)) {
				Badge badge = slots.get(slot);
				if(badge != null && badge.isEnabled() && (player.hasPermission(badge.getPerm()) || badges.containsId(badge.getId()))) {
					appearance = badge.getContents() + appearance;
				}
			}
		}
		return appearance;
	}

	public String toString() { //
		int max = FPChat.getPlugin().getMainConfig().getMaxBadgeSlots();
		String val = "";
		boolean first = true;
		for(int slot = 1; slot <= max; slot++) {
			Badge badge = getBadge(slot);
			int id = 0;
			if(badge != null) {
				id = badge.getId();
			}
			if(first) {
				first = false;
				val = Integer.toString(id);
			}else {
				val = val + "," + Integer.toString(id);
			}
		}
		return val;
	}

	private void setLoadoutFromString(String str) {
		String[] array = str.split(",");
		slots = new HashMap<Integer, Badge>();
		for(int i = 0; i < array.length; i++) {
			int slot = i + 1;
			String item = array[i];
			if(Util.isDigit(item)) {
				int id = Integer.parseInt(item);
				Badge badge = Badge.getList().get(id);
				if(badge != null) {
					slots.put(slot, badge);
				}else {
					System.out.println("BADGE " + id + " NULL!"); //TODO <---------------------------
					slots.put(slot, Badge.getZero());
				}
			}else {
				slots.put(slot, Badge.getZero());
			}
		}
	}

	private boolean hasSlotPermission(int slot) {
		return player.hasPermission("fpchat.slot" + Integer.toString(slot)) || hasSlotBadge(slot);
	}

	private boolean hasSlotBadge(int slot) {
		for(Badge b : Badge.getList().getSlotUnlockBadges(slot)) {
			if(badges.containsId(b.getId())) {
				return true;
			}
		}
		return false;
	}

	private void createPlayerData() {
		createSQLLoadoutEntry();
		createSQLBadgeListEntry();
	}

	private void getPlayerData() {
		String loadoutString = getLoadoutSQLString();
		setLoadoutFromString(loadoutString);
		String badgeListString = getBadgeListSQLString();
		badges = BadgeList.fromString(badgeListString);
	}

	public void savePlayerData() {
		saveNewBadgesToSQL();
		setBadgeListSQLString(badges.toString());
		setLoadoutSQLString(toString()); //TODO Fix ToString always returning 0,0,0
	}

	private void saveNewBadgesToSQL() {
		for(Badge badge : Badge.getList()) {
			if(player.hasPermission(badge.getPerm()) && !badges.containsId(badge.getId())) {
				badges.add(badge);
			}
		}
	}

	private void createSQLLoadoutEntry(){ 
		String uuid = player.getUniqueId().toString();
		Connection connection = null;
		try {
			connection = FPChat.getPlugin().getMySQLConnection().getConnection();
			if(!FPChat.getPlugin().getMySQLConnection().itemExists("player_uuid", uuid, FPChat.getPlugin().getMainConfig().getChatFeatureTable())){
				FPChat.getPlugin().getMySQLConnection().update("INSERT INTO " + FPChat.getPlugin().getMainConfig().getChatFeatureTable() + " (player_uuid, badge_loadout) VALUES ( '" + uuid + "', '' )");
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private String getLoadoutSQLString() {
		String uuid = player.getUniqueId().toString();
		String value = null;
		Connection connection = null;
		try{
			connection = FPChat.getPlugin().getMySQLConnection().getConnection();
			ResultSet rs = FPChat.getPlugin().getMySQLConnection().query("SELECT * FROM " + FPChat.getPlugin().getMainConfig().getChatFeatureTable() + " WHERE player_uuid = '" + uuid + "';", connection);
			if (rs.next()) {
				value = (String) rs.getObject("badge_loadout");
			}
		}catch(SQLException e) {
			FPChat.getPlugin().log(Level.SEVERE, "MySQL get error: " + e.getMessage());
			e.printStackTrace();
		}finally{
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return value;
	}

	private void setLoadoutSQLString(String value) {
		String uuid = player.getUniqueId().toString();
		FPChat.getPlugin().getMySQLConnection().asyncUpdate("UPDATE " + FPChat.getPlugin().getMainConfig().getChatFeatureTable() + " SET badge_loadout = '" + value + "' WHERE player_uuid = '" + uuid + "';");
	}

	private void createSQLBadgeListEntry(){
		String uuid = player.getUniqueId().toString();
		Connection connection = null;
		try {
			connection = FPChat.getPlugin().getMySQLConnection().getConnection();
			if(!FPChat.getPlugin().getMySQLConnection().itemExists("player_uuid", uuid, FPChat.getPlugin().getMainConfig().getPermSyncTable())){
				FPChat.getPlugin().getMySQLConnection().update("INSERT INTO " + FPChat.getPlugin().getMainConfig().getPermSyncTable() + " (player_uuid, badges) VALUES ( '" + uuid + "', '' )");
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private String getBadgeListSQLString() {
		String uuid = player.getUniqueId().toString();
		String value = null;
		Connection connection = null;
		try{
			connection = FPChat.getPlugin().getMySQLConnection().getConnection();
			ResultSet rs = FPChat.getPlugin().getMySQLConnection().query("SELECT * FROM " + FPChat.getPlugin().getMainConfig().getPermSyncTable() + " WHERE player_uuid = '" + uuid + "';", connection);
			if (rs.next()) {
				value = (String) rs.getObject("badges");
			}
		}catch(SQLException e) {
			FPChat.getPlugin().log(Level.SEVERE, "MySQL get error: " + e.getMessage());
			e.printStackTrace();
		}finally{
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return value;
	}

	private void setBadgeListSQLString(String value) {
		String uuid = player.getUniqueId().toString();
		FPChat.getPlugin().getMySQLConnection().asyncUpdate("UPDATE " + FPChat.getPlugin().getMainConfig().getPermSyncTable() + " SET badges = '" + value + "' WHERE player_uuid = '" + uuid + "';");
	}

}
