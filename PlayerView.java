package vlKlapptDart;
import java.awt.Color;

public class PlayerView {
    private String name;
    private Color color;
    private int points;

    public PlayerView(String name, Color color, int points) {
        this.name = name;
        this.color = color;
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
