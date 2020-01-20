package com.fpghoti.fpchatx.badge;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.bukkit.ChatColor;

import com.fpghoti.fpchatx.player.FPlayer;

public class BadgeList {


	public static HashMap<Integer, String> badgelist = new LinkedHashMap<>();
	public static HashMap<Integer, String> badgename = new LinkedHashMap<>();
	public static HashMap<Integer, String> badgeperm = new LinkedHashMap<>();

	public static HashMap<FPlayer, Integer> pageInstance = new HashMap<>();

	
	public static void purge() {
		badgelist = new LinkedHashMap<>();
		badgename = new LinkedHashMap<>();
		badgeperm = new LinkedHashMap<>();

		pageInstance = new HashMap<>();
	}
	
	public static void badgeList(FPlayer p , int page){
		p.sendMessage("");
		p.sendMessage(ChatColor.RED + "HOW TO USE:" + ChatColor.YELLOW + " You can equip badges with " + ChatColor.DARK_AQUA + "/equip <slot number> <badge number>" + ChatColor.YELLOW +". To unequip all badges, use " + ChatColor.DARK_AQUA + "/badgesclear" + ChatColor.YELLOW + ".");
		p.sendMessage(ChatColor.AQUA + "------------------- Your Badges -------------------");
		p.sendMessage("");
		int eventnum = 0;
		//for(Integer item : badgelist.keySet()){
		for(int i = 0; i < badgelist.size(); i++){
				eventnum = eventnum + 1;
		}
		double dp = (float)eventnum / 10.0;
		int pagenum = (int)Math.ceil(dp);
		if(page <= pagenum && page > 0){
			pageInstance.put(p,0);
			int pageten = (page * 10)- 10;
			for(Integer entry : badgelist.keySet()){
				if(pageInstance.get(p) == pageten || pageInstance.get(p) == pageten + 1 || pageInstance.get(p) == pageten + 2 || pageInstance.get(p) == pageten + 3 || pageInstance.get(p) == pageten + 4 || pageInstance.get(p) == pageten + 5 || pageInstance.get(p) == pageten + 6 || pageInstance.get(p) == pageten + 7 || pageInstance.get(p) == pageten + 8 || pageInstance.get(p) == pageten + 9){
					if(entry == 0){
					}else if(p.hasPermission("fpchat.badge" + badgeperm.get(entry)) || p.isSynced(entry)){
						p.sendMessage(badgelist.get(entry) + ChatColor.GOLD + " Badge #" + entry + ": " + ChatColor.GREEN + " - " + badgename.get(entry) + ChatColor.GOLD + " - UNLOCKED");
					}else{
						p.sendMessage(badgelist.get(entry) + ChatColor.GOLD + " Badge #" + entry + ": " + ChatColor.GREEN + " - " + badgename.get(entry) + ChatColor.RED + " - LOCKED");
					}
				}
				pageInstance.put(p,(pageInstance.get(p)+1));
			}
			p.sendMessage(ChatColor.AQUA + "");
			p.sendMessage(ChatColor.YELLOW + "Page " + Integer.toString(page) + " of " + Integer.toString(pagenum));
			p.sendMessage(ChatColor.AQUA + "---------------------------------------------------");
			pageInstance.remove(p);
		}
	}
	
