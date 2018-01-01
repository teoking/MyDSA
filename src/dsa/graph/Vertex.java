package dsa.graph;

import static dsa.graph.Graph.VStatus;
import static dsa.graph.Graph.VStatus.*;

//顶点对象（为简化起见，并未严格封装）
public class Vertex<Tv> {

    //数据、出入度数、状态
    public Tv data;
    public int inDegree;
    public int outDegree;
    public VStatus status;

    //时间标签
    public int dTime;
    public int fTime;

    //在遍历树中的父节点、优先级数
    public int parent;
    public int priority;

    public Vertex(Tv d) {
        data = d;
        inDegree = 0;
        outDegree = 0;
        status = UNDISCOVERED;
        dTime = -1;
        fTime = -1;
        priority = Integer.MAX_VALUE; //暂不考虑权重溢出
    }
}
