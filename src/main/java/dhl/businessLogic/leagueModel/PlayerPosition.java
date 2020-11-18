package dhl.businessLogic.leagueModel;

public enum PlayerPosition {
    GOALIE {
        public String toString() {
            return "goalie";
        }
    },
    FORWARD {
        public String toString() {
            return "forward";
        }
    },
    DEFENSE {
        public String toString() {
            return "defense";
        }
    }

}
