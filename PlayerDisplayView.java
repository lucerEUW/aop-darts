package vlKlapptDart;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PlayerDisplayView extends JPanel {
    private List<PlayerView> players;
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //createPlayerPanel(players);
    }
    
    public PlayerDisplayView(List<PlayerView> players) {
        this.players = players;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);
        
        setPreferredSize(new Dimension(200, 0)); // Fixed width for the player display
        //setPreferredSize(new Dimension((int)(getWidth()/2), 0)); // Changeable width for the player display
        for (PlayerView player : players) {
            add(createPlayerPanel(player));
        }
    }

    private JPanel createPlayerPanel(PlayerView player) {
        JPanel playerPanel = new JPanel();
        playerPanel.setBackground(player.getColor());
        playerPanel.setLayout(new BorderLayout());
        playerPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JLabel nameLabel = new JLabel(player.getName());
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        //nameLabel.setHorizontalAlignment(3);
        nameLabel.setFont(getFont().deriveFont(20.0f));
        
        JLabel pointsLabel = new JLabel(String.valueOf(player.getPoints()));
        pointsLabel.setForeground(Color.WHITE);
        pointsLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        pointsLabel.setFont(getFont().deriveFont(20.0f));
        
        playerPanel.add(nameLabel, BorderLayout.WEST);
        playerPanel.add(pointsLabel, BorderLayout.EAST);

        return playerPanel;
    }
    public void setPointsOf(int player, int points) {
    	players.get(player).setPoints(points);
    	repaint();
    }
    
    public int getPointsOf(int player) {
    	return players.get(player).getPoints();
    }
}
