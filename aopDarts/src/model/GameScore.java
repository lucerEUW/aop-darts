package model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class GameScore {
    
    private List<PlayerModel> players;  // List of players in the game
    private int currentPlayerIndex;     // Index of the current player
    private int round;                  // Current round number
    private boolean gameOver;           // Flag to indicate if the game is over
    
    // Constructor initializes the player list
    public GameScore() {
        players = new ArrayList<>();
    }
    
    // Method to initialize the game with player names, colors, and game type (e.g., score to start with)
    public void initializeGame(List<String> playerNames, List<Color> playerColors, int gameType) {
        for (int j = 0; j < playerNames.size(); j++) {
            players.add(new PlayerModel(playerNames.get(j), playerColors.get(j), gameType));
        }
        gameOver = false;
        round = 1;
        currentPlayerIndex = 0;
    }
    
    // Method to move to the next player's turn
    public void nextTurn() {
        if (!gameOver) {
            currentPlayerIndex++;
            if (currentPlayerIndex >= players.size()) {
                currentPlayerIndex = 0; // Reset to the first player
                round++; // Increase round number when all players have played
            }
        }
    }

    // Method to update the score of the current player after a throw
    public void updateScore(int points) {
        PlayerModel currentPlayer = players.get(currentPlayerIndex);
        int calculatedPoints = currentPlayer.getPoints() - points; // Subtract points from current player's score

        // If the score goes below zero, do nothing (invalid move)
        if (calculatedPoints < 0) {
            return;
        }
        // If the score is exactly zero, the game is over and the player wins
        else if (calculatedPoints == 0) {
            gameOver = true;
            currentPlayer.setPoints(calculatedPoints); // Set the score to zero
            return;
        }
        // Otherwise, update the player's score with the calculated value
        currentPlayer.setPoints(calculatedPoints);
    }

    public PlayerModel getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public int getScore() {
        return getCurrentPlayer().getPoints();
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int getRounds() {
        return round;
    }

    public List<PlayerModel> getPlayers() {
        return players;
    }
}
