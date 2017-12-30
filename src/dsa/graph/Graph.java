package dsa.graph;

public abstract class Graph<Tv, Te> {

    enum VStatus {
        UNDISCOVERED,
        DISCOVERED,
        VISITED
    }

    enum EType {
        UNDETERMINED,
        TREE,
        CROSS,
        FORWARD,
        BACKWARD
    }

    private void reset() {
        for (int i = 0; i < n; i++) {

        }
    }

    // 顶点总数
    public int n;
    public int insert(Tv v);

}
