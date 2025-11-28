
package cs1501.p4;

import java.util.NoSuchElementException;

public class MyGraph {
    public int V;
    public int E;
    public MyBag<WeightEdge>[] adj; //adjency list
    public int time;

    public MyGraph(int V) {
        int time = 0;
        this.V = V;
        this.E = 0;
        adj = (MyBag<WeightEdge>[]) new MyBag<?>[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new MyBag<WeightEdge>();
        }
    }

    public void addEdge(WeightEdge e) {
        int v = e.u;
        int w = e.w;
        adj[v].add(e);
        adj[w].add(e); //add edge to each vertex adj list
        E++;
    }

    //get an edge that connects two points if it exists
    public WeightEdge get(int u, int v) {
        MyBag<WeightEdge>.MyBagIterator iterator = adj[u].iterator();
        while (iterator.hasNext()) {
            WeightEdge e = iterator.next();
            if (e.u == v || e.w == v) {
                return e;
            }
        }
        return null;
    }


    //make a copy of a graoh without vertex v and its edges
    public MyGraph copyWithoutZ(int z) {
        MyGraph newGraph = new MyGraph(this.V);
        for (int v = 0; v < this.V; v++) {
            if (v != z) {
                MyBag<WeightEdge>.MyBagIterator iterator = this.adj[v].iterator();
                while (iterator.hasNext()) {
                    WeightEdge e = iterator.next();
                    int other = e.other(v);
                    if (other != z) {
                        newGraph.addEdge(e);
                    }
                }
            }
        }
        return newGraph;
    }
    //perform dfs to check if graoh is connected by points
    public boolean isConnected(int u, int v) {
        boolean[] visited = new boolean[V];
        DFS(u, visited);
        if (visited[v] == true) return true;
        else return false;
    }
//perform dfs ti check if whole graph is connected
    public boolean isConnected() {
      if(V == 1) return true;
        boolean[] visited = new boolean[V];
        DFS(0, visited);
        for (boolean v : visited) {
            if (!v) {
                return false;
            }
        }
        return true;
    }

    private void DFS(int v, boolean[] marked) {
        marked[v] = true;
        MyBag<WeightEdge>.MyBagIterator iterator = adj[v].iterator();
        while (iterator.hasNext()) {
            WeightEdge e = iterator.next();
            int n = e.other(v);
            if (!marked[n]) {
                DFS(n, marked);
            }
        }
    }

    public boolean isCopperConnected() {
       if(V == 1 ) return true;
        boolean[] marked = new boolean[V];
        dfsCopper(0, marked);
        for (boolean v : marked) {
            if (!v) {
                return false;
            }
        }
        return true;
    }
//perfrom a dfs traversal but only consider copper edges
    private void dfsCopper(int v, boolean[] marked) {
        marked[v] = true;
        MyBag<WeightEdge>.MyBagIterator iterator = adj[v].iterator();
        while (iterator.hasNext()) {
            WeightEdge e = iterator.next();
            if (e.copper == true) { //check if copper
                int n = e.other(v);
                if (!marked[n]) {
                    dfsCopper(n, marked);
                }
            }
        }
    }

    //dfs traversal to find articulation points
    //code is adapted from Recitation 8 material
    public void dfsAP(int u, boolean visited[], int disc[], int low[], int parent[], boolean ap[]) {
        int children = 0;
        visited[u] = true;
        disc[u] = time;
        low[u] = time;
        time++;
        MyBag<WeightEdge>.MyBagIterator iterator = adj[u].iterator();
        while (iterator.hasNext()) {
            WeightEdge e = iterator.next();
            int v = e.other(u); // Get the other vertex of the edge
            if (!visited[v]) {
                children++;
                parent[v] = u;
                dfsAP(v, visited, disc, low, parent, ap); //recur down
                low[u] = Math.min(low[u], low[v]);
                if (parent[u] == -1 && children > 1)  ap[u] = true;     
                if (parent[u] != -1 && low[v] >= disc[u])  ap[u] = true;
            } else if (v != parent[u])  low[u] = Math.min(low[u], disc[v]);
        }
    }

    //code for AP method is adapted from Recitation 8 material
    //returns true if there are no APs. Returns false otherwise
    public boolean AP() {
        boolean visited[] = new boolean[V];
        int disc[] = new int[V];
        int low[] = new int[V];
        int parent[] = new int[V];
        boolean ap[] = new boolean[V];
    
        for (int i = 0; i < V; i++) {
            parent[i] = -1; // -1 to null
            visited[i] = false;
            ap[i] = false;
        }
    
        for (int i = 0; i < V; i++) {
            if (!visited[i])
                dfsAP(i, visited, disc, low, parent, ap); //dfs
        }
        for (int i = 0; i < V; i++) {
            if (ap[i]) {
                return false; //check if any APs exist
            }
        }
    
        return true;
    }
    

   
}
