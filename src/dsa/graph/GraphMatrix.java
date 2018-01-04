package dsa.graph;

import dsa.queue.Queue;
import dsa.stack.Stack;

import static dsa.graph.Graph.EType.*;
import static dsa.graph.Graph.VStatus.*;

public class GraphMatrix<Tv, Te> extends Graph<Tv, Te> {

    protected final MyVector4Matrix<Vertex<Tv>> V;//顶点集（向量）
    protected final MyVector4Matrix<MyVector4Matrix<Edge<Te>>> E;//边集（邻接矩阵）

    public GraphMatrix() {
        n = e = 0;
        V = new MyVector4Matrix<>();
        E = new MyVector4Matrix<>();
    }

    ///////////////////////////////////////////////////////////////////
    // 顶点的动态操作
    //插入顶点，返回编号
    // O(n) 分摊意义上也是如此
    @Override
    public int insert(Tv vertex) {
        //各顶点预留一条潜在的关联边
        for (int j = 0; j < n; j++) {
            E.get(j).insert(null);
        }
        n++;

        //创建新顶点对应的边向量
        E.insert(new MyVector4Matrix<Edge<Te>>(n, n, null));

        //顶点向量增加一个顶点
        return V.insert(new Vertex<>(vertex));
    }

    // O(n) 分摊意义上也是如此
    @Override
    public Tv remove(int i) {
        for (int j = 0; j < n; j++) {
            if (exists(i, j)) {
                //逐条删除
                E.get(i).set(j, null);
                V.get(j).inDegree--;
            }
        }
        E.remove(i);
        n--; //删除第i行

        //删除顶点i
        Tv vBak = vertex(i);
        V.remove(i);

        for (int j = 0; j < n; j++) { //所有入边
            if (E.get(i).remove(i) != null) {
                V.get(j).outDegree--;
            }   //逐条删除
        }

        return vBak; //返回被删除顶点的信息
    }

    ///////////////////////////////////////////////////////////////////
    // 顶点的基本操作：查询第i个顶点（0 <= i < n）
    @Override
    public Tv vertex(int i) {
        return V.get(i).data;
    }

    @Override
    public int inDegree(int i) {
        return V.get(i).inDegree;
    }

    @Override
    public int outDegree(int i) {
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
        return V.get(i).status;
    }

    @Override
    public int dTime(int i) {
        return V.get(i).dTime;
    }

    @Override
    public int fTime(int i) {
        return V.get(i).fTime;
    }

    @Override
    public int parent(int i) {
        return V.get(i).parent;
    }

    @Override
    public int priority(int i) {
        return V.get(i).priority;
    }

    ///////////////////////////////////////////////////////////////////
    // 边的确认操作
    //边(i, j)是否存在
    @Override
    public boolean exists(int i, int j) {
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
        E.get(i).set(j, new Edge<>(edge, w));   //创建新边

        //更新边计数与关联顶点的度数
        e++;
        V.get(i).outDegree++;
        V.get(j).inDegree++;
    }

    @Override
    public Te remove(int i, int j) {
        //备份后删除边记录
        Te eBak = edge(i, j);
        E.get(i).set(j, null);

        //更新边计数与关联顶点的度数
        e--;
        V.get(i).outDegree--;
        V.get(j).inDegree--;

        return eBak;    //返回被删除边的信息
    }

    ///////////////////////////////////////////////////////////////////
    // 边的基本操作：查询顶点i与j之间的联边（0 <= i, j < n且exists(i, j)）
    @Override
    public EType type(int i, int j) {
        return E.get(i).get(j).type;
    }

    @Override
    public Te edge(int i, int j) {
        return E.get(i).get(j).data;
    }

    @Override
    public int weight(int i, int j) {
        return E.get(i).get(j).weight;
    }

    ///////////////////////////////////////////////////////////////////
    @Override
    //所有顶点、边的辅助信息复位
    protected void reset() {
        Vertex<Tv> vertex;
        for (int i = 0; i < n; i++) {   //所有顶点的
            vertex = V.get(i);

            //状态，时间标签
            vertex.status = UNDISCOVERED;
            vertex.dTime = vertex.fTime = -1;

            //（在遍历树中的）父节点，优先级数
            vertex.parent = -1;
            vertex.priority = Integer.MAX_VALUE;

            for (int j = 0; j < n; j++) {
                //类型
                if (exists(i, j)) {
                    E.get(i).get(j).type = UNDETERMINED;
                }
            }
        }
    }

    ///////////////////////////////////////////////////////////////////
    // 算法

    // 时间：O(n+e), 空间：基于邻接表实现的话为O(n+e)，邻接矩阵为O(n^2+e)
    @Override
    public void bfs(int s) {
        reset();
        int clock = 0;
        int v = s;  // 初始化
        do
            if (UNDISCOVERED == status(v))  // 一旦遇到尚未发现的顶点
                BFS(v, clock);  // 即从该定点出发启动一次BFS
        while(s != (v = (++v % n)));
    }

