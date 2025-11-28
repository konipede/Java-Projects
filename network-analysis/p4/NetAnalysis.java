package cs1501.p4;
import java.io.File;
import java.util.Scanner;
import java.io.IOException;
import java.util.ArrayList;


public class NetAnalysis implements NetAnalysisInterface {
    public MyGraph graph; //graph is represented in MyGraph class

    public NetAnalysis(String filename) {
        try (Scanner scanner = new Scanner(new File(filename))) {
            int V = Integer.parseInt(scanner.nextLine().trim());
            graph = new MyGraph(V);
            if(V !=1){
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(" "); //split into an array to make an edge
                int u = Integer.parseInt(parts[0]);
                int v = Integer.parseInt(parts[1]);
                String cableType = parts[2];
                boolean copper = cableType.equalsIgnoreCase("copper");
                int bandwidth = Integer.parseInt(parts[3]);
                int length = Integer.parseInt(parts[4]);
                graph.addEdge(new WeightEdge(u, v, length, copper, bandwidth)); //add the edge to the graph
            }
        }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    //get the lowest latency path with latency as weight
    @Override
    public ArrayList<Integer> lowestLatencyPath(int u, int w) {
       if(u > graph.V -1 ||w > graph.V -1) return null;
        if(!graph.isConnected(u,w)) return null; //make sure graph is connected by points
        ArrayList<WeightEdge> list = Dijk(u, w); //get list of edges
        ArrayList<Integer> vertices = new ArrayList<>();
        if (list.isEmpty()) return null;
        vertices.add(u); 
        for (WeightEdge e : list) { //add each vertices from the edges to the list in order
            int vert = e.getW();
            int vert2 = e.other(vert);
            if (!vertices.contains(vert))  vertices.add(vert);
            if (!vertices.contains(vert2)) vertices.add(vert2);
        }
        if (!vertices.contains(w)) vertices.add(w);
        if(vertices.get(0) == w){
            int size = vertices.size();
            for (int i = 0; i < size / 2; i++) {
                int temp = vertices.get(i);
                vertices.set(i, vertices.get(size - 1 - i));
                vertices.set(size - 1 - i, temp);
            }    
        }
        return vertices;
    }
    
    //find the min bandwith on path p
    @Override
    public int bandwidthAlongPath(ArrayList<Integer> p) throws IllegalArgumentException{
      for(Integer v: p){
        if(v > graph.V -1)throw new IllegalArgumentException(); //if vertex is not in the graph
      }
        if(!isValidPath(p)) throw new IllegalArgumentException();
        double d = bandwidth(p);
        return (int) d;
    }
    @Override
    public boolean copperOnlyConnected(){
        return graph.isCopperConnected();
   
    }
//check to see if the graph will stilll be connected if it loses any two points
@Override
    public boolean canLoseTwoVertices(){
        if(graph.V < 3) return false;
        for(int v = 0; v<graph.V -1; v++){ //make a copy of the graph without a vertex v for each vertex
            MyGraph copy = graph.copyWithoutZ(v); 
           if(!copy.AP()) return false;
        }
    return true;
    }
    //get the lowest mean latency spanning tree
    @Override
    public ArrayList<StEdge> lowestMeanLatencySt(){
        if(!graph.isConnected()) return null;
        return spanTree();
    }
    public double bandwidth(ArrayList<Integer> p) {
        ArrayList<WeightEdge> pathEdges = new ArrayList<>();
        for (int i = 0; i < p.size() - 1; i++) {
            int a = p.get(i);
            int b = p.get(i + 1); //get edges on path 
            WeightEdge e = graph.get(a, b);
            pathEdges.add(e);
        }
        double band = pathEdges.get(0).bandwidth;
        for (WeightEdge edge : pathEdges) { //go through each edge
            if (edge.bandwidth < band) band = edge.bandwidth; //get the smallest bandwith on the path
        }
        return band;
    }

    public boolean isValidPath(ArrayList<Integer> p) {
        if (p == null || p.size() == 1) {
            return false;
        }
        for (int i = 0; i < p.size() - 1; i++) {
            int u = p.get(i);
            int v = p.get(i + 1);
            boolean hasEdge = false;
            MyBag<WeightEdge>.MyBagIterator iterator = graph.adj[u].iterator();
            while (iterator.hasNext()) {
                WeightEdge e = iterator.next();
                if (e.other(u) == v) {
                    hasEdge = true;
                }
            }
            if (!hasEdge) {
                return false;
            }
        }
        return true;
    }
     // Code is adapted from Robert Sedgewick, Algorithms, 4th Edition, page 655, Algorrithm 4.9
     public ArrayList<WeightEdge> Dijk(int u, int v) {
        MyGraph newGraph = new MyGraph(graph.V);
        //first copy edges so graph is a digraph
        for (int i = 0; i < graph.V; i++) {
            MyBag<WeightEdge>.MyBagIterator iterator = graph.adj[i].iterator();
            while (iterator.hasNext()) {
                WeightEdge e = iterator.next();
                newGraph.addEdge(new WeightEdge(e.u, e.w, e.length, e.copper, e.bandwidth));
                newGraph.addEdge(new WeightEdge(e.w, e.u, e.length, e.copper, e.bandwidth)); // Add reverse edge
            }
        }

        //perfrom dijkstra algo
        double[] distTo = new double[graph.V];
        WeightEdge[] edgeTo = new WeightEdge[graph.V];
        MyPQ pq = new MyPQ<Double>();

        for (int i = 0; i < distTo.length; i++) {
            distTo[i] = Double.POSITIVE_INFINITY;
        }
        distTo[u] = 0.0;

        pq.add(u, 0.0); // Add the source vertex with weight 0

        while (pq.n > 0) {
            relax(pq.deleteMin(), distTo, edgeTo, pq, newGraph);
        }

        //get the path
        ArrayList<WeightEdge> path = new ArrayList<>();
        for (int at = v; at != u; at = edgeTo[at].other(at)) {
            WeightEdge edge = edgeTo[at];
            if (edge == null) {
                return new ArrayList<>();
            }
            path.add(edge);
        }
        ArrayList<WeightEdge> reversedPath = new ArrayList<>();
        for (int i = path.size() - 1; i >= 0; i--) {
            reversedPath.add(path.get(i));
        }
        return reversedPath;
    }
    // Code is adapted from Robert Sedgewick, Algorithms, 4th Edition, page 655, Algorrithm 4.9
    private void relax(int v, double[] distTo, WeightEdge[] edgeTo, MyPQ pq, MyGraph newGraph) {
        MyBag<WeightEdge>.MyBagIterator iterator = newGraph.adj[v].iterator();
        while (iterator.hasNext()) {
            WeightEdge e = iterator.next();
            int w = e.getW();
            if (distTo[w] > distTo[v] + e.weight) {
                distTo[w] = distTo[v] + e.weight;
                edgeTo[w] = e;
                if (pq.contains(w)) pq.change(w, distTo[w]);
                else pq.add(w, distTo[w]);
            }
        }
    }

     // Code is adapted from Robert Sedgewick, Algorithms, 4th Edition, page 622, Algorithm 4.7
     public ArrayList<StEdge> spanTree() {
        WeightEdge[] edgeTo = new WeightEdge[graph.V];
        double[] distTo = new double[graph.V];
        boolean[] marked = new boolean[graph.V];
        MyPQ pq = new MyPQ();

        for (int x = 0; x < graph.V; x++) {
            distTo[x] = Double.POSITIVE_INFINITY;
        }
        distTo[0] = 0.0;
        pq.add(0, 0);
        while (pq.n > 0) {
            int x = pq.deleteMin();
            prim(x, edgeTo, distTo, marked, pq);
        }

        ArrayList<StEdge> mst = new ArrayList<>();
        for (int k = 1; k < graph.V; k++) {
            if (edgeTo[k] != null) {
                WeightEdge edge = edgeTo[k];
                StEdge Sedge = new StEdge(edge.u, edge.w);
                mst.add(Sedge);
            }
        }
        return mst;
    }

    // Code is adapted from Robert Sedgewick, Algorithms, 4th Edition, page 622, Algorithm 4.7
    private void prim(int v, WeightEdge[] edgeTo, double[] distTo, boolean[] marked, MyPQ pq) {
        marked[v] = true;
        MyBag<WeightEdge>.MyBagIterator iterator = graph.adj[v].iterator();
        while (iterator.hasNext()) {
            WeightEdge e = iterator.next();
            int w = e.other(v);
            if (marked[w]) continue;
            if (e.weight < distTo[w]) {
                distTo[w] = e.weight;
                edgeTo[w] = e;
                if (pq.contains(w)) pq.change(w, distTo[w]);
                else pq.add(w, distTo[w]);
            }
        }
    }

}
