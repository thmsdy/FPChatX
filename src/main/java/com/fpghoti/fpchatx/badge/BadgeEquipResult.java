package com.fpghoti.fpchatx.badge;

public enum BadgeEquipResult {
	
	SUCCESS{
        @Override
        public String toString() {
            return "Success";
        }
    },
	INVALID_SLOT{
        @Override
        public String toString() {
            return "Invalid slot";
        }
    },
	NO_SQL{
        @Override
        public String toString() {
            return "SQL is not enabled";
        }
    },
	NO_PERMISSION_BADGE{
        @Override
        public String toString() {
            return "User does not have permission for badge";
        }
    },
	NO_PERMISSION_SLOT{
        @Override
        public String toString() {
            return "User does not have permission for slot";
        }
    };
	
}
