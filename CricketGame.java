/*
 * This Java program implements a simple cricket game simulation allowing users to create teams,
 * select players, and simulate a match with scoring and innings.
 * Owner : Arjun Gautam
 * Date : 17/09/24
 */
package Assignment_5;

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class CricketGame {
    public static Constants constant = new Constants();
    private static Team[] teams = new Team[constant.TEAM_SIZE];
    private static int teamCount = 2;
    public static Scanner input = new Scanner(System.in);

    public String[] addPlayer() {
        System.out.println(constant.NAME);
        String playerName = input.nextLine();
        System.out.println(constant.PROMPT_SPECIFICATION);
        String playerRole = input.nextLine();
        return new String[]{playerName, playerRole};
    }

    public CricketGame() {
        teams[0] = new Team(constant.STATIC_TEAM_1);
        for (String player : ExistTeam.teamPlayers[0]) {
            String[] details = player.split(" - ");
            teams[0].addPlayer(details[0], details[1]);
        }

        teams[1] = new Team(constant.STATIC_TEAM_2);
        for (String player : ExistTeam.teamPlayers[1]) {
            String[] details = player.split(" - ");
            teams[1].addPlayer(details[0], details[1]);
        }
        
    }

    //Main method
    public static void main(String[] args) {
        File welcome = new File("Welcome.txt");
        try {
            Scanner sc = new Scanner(welcome);
            while(sc.hasNextLine()){
                String line = sc.nextLine();
                System.out.println(line);
            }
            sc.close();
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }
        CricketGame game = new CricketGame();
        boolean isQuit = false;
        while (!isQuit) {
            System.out.println();
            System.out.println(constant.MENU_OPTIONS);
            System.out.print(constant.PROMPT_OPERATION);
            String operation = input.nextLine();
            switch (operation) {
                case "1":
                    game.viewTeams();
                    break;
                case "2":
                    game.createTeam();
                    break;
                case "3":
                    game.startMatch();
                    break;
                case "4":
                    isQuit = true;
                    break;
                default:
                    System.out.println(constant.INVALID_OPTION);
                    break;
            }
        }
        input.close();
    }

    //This method view the existing teams
    public void viewTeams() {
        System.out.println("1. " + constant.STATIC_TEAM_1);
        System.out.println(constant.PLAYERS);
        for (int i = 0; i < ExistTeam.teamPlayers[0].length; i++) {
            System.out.println((i + 1) + ". " + ExistTeam.teamPlayers[0][i]);
        }
    
        System.out.println("\n2. " + constant.STATIC_TEAM_2);
        System.out.println(constant.PLAYERS);
        for (int i = 0; i < ExistTeam.teamPlayers[1].length; i++) {
            System.out.println((i + 1) + ". " + ExistTeam.teamPlayers[1][i]);
        }

        for (int teamIndex = 2; teamIndex < teamCount; teamIndex++) {
            Team team = teams[teamIndex];
            System.out.println("\nTeam " + (teamIndex + 1) + ": " + team.getTeamName());
            if (team.getPlayerCount() == 0) {
                System.out.println(constant.NO_PLAYERS);
            } else {
                System.out.println(constant.PLAYERS);
                for (int j = 0; j < team.getPlayerCount(); j++) {
                    System.out.println((j + 1) + ". " + team.getPlayers()[j][0] + " - " + team.getPlayers()[j][1]);
                }
            }
        }
    }

    // Method to create new teams
    public void createTeam() {
        if (teamCount >= 10) {
            System.out.println(constant.TEAM_MAX_REACHED);
            return;
        }
        System.out.print(constant.PROMPT_TEAM_NAME);
        String teamName = input.nextLine();
    
        Team newTeam = new Team(teamName);
    
        System.out.println(constant.ATLEAST_11_PLAYERS);
    
        while (newTeam.getPlayerCount() < 11) {
            System.out.println(constant.PROMPT_ADD_PLAYER + (newTeam.getPlayerCount() + 1) + ":");
            String[] playerDetails = addPlayer();
            newTeam.addPlayer(playerDetails[0], playerDetails[1]);
    
            if (newTeam.getPlayerCount() >= 11) {
                System.out.print(constant.PROMPT_ANOTHER_PLAYER);
                String response = input.nextLine();
                if (response.equalsIgnoreCase("no")) {
                    break;  
                }
            }
        }
        teams[teamCount] = newTeam;  
        teamCount++;
        System.out.println("Team " + newTeam.getTeamName() + constant.TEAM_CREATED);
    }
    
    //Method to add players in new team
    public String[][] addPlayers() {
        String[][] players = new String[11][2]; 
        int playerCount = 0;
    
        System.out.println(constant.ATLEAST_11_PLAYERS);
    
        while (playerCount < 11) {
            System.out.println(constant.PROMPT_ADD_PLAYER + (playerCount + 1) + ":");
            
            System.out.print(constant.PROMPT_NAME);
            String playerName = input.nextLine();
            
            System.out.print(constant.PROMPT_SPECIFICATION);
            String playerRole = input.nextLine();
            
            players[playerCount][0] = playerName;  
            players[playerCount][1] = playerRole;  
            playerCount++;
    
            if (playerCount >= 11) {
                System.out.print(constant.PROMPT_ANOTHER_PLAYER);
                String response = input.nextLine();
                
                if (response.equalsIgnoreCase("no")) {
                    break;
                } else {
                    players = expandPlayerArray(players);
                }
            }
        }
        return players;
    }
    
    //Method to expand player in team if want more than 11 players
    private String[][] expandPlayerArray(String[][] original) {
        String[][] newArray = new String[original.length + 1][2];  
        for (int i = 0; i < original.length; i++) {
            newArray[i][0] = original[i][0];
            newArray[i][1] = original[i][1];
        }
        return newArray;
    }
    
    //Method for start match
    public void startMatch() {
        File start = new File("Start Match.txt");
        try {
            Scanner sc = new Scanner(start);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                System.out.println(line);
            }
            sc.close();
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }
    
        viewTeams();
    
        // Loop until a valid team1 is selected
        int team1Index;
        do {
            System.out.print(constant.PROMPT_SELECT_TEAM_1);
            team1Index = Integer.parseInt(input.nextLine()) - 1;
            if (team1Index < 0 || team1Index >= teamCount || teams[team1Index] == null) {
                System.out.println(constant.INVALID_TEAM_1);
            }
        } while (team1Index < 0 || team1Index >= teamCount || teams[team1Index] == null);
    
        Team team1 = teams[team1Index];
    
        // Loop until a valid team2 is selected (and not the same as team1)
        int team2Index;
        Team team2;
        do {
            System.out.print(constant.PROMPT_SELECT_TEAM_2);
            team2Index = Integer.parseInt(input.nextLine()) - 1;
            if (team2Index == team1Index) {
                System.out.println(constant.INVALID_TEAM_2);
            } else if (team2Index < 0 || team2Index >= teamCount || teams[team2Index] == null) {
                System.out.println(constant.INVALID_INDEX_TEAM_2);
            }
        } while (team2Index == team1Index || team2Index < 0 || team2Index >= teamCount || teams[team2Index] == null);
    
        team2 = teams[team2Index];
    
        System.out.println("Team " + team1.getTeamName() + " vs " + team2.getTeamName());
    
        int totalOvers = getTotalOversForMatch();
    
        String[][] activePlayersTeam1 = selectPlayersForMatch(team1);
        String[][] activePlayersTeam2 = selectPlayersForMatch(team2);
    
        System.out.println(constant.CHOOSE + "(1 for " + team1.getTeamName() + ", 2 for " + team2.getTeamName() + "): ");
        int battingTeamIndex = Integer.parseInt(input.nextLine());
    
        Team battingTeam;
        Team bowlingTeam;
        String[][] activePlayersBatting;
        String[][] activePlayersBowling;
    
        if (battingTeamIndex == 1) {
            battingTeam = team1;
            bowlingTeam = team2;
            activePlayersBatting = activePlayersTeam1;
            activePlayersBowling = activePlayersTeam2;
        } else {
            battingTeam = team2;
            bowlingTeam = team1;
            activePlayersBatting = activePlayersTeam2;
            activePlayersBowling = activePlayersTeam1;
        }
    
        System.out.println("Innings 1: " + battingTeam.getTeamName() + " batting.");
        int team1Score = simulateInnings(battingTeam, activePlayersBatting, totalOvers);
    
        System.out.println("Innings 2: " + bowlingTeam.getTeamName() + " batting.");
        int team2Score = simulateInnings(bowlingTeam, activePlayersBowling, totalOvers);
    
        // Display results
        System.out.println("Team " + team1.getTeamName() + " scored: " + team1Score);
        System.out.println("Team " + team2.getTeamName() + " scored: " + team2Score);
    
        if (team1Score > team2Score) {
            System.out.println("Team " + team1.getTeamName() + " wins!");
        } else if (team1Score < team2Score) {
            System.out.println("Team " + team2.getTeamName() + " wins!");
        } else {
            System.out.println(constant.TIE);
        }
    }
    
    // Method to get total overs for innings
    private int getTotalOversForMatch() {
        int overs = 0;
        while (overs <= 0) {
            System.out.print(constant.PROMPT_TOTAL_OVERS);
            try {
                overs = Integer.parseInt(input.nextLine());
                if (overs <= 0) {
                    System.out.println(constant.ENTER_POSITIVE_INTEGER);
                }
            } catch (NumberFormatException e) {
                System.out.println(constant.INVALID_INPUT);
            }
        }
        return overs;
    }
    
    
    //Method for innings
    public int simulateInnings(Team team, String[][] activePlayers, int totalOvers) {
        int totalScore = 0;
        int wickets = 0;
        int balls = 0;
        int totalBalls = totalOvers * 6;
    
        int[] playerScores = new int[activePlayers.length];
        boolean[] isOut = new boolean[activePlayers.length];
        
        int strikerIndex = 0;  
        int nonStrikerIndex = 1;
        int currentPlayerIndex = 2;
    
        System.out.println(constant.BATTING_FOR + team.getTeamName() + ":");
    
        while (wickets < 10 && balls < totalBalls) {
            String striker = activePlayers[strikerIndex][0];
            String nonStriker = activePlayers[nonStrikerIndex][0];
    
            System.out.println(striker + constant.ON_STRIKE + nonStriker + constant.NON_STARIKE);
    
            for (int overBall = 1; overBall <= 6 && wickets < 10 && balls < totalBalls; overBall++) {
                System.out.println(constant.PROMPT_BALL + overBall + constant.ENTER_INPUT_ON_BALL);
                String ballOutcome = input.nextLine();
    
                switch (ballOutcome) {
                    case "w":
                        System.out.println(constant.WIDE_BALL);
                        totalScore += 1;
                        int runsOnWide = -1; 
                        while (true) { 
                            System.out.print(constant.WIDE_BALL_RUN);
                            String extraRuns = input.nextLine();
                            try {
                                runsOnWide = Integer.parseInt(extraRuns);
                                
                                if (runsOnWide >= 0 && runsOnWide <= 4) {
                                    totalScore += runsOnWide;
                                    System.out.println(activePlayers[strikerIndex][0] + " scored " + runsOnWide + constant.RUNS_WIDE); 
                                    break; 
                                } else {
                                    System.out.println(constant.INVALID_RUN_WIDE);
                                }
                            } catch (NumberFormatException e) {
                                System.out.println(constant.INVALID_INPUT);
                            }
                        }
                        overBall--;
                        break;
                    case "n":
                        System.out.println(constant.NO_BALL);
                        totalScore += 1;
                        int runsOnNoBall = -1; 
                        while (true) { 
                            System.out.print(constant.NO_BALL_RUN);
                            String extraRuns = input.nextLine();
                            try {
                                runsOnNoBall = Integer.parseInt(extraRuns);
                                
                                if (runsOnNoBall >= 0 && runsOnNoBall <= 6 && runsOnNoBall != 5) {
                                    totalScore += runsOnNoBall;
                                    System.out.println(activePlayers[strikerIndex][0] + " scored " + runsOnNoBall + constant.RUNS_NO_BALL);
                                    overBall--; 
                                    break; 
                                } else {
                                    System.out.println(constant.INVALID_RUNS);
                                }
                            } catch (NumberFormatException e) {
                                System.out.println(constant.INVALID_INPUT);
                            }
                        }
                        break;
                    case "o":
                        System.out.println(constant.OUT);
                        wickets++;
                        isOut[strikerIndex] = true;  // Mark this player as out
                        if (currentPlayerIndex < 11) {
                            strikerIndex = currentPlayerIndex++;
                            System.out.println(constant.NEXT_PLAYER + activePlayers[strikerIndex][0]);
                        }
                        break;
                    default:
                        try {
                            int runs = Integer.parseInt(ballOutcome);
                            if (runs >= 0 && runs <= 6 && runs != 5) {
                                System.out.println("scored " + runs + " runs.");
                                totalScore += runs;
                                playerScores[strikerIndex] += runs;
    
                                if (runs % 2 != 0) {
                                    int temp = strikerIndex;
                                    strikerIndex = nonStrikerIndex;
                                    nonStrikerIndex = temp;
                                    System.out.println("New striker: " + activePlayers[strikerIndex][0] + ", Non-striker: " + activePlayers[nonStrikerIndex][0]);
                                }
                            } else {
                                System.out.println(constant.INVALID_RUNS);
                                overBall--;
                            }
                        } catch (NumberFormatException e) {
                            System.out.println(constant.INVALID_INPUT_NORMAL);
                            overBall--; 
                        }
                        break;
                }
    
                balls++;
            }
    
            System.out.println(constant.END_OVER);
            int temp = strikerIndex;
            strikerIndex = nonStrikerIndex;
            nonStrikerIndex = temp;
            System.out.println("New striker: " + activePlayers[strikerIndex][0] + ", Non-striker: " + activePlayers[nonStrikerIndex][0]);
        }
    
        System.out.println(constant.INNINGS_COMPLETE + team.getTeamName() + " scored: " + totalScore);
        printScorecard(team, activePlayers, playerScores, isOut, totalScore, wickets);
        return totalScore;
    }
    
    //Method to show scorecard
    public void printScorecard(Team team, String[][] activePlayers, int[] playerScores, boolean[] isOut, int totalScore, int wickets) {
        System.out.println(constant.SCORECARD_HEADER + team.getTeamName() + ":");
        System.out.println(constant.SCORECARD_SEPARATOR);
        System.out.printf(constant.PLAYER_COLUMN, "Player", "Runs", "Status");
        System.out.println(constant.SCORECARD_SEPARATOR);
    
        for (int i = 0; i < activePlayers.length; i++) {
            String playerName = activePlayers[i][0];
            int playerScore = playerScores[i];
            String status;
    
            // Determine the status of the player based on the isOut array
            if (isOut[i]) {
                status = "Out";
            } else {
                status = "Not Out";
            }
    
            // Print player name, score, and status
            System.out.printf(constant.PLAYER_COLUMN, playerName, playerScore, status);
        }
    
        System.out.println(constant.SCORECARD_SEPARATOR);
        System.out.printf(constant.TOTAL_SCORE, totalScore, wickets);
        System.out.println(constant.SCORECARD_SEPARATOR + "\n");
    }
    
    //Method to select active player for match
    public String[][] selectPlayersForMatch(Team team) {
        String[][] activePlayers = new String[11][2];
        int[] selectedPlayers = new int[11];
        int count = 0;
    
        System.out.println(constant.PROMPT_SELECT_PLAYER_MATCH + team.getTeamName());
    
        while (count < 11) {
            int playerIndex = -1; 
            boolean alreadySelected;
    
            do {
                System.out.print(constant.PROMPT_SELECT_PLAYER + (count + 1) + constant.ENTER_PLAYER);
                try {
                    playerIndex = Integer.parseInt(input.nextLine()) - 1;
    
                    if (playerIndex < 0 || playerIndex >= team.getPlayerCount()) {
                        System.out.println(constant.INVALID_PLAYER + team.getPlayerCount() + ".");
                        alreadySelected = true;
                        continue;
                    }
                    alreadySelected = false;
    
                    for (int j = 0; j < count; j++) {
                        if (selectedPlayers[j] == playerIndex) {
                            alreadySelected = true;
                            System.out.println(constant.PLAYER_SELECTED);
                            break;
                        }
                    }
                } catch (NumberFormatException e) {
                    System.out.println(constant.INVALID_INPUT);
                    alreadySelected = true;
                }
            } while (alreadySelected);
    
            selectedPlayers[count++] = playerIndex;
            activePlayers[count - 1][0] = team.getPlayers()[playerIndex][0];
            activePlayers[count - 1][1] = team.getPlayers()[playerIndex][1];
        }
        return activePlayers;
    }   
}
