package vlKlapptDart;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuView extends JFrame {
    private JTextField[] playerNameFields;
    private JComboBox<Integer> playerCountComboBox;
    private JComboBox<String> gameTypeComboBox;

    public MenuView() {
        setTitle("Dart Game Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(0, 1)); // Vertical layout

        // Player count selection
        JLabel playerCountLabel = new JLabel("Select number of players (2-4):");
        playerCountComboBox = new JComboBox<>(new Integer[]{2, 3, 4});
        add(playerCountLabel);
        add(playerCountComboBox);

        // Player name input fields
        playerNameFields = new JTextField[4]; // Max 4 players
        for (int i = 0; i < 4; i++) {
            playerNameFields[i] = new JTextField();
            playerNameFields[i].setVisible(false); // Initially hidden
            add(playerNameFields[i]);
        }

        // Game type selection
        JLabel gameTypeLabel = new JLabel("Select game type:");
        gameTypeComboBox = new JComboBox<>(new String[]{"301", "601"});
        add(gameTypeLabel);
        add(gameTypeComboBox);

        // Button to start the game
        JButton startButton = new JButton("Start Game");
        startButton.addActionListener(new StartGameAction());
        add(startButton);

        // Listener to show/hide name fields based on player count
        playerCountComboBox.addActionListener(e -> updatePlayerNameFields());

        setSize(300, 250);
        setLocationRelativeTo(null); // Center the window
        setVisible(true);
    }

    private void updatePlayerNameFields() {
        int playerCount = (int) playerCountComboBox.getSelectedItem();
        for (int i = 0; i < 4; i++) {
            playerNameFields[i].setVisible(i < playerCount);
        }
        revalidate(); // Refresh the layout
        repaint(); // Repaint the frame
    }

    private class StartGameAction implements ActionListener {
    	Controller controller = new Controller();
    	@Override
        public void actionPerformed(ActionEvent e) {
            int playerCount = (int) playerCountComboBox.getSelectedItem();
            String[] playerNames = new String[playerCount];

            for (int i = 0; i < playerCount; i++) {
                playerNames[i] = playerNameFields[i].getText();
            }

            String gameType = (String) gameTypeComboBox.getSelectedItem();
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            // Start the game with the selected player names and game type
            // Change this so the controller notes all the inputs from the
            // menue. And then starts the view!!!
            controller.startGame(playerCount, playerNames, gameType);
            //new View(playerCount, playerNames, gameType);
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            dispose(); // Close the menu
        }
    }
}

