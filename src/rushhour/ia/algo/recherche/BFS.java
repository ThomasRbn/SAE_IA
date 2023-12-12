package rushhour.ia.algo.recherche;

import ia.framework.common.Action;
import ia.framework.common.State;
import ia.framework.recherche.SearchNode;
import ia.framework.recherche.SearchProblem;
import ia.framework.recherche.TreeSearch;

import java.util.PriorityQueue;

public class BFS extends TreeSearch {
    /**
     * Crée un algorithme de recherche
     *
     * @param p Le problème à résoudre
     * @param s L'état initial
     */
    public BFS(SearchProblem p, State s) {
        super(p, s);
        frontier = new PriorityQueue<>();
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
                }
            }
        }
        return false;
    }
}