package rushhour.ia.algo.jeux;

import rushhour.ia.framework.common.Action;
import rushhour.ia.framework.common.ActionValuePair;
import rushhour.ia.framework.jeux.Game;
import rushhour.ia.framework.jeux.GameState;
import rushhour.ia.framework.jeux.Player;

public class MinMaxAlphaBetaPlayer extends Player {

    public MinMaxAlphaBetaPlayer(Game g, boolean p1) {
        super(g, p1);
        name = "MinMax";
    }

    @Override
    public Action getMove(GameState state) {
        if (player == PLAYER1)
            return MaxValeur(state, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY).getAction();
        else
            return MinValeur(state, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY).getAction();
    }

    private ActionValuePair MaxValeur(GameState state, double alpha, double beta) {
        if (game.endOfGame(state))
            return new ActionValuePair(null, state.getGameValue());
        ActionValuePair best = new ActionValuePair(null, Double.NEGATIVE_INFINITY);

        for (Action a : game.getActions(state)) {
            GameState next = (GameState) game.doAction(state, a);
            ActionValuePair v = MinValeur(next, alpha, beta);
            if (v.getValue() > best.getValue()){
                best = new ActionValuePair(a, v.getValue());
                if (best.getValue() > alpha)
                    best = new ActionValuePair(a, best.getValue());
            }
            if (best.getValue() >= beta)
                return best;
        }
        return best;
    }

    private ActionValuePair MinValeur(GameState state, double alpha, double beta) {
        if (game.endOfGame(state))
            return new ActionValuePair(null, state.getGameValue());
        ActionValuePair best = new ActionValuePair(null, Double.POSITIVE_INFINITY);

        for (Action a : game.getActions(state)) {
            GameState next = (GameState) game.doAction(state, a);
            ActionValuePair v = MaxValeur(next, alpha, beta);
            if (v.getValue() < best.getValue()) {
                best = new ActionValuePair(a, v.getValue());
                if (best.getValue() < beta)
                    best = new ActionValuePair(a, best.getValue());
            }
            if (best.getValue() <= alpha)
                return best;
        }
        return best;
    }

}
