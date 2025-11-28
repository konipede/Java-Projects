# Network Analysis

A comprehensive network analysis tool implementing graph algorithms for analyzing network connectivity, finding optimal paths, and determining network resilience.

## Overview

This project implements various graph algorithms to analyze network topologies, including finding shortest paths, determining connectivity, and analyzing network resilience to vertex failures.

## Features

- **Shortest Path Finding**: Dijkstra's algorithm for lowest latency paths
- **Minimum Spanning Tree**: Prim's algorithm for optimal network topology
- **Connectivity Analysis**: DFS-based connectivity checking
- **Network Resilience**: Articulation point detection for fault tolerance
- **Bandwidth Analysis**: Path bandwidth calculation

## Data Structures

### Core Classes

- **`NetAnalysis`**: Main class implementing network analysis interface
- **`MyGraph`**: Graph implementation using adjacency lists
- **`WeightEdge`**: Weighted edge with latency, bandwidth, and cable type
- **`MyPQ`**: Indexable priority queue for Dijkstra's and Prim's algorithms
- **`MyBag`**: Bag data structure for adjacency lists
- **`StEdge`**: Spanning tree edge representation

## Algorithms Implemented

### 1. Dijkstra's Algorithm
- Finds the lowest latency path between two vertices
- Uses latency as edge weight (calculated from cable length and type)
- Time Complexity: O(E log V) with priority queue

### 2. Prim's Algorithm
- Finds minimum spanning tree with lowest mean latency
- Ensures all vertices are connected with minimum total latency
- Time Complexity: O(E log V)

### 3. Depth-First Search (DFS)
- Checks graph connectivity
- Checks copper-only connectivity
- Time Complexity: O(V + E)

### 4. Articulation Point Detection
- Determines if graph remains connected after vertex removal
- Uses DFS with discovery time and low values
- Time Complexity: O(V + E)

## Usage

```java
// Create network analysis from file
NetAnalysis network = new NetAnalysis("network.txt");

// Find lowest latency path
ArrayList<Integer> path = network.lowestLatencyPath(0, 5);

// Calculate bandwidth along path
int bandwidth = network.bandwidthAlongPath(path);

// Check copper-only connectivity
boolean copperConnected = network.copperOnlyConnected();

// Check if network can lose two vertices
boolean resilient = network.canLoseTwoVertices();

// Find minimum spanning tree
ArrayList<StEdge> mst = network.lowestMeanLatencySt();
```

## Input Format

The input file should have the following format:
```
<number_of_vertices>
<vertex1> <vertex2> <cable_type> <bandwidth> <length>
...
```

Example:
```
5
0 1 copper 1000 100
1 2 optical 2000 150
2 3 copper 1500 200
...
```

## Latency Calculation

- **Copper cables**: latency = length / 230,000,000 m/s
- **Optical cables**: latency = length / 200,000,000 m/s

## Package Structure

All classes are in the `cs1501.p4` package.

## Algorithms & Complexity

- **Lowest Latency Path**: O(E log V) - Dijkstra's algorithm
- **Minimum Spanning Tree**: O(E log V) - Prim's algorithm
- **Connectivity Check**: O(V + E) - DFS
- **Articulation Points**: O(V + E) - DFS variant
- **Bandwidth Calculation**: O(P) where P is path length

## References

- Algorithms adapted from Robert Sedgewick, "Algorithms, 4th Edition"
- Dijkstra's algorithm: Page 655, Algorithm 4.9
- Prim's algorithm: Page 622, Algorithm 4.7

## Author

Kerem

