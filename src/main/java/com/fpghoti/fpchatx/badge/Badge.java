package com.fpghoti.fpchatx.badge;

import com.fpghoti.fpchatx.FPChat;
import com.fpghoti.fpchatx.config.BadgeConfig;

public class Badge {
	
	private static BadgeList badges;
	private static BadgeConfig bconfig = null;
	
	public static void loadBadges() {
		 badges = new BadgeList();
		 bconfig = null;
		 getBadgeConfig().loadBadges(badges);
	}
	
	public static BadgeConfig getBadgeConfig() {
		if(bconfig == null) {
			bconfig = new BadgeConfig(FPChat.getPlugin());
		}
		return bconfig;
	}

	public static BadgeList getList() {
		return badges;
	}
	
	private int id;
	private String name;
	private String contents;
	private String perm;
	private boolean enabled;
	private int slotUnlock;
	
	public Badge(int id, String name, String contents, String perm, boolean enabled) {
		this(id, name, contents, perm, enabled, -1);
	}
	
	public Badge(int id, String name, String contents, String perm, boolean enabled, int slotUnlock) {
		this.id = id;
		this.name = name;
		this.contents = contents.replace("&", "§");
		this.perm = perm;
		this.enabled = enabled;
		this.slotUnlock = slotUnlock;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getContents() {
		return this.contents;
	}
	
	public String getPerm() {
		return "fpchat.badge." + perm;
	}
	
	public String getRawPerm() {
		return perm;
	}
	
	public boolean unlocksSlot(int slot) {
		return slot == slotUnlock;
	}
	
	public int getSlotUnlock() {
		return slotUnlock;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean value) {
		enabled = value;
	}

}
