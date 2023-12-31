package rushhour.ia.algo.recherche;

import rushhour.ia.framework.common.Action;
import rushhour.ia.framework.common.State;
import rushhour.ia.framework.recherche.SearchNode;
import rushhour.ia.framework.recherche.SearchProblem;
import rushhour.ia.framework.recherche.TreeSearch;

import java.util.PriorityQueue;

public class UCS extends TreeSearch {
    /**
     * Crée un algorithme de recherche
     *
     * @param p Le problème à résoudre
     * @param s L'état initial
     */
    public UCS(SearchProblem p, State s) {
        super(p, s);
        frontier = new PriorityQueue<>(new SearchNodeComparator());
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
                        if (n.getState().equals(s.getState()) && n.getCost() > s.getCost()) {
                            frontier.remove(n);
                            frontier.add(s);
                        }
                    }
                }
            }
        }
        return false;
    }
}