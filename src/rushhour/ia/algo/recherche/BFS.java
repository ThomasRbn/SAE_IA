package rushhour.ia.algo.recherche;

import rushhour.ia.framework.common.Action;
import rushhour.ia.framework.common.ArgParse;
import rushhour.ia.framework.common.State;
import rushhour.ia.framework.recherche.SearchNode;
import rushhour.ia.framework.recherche.SearchProblem;
import rushhour.ia.framework.recherche.TreeSearch;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;

public class BFS extends TreeSearch {
    /**
     * Crée un algorithme de recherche
     *
     * @param p Le problème à résoudre
     * @param s L'état initial
     */
    public BFS(SearchProblem p, State s) {
        super(p, s);
    }

    @Override
    public boolean solve() {
        SearchNode node = SearchNode.makeRootSearchNode(intial_state);
        State state = node.getState();
        frontier = new ArrayDeque<>();
        explored = new HashSet<>();
        frontier.add(node);
        if (ArgParse.DEBUG) {
            System.out.print("[" + state);
        }

        while (!frontier.isEmpty()) {
            node = frontier.remove();
            state = node.getState();
            if (ArgParse.DEBUG) {
                System.out.print(" + " + node.getAction() + "] -> [" + state);
            }
            if (problem.isGoalState(state)) {
                end_node = node;
                if (ArgParse.DEBUG) {
                    System.out.println("]");
                }
                return true;
            } else {
                explored.add(state);
            }
            for (Action a : problem.getActions(state)) {
                SearchNode child = SearchNode.makeChildSearchNode(problem, node, a);
                State child_state = child.getState();
                if (!frontier.contains(child) && !explored.contains(child_state)) {
                    frontier.add(child);
                }
            }
        }
        if (ArgParse.DEBUG) {
            System.out.println("]");
        }
        return false;

    }
}

