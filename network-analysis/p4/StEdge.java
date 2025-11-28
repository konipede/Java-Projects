package cs1501.p4;

/**
 * A Spanning Tree Edge
 * @author Dr. Farnan
 * @author Dr. Garrison
 */
public class StEdge {

    /**
     * One endpoint of this edge
     */
    protected int u;

    /**
     * The other endpoint of this edge
     */
    protected int w;

    /**
     * Basic constructor
     */
    public StEdge(int v1, int v2) {
        u = v1;
        w = v2;
    }

    /**
     * Equality comparison, treating edges as undirected
     */
    public boolean equals(Object other) {
        // Return true if both are the same reference
        if (this == other) return true;

        // Return false if other is not an StEdge
        if (!(other instanceof StEdge)) return false;

        // Cast other to StEdge
        @SuppressWarnings("unchecked")
        StEdge otherEdge = (StEdge) other;

        // Compare endpoints
        return (this.u == otherEdge.u && this.w == otherEdge.w) ||
                (this.u == otherEdge.w && this.w == otherEdge.u);
    }

    public String toString() {
        return "(" + String.valueOf(u) + ", " + String.valueOf(w) + ")";
    }

}

