package dsa.graph;

/**
 * Created by teoking on 18-1-4.
 */
public interface PriorityUpdater<Tv, Te> {

    void update(Graph<Tv, Te> graph, int s, int w);
}
