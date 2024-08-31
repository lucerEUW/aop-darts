package view;

import javax.swing.*;

import model.Throw;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.Scanner;


public class DartboardView extends JPanel {
	private ArrayList<DartView> darts = new ArrayList<DartView>();
	private int diameter = 0;
	private float aimX = 0.0f;
	private float aimY = 0.0f;
	public static Graphics g;
	private JButton throwButton;
	private DartboardView dartboard = this;
	
    public DartboardView() {
        this.setBackground(Color.WHITE); // Background color
        this.setLayout(new BorderLayout()); // Use BorderLayout for positioning
        // Create the throw button
        throwButton = new JButton("Throw");
        throwButton.setPreferredSize(new Dimension(100, 50));
        
        // Add mouse listener to the button
        throwButton.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
            	Throw.setxValue();
            }
            public void mouseReleased(MouseEvent e) {
            	Throw.setyValue(dartboard);
            }
        });

        // Create a panel for the button and add it to the top
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0)); // Align to the right with no gaps
        buttonPanel.setOpaque(false); // Make the button panel non-opaque
        buttonPanel.add(throwButton);
        add(buttonPanel, BorderLayout.NORTH); // Add button panel to the top
    }
    
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.g = g;
        drawDartboard(g);
        drawDarts(g);
        drawAimingIndicators(g);
    }
    
    // Graphics for the Dart Board
    private void drawDartboard(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = (int) (getWidth()*1);
        int height = getHeight();
        diameter = Math.min(width, height); // The dartboard is a square, so the smaller dimension is used
        int radius = diameter / 2;

        int centerX = diameter / 2;
        int centerY = height / 2;

        // Calculate radius based on the dimensions in the image
        int outerBullRadius = (int) ( 15.9 / 200.0 * radius);
        int bullseyeRadius = (int) ( 6.35 / 200.0 * radius);
        int tripleRingOuterRadius = (int) ( 107.0 / 200.0 * radius);
        int tripleRingInnerRadius = (int) ( 99.0 / 200.0 * radius);
        int doubleRingOuterRadius = (int) ( 170.0 / 200.0 * radius);
        int doubleRingInnerRadius = (int) ( 162.0/ 200.0 * radius);
        int outerBlackRingRadius = (int) ( 200.0 / 200.0 * radius);
        int innerBlackRingRadius = (int) ( 170.0/ 200.0 * radius);

        // Draw the dartboard sections (20 sections alternating between black/light brown) with 9-degree rotation
        for (int i = 0; i < 20; i++) {
            double angle = Math.toRadians(i * 18 + 9); // Rotate by 9 degrees
            g2d.setColor((i % 2 == 0) ? Color.BLACK : new Color(0xF9DFBC)); // Black/Light Brown
            g2d.fillArc(centerX - radius, centerY - radius, diameter, diameter, (int) Math.toDegrees(angle), 18);
        }

        // Draw the black ring
        drawRing(g2d, centerX, centerY, innerBlackRingRadius, outerBlackRingRadius, Color.BLACK, Color.BLACK);
        
        // Draw the triple ring (red/green)
        drawRing(g2d, centerX, centerY, tripleRingInnerRadius, tripleRingOuterRadius, new Color(0xE3292E), new Color(0x309F6A));

        // Draw the double ring (red/green)   new Color(0xD3292A), new Color(0x007A33)
        drawRing(g2d, centerX, centerY, doubleRingInnerRadius, doubleRingOuterRadius, new Color(0xE3292E), new Color(0x309F6A));

        // Draw the outer bull (green)
        g2d.setColor(new Color(0x309F6A));
        g2d.fillOval(centerX - outerBullRadius, centerY - outerBullRadius, outerBullRadius * 2, outerBullRadius * 2);

        // Draw the bullseye (inner bull - red)
        g2d.setColor(new Color(0xE3292E));
        g2d.fillOval(centerX - bullseyeRadius, centerY - bullseyeRadius, bullseyeRadius * 2, bullseyeRadius * 2);
        
        // Draw the numbers on the outer black ring
        drawNumbers(g2d, centerX, centerY, innerBlackRingRadius, radius);
    }
    //
    private void drawRing(Graphics2D g2d, int centerX, int centerY, int innerRadius, int outerRadius, Color color1, Color color2) {
        int outerDiameter = outerRadius * 2;
        int innerDiameter = innerRadius * 2;

        for (int i = 0; i < 20; i++) {
            double angle = Math.toRadians(i * 18 + 9); // Rotate by 9 degrees
            g2d.setColor(i % 2 == 0 ? color1 : color2);

            // Create a shape for the outer arc
            Arc2D outerArc = new Arc2D.Double(centerX - outerRadius, centerY - outerRadius, outerDiameter, outerDiameter, 
                                              Math.toDegrees(angle), 18, Arc2D.PIE);

            // Create a shape for the inner arc (hole)
            Arc2D innerArc = new Arc2D.Double(centerX - innerRadius, centerY - innerRadius, innerDiameter, innerDiameter, 
                                              Math.toDegrees(angle), 18, Arc2D.PIE);

            // Create the ring by subtracting the inner arc from the outer arc
            Area ring = new Area(outerArc);
            ring.subtract(new Area(innerArc));

            // Fill the resulting ring
            g2d.fill(ring);
        }
        
    }
    //
    private void drawNumbers(Graphics2D g2d, int centerX, int centerY, int radius, int dartboardRadius) {
        String[] numbers = {"20", "1", "18", "4", "13", "6", "10", "15", "2", "17", 
                            "3", "19", "7", "16", "8", "11", "14", "9", "12", "5"};

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, dartboardRadius / 15));

        for (int i = 0; i < 20; i++) {
            double angle = Math.toRadians(i * 18 - 89); // Adjusted for correct alignment of numbers

            int x = centerX + (int) ((radius + dartboardRadius / 15) * Math.cos(angle));
            int y = centerY + (int) ((radius + dartboardRadius / 15) * Math.sin(angle));

            AffineTransform original = g2d.getTransform();

            // Rotate text so it faces outwards
            g2d.translate(x, y);
            g2d.rotate(angle + Math.toRadians(90));

            // Draw the number
            String number = numbers[i];
            int strWidth = g2d.getFontMetrics().stringWidth(number);
            g2d.drawString(number, -strWidth / 2, 0);

            g2d.setTransform(original);
        }
    }
    // //
    
    // Graphics for throwing Darts
    public void drawDarts(Graphics g) {
        for (DartView dart : darts) {
            dart.draw(g);
        }
    }
    //
    public void addDart(float x, float y,Color color) {
    	System.out.println("input:" + x + " " + y);
    	x = x*diameter;
    	y = (getHeight()-diameter)/2 + y*diameter;
    	System.out.println("real :" + x + " " + y);
        darts.add(new DartView((int)x,(int)y, color, (int)diameter/18));
        System.out.println("dart added");
        repaint();  // Repaint the view to show the new dart
    }
    //
    public void clearDarts() {
        darts.clear();
        repaint();  // Repaint the view to clear the darts
    }
    // //
    
    // Graphics for AmimngIndicators
    private void drawAimingIndicators(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        int height = getHeight();
        
        // Draw the bottom aiming bar
        int barHeight = 10; // Height of the aiming bar
        g2d.fillRect(0, (height/2) + (diameter/2) - barHeight, diameter, barHeight);
        
        // Draw the right aiming bar
        g2d.fillRect(0, (height/2) - (diameter/2) - barHeight, barHeight, diameter);
       
        // Draw visual cues for center and triple ring
        int centerX = diameter / 2;
        int centerY = height / 2;
        
        // Center marker on bottom bar
        g2d.setColor(Color.BLUE);
        g2d.fillRect(centerX - (int)(( 15.9 / 200.0 * (diameter))/2), (height/2) + (diameter/2) - barHeight, (int)( 15.9 / 200.0 * (diameter)), barHeight); // Center marker

        // Center marker on left bar
        g2d.fillRect(0, centerY - (int)(( 15.9 / 200.0 * (diameter))/2), barHeight, (int)( 15.9 / 200.0 * (diameter))); // Center marker
        
        // Draw the aiming indicators
        int aimXPos = (int) (aimX * diameter);
        int aimYPos = (int) (aimY * diameter) + ((height/2) - (diameter/2));
        // Draw the aiming indicator on the bottom bar
        g2d.setColor(Color.RED);
        g2d.fillRect(aimXPos-5, (height/2) + (diameter/2) - barHeight, 10, barHeight); // Small rectangle for aiming indicator

        // Draw the aiming indicator on the left bar
        g2d.fillRect(0, aimYPos - 5, barHeight, 10); // Small rectangle for aiming indicator
    }
    
    // Method to update the aiming position
    public void setAimingPosition(float x, float y) {
       // this.aimX = Math.max(0, Math.min(1, x)); // Clamp x between 0 and 1
        //this.aimY = Math.max(0, Math.min(1, y)); // Clamp y between 0 and 1
    	aimX = x;
    	aimY = y;
    	drawAimingIndicators(g);
        repaint(); // Repaint to update the aiming indicators
    }
    // //
}
