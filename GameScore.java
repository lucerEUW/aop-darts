package vlKlapptDart;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class GameScore {
	
	private List<PlayerView> players;
	private int currentPlayerIndex;
	private int round;
	private boolean gameOver;
	
	public GameScore() {
		players = new ArrayList<>();

	}
	
    public void initializeGame(List<String> playerNames,List<Color> playerColors, int gameType) {
    	for (int j = 0; j < playerNames.size(); j++) {
    		players.add(new PlayerView(playerNames.get(j),playerColors.get(j), gameType));
		}
        gameOver = false;
        round = 1;
		currentPlayerIndex = 0;
    }
	
    public void nextTurn() {
        if (!gameOver) {
            currentPlayerIndex++;
            if (currentPlayerIndex >= players.size()) {
                currentPlayerIndex = 0;
                round++;
            }
        }
    }

    public void updateScore(int points) {
    	PlayerView currentPlayer = players.get(currentPlayerIndex);
        int calculatedPoints = currentPlayer.getPoints() - points;
        		
        if(calculatedPoints < 0) {
        	System.out.println("Unterschossen!");
        	return;
        }
        else if (calculatedPoints == 0) {
            gameOver = true;
            currentPlayer.setPoints(calculatedPoints);
            System.out.println(currentPlayer.getName() + " wins!");
            return;
        }
        currentPlayer.setPoints(calculatedPoints);
    }

    public PlayerView getCurrentPlayer() {
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
    
    public List<PlayerView> getPlayers() {
    	return players;
    }
}
