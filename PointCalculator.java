package vlKlapptDart;

public class PointCalculator {
	
	static int radius = 280;
    // Konstanten f�r die Mitte der Scheibe
    private static final int CENTER_X = 50;
    private static final int CENTER_Y = 50;
    
    // Radien f�r die Bereiche
    private static final double BULLSEYE_RADIUS = 1; // Bullseye
    private static final double INNER_BULL_RADIUS = 4; // Inner Bull (Single Bullseye)
    private static final double TRIPLE_RING_INNER_RADIUS = 25;
    private static final double TRIPLE_RING_OUTER_RADIUS = 27;
    private static final double DOUBLE_RING_INNER_RADIUS = 40;
    private static final double DOUBLE_RING_OUTER_RADIUS = 42;
    /*
    private static final double BULLSEYE_RADIUS = 5.0; // Bullseye
    private static final double INNER_BULL_RADIUS = 10.0; // Inner Bull (Single Bullseye)
    private static final double TRIPLE_RING_INNER_RADIUS = 25.0;
    private static final double TRIPLE_RING_OUTER_RADIUS = 30.0;
    private static final double DOUBLE_RING_INNER_RADIUS = 40.0;
    private static final double DOUBLE_RING_OUTER_RADIUS = 45.0;
    */
    /**
     * Berechnet die Distanz zwischen einem Punkt und der Mitte der Scheibe.
     *
     * @param x X-Koordinate des Wurfs
     * @param y Y-Koordinate des Wurfs
     * @return die Distanz zum Mittelpunkt der Scheibe
     */
    private double calculateDistance(int x, int y) {
        return Math.sqrt(Math.pow(x - CENTER_X, 2) + Math.pow(y - CENTER_Y, 2));
    }

    /**
     * Berechnet den Winkel eines Punkts relativ zur Mitte.
     *
     * @param x X-Koordinate des Wurfs
     * @param y Y-Koordinate des Wurfs
     * @return Winkel in Grad relativ zu 0� (oben)
     */
    private double calculateAngle(int x, int y) {
        double angle = Math.toDegrees(Math.atan2(y - CENTER_Y, x - CENTER_X));
        if (angle < 0) {
            angle += 360;
        }
        return angle;
    }

    /**
     * Bestimmt den Sektor basierend auf dem Winkel.
     *
     * @param angle Winkel in Grad relativ zu 0� (oben)
     * @return die Nummer des Sektors (1-20)
     */
    private int getSector(double angle) {
        int[] sectors = {6, 10, 15, 2, 17, 3, 19, 7, 16, 8, 11, 14, 9, 12, 5, 20, 1, 18, 4, 13};
        int sectorIndex = (int) ((angle + 9) % 360) / 18; // +9 zur Mitte der Sektoren, /18 f�r 20 Sektoren
        return sectors[sectorIndex];
    }

    /**
     * �berpr�ft, in welchem Bereich der Wurf liegt.
     *
     * @param x X-Koordinate des Wurfs
     * @param y Y-Koordinate des Wurfs
     * @return eine Beschreibung des Treffers
     */
    public int checkHit(int x, int y) {
        double distance = calculateDistance(x, y);
        double angle = calculateAngle(x, y);
        int sector = getSector(angle);
        System.out.println("Distnce: " + distance);
       // System.out.println("Bullseye rdius:" + DOUBLE_RING_INNER_RADIUS);
        if (distance <= BULLSEYE_RADIUS) {
            return 50;
        } else if (distance <= INNER_BULL_RADIUS) {
            return 25;
        } else if (distance >= TRIPLE_RING_INNER_RADIUS && distance <= TRIPLE_RING_OUTER_RADIUS) {
            return 3* + sector;
        } else if (distance >= DOUBLE_RING_INNER_RADIUS && distance <= DOUBLE_RING_OUTER_RADIUS) {
            return 2* + sector;
        } else if (distance <= DOUBLE_RING_OUTER_RADIUS) {
            return + sector;
        } else {
            return 0;
        }
    }
    /*
    public static void main(String[] args) {
        PointCalculator detector = new PointCalculator();
        // Beispielaufrufe
        System.out.println(detector.checkHit(56,75)); // Bullseye!
        //System.out.println(detector.checkHit(60, 50)); // Single 6
        //System.out.println(detector.checkHit(55, 55)); // Single 1 (je nach Koordinaten und Sektorverteilung)
        //System.out.println(detector.checkHit(40, 65)); // Triple 14 (je nach Koordinaten)
        //System.out.println(detector.checkHit(90, 90)); // Missed the target
    }*/
}
