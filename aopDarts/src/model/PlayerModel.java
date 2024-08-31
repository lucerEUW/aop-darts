package model;
import java.awt.Color;

public class PlayerModel {
    private String name;
    private Color color;
    private int points;

    public void setPoints(int points) {
	    this.points = points;
	}

	public PlayerModel(String name, Color color, int points) {
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
}
