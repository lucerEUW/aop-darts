package contoller;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.swing.SwingUtilities;

import model.GameScore;
import model.PointCalculator;
import model.Throw;
import view.MenuView;
import view.View;

public class Controller {
    private static GameScore score = new GameScore();
    private static View view;
    private static PointCalculator calculator = new PointCalculator();
    
    // Method to start the game, initializes players and the view
    public void startGame(int playerCount, String[] playerNames, String gameType) {
        List<String> players = Arrays.asList(playerNames);
        List<Color> colors = Arrays.asList(
            new Color(0xC21807), // Red
            new Color(0x0E60D7), // Blue
            new Color(0x0B6623), // Green
            new Color(0xFFD300)  // Yellow
        );

        // Initialize the game with the provided players, colors, and game type
        score.initializeGame(players, colors, Integer.parseInt(gameType));
        view = new View(playerCount, playerNames, gameType);
        view.setVisible(true);
        new Thread(this::gameLoop).start(); // Start the main game loop in a new thread
    }
    
    // The main game loop, controls the flow of the game
    private void gameLoop() {
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter 'debug' to enter debug mode");
        String input = reader.nextLine();
        boolean debugActiv = false;
        int x;
        int y;
        
        // Activate debug mode if the user enters "debug"
        if (input.equals("debug")) {
            debugActiv = true;
        }
        
        while (!score.isGameOver()) {
            int player = score.getCurrentPlayerIndex();
            SwingUtilities.invokeLater(() -> View.playerDisplayPanel.highlightPlayer(player));
            ExecutorService service = Executors.newSingleThreadExecutor();

            for (int throwNumber = 1; throwNumber < 4; throwNumber++) {
                if (!debugActiv) {
                    // Simulate a throw and wait for it to complete
                    Throw currentThrow = new Throw();
                    Runnable task = () -> {currentThrow.Throw(view);};
                    Future<?> future = service.submit(task);
                    
                    try {
                        future.get(); // Wait for the throw to complete
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                    Throw.resetThrow();
                    
                    // Convert the normalized coordinates to integers
                    x = (int) (currentThrow.getx() * 100);
                    y = (int) (currentThrow.gety() * 100);
                } else {
                    // In debug mode, allow manual input of coordinates
                    System.out.println("Coordinates go from 0.00 to 0.99, 0.50 is the center");
                    System.out.println("Write Coordinates like this: \"1356\" : x = 0.13 y = 0.56");
                    
                    input = reader.nextLine();
                    input = input + "0000"; // Pad the input to avoid errors
                    x = Integer.parseInt(input.substring(0, 2));
                    y = Integer.parseInt(input.substring(2, 4));
                }
                
                // Calculate the score based on the throw coordinates
                int points = calculator.checkHit(x, y);
                System.out.println("throw number " + throwNumber + " by player" + player);
                System.out.println("registered Hit: " + points);
                View.dartboard.addDart((float) x / 100, (float) y / 100, score.getCurrentPlayer().getColor());
                score.updateScore(points);
                View.playerDisplayPanel.setPointsOf(score.getCurrentPlayerIndex(), score.getScore());
            }
            
            synchronized (this) {
                SwingUtilities.invokeLater(() -> {
                    score.nextTurn(); // Move to the next player's turn
                    View.playerDisplayPanel.resetHighlighting();
                    SwingUtilities.invokeLater(() -> View.playerDisplayPanel.highlightPlayer(score.getCurrentPlayerIndex()));
                });
                
                try {
                    Thread.sleep(1000); // Pause before the next round
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                
                SwingUtilities.invokeLater(() -> View.dartboard.clearDarts()); // Clear the dartboard for the next player
            }
        }
        view.showWinScreen(score.getPlayers());
        reader.close(); // Close the scanner when the game is over
    }

    // Entry point of the program, starts with the menu
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MenuView::new);
    }
}
