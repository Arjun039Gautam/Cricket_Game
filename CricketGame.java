/*
 * This Java program implements a simple cricket game simulation allowing users to create teams,
 * select players, and simulate a match with scoring and innings.
 * Owner : Arjun Gautam
 * Date : 17/09/24
 */
package Assignment_5;

import java.util.*;

public class CricketGame {
    private static Team[] teams = new Team[10];
    private static int teamCount = 2;
    public static Scanner input = new Scanner(System.in);

    public String[] addPlayer() {
        System.out.println("Name: ");
        String playerName = input.nextLine();
        System.out.println("Specification: ");
        String playerRole = input.nextLine();
        return new String[]{playerName, playerRole};
    }

    public CricketGame() {
        teams[0] = new Team("India");
        for (String player : ExistTeam.teamPlayers[0]) {
            String[] details = player.split(" - ");
            teams[0].addPlayer(details[0], details[1]);
        }

        teams[1] = new Team("Jaipur Royals");
        for (String player : ExistTeam.teamPlayers[1]) {
            String[] details = player.split(" - ");
            teams[1].addPlayer(details[0], details[1]);
        }
        
    }

    //Main method
    public static void main(String[] args) {
        
        CricketGame game = new CricketGame();
        boolean isQuit = false;
        while (!isQuit) {
            System.out.println();
            System.out.println("1. View Teams \n2. Create Team \n3. Start Match \n4. Quit");
            System.out.print("Enter operation: ");
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
                    System.out.println("Invalid option. Please choose 1, 2, 3, or 4.");
                    break;
            }
        }
        input.close();
    }

    //This method view the existing teams
    public void viewTeams() {
        // Display Team A and Team B, which are already in positions 0 and 1
        System.out.println("1. India");
        System.out.println("Players:");
        for (int i = 0; i < ExistTeam.teamPlayers[0].length; i++) {
            System.out.println((i + 1) + ". " + ExistTeam.teamPlayers[0][i]);
        }
    
        System.out.println("\n2. Jaipur Royals");
        System.out.println("Players:");
        for (int i = 0; i < ExistTeam.teamPlayers[1].length; i++) {
            System.out.println((i + 1) + ". " + ExistTeam.teamPlayers[1][i]);
        }
        for (int i = 2; i < teamCount; i++) {
            Team team = teams[i];
            System.out.println("\nTeam " + (i + 1) + ": " + team.getTeamName());
            if (team.getPlayerCount() == 0) {
                System.out.println("No players in this team.");
            } else {
                System.out.println("Players:");
                for (int j = 0; j < team.getPlayerCount(); j++) {
                    System.out.println((j + 1) + ". " + team.getPlayers()[j][0] + " - " + team.getPlayers()[j][1]);
                }
            }
        }
    }

    // Method to create new teams
    public void createTeam() {
        if (teamCount >= 10) {
            System.out.println("Maximum number of teams reached.");
            return;
        }
        System.out.print("Enter team name: ");
        String teamName = input.nextLine();
    
        Team newTeam = new Team(teamName);
    
        System.out.println("You need to add at least 11 players to the team.");
    
        while (newTeam.getPlayerCount() < 11) {
            System.out.println("Adding player " + (newTeam.getPlayerCount() + 1) + ":");
            String[] playerDetails = addPlayer();
            newTeam.addPlayer(playerDetails[0], playerDetails[1]);
    
            if (newTeam.getPlayerCount() >= 11) {
                System.out.print("Do you want to add another player? (yes/no): ");
                String response = input.nextLine();
                if (response.equalsIgnoreCase("no")) {
                    break;  
                }
            }
        }
        teams[teamCount] = newTeam;  
        teamCount++;
        System.out.println("Team " + newTeam.getTeamName() + " created successfully.");
    }
    
    //Method to add players in new team
    public String[][] addPlayers() {
        String[][] players = new String[11][2]; 
        int playerCount = 0;
    
        System.out.println("You need to add a minimum of 11 players.");
    
        while (playerCount < 11) {
            System.out.println("Adding player " + (playerCount + 1) + ":");
            
            System.out.print("Name: ");
            String playerName = input.nextLine();
            
            System.out.print("Specification: ");
            String playerRole = input.nextLine();
            
            players[playerCount][0] = playerName;  
            players[playerCount][1] = playerRole;  
            playerCount++;
    
            if (playerCount >= 11) {
                System.out.print("Do you want to add another player? (yes/no): ");
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
        viewTeams();
        System.out.print("Select Team 1: ");
        int team1Index = Integer.parseInt(input.nextLine()) - 1;
        
        // Check if team1Index is valid
        if (team1Index < 0 || team1Index >= teamCount || teams[team1Index] == null) {
            System.out.println("Invalid selection for Team 1. Please select a valid team.");
            return;
        }
        
        Team team1 = teams[team1Index];
        int team2Index;
        Team team2;
    
        do {
            System.out.print("Select Team 2: ");
            team2Index = Integer.parseInt(input.nextLine()) - 1;
    
            if (team2Index == team1Index) {
                System.out.println("Invalid selection. Team 2 cannot be the same as Team 1. Please select a different team.");
            }
        } while (team2Index == team1Index);
    
        team2 = teams[team2Index];
    
        System.out.println("Team " + team1.getTeamName() + " vs " + team2.getTeamName());
    
        int totalOvers = getTotalOversForMatch();
    
        System.out.println("Select 11 players for " + team1.getTeamName() + ":");
        String[][] activePlayersTeam1 = selectPlayersForMatch(team1);
    
        System.out.println("Select 11 players for " + team2.getTeamName() + ":");
        String[][] activePlayersTeam2 = selectPlayersForMatch(team2);
    
        System.out.println("Choose which team plays first (1 for " + team1.getTeamName() + ", 2 for " + team2.getTeamName() + "): ");
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
            System.out.println("It's a tie!");
        }
    }
    
    //Method to get total overs for innings
    private int getTotalOversForMatch() {
        int overs = 0;
        while (overs <= 0) {
            System.out.print("Enter the total number of overs for the match: ");
            try {
                overs = Integer.parseInt(input.nextLine());
                if (overs <= 0) {
                    System.out.println("Invalid input! Please enter a positive number.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid number.");
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
    
        System.out.println("Batting for " + team.getTeamName() + ":");
    
        while (wickets < 10 && balls < totalBalls) {
            String striker = activePlayers[strikerIndex][0];
            String nonStriker = activePlayers[nonStrikerIndex][0];
    
            System.out.println(striker + " is on strike with " + nonStriker + " at the non-striker's end.");
    
            for (int overBall = 1; overBall <= 6 && wickets < 10 && balls < totalBalls; overBall++) {
                System.out.println("Ball " + overBall + " (Enter runs, or 'w' for wide, 'n' for no ball, 'o' for out):");
                String ballOutcome = input.nextLine();
    
                switch (ballOutcome) {
                    case "w":
                        System.out.println("Wide ball! 1 run added.");
                        totalScore += 1;
                        int runsOnWide = -1; 
                        while (true) { 
                            System.out.print("Enter runs scored off the wide-ball delivery (0, 1, 2, 3, 4): ");
                            String extraRuns = input.nextLine();
                            try {
                                runsOnWide = Integer.parseInt(extraRuns);
                                
                                if (runsOnWide >= 0 && runsOnWide <= 4) {
                                    totalScore += runsOnWide;
                                    System.out.println(activePlayers[strikerIndex][0] + " scored " + runsOnWide + " runs on the wide-ball."); 
                                    break; 
                                } else {
                                    System.out.println("Invalid runs. Please enter a number (0, 1, 2, 3, 4).");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input! Please enter a number.");
                            }
                        }
                        overBall--;
                        break;
                    case "n":
                        System.out.println("No ball! 1 run added.");
                        totalScore += 1;
                        int runsOnNoBall = -1; 
                        while (true) { 
                            System.out.print("Enter runs scored off the no-ball delivery (0, 1, 2, 3, 4, 6): ");
                            String extraRuns = input.nextLine();
                            try {
                                runsOnNoBall = Integer.parseInt(extraRuns);
                                
                                if (runsOnNoBall >= 0 && runsOnNoBall <= 6 && runsOnNoBall != 5) {
                                    totalScore += runsOnNoBall;
                                    System.out.println(activePlayers[strikerIndex][0] + " scored " + runsOnNoBall + " runs on the no-ball.");
                                    overBall--; 
                                    break; 
                                } else {
                                    System.out.println("Invalid runs. Please enter a number (0, 1, 2, 3, 4, or 6).");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input! Please enter a number.");
                            }
                        }
                        break;
                    case "o":
                        System.out.println("Out!");
                        wickets++;
                        isOut[strikerIndex] = true;  // Mark this player as out
                        if (currentPlayerIndex < 11) {
                            strikerIndex = currentPlayerIndex++;
                            System.out.println("Next player in: " + activePlayers[strikerIndex][0]);
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
                                System.out.println("Invalid runs. Please enter 0, 1, 2, 3, 4, or 6.");
                                overBall--;
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input! Enter a number for runs or 'w', 'n', 'o'.");
                            overBall--; 
                        }
                        break;
                }
    
                balls++;
            }
    
            System.out.println("End of over. Striker and non-striker swap positions.");
            int temp = strikerIndex;
            strikerIndex = nonStrikerIndex;
            nonStrikerIndex = temp;
            System.out.println("New striker: " + activePlayers[strikerIndex][0] + ", Non-striker: " + activePlayers[nonStrikerIndex][0]);
        }
    
        System.out.println("Innings completed. Team " + team.getTeamName() + " scored: " + totalScore);
        System.out.println("Player Scores:");
        for (int i = 0; i < activePlayers.length; i++) {
            System.out.println(activePlayers[i][0] + ": " + playerScores[i] + " runs");
        }
        printScorecard(team, activePlayers, playerScores, isOut, totalScore, wickets);
        return totalScore;
    }
    
    //Method to show scorecard
    public void printScorecard(Team team, String[][] activePlayers, int[] playerScores, boolean[] isOut, int totalScore, int wickets) {
        System.out.println("\nScorecard for " + team.getTeamName() + ":");
        System.out.println("----------------------------------------------------");
        System.out.printf("%-20s %-10s %-10s\n", "Player", "Runs", "Status");
        System.out.println("----------------------------------------------------");
    
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
            System.out.printf("%-20s %-10d %-10s\n", playerName, playerScore, status);
        }
    
        System.out.println("----------------------------------------------------");
        System.out.printf("Total Score: %-10d Wickets: %-10d\n", totalScore, wickets);
        System.out.println("----------------------------------------------------\n");
    }
    
    //Method to select active player for match
    public String[][] selectPlayersForMatch(Team team) {
        String[][] activePlayers = new String[11][2];
        int[] selectedPlayers = new int[11];
        int count = 0;
    
        System.out.println("Select 11 players from team " + team.getTeamName());
    
        while (count < 11) {
            int playerIndex = -1; 
            boolean alreadySelected;
    
            do {
                System.out.print("Select player " + (count + 1) + " (Enter player number): ");
                try {
                    playerIndex = Integer.parseInt(input.nextLine()) - 1;
    
                    if (playerIndex < 0 || playerIndex >= team.getPlayerCount()) {
                        System.out.println("Invalid player number. Please enter a number between 1 and " + team.getPlayerCount() + ".");
                        alreadySelected = true;
                        continue;
                    }
                    alreadySelected = false;
    
                    for (int j = 0; j < count; j++) {
                        if (selectedPlayers[j] == playerIndex) {
                            alreadySelected = true;
                            System.out.println("Player already selected. Please choose a different player.");
                            break;
                        }
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
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
