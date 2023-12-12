package rushhour.ia.problemes;

import rushhour.ia.framework.common.Action;

import java.util.Objects;

public class RushHourCar {

    private int length;
    private int[][] position;
    private char name;
    private boolean isVertical;

    public RushHourCar(int length, int[][] position, char name, boolean isVertical) {
        this.length = length;
        this.position = position;
        this.name = name;
        this.isVertical = isVertical;
    }

    public int getLength() {
        return length;
    }

    public char getName() {
        return name;
    }

    public boolean isMoveLegal(Action action) {
        return switch (action.getName()) {
            case "UP" -> isVertical && position[0][1] > 0;
            case "DOWN" -> isVertical && position[length - 1][1] < 5;
            case "LEFT" -> !isVertical && position[0][0] > 0;
            case "RIGHT" -> !isVertical && position[length - 1][0] < 5;
            default -> false;
        };
    }

    public void doAction(Action action) {
        if (!isMoveLegal(action)) {
            return;
        }
        switch (action.getName()) {
            case "UP" -> {
                for (int i = 0; i < length; i++) {
                    position[i][1]--;
                }
            }
            case "DOWN" -> {
                for (int i = 0; i < length; i++) {
                    position[i][1]++;
                }
            }
            case "LEFT" -> {
                for (int i = 0; i < length; i++) {
                    position[i][0]--;
                }
            }
            case "RIGHT" -> {
                for (int i = 0; i < length; i++) {
                    position[i][0]++;
                }
            }
        }
    }

    public int[][] getPosition() {
        return position;
    }
}
