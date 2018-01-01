package dsa.graph;

import dsa.vec.MySortedVector;
import dsa.vec.MyVector;
import dsa.vec.Vector;

public class GraphMatrix<Tv, Te> extends Graph<Tv, Te> {

    private MyVector<Vertex<Tv>> V;//顶点集（向量）
    private MyVector<MyVector<Edge<Te>>> E;//边集（邻接矩阵）
//    private final Edge<Te> EMPTY_EDGE = new Edge<>(null, Integer.MAX_VALUE);

    public GraphMatrix() {
        n = e = 0;
        V = new MyVector<>();
        E = new MyVector<>();
    }

    ///////////////////////////////////////////////////////////////////
    // 顶点的动态操作
    //插入顶点，返回编号
    // O(n) 分摊意义上也是如此
    @Override
    public int insert(Tv vertex) {
        //各顶点预留一条潜在的关联边
        for (int j = 0; j < n; j++) {
//            E.elem[j].insert(EMPTY_EDGE);
            E.get(j).insert(null);
        }
        n++;

        //创建新顶点对应的边向量
        E.insert(new MyVector<>(n, n, null));

        //顶点向量增加一个顶点
        return V.insert(new Vertex<>(vertex));
    }

    // O(n) 分摊意义上也是如此
    @Override
    public Tv remove(int i) {
        for (int j = 0; j < n; j++) {
            if (exists(i, j)) {
                //逐条删除
                E.elem[i].elem[j] = null;
                V.elem[j].inDegree--;
            }
        }
        E.remove(i);
        n--; //删除第i行

        //删除顶点i
        Tv vBak = vertex(i);
        V.remove(i);

        for (int j = 0; j < n; j++) { //所有入边
//            if (E.elem[j].remove(i) != null) {
//                V.elem[j].outDegree--;
//            } //逐条删除

            if (E.get(i).remove(i) != null) {
                V.get(j).outDegree--;
            }
        }

        return vBak; //返回被删除顶点的信息
    }

    ///////////////////////////////////////////////////////////////////
    // 顶点的基本操作：查询第i个顶点（0 <= i < n）
    @Override
    public Tv vertex(int i) {
//        return V.elem[i].data;
        return V.get(i).data;
    }

    @Override
    public int inDegree(int i) {
//        return V.elem[i].inDegree;
        return V.get(i).inDegree;
    }

    @Override
    public int outDegree(int i) {
//        return V.elem[i].outDegree;
        return V.get(i).outDegree;
    }

    @Override
    public int firstNbr(int i) {
        return nextNbr(i, n);
    }

    @Override
    public int nextNbr(int i, int j) {
        //逆向线性试探
        while ((-1 < j) && (!exists(i, --j))) {

        }
        return j;
    }

    @Override
    public VStatus status(int i) {
//        return V.elem[i].status;
        return V.get(i).status;
    }

    @Override
    public int dTime(int i) {
//        return V.elem[i].dTime;
        return V.get(i).dTime;
    }

    @Override
    public int fTime(int i) {
//        return V.elem[i].fTime;
        return V.get(i).fTime;
    }

    @Override
    public int parent(int i) {
//        return V.elem[i].parent;
        return V.get(i).parent;
    }

    @Override
    public int priority(int i) {
//        return V.elem[i].priority;
        return V.get(i).priority;
    }

    ///////////////////////////////////////////////////////////////////
    // 边的确认操作
    //边(i, j)是否存在
    @Override
    public boolean exists(int i, int j) {
//        return (0 <= i) && (i < n)
//                && (0 <= j) && (j < n)
//                && E.elem[i].elem[j] != null
//                && E.elem[i].elem[j] != EMPTY_EDGE;
        return (0 <= i) && (i < n)
                && (0 <= j) && (j < n)
                && E.get(i).get(j) != null;
    }

    ///////////////////////////////////////////////////////////////////
    // 边的动态操作
    @Override
    public void insert(Te edge, int i, int j, int w) { //插入权重为w的边e = (i, j)
        if (exists(i, j))
            return; //确保该边尚不存在
        //E.elem[i].elem[j] = new Edge<>(edge, w);   //创建新边
        E.get(i).insert(j, new Edge<>(edge, w));

        //更新边计数与关联顶点的度数
        e++;
//        V.elem[i].outDegree++;
        V.get(i).outDegree++;
//        V.elem[j].inDegree++;
        V.get(j).inDegree++;
    }

    @Override
    public Te remove(int i, int j) {
        //备份后删除边记录
        Te eBak = edge(i, j);
        E.elem[i].elem[j] = null;

        //更新边计数与关联顶点的度数
        e--;
        V.elem[i].outDegree--;
        V.elem[j].inDegree--;

        return eBak;    //返回被删除边的信息
    }

    ///////////////////////////////////////////////////////////////////
    // 边的基本操作：查询顶点i与j之间的联边（0 <= i, j < n且exists(i, j)）
    @Override
    public EType type(int i, int j) {
        return E.elem[i].elem[j].type;
    }

    @Override
    public Te edge(int i, int j) {
        return E.elem[i].elem[j].data;
    }

    @Override
    public int weight(int i, int j) {
        return E.elem[i].elem[j].weight;
    }

    ///////////////////////////////////////////////////////////////////
    @Override
    //所有顶点、边的辅助信息复位
    protected void reset() {
        Vertex<Tv> vertex;
        for (int i = 0; i < n; i++) {   //所有顶点的
            vertex = V.elem[i];

            //状态，时间标签
            vertex.status = VStatus.UNDISCOVERED;
            vertex.dTime = vertex.fTime = -1;

            //（在遍历树中的）父节点，优先级数
            vertex.parent = -1;
            vertex.priority = Integer.MAX_VALUE;

            for (int j = 0; j < n; j++) {
                //类型
                if (exists(i, j)) {
                    E.elem[i].elem[j].type = EType.UNDETERMINED;
                }
            }
        }
    }
}
