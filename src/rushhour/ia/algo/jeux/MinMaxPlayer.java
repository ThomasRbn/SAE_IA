package rushhour.ia.algo.jeux;

import rushhour.ia.framework.common.Action;
import rushhour.ia.framework.common.ActionValuePair;
import rushhour.ia.framework.jeux.Game;
import rushhour.ia.framework.jeux.GameState;
import rushhour.ia.framework.jeux.Player;

public class MinMaxPlayer extends Player {

    /**
     * Represente un joueur
     *
     * @param g          l'instance du jeux
     * @param player_one si joueur 1
     */
    public MinMaxPlayer(Game g, boolean player_one) {
        super(g, player_one);
        name = "MinMax";
    }

    public Action minMax(GameState s){
        return player == PLAYER1 ? max(s).getAction() : min( s).getAction();
    }

    public ActionValuePair max(GameState s){
        if (game.endOfGame(s)){
            return new ActionValuePair(null, s.getGameValue());
        }

        double vMax = Double.NEGATIVE_INFINITY;
        Action cMax = null;

        for (Action c : game.getActions(s)){
            GameState sSuivant = (GameState) game.doAction(s, c);
            ActionValuePair v = min(sSuivant);
            if (v.getValue() > vMax){
                vMax = v.getValue();
                cMax = c;
            }
        }
        return new ActionValuePair(cMax, vMax);
    }

    public ActionValuePair min(GameState s){
        if (game.endOfGame(s)){
            return new ActionValuePair(null, s.getGameValue());
        }

        double vMin = Double.POSITIVE_INFINITY;
        Action cMin = null;

        for (Action c : game.getActions(s)){
            GameState sSuivant = (GameState) game.doAction(s, c);
            ActionValuePair v = max(sSuivant);
            if (v.getValue() < vMin){
                vMin = v.getValue();
                cMin = c;
            }
        }
        return new ActionValuePair(cMin, vMin);
    }

    @Override
    public Action getMove(GameState state) {
        return minMax(state);
    }
}
