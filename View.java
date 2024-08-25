package vlKlapptDart;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MenuView::new); // Start with the menu
        System.out.println("HelloWorld");
        
        
        Scanner reader = new Scanner(System.in);
        
        System.out.println("Coordinates go from 0.00 to 0.99, 0.50 is the center");
        System.out.println("Write Coordinates like this: \"1356\" : x = 0.13 y = 0.56");
        
        for(int i = 0; i< 50; i++) {
        	String tmp = reader.nextLine();
        	if(tmp.equals("c")) {
        		SwingUtilities.invokeLater(() -> dartboard.clearDarts());
        	}else {
        		int x = Integer.parseInt(tmp.substring(0, 2));
        		int y = Integer.parseInt(tmp.substring(2, 4));
        		SwingUtilities.invokeLater(() -> dartboard.addDart((float)x/100,(float)y/100,Color.green));
        		playerDisplayPanel.setPointsOf(i % 4, x);
        	}
        }
        reader.close();
        
    }
}

