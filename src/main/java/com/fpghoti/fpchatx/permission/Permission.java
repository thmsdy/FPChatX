package com.fpghoti.fpchatx.permission;

import com.fpghoti.fpchatx.player.FPlayer;

public class Permission {
	
	public static String noPerm = "You lack the permission required to perform this action.";

	public static boolean canUseColor(FPlayer p) {
		return p.hasPermission("fpchat.colorcodes");
	}
	
	public static boolean canUseColor(String playername) {
		if(FPlayer.getPlayer(playername) != null) {
			FPlayer p = FPlayer.getPlayer(playername);
			return canUseColor(p);
		}
		return false;
	}
	
	public static boolean canShoutColor(FPlayer p) {
		return p.hasPermission("fpchat.shoutcolor");
	}
	
	public static boolean canShoutColor(String playername) {
		if(FPlayer.getPlayer(playername) != null) {
			FPlayer p = FPlayer.getPlayer(playername);
			return canShoutColor(p);
		}
		return false;
	}
	
	public static boolean canPMColor(FPlayer p) {
		return p.hasPermission("fpchat.pmcolor");
	}
	
	public static boolean canPMColor(String playername) {
		if(FPlayer.getPlayer(playername) != null) {
			FPlayer p = FPlayer.getPlayer(playername);
			return canPMColor(p);
		}
		return false;
	}
	
	public static boolean canBubbleCode(FPlayer p) {
		return p.hasPermission("fpchat.bubblecode");
	}
	
	public static boolean canBubbleCode(String playername) {
		if(FPlayer.getPlayer(playername) != null) {
			FPlayer p = FPlayer.getPlayer(playername);
			return canBubbleCode(p);
		}
		return false;
	}
	
	public static boolean canSpy(FPlayer p) {
		return p.hasPermission("fpchat.spy");
	}
	
	public static boolean canSpy(String playername) {
		if(FPlayer.getPlayer(playername) != null) {
			FPlayer p = FPlayer.getPlayer(playername);
			return canSpy(p);
		}
		return false;
	}
	
	public static boolean isAdmin(FPlayer p) {
		return p.hasPermission("fpchat.admin");
	}
	
	public static boolean isAdmin(String playername) {
		if(FPlayer.getPlayer(playername) != null) {
			FPlayer p = FPlayer.getPlayer(playername);
			return isAdmin(p);
		}
		return false;
	}
	
	public static boolean canBan(FPlayer p) {
		return p.hasPermission("fpchat.ban");
	}
	
	public static boolean canMakeChannel(FPlayer p) {
		return p.hasPermission("fpchat.makechannel");
	}
	
	public static boolean canMakeChannel(String playername) {
		if(FPlayer.getPlayer(playername) != null) {
			FPlayer p = FPlayer.getPlayer(playername);
			return canMakeChannel(p);
		}
		return false;
	}
	
	public static boolean canMakeTempChannel(FPlayer p) {
		return p.hasPermission("fpchat.maketempchannel");
	}
	
	public static boolean canMakeTempChannel(String playername) {
		if(FPlayer.getPlayer(playername) != null) {
			FPlayer p = FPlayer.getPlayer(playername);
			return canMakeTempChannel(p);
		}
		return false;
	}
	
	public static boolean canAlertMakeChannel(FPlayer p) {
		return p.hasPermission("fpchat.alertmakechannel");
	}
	
	public static boolean canAlertMakeChannel(String playername) {
		if(FPlayer.getPlayer(playername) != null) {
			FPlayer p = FPlayer.getPlayer(playername);
			return canAlertMakeChannel(p);
		}
		return false;
	}
	
	public static boolean canIgnore(FPlayer p) {
		return p.hasPermission("fpchat.ignore");
	}
	
	public static boolean canIgnore(String playername) {
		if(FPlayer.getPlayer(playername) != null) {
			FPlayer p = FPlayer.getPlayer(playername);
			return canIgnore(p);
		}
		return false;
	}
	
	public static boolean isStaff(FPlayer p) {
		return p.hasPermission("fpchat.staff");
	}
	
	public static boolean isStaff(String playername) {
		if(FPlayer.getPlayer(playername) != null) {
			FPlayer p = FPlayer.getPlayer(playername);
			return isStaff(p);
		}
		return false;
	}
	
	public static boolean canKick(FPlayer p) {
		return p.hasPermission("fpchat.kick");
	}
	
	public static boolean canKick(String playername) {
		if(FPlayer.getPlayer(playername) != null) {
			FPlayer p = FPlayer.getPlayer(playername);
			return canKick(p);
		}
		return false;
	}
	
	public static boolean canMute(FPlayer p) {
		return p.hasPermission("fpchat.mute");
	}
	
	public static boolean canMute(String playername) {
		if(FPlayer.getPlayer(playername) != null) {
			FPlayer p = FPlayer.getPlayer(playername);
			return canMute(p);
		}
		return false;
	}
	
	public static boolean canHush(FPlayer p) {
		return p.hasPermission("fpchat.hush");
	}
	
	public static boolean canHush(String playername) {
		if(FPlayer.getPlayer(playername) != null) {
			FPlayer p = FPlayer.getPlayer(playername);
			return canHush(p);
		}
		return false;
	}
	
	public static boolean canShout(FPlayer p) {
		return p.hasPermission("fpchat.shout");
	}
	
	public static boolean canShout(String playername) {
		if(FPlayer.getPlayer(playername) != null) {
			FPlayer p = FPlayer.getPlayer(playername);
			return canShout(p);
		}
		return false;
	}
	
	public static boolean canToggleShout(FPlayer p) {
		return p.hasPermission("fpchat.toggleshout");
	}
	
	public static boolean canToggleShout(String playername) {
		if(FPlayer.getPlayer(playername) != null) {
			FPlayer p = FPlayer.getPlayer(playername);
			return canToggleShout(p);
		}
		return false;
	}
	
	
	public static boolean isDistinguished(FPlayer p) {
		return p.hasPermission("fpchat.distinguished");
	}
	
	public static boolean isDistinguished(String playername) {
		if(FPlayer.getPlayer(playername) != null) {
			FPlayer p = FPlayer.getPlayer(playername);
			return isDistinguished(p);
		}
		return false;
	}
	
	public static boolean canDeleteChannel(FPlayer p) {
		return p.hasPermission("fpchat.deletechannel");
	}
	
	public static boolean canDeleteChannel(String playername) {
		if(FPlayer.getPlayer(playername) != null) {
			FPlayer p = FPlayer.getPlayer(playername);
			return canDeleteChannel(p);
		}
		return false;
	}
	
	public static boolean canPrivateMessage(FPlayer p) {
		return p.hasPermission("fpchat.privatemessage");
	}
	
	public static boolean canPrivateMessage(String playername) {
		if(FPlayer.getPlayer(playername) != null) {
			FPlayer p = FPlayer.getPlayer(playername);
			return canPrivateMessage(p);
		}
		return false;
	}
	
}
