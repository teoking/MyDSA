package dsa.graph;

import dsa.stack.Stack;

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

    //所有顶点、边的辅助信息复位
    abstract protected void reset();

    // 顶点
    public int n; //顶点总数
    public abstract int insert ( Tv v); //插入顶点，返回编号
    public abstract Tv remove ( int v ); //删除顶点及其关联边，返回该顶点信息
    public abstract Tv vertex (int v); //顶点v的数据（该顶点的确存在）
    public abstract int inDegree (int v); //顶点v的入度（该顶点的确存在）
    public abstract int outDegree (int v); //顶点v的出度（该顶点的确存在）
    public abstract int firstNbr (int v); //顶点v的首个邻接顶点
    public abstract int nextNbr ( int v, int u); //顶点v的（相对于顶点j的）下一邻接顶点
    public abstract VStatus status (int v); //顶点v的状态
    public abstract int dTime (int v); //顶点v的时间标签dTime
    public abstract int fTime (int v); //顶点v的时间标签fTime
    public abstract int parent (int v); //顶点v在遍历树中的父亲
    public abstract int priority (int v); //顶点v在遍历树中的优先级数


    // 边：这里约定，无向边均统一转化为方向互逆的一对有向边，从而将无向图视作有向图的特例
    public int e; //边总数
    public abstract boolean exists ( int v, int u); //边(v, u)是否存在
    public abstract void insert ( Te e, int v, int u, int w); //在顶点v和u之间插入权重为w的边e
    public abstract Te remove ( int v, int u); //删除顶点v和u之间的边e，返回该边信息
    public abstract EType type ( int v, int u); //边(v, u)的类型
    public abstract Te edge ( int v, int u); //边(v, u)的数据（该边的确存在）
    public abstract int weight ( int v, int u); //边(v, u)的权重

    // 算法

    /**
     * Breadth-first search.
     * @param v
     */
    public abstract void bfs (int v); //广度优先搜索算法

    /**
     * Depth-first search
     * @param v
     */
    public abstract void dfs (int v); //深度优先搜索算法
//    abstract void bcc (int v); //基于DFS的双连通分量分解算法
    abstract Stack<Tv> tSort (int v); //基于DFS的拓扑排序算法
//    abstract void prim (int v); //最小支撑树Prim算法
//    abstract void dijkstra (int v); //最短路径Dijkstra算法
    //template <typename PU> void pfs ( int, PU ); //优先级搜索框架
}
