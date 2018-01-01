package dsa.graph;

public class Edge<Te> {

    //数据、权重、类型
    public Te data;
    public int weight;
    public Graph.EType type;

    public Edge(Te d, int weight) {
        data = d;
        this.weight = weight;
        type = Graph.EType.UNDETERMINED;
    }
}

