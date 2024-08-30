package vlKlapptDart;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class Controller {
	private static GameScore score = new GameScore();
	private static View view;
    private static PointCalculator calculator = new PointCalculator();
	//private static DartboardView dartboardView = DartboardView.getInstance();
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
		while(!score.isGameOver()) { 
			
			int player = score.getCurrentPlayerIndex(); // player count = 2
			// example how to highlite a player:
			SwingUtilities.invokeLater(() -> View.playerDisplayPanel.highlightPlayer(player));
			ExecutorService service = Executors.newSingleThreadExecutor();
			
		
			for(int i = 0; i < 3; i++) {
				if(score.isGameOver()) {
					view.showWinScreen(score.getPlayers());
					break;
				}
				//SwingUtilities.invokeLater(() -> View.playerDisplayPanel.resetHighlighting());
				Throw currentThrow = new Throw();
				Runnable task = () -> {currentThrow.Throw(view, score);};
				
				Future <?> future = service.submit(task);
				try {
					future.get();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				Throw.xRunning = true;
				Throw.yRunning = false;
				Throw.finishedThrow = false;
				
				int x = (int) (currentThrow.xValue *100);
				int y = (int) (currentThrow.yValue *100);
				System.out.println(x + ":" + y);
				int points = calculator.checkHit(x, y);
				System.out.println(points);
				View.dartboard.addDart(currentThrow.xValue, currentThrow.yValue, score.getCurrentPlayer().getColor());
                score.updateScore(points);
                System.out.println("Points: "+ points+ " Player: " + score.getCurrentPlayerIndex() + " Konto neu: " + score.getScore());
                // Update GUI elements synchronously
                View.playerDisplayPanel.setPointsOf(score.getCurrentPlayerIndex(), score.getScore());
				
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
