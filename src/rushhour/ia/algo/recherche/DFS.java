package rushhour.ia.algo.recherche;

import rushhour.ia.framework.common.Action;
import rushhour.ia.framework.common.ArgParse;
import rushhour.ia.framework.common.State;
import rushhour.ia.framework.recherche.HasHeuristic;
import rushhour.ia.framework.recherche.SearchNode;
import rushhour.ia.framework.recherche.SearchProblem;
import rushhour.ia.framework.recherche.TreeSearch;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.LinkedList;

public class DFS extends TreeSearch {
    public DFS(SearchProblem p, State s) {
        super(p, s);
    }

    @Override
    public boolean solve() {
        end_node = SearchNode.makeRootSearchNode(intial_state);
        intial_state = end_node.getState();
        frontier = new LinkedList<>();
        frontier.add(end_node);
        explored = new HashSet<>();
        if (ArgParse.DEBUG) {
            System.out.print("[" + intial_state);
        }
        while (!frontier.isEmpty()) {
            end_node = frontier.remove();
            intial_state = end_node.getState();
            if (ArgParse.DEBUG) {
                System.out.print(" + " + end_node.getAction() + "] -> [" + intial_state);
            }
            if (problem.isGoalState(intial_state)) {
                if (ArgParse.DEBUG) {
                    System.out.println("]");
                }
                return true;
            } else {
                explored.add(intial_state);
            }
            for (Action a : problem.getActions(intial_state)) {
                // depth first search
                SearchNode child = SearchNode.makeChildSearchNode(problem, end_node, a);
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
