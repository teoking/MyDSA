package dsa.graph;

import static dsa.graph.Graph.VStatus.UNDISCOVERED;

/**
 * Created by teoking on 18-1-4.
 */
public class PrimsPriorityUpdater<Tv, Te> implements PriorityUpdater<Tv, Te> {

    @Override
    public void update(Graph<Tv, Te> g, int uk, int v) {
        GraphMatrix<Tv, Te> gm = (GraphMatrix<Tv, Te>) g;
        if (UNDISCOVERED == g.status(v)) {
            if (g.priority(v) > g.weight(uk, v)) {
                gm.V.get(v).priority = gm.weight(uk, v);
                gm.V.get(v).parent = uk;
            }
        }
    }
}
