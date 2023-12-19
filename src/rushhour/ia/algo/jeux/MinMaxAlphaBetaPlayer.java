package rushhour.ia.algo.jeux;

import rushhour.ia.framework.common.Action;
import rushhour.ia.framework.common.ActionValuePair;
import rushhour.ia.framework.jeux.Game;
import rushhour.ia.framework.jeux.GameState;
import rushhour.ia.framework.jeux.Player;
import rushhour.ia.problemes.ConnectFourState;

import java.util.Arrays;

public class MinMaxAlphaBetaPlayer extends Player {

    private int numStates = 0;
    private int profondeur = 0;
    private int profondeurMax = 500000;

    public MinMaxAlphaBetaPlayer(Game g, boolean p1) {
        super(g, p1);
        name = "MinMaxAlphaBeta";
    }

    @Override
    public Action getMove(GameState state) {
        profondeur = 0;
        if (player == PLAYER1)
            return MaxValeur(state, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY).getAction();
        else
            return MinValeur(state, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY).getAction();
    }

    private ActionValuePair MaxValeur(GameState s, double alpha, double beta) {
        if (game.endOfGame(s)) {
            return new ActionValuePair(null, s.getGameValue());
        }

        if (profondeur >= profondeurMax) {
            return evaluate(s);
        }

        double vMax = Double.NEGATIVE_INFINITY;
        Action cMax = null;
        profondeur++;
        for (Action c : game.getActions(s)) {
            numStates++;
            GameState sSuivant = (GameState) game.doAction(s, c);
            ActionValuePair v = MinValeur(sSuivant, alpha, beta);
            if (v.getValue() > vMax) {
                vMax = v.getValue();
                cMax = c;
                if (vMax > alpha) {
                    alpha = vMax;
                }
            }
            if (vMax >= beta) {
                return new ActionValuePair(cMax, vMax);
            }
        }
        return new ActionValuePair(cMax, vMax);
    }

    private ActionValuePair evaluate(GameState s) {
        ConnectFourState state = (ConnectFourState) s;
        int possibleWinsX = 0;
        int possibleWinsO = 0;
        int[][] board = state.getBoard();
        int rows = state.getRows();
        int cols = state.getCols();
        // possible horizontal wins
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols - 3; c++) {
                if (r + 4 > rows)
                    break;
                possibleWinsX += isEqualOrEmpty(board[r][c + 1], ConnectFourState.X) + isEqualOrEmpty(board[r][c + 2], ConnectFourState.X) + isEqualOrEmpty(board[r][c + 3], ConnectFourState.X);
                possibleWinsO += isEqualOrEmpty(board[r][c + 1], ConnectFourState.O) + isEqualOrEmpty(board[r][c + 2], ConnectFourState.O) + isEqualOrEmpty(board[r][c + 3], ConnectFourState.O);
            }
        }
        // possible vertical wins
        for (int r = 0; r < rows - 3; r++) {
            for (int c = 0; c < cols; c++) {
                if (c + 4 > cols)
                    break;
                possibleWinsX += isEqualOrEmpty(board[r + 1][c], ConnectFourState.X) + isEqualOrEmpty(board[r + 2][c], ConnectFourState.X) + isEqualOrEmpty(board[r + 3][c], ConnectFourState.X);
                possibleWinsO += isEqualOrEmpty(board[r + 1][c], ConnectFourState.O) + isEqualOrEmpty(board[r + 2][c], ConnectFourState.O) + isEqualOrEmpty(board[r + 3][c], ConnectFourState.O);
            }
        }
        // possible diagonal up wins
        for (int r = 0; r < rows - 3; r++) {
            for (int c = 0; c < cols - 3; c++) {
                if (r + 4 > rows || c + 4 > cols)
                    break;
                possibleWinsX += isEqualOrEmpty(board[r + 1][c + 1], ConnectFourState.X) + isEqualOrEmpty(board[r + 2][c + 2], ConnectFourState.X) + isEqualOrEmpty(board[r + 3][c + 3], ConnectFourState.X);
                possibleWinsO += isEqualOrEmpty(board[r + 1][c + 1], ConnectFourState.O) + isEqualOrEmpty(board[r + 2][c + 2], ConnectFourState.O) + isEqualOrEmpty(board[r + 3][c + 3], ConnectFourState.O);
            }
        }
        // possible diagonal down wins
        for (int r = 3; r < rows; r++) {
            for (int c = 0; c < cols - 3; c++) {
                if (r - 4 < 0 || c + 4 > cols)
                    break;
                possibleWinsX += isEqualOrEmpty(board[r - 1][c + 1], ConnectFourState.X) + isEqualOrEmpty(board[r - 2][c + 2], ConnectFourState.X) + isEqualOrEmpty(board[r - 3][c + 3], ConnectFourState.X);
                possibleWinsO += isEqualOrEmpty(board[r - 1][c + 1], ConnectFourState.O) + isEqualOrEmpty(board[r - 2][c + 2], ConnectFourState.O) + isEqualOrEmpty(board[r - 3][c + 3], ConnectFourState.O);
            }
        }
        double finalValue = 0;
        if (possibleWinsX > possibleWinsO) {
            finalValue = (double) possibleWinsO / possibleWinsX;
        } else if (possibleWinsX < possibleWinsO) {
            finalValue = (double) possibleWinsX / possibleWinsO;
        } else {
            finalValue = 0.5;
        }
        return new ActionValuePair(null, finalValue);
    }

    private int isEqualOrEmpty(int a, int b) {
        if (a == b) {
            return 2;
        } else if (a == 0) {
            return 1;
        }
        return 0;
    }

    private ActionValuePair MinValeur(GameState s, double alpha, double beta) {
        if (game.endOfGame(s)) {
            return new ActionValuePair(null, s.getGameValue());
        }

        if (profondeur >= profondeurMax) {
            return evaluate(s);
        }

        double vMin = Double.POSITIVE_INFINITY;
        Action cMin = null;
        profondeur++;

        for (Action c : game.getActions(s)) {
            numStates++;
            GameState sSuivant = (GameState) game.doAction(s, c);
            ActionValuePair v = MaxValeur(sSuivant, alpha, beta);
            if (v.getValue() < vMin) {
                vMin = v.getValue();
                cMin = c;
                if (vMin < beta) {
                    beta = vMin;
                }
            }
            if (vMin <= alpha) {
                return new ActionValuePair(cMin, vMin);
            }
        }
        return new ActionValuePair(cMin, vMin);
    }

}
