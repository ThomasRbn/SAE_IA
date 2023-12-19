package rushhour.ia.problemes;

import rushhour.ia.framework.common.State;
import rushhour.ia.framework.recherche.HasHeuristic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class RushHourState extends State implements HasHeuristic {

    private List<RushHourCar> cars;
    private char[][] board;

    public RushHourState() {
        cars = new ArrayList<>();
        board = new char[8][6];
        for (int i = 0; i < 8; i++) {
            board[i] = new char[8];
            for (int j = 0; j < 6; j++) {
                board[i][j] = ' ';
            }
        }

        //Niveau BEGINNER
        /*RushHourCar redcar = new RushHourCar(2, new int[][]{{0, 2}, {1, 2}}, 'R', false);
        RushHourCar car1 = new RushHourCar(3, new int[][]{{2, 0}, {2, 1}, {2, 2}}, 'A', true);
        RushHourCar car2 = new RushHourCar(2, new int[][]{{4, 0}, {5, 0}}, 'B', false);
        RushHourCar car3 = new RushHourCar(3, new int[][]{{0, 3}, {1, 3}, {2, 3}}, 'C', false);
        RushHourCar car4 = new RushHourCar(3, new int[][]{{5, 3}, {5, 4}, {5, 5}}, 'D', true);
        cars.addAll(List.of(redcar, car1, car2, car3, car4));*/

        //Niveau EXPERT
        RushHourCar redcar = new RushHourCar(2, new int[][]{{1, 2}, {2, 2}}, 'R', false);
        RushHourCar car1 = new RushHourCar(2, new int[][]{{0, 0}, {0, 1}}, 'A', true);
        RushHourCar car2 = new RushHourCar(2, new int[][]{{1, 0}, {1, 1}}, 'B', true);
        RushHourCar car3 = new RushHourCar(2, new int[][]{{2, 0}, {3, 0}}, 'C', false);
        RushHourCar car4 = new RushHourCar(2, new int[][]{{4, 0}, {4, 1}}, 'D', true);
        RushHourCar car5 = new RushHourCar(2, new int[][]{{5, 0}, {5, 1}}, 'E', true);
        RushHourCar car6 = new RushHourCar(3, new int[][]{{0, 2}, {0, 3}, {0, 4}}, 'F', true);
        RushHourCar car7 = new RushHourCar(2, new int[][]{{1, 3}, {2, 3}}, 'G', false);
        RushHourCar car8 = new RushHourCar(2, new int[][]{{3, 2}, {3, 3}}, 'H', true);
        RushHourCar car9 = new RushHourCar(2, new int[][]{{5, 2}, {5, 3}}, 'I', true);
        RushHourCar car10 = new RushHourCar(2, new int[][]{{0, 5}, {1, 5}}, 'J', false);
        RushHourCar car11 = new RushHourCar(2, new int[][]{{2, 4}, {2, 5}}, 'K', true);
        RushHourCar car12 = new RushHourCar(3, new int[][]{{3, 4}, {4, 4}, {5, 4}},'L', false);
        RushHourCar car13 = new RushHourCar(3, new int[][]{{3, 5}, {4, 5}, {5, 5}},'M', false);
        cars.addAll(List.of(redcar, car1, car2, car3, car4, car5, car6, car7, car8, car9, car10, car11, car12, car13));

        for (RushHourCar car : cars) {
            int[][] positions = car.getPosition();
            for (int i = 0; i < car.getLength(); i++) {
                board[positions[i][0]][positions[i][1]] = car.getName();
            }
        }
    }

    public RushHourState(RushHourState rh) {
        this.cars = new ArrayList<>();
        for (RushHourCar car : rh.cars) {
            this.cars.add(new RushHourCar(car));
        }
        this.board = new char[rh.board.length][rh.board[0].length];
        updateBoard();
    }

    public void updateBoard() {
        for (int i = 0; i < 8; i++) {
            board[i] = new char[6];
            for (int j = 0; j < 6; j++) {
                board[i][j] = ' ';
            }
        }

        for (RushHourCar car : cars) {
            int[][] positions = car.getPosition();
            for (int i = 0; i < car.getLength(); i++) {
                board[positions[i][0]][positions[i][1]] = car.getName();
            }
        }
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
        System.out.println(Arrays.deepToString(pos));
        for (int i = 0; i < carToMove.getLength(); i++) {
            pos[i][0]++;
        }
        System.out.println(Arrays.deepToString(pos));
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
        System.out.println(Arrays.deepToString(pos));
        for (int i = 0; i < carToMove.getLength(); i++) {
            pos[i][0]--;
        }
        System.out.println(Arrays.deepToString(pos));
    }

    @Override
    public String toString() {
        return "\t  0   1   2   3   4   5   6   7\n" +
                "\t+---+---+---+---+---+---+\n" +
                "0   |  " + board[0][0] + "| " + board[1][0] + " | " + board[2][0] + " | " + board[3][0] + " | " + board[4][0] + " | " + board[5][0] + " |\n" +
                "\t+---+---+---+---+---+---+\n" +
                "1   | " + board[0][1] + " | " + board[1][1] + " | " + board[2][1] + " | " + board[3][1] + " | " + board[4][1] + " | " + board[5][1] + " |\n" +
                "\t+---+---+---+---+---+---+---+---+\n" +
                "2   | " + board[0][2] + " | " + board[1][2] + " | " + board[2][2] + " | " + board[3][2] + " | " + board[4][2] + " | " + board[5][2] + " | " + board[6][2] + " | " + board[7][2] + " |\n" +
                "\t+---+---+---+---+---+---+---+---+\n" +
                "3   | " + board[0][3] + " | " + board[1][3] + " | " + board[2][3] + " | " + board[3][3] + " | " + board[4][3] + " | " + board[5][3] + " |\n" +
                "\t+---+---+---+---+---+---+\n" +
                "4   | " + board[0][4] + " | " + board[1][4] + " | " + board[2][4] + " | " + board[3][4] + " | " + board[4][4] + " | " + board[5][4] + " |\n" +
                "\t+---+---+---+---+---+---+\n" +
                "5   | " + board[0][5] + " | " + board[1][5] + " | " + board[2][5] + " | " + board[3][5] + " | " + board[4][5] + " | " + board[5][5] + " |\n" +
                "\t+---+---+---+---+---+---+\n";

    }

    @Override
    protected State cloneState() {
        return new RushHourState(this);
    }

    @Override
    protected boolean equalsState(State o) {
        RushHourState that = (RushHourState) o;
        return Arrays.deepEquals(board, that.board);
    }

    @Override
    protected int hashState() {
        return Objects.hash(cars);
    }

    @Override
    public double getHeuristic() {
        int endPoint = 7;
        int carPoint = cars.get(0).getPosition()[1][0];
        return endPoint - carPoint;
    }
}
