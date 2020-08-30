package com.fpghoti.fpchatx.badge;

import java.util.ArrayList;
import java.util.Iterator;


public class BadgeList implements Iterable<Badge>{
	
	private ArrayList<Badge> list = new ArrayList<Badge>();
	private Badge empty = new Badge(0, "Empty", "", "", false);
	
	@Override
	public Iterator<Badge> iterator() {
		return new ArrayList<Badge>(list).iterator();
	}
	
	public void add(Badge badge) {
		if(badge.getId() < 1) {
			return;
		}
		if(overwrites(badge)) {
			remove(badge.getId());
		}
		list.add(badge);
	}
	
	public void remove(int id) {
		ArrayList<Badge> rl = new ArrayList<Badge>(list);
		for(Badge b : rl) {
			if(b.getId() == id) {
				list.remove(b);
			}
		}
	}
	
	public void remove(Badge badge) {
		remove(badge.getId());
	}
	
	public boolean containsId(int id) {
		for(Badge b : list) {
			if(b.getId() == id) {
				return true;
			}
		}
		return false;
	}
	
	public boolean overwrites(Badge badge) {
		return containsId(badge.getId());
	}
	
	public Badge get(int id) {
		if(id <= 0 || !containsId(id)) {
			return empty;
		}
		for(Badge b : list) {
			if(b.getId() == id) {
				return b;
			}
		}
		return empty;
	}
	
	public Badge getIndex(int index) {
		return list.get(index);
	}
	
	public int getLastId() {
		int last = 0;
		for(Badge b : list) {
			int id = b.getId();
			if(id > last) {
				last = id;
			}
		}
		return last;
	}
	
	public int getListSize() {
		return list.size();
	}
	
	public int size() {
		return getLastId() + 1;
	}
	
	public boolean isEmpty() {
		return list.isEmpty();
	}
	
	public BadgeList getEnabledBadges() {
		BadgeList enabled = new BadgeList();
		for(int i = 0; i < size(); i++) {
			Badge b = get(i);
			if(b.isEnabled()) {
				enabled.add(b);
			}
		}
		return enabled;
	}
	
	public BadgeList getSlotUnlockBadges(int slot) {
		BadgeList slotBadges = new BadgeList();
		for(int i = 0; i < size(); i++) {
			Badge b = get(i);
			if(b.isEnabled() && b.getSlotUnlock() == slot) {
				slotBadges.add(b);
			}
		}
		return slotBadges;
	}

}
