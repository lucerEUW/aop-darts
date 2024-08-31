package model;

public class PointCalculator {

    // Radius of the dartboard in some unit (e.g., pixels)
    static int radius = 280;
    
    // Constants for the center of the dartboard
    private static final int CENTER_X = 50;
    private static final int CENTER_Y = 50;
    
    // Radii for different scoring areas on the dartboard
    private static final double BULLSEYE_RADIUS = 1; // Bullseye (50 points)
    private static final double INNER_BULL_RADIUS = 4; // Inner Bull (25 points)
    private static final double TRIPLE_RING_INNER_RADIUS = 25; // Inner boundary of the triple ring
    private static final double TRIPLE_RING_OUTER_RADIUS = 27; // Outer boundary of the triple ring
    private static final double DOUBLE_RING_INNER_RADIUS = 40; // Inner boundary of the double ring
    private static final double DOUBLE_RING_OUTER_RADIUS = 42; // Outer boundary of the double ring
    
    private double calculateDistance(int x, int y) {
        return Math.sqrt(Math.pow(x - CENTER_X, 2) + Math.pow(y - CENTER_Y, 2));
    }

    private double calculateAngle(int x, int y) {
        double angle = Math.toDegrees(Math.atan2(y - CENTER_Y, x - CENTER_X));
        if (angle < 0) {
            angle += 360; // Adjusts for negative angles to keep the result between 0° and 360°
        }
        return angle;
    }

    private int getSector(double angle) {
        int[] sectors = {6, 10, 15, 2, 17, 3, 19, 7, 16, 8, 11, 14, 9, 12, 5, 20, 1, 18, 4, 13};
        int sectorIndex = (int) ((angle + 9) % 360) / 18; // +9 shifts to the middle of each sector, /18 for 20 sectors
        return sectors[sectorIndex];
    }

    public int checkHit(int x, int y) {
        double distance = calculateDistance(x, y); // Calculate the distance from the center
        double angle = calculateAngle(x, y); // Calculate the angle relative to the center
        int sector = getSector(angle); // Determine the sector number
        
        // Determine the score based on distance and sector
        if (distance <= BULLSEYE_RADIUS) {
            return 50; // Bullseye
        } else if (distance <= INNER_BULL_RADIUS) {
            return 25; // Inner Bull (Single Bullseye)
        } else if (distance >= TRIPLE_RING_INNER_RADIUS && distance <= TRIPLE_RING_OUTER_RADIUS) {
            return 3 * sector; // Triple score for that sector
        } else if (distance >= DOUBLE_RING_INNER_RADIUS && distance <= DOUBLE_RING_OUTER_RADIUS) {
            return 2 * sector; // Double score for that sector
        } else if (distance <= DOUBLE_RING_OUTER_RADIUS) {
            return sector; // Single score for that sector
        } else {
            return 0; // Outside the dartboard scoring area
        }
    }
}
