package dhl.leagueModel;

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
    DEFENCE {
        public String toString() {
            return "defence";
        }
    }

}
