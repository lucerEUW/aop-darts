package vlKlapptDart;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerDisplayView extends JPanel {
    private List<PlayerView> players;
    private List<JLabel> pointsLabels; // List to hold points labels
    private List<JPanel> playerPanels; // List to hold player panels
    private Border highlightBorder; // Border for highlighting

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    public PlayerDisplayView(List<PlayerView> players) {
        this.players = players;
        this.pointsLabels = new ArrayList<>(); // Initialize the list for points labels
        this.playerPanels = new ArrayList<>(); // Initialize the list for player panels
        this.highlightBorder = BorderFactory.createLineBorder(Color.BLACK, 5);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(200, 0));

        for (PlayerView player : players) {
            add(createPlayerPanel(player));
        }
    }

    private JPanel createPlayerPanel(PlayerView player) {
        JPanel playerPanel = new JPanel();
        playerPanel.setBackground(player.getColor());
        playerPanel.setLayout(new BorderLayout());
        playerPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Add some padding

        JLabel nameLabel = new JLabel(player.getName());
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 17));

        JLabel pointsLabel = new JLabel(String.valueOf(player.getPoints()));
        pointsLabel.setForeground(Color.WHITE);
        pointsLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        pointsLabel.setFont(new Font("Arial", Font.BOLD, 17));

        playerPanel.add(nameLabel, BorderLayout.WEST);
        playerPanel.add(pointsLabel, BorderLayout.EAST);

        pointsLabels.add(pointsLabel); // Store the points label for later access
        playerPanels.add(playerPanel); // Store the player panel for highlighting

        return playerPanel;
    }

    public void setPointsOf(int playerIndex, int points) {
        players.get(playerIndex).setPoints(points);
        pointsLabels.get(playerIndex).setText(String.valueOf(points)); // Update the JLabel text
    }

    public int getPointsOf(int playerIndex) {
        return players.get(playerIndex).getPoints();
    }

    // Method to highlight the player whose turn it is
    public void highlightPlayer(int playerIndex) {
        // Reset all player panels to remove highlighting
        resetHighlighting();
        // Highlight the current player's panel by adding a border
        playerPanels.get(playerIndex).setBorder(highlightBorder);
    }

    // Method to reset highlighting for all players
    public void resetHighlighting() {
        for (int i = 0; i < playerPanels.size(); i++) {
            playerPanels.get(i).setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Remove border
        }
    }
}
