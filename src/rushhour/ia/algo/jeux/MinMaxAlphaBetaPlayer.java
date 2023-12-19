package rushhour.ia.algo.jeux;

import rushhour.ia.framework.common.Action;
import rushhour.ia.framework.common.ActionValuePair;
import rushhour.ia.framework.jeux.Game;
import rushhour.ia.framework.jeux.GameState;
import rushhour.ia.framework.jeux.Player;

public class MinMaxAlphaBetaPlayer extends Player {

    private int numStates = 0;
    private int profondeur = 0;
    private int profondeurMax = 5000000;
    long startTime = System.currentTimeMillis();
    long endTime = System.currentTimeMillis();

    public MinMaxAlphaBetaPlayer(Game g, boolean p1) {
        super(g, p1);
        name = "MinMaxAlphaBeta";
    }

    @Override
    public Action getMove(GameState state) {
        profondeur = 0;
        System.out.println(numStates);
        long startTime = System.currentTimeMillis();
        if (player == PLAYER1)
            return MaxValeur(state, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY).getAction();
        else
            return MinValeur(state, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY).getAction();
    }

    private ActionValuePair MaxValeur(GameState s, double alpha, double beta) {
        if (game.endOfGame(s) || profondeur >= profondeurMax) {
            endTime = System.currentTimeMillis();
            System.out.println("That took " + (endTime - startTime) + " milliseconds");
            return new ActionValuePair(null, s.getGameValue());
        }

        double vMax = Double.NEGATIVE_INFINITY;
        Action cMax = null;
        profondeur++;
        for (Action c : game.getActions(s)) {
            numStates++;
            System.out.println(numStates);
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

    private ActionValuePair MinValeur(GameState s, double alpha, double beta) {
        if (game.endOfGame(s) || profondeur == profondeurMax) {
            endTime = System.currentTimeMillis();
            System.out.println("That took " + (endTime - startTime) + " milliseconds");
            return new ActionValuePair(null, s.getGameValue());
        }

        double vMin = Double.POSITIVE_INFINITY;
        Action cMin = null;
        profondeur++;

        for (Action c : game.getActions(s)) {
            numStates++;
            System.out.println(numStates);
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
