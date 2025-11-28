package cs1501.p4;

import java.util.ArrayList;

/**
 * Network Analysis specification interface for CS1501 Project 4
 * @author Dr. Farnan
 * @author Dr. Garrison
 */
interface NetAnalysisInterface {

    /**
     * Determines the lowest latency path from vertex `u` to vertex `w` in this graph.
     *
     * @param u the starting vertex
     * @param w the destination vertex
     *
     * @return A list of the vertex id's representing the path (should start with `u` and end with
     * `w`), or `null` if no path exists
     */
    public ArrayList<Integer> lowestLatencyPath(int u, int w);

    /**
     * Determines the bandwidth available along a given path through this graph (the minimum
     * bandwidth of an edge in the path).
     *
     * @param A list of the vertex id's representing the path
     *
     * @throws IllegalArgumentException if `p` is not a valid path for this graph
     *
     * @return The bandwidth available along the specified path
     */
    public int bandwidthAlongPath(ArrayList<Integer> p) throws IllegalArgumentException;

    /**
     * Determines whether this graph is connected when considering only copper links.
     *
     * @return `true` if this graph is connected when considering only the copper links, or `false`
     * if not
     */
    public boolean copperOnlyConnected();

    /**
     * Return `true` if this graph would remain connected even if any two vertices in this graph
     * failed, or `false` otherwise
     *
     * @return Whether this graph would remain connected for any two failed vertices
     */
    public boolean canLoseTwoVertices();

    /**
     * Finds the lowest arithmetic mean ("average") latency spanning tree for this graph (i.e., a
     * spanning tree with the lowest mean latency per edge), and returns it as an `ArrayList` of
     * `StEdge` edges.
     *
     * Note that implementations of this interface are not required to use `StEdge` objects as their
     * internal representation. Those that use different internal representations may use `StEdge`
     * only for constructing the result for `lowestMeanLatencySt()`
     *
     * @return a list of `StEdge` objects that represents the lowest mean latency spanning tree, or
     * `null` if the graph is not connected
     */
    public ArrayList<StEdge> lowestMeanLatencySt();

}

