package vlKlapptDart;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class Controller {
	private static GameScore score = new GameScore();
	private static View view;
    private static PointCalculator calculator = new PointCalculator();
	
    public void startGame(int playerCount, String[] playerNames, String gameType) {
    	List<String> players = Arrays.asList(playerNames);
    	List<Color> colors = Arrays.asList(
    		    new Color(0xC21807), 
    		    new Color(0x0E60D7), 
    		    new Color(0x0B6623), 
    		    new Color(0xFFD300)
    		);
    	
        score.initializeGame(players,colors, Integer.parseInt(gameType));
        view = new View(playerCount, playerNames, gameType);
        view.setVisible(true);
        new Thread(this::gameLoop).start();
    }
	
  
    private void gameLoop() {
    	Scanner reader = new Scanner(System.in);
		
		System.out.println("Coordinates go from 0.00 to 0.99, 0.50 is the center");
		System.out.println("Write Coordinates like this: \"1356\" : x = 0.13 y = 0.56");
		while(!score.isGameOver()) { 
			int player = score.getCurrentPlayerIndex(); // player count = 2
			// example how to highlite a player:
			SwingUtilities.invokeLater(() -> View.playerDisplayPanel.highlightPlayer(player));
			for(int i = 0; i< 3; i++) {
				String input = reader.nextLine();
				
				
				if(input.equals("c")) {
					// example how to clear all darts:
					SwingUtilities.invokeLater(() -> View.dartboard.clearDarts());
					// example how to clear highlits:
					SwingUtilities.invokeLater(() -> View.playerDisplayPanel.resetHighlighting());
				}else {
					input = input + "0000";
					int x = Integer.parseInt(input.substring(0, 2));
					int y = Integer.parseInt(input.substring(2, 4));
					int points = calculator.checkHit(x, y);
					
					
					
					// example how to throw a dart:
					SwingUtilities.invokeLater(() -> {
                        // Update the player's score
                        score.updateScore(points);
                        System.out.println("Points: "+ points+ " Player: " + score.getCurrentPlayerIndex() + " Konto neu: " + score.getScore());
                        // Update GUI elements synchronously
                        View.dartboard.addDart((float) x / 100, (float) y / 100, score.getCurrentPlayer().getColor());
                        View.playerDisplayPanel.setPointsOf(score.getCurrentPlayerIndex(), score.getScore());
                    });
				}	
			}
			
			synchronized (this) {
	            SwingUtilities.invokeLater(() -> {
	                score.nextTurn();
	                View.playerDisplayPanel.resetHighlighting();
	                SwingUtilities.invokeLater(() -> View.playerDisplayPanel.highlightPlayer(score.getCurrentPlayerIndex()));
	            });
	            try {
	                // Warte für 1 Sekunde (1000 Millisekunden)
	                Thread.sleep(1000);
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
			 //new Timer(1000, event -> View.dartboard.clearDarts()).start();
			SwingUtilities.invokeLater(() -> View.dartboard.clearDarts());
			
			//reader.close();
			}
		}
    }
    
	public static void main(String[] args) {
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// This has to be started from the controller.
		// further details in Menue Class Line 70ff
		SwingUtilities.invokeLater(MenuView::new); // Start with the menu
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		
	}
}
