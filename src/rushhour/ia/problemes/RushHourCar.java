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
        return this.isVertical ? isLegalVertical(a, cars) : isLegalHorizontal(a, cars);
    }

    public boolean isLegalVertical(Action a, List<RushHourCar> cars) {
        boolean collision = false;
        return switch (a.getName()) {
            case "UP" -> {
                for (RushHourCar car : cars) {
                    if (car != this) {
                        for (int i = 0; i < this.length; i++) {
                            for (int j = 0; j < car.length; j++) {
                                if (this.position[i][0] == car.position[j][0] && this.position[i][1] - 1 == car.position[j][1]) {
                                    collision = true;
                                    break;
                                }
                            }
                        }
                    }
                }
                yield this.position[0][1] > 0 && !collision;
            }
            case "DOWN" -> {
                for (RushHourCar car : cars) {
                    if (car != this) {
                        for (int i = 0; i < this.length; i++) {
                            for (int j = 0; j < car.length; j++) {
                                if (this.position[i][0] == car.position[j][0] && this.position[i][1] + 1 == car.position[j][1]) {
                                    collision = true;
                                    break;
                                }
                            }
                        }
                    }
                }
                yield this.position[this.length - 1][1] < 5 && !collision;
            }
            default -> false;
        };
    }

    public boolean isLegalHorizontal(Action a, List<RushHourCar> cars) {
        boolean collision = false;
        return switch (a.getName()) {
            case "LEFT" -> {
                for (RushHourCar car : cars) {
                    if (car != this) {
                        for (int i = 0; i < this.length; i++) {
                            for (int j = 0; j < car.length; j++) {
                                if (this.position[i][0] - 1 == car.position[j][0] && this.position[i][1] == car.position[j][1]) {
                                    collision = true;
                                    break;
                                }
                            }
                        }
                    }
                }
                yield this.position[0][0] > 0 && !collision;
            }
            case "RIGHT" -> {
                for (RushHourCar car : cars) {
                    if (car != this) {
                        for (int i = 0; i < this.length; i++) {
                            for (int j = 0; j < car.length; j++) {
                                if (this.position[i][0] + 1 == car.position[j][0] && this.position[i][1] == car.position[j][1]) {
                                    collision = true;
                                    break;
                                }
                            }
                        }
                    }
                }

                if (this.getName() == 'R') {
                    yield this.position[this.length - 1][0] < 7 && !collision;
                }
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
        int[][] positionCopy = new int[position.length][position[0].length];
        for (int i = 0; i < position.length; i++) {
            positionCopy[i] = position[i].clone();
        }
        return positionCopy;
    }

    public boolean isVertical() {
        return isVertical;
    }
}
