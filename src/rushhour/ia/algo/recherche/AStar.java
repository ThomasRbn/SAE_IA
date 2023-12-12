package rushhour.ia.algo.recherche;

import ia.framework.common.Action;
import ia.framework.common.State;
import ia.framework.recherche.HasHeuristic;
import ia.framework.recherche.SearchNode;
import ia.framework.recherche.SearchProblem;
import ia.framework.recherche.TreeSearch;

public class AStar extends TreeSearch implements HasHeuristic {

    /**
     * Crée un algorithme de recherche
     *
     * @param p Le problème à résoudre
     * @param s L'état initial
     */
    public AStar(SearchProblem p, State s) {
        super(p, s);
    }

    @Override
    public double getHeuristic() {
        return 0;
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
                        if (n.equals(s) && n.getHeuristic() + n.getCost() > s.getHeuristic() + s.getCost()) {
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
