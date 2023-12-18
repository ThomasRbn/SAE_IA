package rushhour.ia.algo.recherche;

import rushhour.ia.framework.common.Action;
import rushhour.ia.framework.common.State;
import rushhour.ia.framework.recherche.HasHeuristic;
import rushhour.ia.framework.recherche.SearchNode;
import rushhour.ia.framework.recherche.SearchProblem;
import rushhour.ia.framework.recherche.TreeSearch;

import java.util.ArrayDeque;
import java.util.HashSet;

public class GFS extends TreeSearch implements HasHeuristic {
    /**
     * Crée un algorithme de recherche
     *
     * @param p Le problème à résoudre
     * @param s L'état initial
     */
    public GFS(SearchProblem p, State s) {
        super(p, s);
        frontier = new ArrayDeque<>();
        explored = new HashSet<>();
    }

    @Override
    public boolean solve() {
        SearchNode node = SearchNode.makeRootSearchNode(intial_state);
        frontier.add(node);
        explored.add(node.getState());

        while (!frontier.isEmpty()) {
            node = frontier.poll();
            if (problem.isGoalState(node.getState())) {
                end_node = node;
                return true;
            }
            explored.add(node.getState());
            for (Action a : problem.getActions(node.getState())) {
                SearchNode s = SearchNode.makeChildSearchNode(this.problem, node, a);
                if (!explored.contains(s.getState()) && !frontier.contains(s)) {
                    frontier.add(s);
                } else if (frontier.contains(s)) {
                    for (SearchNode n : frontier) {
                        if (n.equals(s) && n.getHeuristic() > s.getHeuristic()) {
                            frontier.remove(n);
                            frontier.add(s);
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public double getHeuristic() {
        return end_node.getHeuristic();
    }
}
