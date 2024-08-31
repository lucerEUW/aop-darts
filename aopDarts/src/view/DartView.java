package view;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class DartView {
    private int x;
    private int y;
    private Color color;
    private int size;

    public DartView(int x, int y, Color color, int size) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.size = size;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Color getColor() {
        return color;
    }

    public int getSize() {
        return size;
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int dartRadius = size / 2;
        int xCenter = (int) (x - dartRadius * 0.6);
        int yCenter = (int) (y - dartRadius * 0.6);

        // Draw the dart circle
        g.setColor(color);
        g.fillOval(xCenter, yCenter, (int)(size * 0.6), (int)(size * 0.6));

        // Draw the "X" using two rectangles
        g.setColor(Color.DARK_GRAY);
        int rectThickness = size / 6;  // Thickness of the rectangles forming the "X"

        // Rectangle 1: Top-left to bottom-right diagonal
        g2d.rotate(Math.toRadians(45), x, y);
        g2d.fillRect(x - rectThickness / 2, y - dartRadius, rectThickness, size);

        // Rectangle 2: Top-right to bottom-left diagonal
        g2d.rotate(Math.toRadians(90), x, y); // Rotate by 90 degrees from the previous angle
        g2d.fillRect(x - rectThickness / 2, y - dartRadius, rectThickness, size);

        // Reset the rotation back to the original state
        g2d.setTransform(new AffineTransform());
    }
}
