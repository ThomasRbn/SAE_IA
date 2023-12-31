package rushhour.ia.algo.recherche;

import rushhour.ia.framework.common.Action;
import rushhour.ia.framework.common.State;
import rushhour.ia.framework.recherche.SearchNode;
import rushhour.ia.framework.recherche.SearchProblem;
import rushhour.ia.framework.recherche.TreeSearch;

import java.util.ArrayDeque;
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
        frontier = new ArrayDeque<>();
    }

    @Override
    public boolean solve() {
        SearchNode node = SearchNode.makeRootSearchNode(intial_state);
        frontier.add(node);

        while (!frontier.isEmpty()) {
            node = frontier.poll();
            if (problem.isGoalState(node.getState())) {
                end_node = node;
                System.out.println("Solution trouvée" + node.getState());
                return true;
            }

            explored.add(node.getState());
            System.out.println(problem.getActions(node.getState()));
            for (Action a : problem.getActions(node.getState())) {
                SearchNode s = SearchNode.makeChildSearchNode(this.problem, node, a);

                if (!explored.contains(s.getState()) && !frontier.contains(s)) {
                    System.out.println("Action effectuée : " + a);
                    frontier.add(s);
                }
                System.out.println(explored);
                for (State n : explored) {
                    System.out.println(n.hashCode());
                }
            }
        }
        return false;
    }
}