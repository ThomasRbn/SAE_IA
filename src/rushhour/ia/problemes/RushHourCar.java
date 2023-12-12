package rushhour.ia.problemes;

import rushhour.ia.framework.common.Action;

import java.util.List;

public class RushHourCar {

    protected int length;
    protected int[][] position;
    protected char name;
    protected boolean isVertical;

    public RushHourCar(int length, int[][] position, char name, boolean isVertical) {
        this.length = length;
        this.position = position;
        this.name = name;
        this.isVertical = isVertical;
    }

    public boolean isLegal(Action a, List<RushHourCar> cars) {
        boolean collision = false;
        return switch (a.getName()) {
            case "UP" -> {
                if (!this.isVertical) {
                    yield false;
                }

                for (RushHourCar car : cars) {
                    if (this.position[0][1] == car.getPosition()[0][1] && this.name != car.getName()) {
                        collision = true;
                    }
                }

                yield this.position[0][1] > 0 && !collision;
            }
            case "DOWN" -> {
                if (!this.isVertical) {
                    yield false;
                }

                for (RushHourCar car : cars)
                    if (this.position[this.length - 1][1] == car.getPosition()[car.getLength() - 1][1] && this.name != car.getName())
                        collision = true;

                yield this.position[this.length - 1][1] < 5 && !collision;
            }
            case "LEFT" -> {
                if (this.isVertical) {
                    yield false;
                }

                for (RushHourCar car : cars)
                    if (this.position[0][0] == car.getPosition()[0][0] && this.name != car.getName())
                        collision = true;

                yield this.position[0][0] > 0 && !collision;
            }
            case "RIGHT" -> {
                if (this.isVertical) {
                    yield false;
                }

                for (RushHourCar car : cars)
                    if (this.position[this.length - 1][0] == car.getPosition()[car.getLength() - 1][0] && this.name != car.getName())
                        collision = true;
                yield this.position[this.length - 1][0] < 5 && !collision;
            }
            default -> false;
        };
    }

    public int getLength() {
        return length;
    }

    public char getName() {
        return name;
    }

    public int[][] getPosition() {
        return position;
    }
}
