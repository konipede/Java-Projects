package cs1501.p4;

class WeightEdge extends StEdge implements Comparable<WeightEdge> {
    public boolean copper;
    public double weight;
    public double length;
    public double bandwidth;
   
    //WeightEdge uses StEdge as its parent to get the vertexes
    public WeightEdge(int u, int v, double length, boolean copper, double bandwidth) {
        super(u, v);
        this.length = length; //length of the edge 
        this.copper = copper; // trueo for copper or  false for optical boolean
        this.bandwidth = bandwidth;
        if (copper) {
            this.weight = length / 230000000.0; //latency calculations
        } else {
            this.weight = length / 200000000.0; //latency calculations
        }
    }
    
    public boolean equals(Object other) {
        return super.equals(other);
    }

    
//returns other vertex of edge 
    public int other(int vertex) {
        if (vertex == u) return w;
        else if (vertex == w) return u;
        else throw new IllegalArgumentException("Illegal endpoint");
    }
//returns w 
    public int getW() {
        return super.w;
    }
//compares based on latency
    @Override
    public int compareTo(WeightEdge other) {
        return Double.compare(this.weight, other.weight);
    }
}

