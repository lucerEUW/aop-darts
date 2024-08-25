package vlKlapptDart;

import javax.swing.*;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.Scanner;


public class DartboardView extends JPanel {
	private ArrayList<DartView> darts = new ArrayList<DartView>();
	private int diameter = 0;
	
    public DartboardView() {
        this.setBackground(Color.WHITE); // Background color
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawDartboard(g);
        drawDarts(g);
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

        // Calculate radii based on the dimensions in the image
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
    
    private void drawDarts(Graphics g) {
        for (DartView dart : darts) {
            dart.draw(g);
        }
    }

    public void addDart(float x, float y,Color color) {
    	System.out.println("input:" + x + " " + y);
    	x = x*diameter;
    	y = (getHeight()-diameter)/2 + y*diameter;
    	System.out.println(" real:" + x + " " + y);
        darts.add(new DartView((int)x,(int)y, color, (int)diameter/18));
        repaint();  // Repaint the view to show the new dart
    }

    public void clearDarts() {
        darts.clear();
        repaint();  // Repaint the view to clear the darts
    }
    
    
    // Main is only for testing not final and should be deleted at some point
    public static void main(String[] args) {
    	//SwingUtilities.invokeLater(Menu::new); // Start with the menu
    	
        // Initialize the model, view, and controller
        DartboardView view = new DartboardView();

        // Create the JFrame outside the invokeLater block to make it accessible
        JFrame frame = new JFrame("Dartboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(view);
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
        
        // Set the frame to always be on top
        frame.setAlwaysOnTop(true);

        // Display the frame on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> frame.setVisible(true));

        // Simulate reading user input and adding a dart
        // Write Coordinates or c to clearthe board
        Scanner reader = new Scanner(System.in);
        
        System.out.println("Coordinates go from 0.00 to 0.99, 0.50 is the center");
        System.out.println("Write Coordinates like this: \"1356\" : x = 0.13 y = 0.56");
        
        for(int i = 0; i< 50; i++) {
        	String tmp = reader.nextLine();
        	if(tmp.equals("c")) {
        		SwingUtilities.invokeLater(() -> view.clearDarts());
        	}else {
        		int x = Integer.parseInt(tmp.substring(0, 2));
        		int y = Integer.parseInt(tmp.substring(2, 4));
        		SwingUtilities.invokeLater(() -> view.addDart((float)x/100,(float)y/100,Color.green));
        		
        	}
        }
        reader.close();
        
    }
    
}
