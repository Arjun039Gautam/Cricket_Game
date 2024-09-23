package Assignment_5;

public class Team {
    String teamName;
    String[][] players = new String[20][2]; 
    int playerCount = 0; 

    public Team(String teamName) {
        this.teamName = teamName;
    }

    public void addPlayer(String playerName, String playerRole) {
        if (playerCount < 20) {
            players[playerCount][0] = playerName;
            players[playerCount][1] = playerRole;
            playerCount++;
        } else {
            System.out.println("Team is full. Cannot add more players.");
        }
    }
    
    public String getTeamName() {
        return teamName;
    }

    public String[][] getPlayers() {
        return players;
    }

    public int getPlayerCount() {
        return playerCount;
    }
}
