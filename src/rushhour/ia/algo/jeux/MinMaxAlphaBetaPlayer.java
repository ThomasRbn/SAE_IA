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
        int player = s.getPlayerToMove();
        ConnectFourState state = (ConnectFourState) s;
        int possibleWinsX = 0;
        int possibleWinsO = 0;
        boolean isGoalStateX = false;
        boolean isGoalStateY = false;
        int[][] board = state.getBoard();
        int rows = state.getRows();
        int cols = state.getCols();
        // possible horizontal wins
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols - 3; c++) {
                if (r + 4 > rows)
                    break;
                possibleWinsX += isEqualOrEmpty(board[r][c], ConnectFourState.X) + isEqualOrEmpty(board[r][c + 1], ConnectFourState.X) * 2 + isEqualOrEmpty(board[r][c + 2], ConnectFourState.X) * 10 + isEqualOrEmpty(board[r][c + 3], ConnectFourState.X) * 100;
                isGoalStateX = isGoalStateX || isGoalState(player, board[r][c], board[r][c + 1], board[r][c + 2], board[r][c + 3]);
                possibleWinsO += isEqualOrEmpty(board[r][c], ConnectFourState.O) + isEqualOrEmpty(board[r][c + 1], ConnectFourState.O) * 2 + isEqualOrEmpty(board[r][c + 2], ConnectFourState.O) * 10 + isEqualOrEmpty(board[r][c + 3], ConnectFourState.O) * 100;
                isGoalStateY = isGoalStateY || isGoalState(player, board[r][c], board[r][c + 1], board[r][c + 2], board[r][c + 3]);
            }
        }
        // possible vertical wins
        for (int r = 0; r < rows - 3; r++) {
            for (int c = 0; c < cols; c++) {
                if (c + 4 > cols)
                    break;
                possibleWinsX += isEqualOrEmpty(board[r][c], ConnectFourState.X) + isEqualOrEmpty(board[r + 1][c], ConnectFourState.X) * 2 + isEqualOrEmpty(board[r + 2][c], ConnectFourState.X) * 10 + isEqualOrEmpty(board[r + 3][c], ConnectFourState.X) * 100;
                isGoalStateX = isGoalStateX || isGoalState(player, board[r][c], board[r + 1][c], board[r + 2][c], board[r + 3][c]);
                possibleWinsO += isEqualOrEmpty(board[r][c], ConnectFourState.O) + isEqualOrEmpty(board[r + 1][c], ConnectFourState.O) * 2 + isEqualOrEmpty(board[r + 2][c], ConnectFourState.O) * 10 + isEqualOrEmpty(board[r + 3][c], ConnectFourState.O) * 100;
                isGoalStateY = isGoalStateY || isGoalState(player, board[r][c], board[r + 1][c], board[r + 2][c], board[r + 3][c]);
            }
        }
        // possible diagonal up wins
        for (int r = 0; r < rows - 3; r++) {
            for (int c = 0; c < cols - 3; c++) {
                if (r + 4 > rows || c + 4 > cols)
                    break;
                possibleWinsX += isEqualOrEmpty(board[r][c], ConnectFourState.X) + isEqualOrEmpty(board[r + 1][c + 1], ConnectFourState.X) * 2 + isEqualOrEmpty(board[r + 2][c + 2], ConnectFourState.X) * 10 + isEqualOrEmpty(board[r + 3][c + 3], ConnectFourState.X) * 100;
                isGoalStateX = isGoalStateX || isGoalState(player, board[r][c], board[r + 1][c + 1], board[r + 2][c + 2], board[r + 3][c + 3]);
                possibleWinsO += isEqualOrEmpty(board[r][c], ConnectFourState.O) + isEqualOrEmpty(board[r + 1][c + 1], ConnectFourState.O) * 2 + isEqualOrEmpty(board[r + 2][c + 2], ConnectFourState.O) * 10 + isEqualOrEmpty(board[r + 3][c + 3], ConnectFourState.O) * 100;
                isGoalStateY = isGoalStateY || isGoalState(player, board[r][c], board[r + 1][c + 1], board[r + 2][c + 2], board[r + 3][c + 3]);
            }
        }
        // possible diagonal down wins
        for (int r = 3; r < rows; r++) {
            for (int c = 0; c < cols - 3; c++) {
                if (r - 4 < 0 || c + 4 > cols)
                    break;
                possibleWinsX += isEqualOrEmpty(board[r][c], ConnectFourState.X) + isEqualOrEmpty(board[r - 1][c + 1], ConnectFourState.X) * 2 + isEqualOrEmpty(board[r - 2][c + 2], ConnectFourState.X) * 10 + isEqualOrEmpty(board[r - 3][c + 3], ConnectFourState.X) * 100;
                isGoalStateX = isGoalStateX || isGoalState(player, board[r][c], board[r - 1][c + 1], board[r - 2][c + 2], board[r - 3][c + 3]);
                possibleWinsO += isEqualOrEmpty(board[r][c], ConnectFourState.O) + isEqualOrEmpty(board[r - 1][c + 1], ConnectFourState.O) * 2 + isEqualOrEmpty(board[r - 2][c + 2], ConnectFourState.O) * 10 + isEqualOrEmpty(board[r - 3][c + 3], ConnectFourState.O) * 100;
                isGoalStateY = isGoalStateY || isGoalState(player, board[r][c], board[r - 1][c + 1], board[r - 2][c + 2], board[r - 3][c + 3]);
            }
        }
        double finalValue = 0;
        if (player == ConnectFourState.X) {
            if (isGoalStateX) possibleWinsX = 1;
            if (isGoalStateY) possibleWinsO = 0;
        } else {
            if (isGoalStateX) possibleWinsX = 0;
            if (isGoalStateY) possibleWinsO = 1;
        }
        if(possibleWinsX > possibleWinsO){
            finalValue = (double) possibleWinsO/possibleWinsX;
        } else {
            finalValue = (double) possibleWinsX/possibleWinsO;
        }
        return new ActionValuePair(null, finalValue);
    }

    private boolean isGoalState(int player, int i, int i1, int i2, int i3) {
        return (i == player && i1 == player && i2 == player && i3 == player);
    }

    private double isEqualOrEmpty(int a, int b) {
        if (a == b) {
            return 5;
        } else if (a == 0) {
            return 0.1;
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
