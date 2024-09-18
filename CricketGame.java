/*
 * Problem : Create a cricket game using java.
 * Owner Name : Arjun Gautam
 * Date : 17/09/24
 */
package Assignment_5;
import java.util.Scanner;

/*
 * The Team class stores a team's name (teamName), a 2D array of player names and roles (players),
 * and tracks the number of players (playerCount), with the addPlayer() method adding players if the team isn't full.
 */
class Team {
    String teamName;
    String[][] players = new String[11][2]; 
    int playerCount = 0; 

    Team(String teamName) {
        this.teamName = teamName;
    }
    void addPlayer(String playerName, String playerRole) {
        if (playerCount < 11) {
            players[playerCount][0] = playerName;
            players[playerCount][1] = playerRole;
            playerCount++;
        } else {
            System.out.println("Team is full. Cannot add more players.");
        }
    }
}

public class CricketGame {

    private static Team[] teams = new Team[10]; 
    private static int teamCount = 0; 

/*
 * The viewTeams() method checks if teams exist, prints each team's name, and for each team, 
 * it either shows "No players" if playerCount is 0 or lists all players and their roles.
 */
    public static void viewTeams() {
        if (teamCount == 0) {
            System.out.println("No teams have been created yet.");
        } else {
            for (int i = 0 ; i < teamCount ; i++) {
                Team team = teams[i];
                System.out.println("Team " + (i + 1) + ": " + team.teamName);
                if (team.playerCount == 0) {
                    System.out.println("No players in this team.");
                } else {
                    System.out.println("Players:");
                    for (int j = 0 ; j < team.playerCount ; j++) {
                        System.out.println((j + 1) + ". Name: " + team.players[j][0] + ", Role: " + team.players[j][1]);
                    }
                }
            }
        }
    }

/*
 * The addPlayer() method prompts the user to input a player's name and role, 
 * then returns them as a String[] array containing the name and role.
 */  
    public static String[] addPlayer() {
        Scanner input = new Scanner(System.in);
        System.out.println("Name: ");
        String playerName = input.nextLine();
        System.out.println("Role: ");
        String playerRole = input.nextLine();
        return new String[] { playerName, playerRole };
    }

/*
 * The createTeam() method checks if the max team limit is reached, prompts for a team name, 
 * then in a loop allows adding up to 11 players using addPlayer(), stores the team in the teams[] array, 
 * increments teamCount, and confirms team creation with a success message.
 */
    public static void createTeam() {
        if (teamCount >= 10) {
            System.out.println("Maximum number of teams reached.");
            return;
        }
        Scanner input = new Scanner(System.in);
        System.out.print("Enter team name: ");
        String teamName = input.nextLine();

        Team newTeam = new Team(teamName); 
        boolean isQuit = false;

        while (!isQuit) {
            System.out.println("1. Add player \n2. Done");
            System.out.print("Enter operation: ");
            String operation = input.nextLine();
            switch (operation) {
                case "1":
                    if (newTeam.playerCount < 11) {
                        String[] playerDetails = addPlayer();
                        newTeam.addPlayer(playerDetails[0], playerDetails[1]);
                    } else {
                        System.out.println("Team is full. Cannot add more players.");
                    }
                    break;
                case "2":
                    isQuit = true;
                    break;
                default:
                    System.out.println("Invalid option. Please choose 1 or 2.");
                    break;
            }
        }
        teams[teamCount] = newTeam; 
        teamCount++;
        System.out.println("Team " + newTeam.teamName + " created successfully.");
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        boolean isQuit = true;
        while (isQuit) {
            System.out.println();
            System.out.println("1. View Teams \n2. Create Team \n3. Start Match \n4. Quit");
            System.out.print("Enter operation: ");
            String operation = input.nextLine();
            switch (operation) {
                case "1":
                    viewTeams();  
                    break;
                case "2":
                    createTeam(); 
                    break;
                case "3":
                    
                    break;
                case "4":
                    isQuit = false;  
                    break;
                default:
                    System.out.println("Invalid option. Please choose 1, 2, 3, or 4.");
                    break;
            }
        }
        input.close();
    }
}
