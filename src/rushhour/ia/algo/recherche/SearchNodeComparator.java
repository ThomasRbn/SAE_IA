package rushhour.ia.algo.recherche;

import rushhour.ia.framework.recherche.SearchNode;

import java.util.Comparator;

public class SearchNodeComparator implements Comparator<SearchNode> {
    @Override
    public int compare(SearchNode o1, SearchNode o2) {
        return (int) (o1.getCost() - o2.getCost());
    }
}