	public static void setupBadges(){
		
		//DO NOT CHANGE ANY OF THE BADGEID NUMBERS. THIS WILL REALLY SCREW UP PEOPLES BADGE PERMISSIONS IN GAME. I WILL SOON ADD SOMETHING FOR PEOPLE TO MORE EASILY SEE THEIR UNLOCKED BADGES
		
		//These are the designs used in game of each badge
		
		badgelist.put(0, "");
		badgelist.put(1, "&6{✮}");
		badgelist.put(2, "&b{&7❂&b}");
		badgelist.put(3, "&c[０Ｇ]");
		badgelist.put(4, "&8{&9Ⓜ&8}");
		badgelist.put(5, "&b(✌)");
		badgelist.put(6, "&3{☹}");
		badgelist.put(7, "&4<&8➳&4>");
		badgelist.put(8, "&a(รɦṁ)");
		badgelist.put(9, "&e[Ⓒ]");
		badgelist.put(10, "&8[&f☠&8]");
		badgelist.put(11, "&b{&f☃&b}");
		badgelist.put(12, "&d{&6❀&d}");
		badgelist.put(13, "&e<&a☣&e>");
		badgelist.put(14, "&5(&3☔&5)");
		badgelist.put(15, "&a{☎}");
		badgelist.put(16, "&7{&b♫&7}");
		badgelist.put(17, "&e<&6⚡&e>");
		badgelist.put(18, "&3(&c⍤&3)");
		badgelist.put(19, "&f[&c♛&f]");
		badgelist.put(20, "&b[&a✦&b]");
		badgelist.put(21, "&e[&7♜&e]");
		badgelist.put(22, "&6[&7⚒&6]");
		badgelist.put(23, "&3[&7❖&3]");
		badgelist.put(24, "&2[&7⧈&2]");
		badgelist.put(25, "&5(&d⚉&5)");
		badgelist.put(26, "&1[&4β&1]");
		badgelist.put(27, "&b{&3&lᴮᵀ&b}");
		badgelist.put(28, "&c{&b&lᴴᴮᵀ&c}");
		badgelist.put(29, "&8{&6&lᴮ&a&lᵒ&6&lᵒ&8}");
		badgelist.put(30, "&3<&eᵒᵒᶠ&3>");
		badgelist.put(31, "&b&l{&f&l✔&b&l}");
		badgelist.put(32, "&a|ＰＲ０|");
		badgelist.put(33, "&1⦅&b&lx2&r&1⦆");
		badgelist.put(34, "&a«&2&lx2&r&a»");
		badgelist.put(35, "&e⦉&6x2&e⦊");
		badgelist.put(36, "&6﴾&cⰀ&6﴿");
		badgelist.put(37, "&7﴾※﴿");
		badgelist.put(38, "&7⌈&f◉&d⩐&f◉&7⌉");
		badgelist.put(39, "&8[&7༗&8]");
		badgelist.put(40, "&8[&bⒺ➀&8]");
		badgelist.put(41, "&8[&eⒺ➁&8]");
		badgelist.put(42, "&8[&2Ⓔ➂&8]");
		badgelist.put(43, "&8[&6Ⓔ&c➃&8]");
		badgelist.put(44, "&8[&bⒺ&a➄&8]");
		badgelist.put(45, "&8[&cⒺ&b➅&8]");
		badgelist.put(46, "&5{&dᴠɪᴘ&5}");
		badgelist.put(47, "&5{&4ᴠ&eɪ&9ᴘ&a⁺&5}");
		badgelist.put(48, "&6{&bᴠ&c⁺&a⁺&6}");
		badgelist.put(49, "&3{&bᴛᴏᴘ&3}");
		badgelist.put(50, "&6{&eᴘʟᴀᴛ&6}");
		badgelist.put(51, "&8{&7ᴛɪ&8}");
		badgelist.put(52, "&c⦉&4x2&c⦊");
		badgelist.put(53, "&b⦅&1&lx3&r&b⦆");
		badgelist.put(54, "&8[&5✥&8]");
		badgelist.put(55, "&8{&bⒶ&8}");
		badgelist.put(56, "&8{&3ᴼᴾ&8}");
		badgelist.put(57, "&8{&4&l♔&8}");
		badgelist.put(58, "&7{&9☯&7}");
		badgelist.put(59, "&f⟦&6&o&l０Ｇ&8&f⟧");
		badgelist.put(60, "&6ヽ( •_)ᕗ");
		badgelist.put(61, "&c⦇&6☕&c⦈");
		badgelist.put(62, "&b&l⦍&c&lＦ&b&l⦐");
		badgelist.put(63, "&a⦇&e⧔⧕&d⦈");
		badgelist.put(64, "&2[&c❒&2]");
		badgelist.put(65, "&4{&e▼&4}");
		badgelist.put(66, "&e{&d☞&e}");
		badgelist.put(67, "&f<&7✄&f>");
		badgelist.put(68, "&5⸨&d◕‿‿◕&5⸩");
		badgelist.put(69, "&d⌇&4❤&d⌇");
		badgelist.put(70, "&6ʕ•ᴥ•ʔ");
		badgelist.put(71, "&f<&9☆彡&f>");
		badgelist.put(72, "&a(&5✿&a}");
		badgelist.put(73, "&a&k!&e&k!&c&k!&b&k!&c&k!&e&k!&a&k!&r");
		badgelist.put(74, "&a[&f[&6Ⱑ&f]&a]");
		badgelist.put(75, "&4]&8⸮&4[");
		badgelist.put(76, "&0【&7&l〆&r&0】");
		badgelist.put(77, "&9[&2⸙&9]");
		badgelist.put(78, "&8⸠⨷⸡");
		badgelist.put(79, "&a⦑&4⚐&9⚑&a⦒");
		badgelist.put(80, "p");
		
		// These are the badge names listed in the badge menu.
		// See notes here for possible badge uses
		
		badgename.put(0, "Empty"); //Represents an empty badge slot. Equipping removes whatever badge is in that slot.
		badgename.put(1, "Gold Star"); //We have given these to people who win contests or in game events we have
		badgename.put(2, "Silver Star"); //We've used this as a voting reward in the past
		badgename.put(3, "Original Player"); //I've tried to reserve this one but people started giving it out, but we can still give this to old donors.
		badgename.put(4, "Staff"); //Staff only
		badgename.put(5, "Peace"); //Sell in-game or on the donor shop
		badgename.put(6, "Boo-hoo"); //Obtainable by easter egg in one of the spawn vaults
		badgename.put(7, "Hunter"); //Sell in-game or on the donor shop
		badgename.put(8, "Shmeckle"); //Sell in-game or on the donor shop
		badgename.put(9, "Copyright"); //Sell in-game or on the donor shop
		badgename.put(10, "Skull"); //Sell in-game or on the donor shop
		badgename.put(11, "Snowman"); //Sell in-game or on the donor shop
		badgename.put(12, "Flower"); //Sell in-game or on the donor shop
		badgename.put(13, "Radioactive"); //Sell in-game or on the donor shop
		badgename.put(14, "Rainy"); //Sell in-game or on the donor shop
		badgename.put(15, "Telephone"); //Sell in-game or on the donor shop
		badgename.put(16, "Music"); //Sell in-game or on the donor shop
		badgename.put(17, "Lightning"); //Sell in-game or on the donor shop
		badgename.put(18, "Wow"); //Sell in-game or on the donor shop
		badgename.put(19, "Monarch"); //Give to old creative donor with Monarch
		badgename.put(20, "Baron"); //Give to old creative donor with Baron
		badgename.put(21, "Artificer"); // Give to old creative donor with Artificer
		badgename.put(22, "Craftsman"); // Give to old creative donor with Craftsman
		badgename.put(23, "Journeyman"); // Give to old creative donor with Journeyman
		badgename.put(24, "Carpenter"); // Give to old creative donor with Carpenter
		badgename.put(25, "Pig"); //Sell in-game or on the donor shop
		badgename.put(26, "Beta"); // Reserve for contests or special gift from staff
		badgename.put(27, "Build Team"); // Reserve for Build team
		badgename.put(28, "Head Builder"); // May not have any use anymore, but not really something for players
		badgename.put(29, "Boo"); //Sell in-game or on the donor shop (OR give to players at Halloween)
		badgename.put(30, "OOF!"); //Obtainable easteregg
		badgename.put(31, "Trusted / Verified Player"); //Give to trusted/ex staff/ build team
		badgename.put(32, "Professional Player"); //Can make a reward for parkour. We've associated this badge with an achievement in the past
		badgename.put(33, "x2 Slots (Blue)"); // Anyone with this badge can equip a badge to a second slot
		badgename.put(34, "x2 Slots (Green)"); // Same as #33 but green
		badgename.put(35, "x2 Slots (Yellow)"); // same as #33 and #34 but yellow
		badgename.put(36, "Gilded Crest"); //I've typically only given this one to staff
		badgename.put(37, "Silver Crest"); //Sell in-game or on the donor shop
		badgename.put(38, "Derp"); //Sell in-game or on the donor shop
		badgename.put(39, "Hawk-Eye");//Sell in-game or on the donor shop
		badgename.put(40, "Expert Level 1"); // Survival can make unlockable with rank progression
		badgename.put(41, "Expert Level 2"); // Survival can make unlockable with rank progression
		badgename.put(42, "Expert Level 3"); // Survival can make unlockable with rank progression
		badgename.put(43, "Expert Level 4"); // Survival can make unlockable with rank progression
		badgename.put(44, "Expert Level 5"); // Survival can make unlockable with rank progression
		badgename.put(45, "Expert Level 6"); // Survival can make unlockable with rank progression
		badgename.put(46, "VIP"); // Give to old main donors
		badgename.put(47, "VIP+"); // Give to old main donors
		badgename.put(48, "VIP++"); // Give to old main donors
		badgename.put(49, "Topaz"); // Give to old main donors
		badgename.put(50, "Platinum"); // Give to old main donors
		badgename.put(51, "Titanium"); // Give to old main donors
		badgename.put(52, "x2 Slots (Red)"); // Same as #33, #34, and #35 but red
		badgename.put(53, "x3 Slots (Blue)"); // Anyone with this badge can equip 3 badges
		badgename.put(54, "Architect"); //Can make unlockable on creative
		badgename.put(55, "Admin"); // Reserved for staff
		badgename.put(56, "Operator"); // Reserved for staff
		badgename.put(57, "Owner"); // I wonder who this is for
		badgename.put(58, "PvP Legend"); // Can give to old PvP donors and maybe even kitpvp donors
		badgename.put(59, "Gold OG"); // Can give to old donors who had some of the higher ranks
		badgename.put(60, "Dab"); //Sell in-game or on the donor shop
		badgename.put(61, "Coffee"); //Sell in-game or on the donor shop
		badgename.put(62, "F"); //Sell in-game or on the donor shop
		badgename.put(63, "Candy"); //Sell in-game or on the donor shop
		badgename.put(64, "Box"); //Sell in-game or on the donor shop
		badgename.put(65, "Pizza"); //Sell in-game or on the donor shop
		badgename.put(66, "Pointer"); //Sell in-game or on the donor shop
		badgename.put(67, "Scissors"); //Sell in-game or on the donor shop
		badgename.put(68, "Grin");//Sell in-game or on the donor shop
		badgename.put(69, "Heart"); //Can give away for Valentine's day
		badgename.put(70, "Bear");//Sell in-game or on the donor shop
		badgename.put(71, "Comet");//Sell in-game or on the donor shop
		badgename.put(72, "Tropical Flower");//Sell in-game or on the donor shop
		badgename.put(73, "Magic");//Sell in-game or on the donor shop
		badgename.put(74, "Sealed");//Sell in-game or on the donor shop
		badgename.put(75, "Backwards");//Sell in-game or on the donor shop
		badgename.put(76, "Dagger");//Sell in-game or on the donor shop
		badgename.put(77, "Leaf");//Sell in-game or on the donor shop
		badgename.put(78, "Starship");//Sell in-game or on the donor shop
		badgename.put(79, "Flags");//Sell in-game or on the donor shop
		badgename.put(80, "Stunned");//Sell in-game or on the donor shop
		
		
		// BADGE PERMS
		// These are the specific perm for each badge. They go like this:
		// fpchat.badge.<badgeperm>   I.E fpchat.badge.goldstar
		// See notes above for badges use ideas
		
		badgeperm.put(0, "");
		badgeperm.put(1, ".goldstar");
		badgeperm.put(2, ".silverstar");
		badgeperm.put(3, ".og");
		badgeperm.put(4, ".staff");
		badgeperm.put(5, ".peace");
		badgeperm.put(6, ".boohoo");
		badgeperm.put(7, ".hunter");
		badgeperm.put(8, ".shmeckle");
		badgeperm.put(9, ".copyright");
		badgeperm.put(10, ".skull");
		badgeperm.put(11, ".snowman");
		badgeperm.put(12, ".flower");
		badgeperm.put(13, ".radioactive");
		badgeperm.put(14, ".rainy");
		badgeperm.put(15, ".telephone");
		badgeperm.put(16, ".music");
		badgeperm.put(17, ".lightning");
		badgeperm.put(18, ".wow");
		badgeperm.put(19, ".monarch");
		badgeperm.put(20, ".baron");
		badgeperm.put(21, ".artificer");
		badgeperm.put(22, ".craftsman");
		badgeperm.put(23, ".journeyman");
		badgeperm.put(24, ".carpenter");
		badgeperm.put(25, ".pig");
		badgeperm.put(26, ".fpn");
		badgeperm.put(27, ".bt");
		badgeperm.put(28, ".hbt");
		badgeperm.put(29, ".boo");
		badgeperm.put(30, ".oof");
		badgeperm.put(31, ".verified");
		badgeperm.put(32, ".pro");
		badgeperm.put(33, ".2blue");
		badgeperm.put(34, ".2green");
		badgeperm.put(35, ".2yellow");
		badgeperm.put(36, ".gildedcrest");
		badgeperm.put(37, ".silvercrest");
		badgeperm.put(38, ".derp");
		badgeperm.put(39, ".hawkeye");
		badgeperm.put(40, ".expert1");
		badgeperm.put(41, ".expert2");
		badgeperm.put(42, ".expert3");
		badgeperm.put(43, ".expert4");
		badgeperm.put(44, ".expert5");
		badgeperm.put(45, ".expert6");
		badgeperm.put(46, ".vip");
		badgeperm.put(47, ".vip+");
		badgeperm.put(48, ".vip++");
		badgeperm.put(49, ".topaz");
		badgeperm.put(50, ".platinum");
		badgeperm.put(51, ".titanium");
		badgeperm.put(52, ".2red");
		badgeperm.put(53, ".3blue");
		badgeperm.put(54, ".architect");
		badgeperm.put(55, ".admin");
		badgeperm.put(56, ".operator");
		badgeperm.put(57, ".owner");
		badgeperm.put(58, ".pvplegend");
		badgeperm.put(59, ".goldog");
		badgeperm.put(60, ".dab");
		badgeperm.put(61, ".coffee");
		badgeperm.put(62, ".f");
		badgeperm.put(63, ".candy");
		badgeperm.put(64, ".box");
		badgeperm.put(65, ".pizza");
		badgeperm.put(66, ".pointer");
		badgeperm.put(67, ".scissors");
		badgeperm.put(68, ".grin");
		badgeperm.put(69, ".heart");
		badgeperm.put(70, ".bear");
		badgeperm.put(71, ".comet");
		badgeperm.put(72, ".tropicalflower");
		badgeperm.put(73, ".magic");
		badgeperm.put(74, ".sealed");
		badgeperm.put(75, ".backwards");
		badgeperm.put(76, ".dagger");
		badgeperm.put(77, ".leaf");
		badgeperm.put(78, ".starship");
		badgeperm.put(79, ".flags");
		badgeperm.put(80, ".stunned");
	}
	
	
}
