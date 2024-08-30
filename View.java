package vlKlapptDart;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
/*
 * Dartboard Colors green: 0x309F6A red: 0xE3292E white: 0xF9DFBC black: 0x000000
 *    Player Colors red: 0xC21807 blue: 0x0E60D7 green: 0x0E60D7 yellow: 0xFFD300
 */
public class View extends JFrame {
	public static DartboardView dartboard;
	public static PlayerDisplayView playerDisplayPanel;
	//private int numberOfPlayers;
	//private String gameType;
	
    public View(int numberOfPlayers, String[] playerNames, String gameType) {
    	//this.numberOfPlayers = numberOfPlayers;
    	//this.gameType = gameType;
    	
        setTitle("Dart Game - " + gameType);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        this.setAlwaysOnTop(true);
        
        // Create a dartboard
        //DartboardView dartboard = new DartboardView();
        dartboard = new DartboardView();
        dartboard.setBackground(Color.WHITE);
        //dartboard.add(new JLabel("Dart Board", SwingConstants.CENTER));
        
        // Create players based on the number of players
        List<PlayerView> players = new ArrayList<>();
        //					RED						BLUE				GREEN				YELLOW
        Color[] colors = {new Color(0xC21807), new Color(0x0E60D7), new Color(0x0B6623), new Color(0xFFD300)}; // Predetermined colors
        
        for (int i = 0; i < numberOfPlayers; i++) {
            players.add(new PlayerView(playerNames[i], colors[i], Integer.parseInt(gameType)));
        }

        // Create the player display panel
        playerDisplayPanel = new PlayerDisplayView(players);

        // Add the dartboard and player display to the frame
        add(dartboard, BorderLayout.CENTER);
        add(playerDisplayPanel, BorderLayout.EAST);

        setSize(800, 600); // Set the size of the window
        setLocationRelativeTo(null); // Center the window
        setVisible(true);
    }
    
    // Method to show the win screen
    public void showWinScreen(List<PlayerView> players) {
        // Create a JDialog for the win screen
        JDialog winDialog = new JDialog(this, "Game Over", true);
        winDialog.setLayout(new BorderLayout());
        winDialog.setSize(300, 200);
        winDialog.setLocationRelativeTo(this); // Center the dialog

        // Create a panel to hold the rankings
        JPanel rankingPanel = new JPanel();
        rankingPanel.setLayout(new BoxLayout(rankingPanel, BoxLayout.Y_AXIS));

        // Sort players by points in descending order
        List<PlayerView> sortedPlayers = new ArrayList<>(players);
        Collections.sort(sortedPlayers, Comparator.comparingInt(PlayerView::getPoints));//.reversed()

        // Add player rankings to the panel
        for (int i = 0; i < sortedPlayers.size(); i++) {
            PlayerView player = sortedPlayers.get(i);
            JLabel rankingLabel = new JLabel((i + 1) + ". " + player.getName() + ": " + player.getPoints() + " points");
            //rankingLabel.setFont(getFont().deriveFont(20.0f));
            rankingLabel.setFont(new Font("Arial", Font.BOLD, 17));

            rankingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            rankingPanel.add(rankingLabel);
        }

        // Add the ranking panel to the dialog
        winDialog.add(rankingPanel, BorderLayout.CENTER);

        // Create an OK button to close the dialog
        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> winDialog.dispose());
        okButton.setFont(new Font("Arial", Font.BOLD, 17));
        winDialog.add(okButton, BorderLayout.SOUTH);

        // Show the dialog
        winDialog.setVisible(true);
    }
    
/*
    public int getPlayerCount() {
    	return numberOfPlayers;
    }
    public String getGameType() {
    	return gameType;
    }
*/
    public static void main(String[] args) {
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    	// This has to be started from the controller.
    	// further details in Menue Class Line 70ff
        SwingUtilities.invokeLater(MenuView::new); // Start with the menu
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        System.out.println("HelloWorld");

        Scanner reader = new Scanner(System.in);
        
        System.out.println("Coordinates go from 0.00 to 0.99, 0.50 is the center");
        System.out.println("Write Coordinates like this: \"1356\" : x = 0.13 y = 0.56");
        
        for(int i = 0; i< 50; i++) {
        	String input = reader.nextLine();
        	int player = i % 2; // player count = 2
        	// example how to highlite a player:
    		SwingUtilities.invokeLater(() -> playerDisplayPanel.highlightPlayer(player));
    		
        	if(input.equals("c")) {
        		// example how to clear all darts:
        		SwingUtilities.invokeLater(() -> dartboard.clearDarts());
        		// example how to clear highlits:
        		SwingUtilities.invokeLater(() -> playerDisplayPanel.resetHighlighting());
        	}else {
        		input = input + "0000";
        		int x = Integer.parseInt(input.substring(0, 2));
        		int y = Integer.parseInt(input.substring(2, 4));
        		
        		// example how to throw a dart:
        		SwingUtilities.invokeLater(() -> dartboard.addDart((float)x/100,(float)y/100,new Color(0xC21807)));
        		// example how to update score:
        		SwingUtilities.invokeLater(() -> playerDisplayPanel.setPointsOf(player, x+y));
        		
        	}
        }
        reader.close();
        
    }
    
    
    /*
    // Method to show the win screen
    public void showWinScreen(List<PlayerView> players) {
        // Create a JDialog for the win screen
        JDialog winDialog = new JDialog(this, "Game Over", true);
        winDialog.setLayout(new BorderLayout());
        winDialog.setSize(300, 200);
        winDialog.setLocationRelativeTo(this); // Center the dialog

        // Create a panel to hold the rankings
        JPanel rankingPanel = new JPanel();
        rankingPanel.setLayout(new BoxLayout(rankingPanel, BoxLayout.Y_AXIS));

        // Add player rankings to the panel
        for (int i = 0; i < sortedPlayers.size(); i++) {
            PlayerView player = sortedPlayers.get(i);
            JLabel rankingLabel = new JLabel((i + 1) + ". " + player.getName() + ": " + player.getPoints() + " points");
            //rankingLabel.setFont(getFont().deriveFont(20.0f));
            rankingLabel.setFont(new Font("Arial", Font.BOLD, 17));

            rankingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            rankingPanel.add(rankingLabel);
        }

        // Add the ranking panel to the dialog
        winDialog.add(rankingPanel, BorderLayout.CENTER);

        // Create an OK button to close the dialog
        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> winDialog.dispose());
        okButton.setFont(new Font("Arial", Font.BOLD, 17));
        winDialog.add(okButton, BorderLayout.SOUTH);

        // Show the dialog
        winDialog.setVisible(true);
    }
     */
}

