package view;
import javax.swing.*;

import model.PlayerModel;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
/*
 * Dartboard Colors green: 0x309F6A red: 0xE3292E white: 0xF9DFBC black: 0x000000
 *    Player Colors red: 0xC21807 blue: 0x0E60D7 green: 0x0E60D7 yellow: 0xFFD300
 */
public class View extends JFrame {
	public static DartboardView dartboard;
	public static PlayerDisplayView playerDisplayPanel;
	
    public View(int numberOfPlayers, String[] playerNames, String gameType) {

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
        List<PlayerModel> players = new ArrayList<>();
        //					RED						BLUE				GREEN				YELLOW
        Color[] colors = {new Color(0xC21807), new Color(0x0E60D7), new Color(0x0B6623), new Color(0xFFD300)}; // Predetermined colors
        
        for (int i = 0; i < numberOfPlayers; i++) {
            players.add(new PlayerModel(playerNames[i], colors[i], Integer.parseInt(gameType)));
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
    public void showWinScreen(List<PlayerModel> players) {
        // Create a JDialog for the win screen
        JDialog winDialog = new JDialog(this, "Game Over", true);
        winDialog.setLayout(new BorderLayout());
        winDialog.setSize(300, 200);
        winDialog.setLocationRelativeTo(this); // Center the dialog

        // Create a panel to hold the rankings
        JPanel rankingPanel = new JPanel();
        rankingPanel.setLayout(new BoxLayout(rankingPanel, BoxLayout.Y_AXIS));

        // Sort players by points in descending order
        List<PlayerModel> sortedPlayers = new ArrayList<>(players);
        Collections.sort(sortedPlayers, Comparator.comparingInt(PlayerModel::getPoints));//.reversed()

        // Add player rankings to the panel
        for (int i = 0; i < sortedPlayers.size(); i++) {
            PlayerModel player = sortedPlayers.get(i);
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
}