    //广度优先搜索BFS算法（单个连通域）
    private void BFS(int v, int clock) {
        Queue<Integer> q = new Queue<>();   // 引入辅助栈

        // 初始化起点
        V.get(v).status = DISCOVERED;
        q.enqueue(v);

        while (!q.empty()) {
            // 取出队首顶点v
            v = q.dequeue();
            V.get(v).dTime = ++clock;

            // 枚举v的所有邻居u
            for (int u = firstNbr(v); -1 < u; u = nextNbr(v, u)) {
                // 若u尚未被发现
                if (UNDISCOVERED == status(u)) {
                    // 发现该顶点
                    V.get(u).status = DISCOVERED;
                    q.enqueue(u);
                    // 引入树边拓展支撑树
                    E.get(v).get(u).type = TREE;
                    V.get(u).parent = v;
                } else {    // 若u已被发现，或者甚至已访问完毕，则
                    //将(v, u)归类于跨边
                    E.get(v).get(u).type = CROSS;

                }
                // 至此，当前顶点访问完毕
                V.get(v).status = VISITED;
                System.out.println(V.get(v));
            }
        }
    }

    // 时间：O（n+e），； 空间：O（n+e），可改写为迭代方式（引入一个辅助栈）
    @Override
    public void dfs(int s) {    //深度优先搜索DFS算法（全图）
        // 初始化
        reset();
        int clock = 0;
        int v = s;

        do {    //逐一检查所有顶点
            if (UNDISCOVERED == status(v))  //一旦遇到尚未发现的顶点
                DFS(v, clock);  // 即从该顶点出发启动一次DFS
        } while (s != (v = (++v % n))); // 按序号检查，故不漏不重
    }

    //深度优先搜索DFS算法（单个连通域）
    private void DFS(int v, int clock) {
        V.get(v).dTime = ++clock;
        V.get(v).status = DISCOVERED;   //发现当前顶点v
        // 枚举v的所有邻居u
        for (int u = firstNbr(v); -1 < u; u = nextNbr(v, u)) {
            switch (status(u)) { //并视其状态分别处理
                case UNDISCOVERED://u尚未发现，意味着支撑树可在此拓展
                    E.get(v).get(u).type = TREE;
                    V.get(u).parent = v;
                    DFS(u, clock);
                case DISCOVERED: //u已被发现但未访问完毕，应属被后代指向的祖先
                    E.get(v).get(u).type = BACKWARD;
                    break;
                default: //u已访问完毕（VISITED，有向图），则视承袭关系分为前向边或跨边
                    E.get(v).get(u).type = dTime(v) < dTime(u) ? FORWARD
                            : CROSS;
                    break;
            }
        }
        V.get(v).status = VISITED;
        V.get(v).fTime = ++clock; //至此，当前顶点v方告访问完毕
    }

    // 时间：O(n+e)  空间：O(n)辅助栈
    @Override
    public Stack<Tv> tSort(int s) { //基于DFS的拓扑排序(topological sorting)
        // 初始化
        reset();
        int clock = 0;
        int v = s;
        Stack<Tv> stack = new Stack<>(); //用栈记录排序顶点
        do {
            if (UNDISCOVERED == status(v)) {
                if (!TSort(v, clock, stack)) { //clock并非必需
                    while (!stack.empty()) { //任一连通域（亦即整图）非DAG
                        stack.pop();
                    }
                    break; //则不必继续计算，故直接返回
                }
            }
        } while (s != (v = (++v % n)));

        return stack; //若输入为DAG，则S内各顶点自顶向底排序；否则（不存在拓扑排序），S空
    }

    private boolean TSort(int v, int clock, Stack<Tv> stack) {
        V.get(v).dTime = ++clock;
        V.get(v).status = DISCOVERED;   //发现当前顶点v

        // 枚举v的所有邻居u
        for (int u = firstNbr(v); -1 < u; u = nextNbr(v, u)) {
            switch (status(u)) { //并视其状态分别处理
                case UNDISCOVERED://u尚未发现，意味着支撑树可在此拓展
                    E.get(v).get(u).type = TREE;
                    V.get(u).parent = v;
                    if (!TSort(u, clock, stack)) //从顶点u处出发深入搜索
                        return false; //若u及其后代不能拓扑排序（则全图亦必如此），故返回并报告
                    break;
                case DISCOVERED:
                    //一旦发现后向边（非DAG），则
                    E.get(v).get(u).type = BACKWARD;
                    return false; // 不必深入，故返回并报告
                default: //u已访问完毕（VISITED，有向图），则视承袭关系分为前向边或跨边
                    E.get(v).get(u).type = dTime(v) < dTime(u) ? FORWARD
                            : CROSS;
                    break;
            }
        }
        V.get(v).status = VISITED;
        //顶点被标记为VISITED时，随即入栈
        stack.push(vertex(v));

        //v及其后代可以拓扑排序
        return true;
    }

