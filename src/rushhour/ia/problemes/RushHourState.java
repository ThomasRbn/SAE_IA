package rushhour.ia.problemes;

import rushhour.ia.framework.common.State;
import rushhour.ia.framework.jeux.GameState;

import java.util.Arrays;
import java.util.List;

public class RushHourState extends GameState {


    private char[][] board;

    public RushHourState(List<RushHourCar> cars) {
        this.board = new char[6][6];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6 ; j++) {
                board[i][j] = ' ';
            }
        }

        for (RushHourCar car : cars) {
            for (int[] position : car.getPosition()) {
                board[position[0]][position[1]] = car.getName();
            }
        }
    }

    public char[][] getBoard() {
        return board;
    }

    public void setPlayerToMove(int player_to_move) {
        this.player_to_move = player_to_move;
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
        return this.clone();
    }

    @Override
    protected int hashState() {
        return Arrays.deepHashCode(board);
    }
}
