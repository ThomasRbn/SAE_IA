package rushhour.ia.problemes;

import rushhour.ia.framework.common.Action;
import rushhour.ia.framework.common.State;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RushHourState extends State {

    private List<RushHourCar> cars;
    private char[][] board;

    public RushHourState() {
        board = new char[6][6];
        for (int i = 0; i < 6; i++) {
            board[i] = new char[6];
            for (int j = 0; j < 6; j++) {
                board[i][j] = ' ';
            }
        }

        RushHourCar redcar = new RushHourCar(2, new int[][]{{0, 2}, {1, 2}}, 'R', false);
        RushHourCar car1 = new RushHourCar(3, new int[][]{{2, 0}, {2, 1}, {2, 2}}, 'A', true);
        RushHourCar car2 = new RushHourCar(2, new int[][]{{4, 0}, {5, 0}}, 'B', false);
        RushHourCar car3 = new RushHourCar(3, new int[][]{{0, 3}, {1, 3}, {2, 3}}, 'C', false);
        RushHourCar car4 = new RushHourCar(3, new int[][]{{5, 3}, {5, 4}, {5, 5}}, 'D', true);
        cars = new ArrayList<>();
        cars.addAll(List.of(redcar, car1, car2, car3, car4));

        for (RushHourCar car : cars) {
            int[][] positions = car.getPosition();
            for (int i = 0; i < car.getLength(); i++) {
                board[positions[i][0]][positions[i][1]] = car.getName();
            }
        }

        System.out.println(this);
    }

    public RushHourState(char[][] board, List<RushHourCar> cars) {
        this.board = board;
        this.cars = cars;
    }

    public RushHourCar getCarToMove(char name) {
        for (RushHourCar car : cars) {
            if (car.name == name) {
                return car;
            }
        }
        return null;
    }

    public char[][] getBoard() {
        return board;
    }

    public List<RushHourCar> getCars() {
        return cars;
    }

    public void moveCarUp(char car) {
        RushHourCar carToMove = getCarToMove(car);
        int[][] pos = carToMove.getPosition();
        for (int i = 0; i < carToMove.getLength(); i++) {
            pos[i][1]--;
        }
    }

    public void moveCarRight(char car) {
        RushHourCar carToMove = getCarToMove(car);
        int[][] pos = carToMove.getPosition();
        for (int i = 0; i < carToMove.getLength(); i++) {
            pos[i][0]++;
        }
    }

    public void moveCarDown(char car) {
        RushHourCar carToMove = getCarToMove(car);
        int[][] pos = carToMove.getPosition();
        for (int i = 0; i < carToMove.getLength(); i++) {
            pos[i][1]++;
        }
    }

    public void moveCarLeft(char car) {
        RushHourCar carToMove = getCarToMove(car);
        int[][] pos = carToMove.getPosition();
        for (int i = 0; i < carToMove.getLength(); i++) {
            pos[i][0]--;
        }
    }

    @Override
    public String toString() {
        return "\t  0   1   2   3   4   5\n" +
                "\t+---+---+---+---+---+---+\n" +
                "0   |  " + board[0][0] + "| " + board[1][0] + " | " + board[2][0] + " | " + board[3][0] + " | " + board[4][0] + " | " + board[5][0] + " |\n" +
                "\t+---+---+---+---+---+---+\n" +
                "1   | " + board[0][1] + " | " + board[1][1] + " | " + board[2][1] + " | " + board[3][1] + " | " + board[4][1] + " | " + board[5][1] + " |\n" +
                "\t+---+---+---+---+---+---+\n" +
                "2   | " + board[0][2] + " | " + board[1][2] + " | " + board[2][2] + " | " + board[3][2] + " | " + board[4][2] + " | " + board[5][2] + " |\n" +
                "\t+---+---+---+---+---+---+\n" +
                "3   | " + board[0][3] + " | " + board[1][3] + " | " + board[2][3] + " | " + board[3][3] + " | " + board[4][3] + " | " + board[5][3] + " |\n" +
                "\t+---+---+---+---+---+---+\n" +
                "4   | " + board[0][4] + " | " + board[1][4] + " | " + board[2][4] + " | " + board[3][4] + " | " + board[4][4] + " | " + board[5][4] + " |\n" +
                "\t+---+---+---+---+---+---+\n" +
                "5   | " + board[0][5] + " | " + board[1][5] + " | " + board[2][5] + " | " + board[3][5] + " | " + board[4][5] + " | " + board[5][5] + " |\n" +
                "\t+---+---+---+---+---+---+\n";

    }

    @Override
    protected State cloneState() {
        return new RushHourState(board, cars);
    }

    @Override
    protected boolean equalsState(State o) {
        RushHourState other = (RushHourState) o;
        return Arrays.deepEquals(board, other.board)
                && cars.equals(other.cars);
    }

    @Override
    protected int hashState() {
        return 31 * cars.hashCode()
                + 31 * Arrays.deepHashCode(board);
    }
}