    // 时间：O(n+e) ， 空间：O(n+e)
    @Override
    public void bcc(int s) {
        // 初始化
        reset();
        int clock = 0;
        int v = s;
        Stack<Integer> stack = new Stack<>(); //用栈记录排序顶点

        do {
            if (UNDISCOVERED == status(v)) { //一旦发现未发现的顶点（新连通分量）
                BCC(v, clock, stack); //即从该顶点出发启动一次BCC
                stack.pop(); //遍历返回后，弹出栈中最后一个顶点——当前连通域的起点
            }
        } while (s != (v = (++v % n)));
    }

    //利用此处闲置的fTime[]充当hca[]
    private int hca(int v) {
        return V.get(v).fTime;
    }

    private void BCC(int v, int clock, Stack<Integer> stack) {
        //利用此处闲置的fTime[]充当hca[]
        V.get(v).fTime = V.get(v).dTime = ++clock;
        //v被发现并入栈
        V.get(v).status = DISCOVERED;
        stack.push(v);

        // 枚举v的所有邻居u
        for (int u = firstNbr(v); -1 < u; u = nextNbr(v, u)) {
            switch ( status ( u ) ) { //并视u的状态分别处理
                case UNDISCOVERED:
                    //从顶点u处深入
                    V.get(u).parent = v;
                    E.get(v).get(u).type = TREE;
                    BCC(u, clock, stack);

                    //遍历返回后，若发现u（通过后向边）可指向v的真祖先
                    if (hca(u) < dTime(v))
                        //则v亦必如此
                        V.get(v).fTime = Math.min(hca(v), hca(u));
                    else {
                        //否则，以v为关节点（u以下即是一个BCC，且其中顶点此时正集中于栈S的顶部）
                        System.out.print("BCC rooted at " + vertex(v) + ":");
                        Stack<Integer> temp = new Stack<>();
                        do {
                            temp.push(stack.pop());
                            System.out.print(vertex(temp.top()));
                        } while (v != temp.top());

                        while(!temp.empty())
                            stack.push(temp.pop());

                        while (v != stack.pop()); //依次弹出当前BCC中的节点，亦可根据实际需求转存至其它结构

                        stack.push(v); //最后一个顶点（关节点）重新入栈——分摊不足一次
                        System.out.println();
                    }
                    break;
                case DISCOVERED:
                    E.get(v).get(u).type = BACKWARD; //标记(v, u)，并按照“越小越高”的准则
                    if (u != parent(v))
                        V.get(v).fTime = Math.min(hca(v), dTime(u)); //更新hca[v]
                    break;
                default: //VISITED (digraphs only)
                    E.get(v).get(u).type = (dTime(v) < dTime(u)) ? FORWARD : CROSS;
                    break;
            }
        }
        V.get(v).status = VISITED; //对v的访问结束
    }

    // 时间： O(n^2)，后面借助优先级队列等结构可以提高此算法效率
    //优先级搜索（全图）
    @Override
    void pfs(int s, PriorityUpdater pu) { //assert: 0 <= s < n
        // 初始化
        reset();
        int v = s;

        //逐一检查所有顶点
        do {
            if (UNDISCOVERED == status(v)) { //一旦发现未发现的顶点
                PFS(v, pu); //即从该顶点出发启动一次PFS
            }
        } while (s != (v = (++v % n))); //按序号检查，故不漏不重
    }

    //通过定义具体的优先级更新策略prioUpdater，即可实现不同的算法功能
    private void PFS(int s, PriorityUpdater pu) {
        //初始化，起点s加至PFS树中
        V.get(s).priority = 0;
        V.get(s).status = VISITED;
        V.get(s).parent = -1;

        //将下一顶点和边加至PFS树中
        while (true) {
            //枚举s的所有邻居w
            for (int w = firstNbr(s); -1 < w; w = nextNbr(s, w)) {
                //更新顶点w的优先级及其父顶点
                pu.update(this, s, w);
            }

            for (int shortest = Integer.MAX_VALUE, w = 0; w < n; w++) {
                //从尚未加入遍历树的顶点中
                if (UNDISCOVERED == status(w)) {
                    //选出下一个
                    if (shortest > priority(w)) {
                        shortest = priority(w);
                        //优先级最高的顶点s
                        s = w;
                    }
                }
            }

            //直至所有顶点均已加入
            if (VISITED == status(s))
                break;

            //将s及与其父的联边加入遍历树
            V.get(s).status = VISITED;
            E.get(parent(s)).get(s).type = TREE;
        }
    }
}
