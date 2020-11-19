# Dijkstra algorithm on Weighted graph
Implementation of weighted graph and search algorithm using the Dijkstra graph search system

# Usage
To work with the project you need to:\
1. create an instance of the graph itself: `WGraph_DS graph = new WGraph_DS()`
2. add nodes to the graph by giving the function a specific key: `graph.addNode(key)`
3. add edges, connections: `graph.addEdge(node2key,node1key)`
4. create an instance of the graph algorithm: `WGraph_Algo algo = new WGraph_Algo()`
5. initialize the algo with the graph you created: ` algo.init(graph)`
6. then use the methods provided to run and use the graph:\
    * `algo.isConnected()` to check if the graph you created all nodes connect
    * `algo.shortestPath(src,dest)` to find all the nodes between the src node to the dest node
    * `algo.shortestPathDist(src,dest)` to find how many nodes are between the src node to the dest node
## Classes
* __WGraph_Algo__\
    Responsible for running the algorithm on specific graph
* __WGraph_DS__\
    Controls the graph. Adding,Removing nodes and creating connections between nodes
## Interfaces
* __weighted_graph_algorithms__
* __weighted_graph__
## General Description of Methods, Divided by Class 
* __WGraph_Algo__
    * __init(graph g)__\
    initializes a new graph object into the class.
    * __copy()__\
    creates a deep copy of the initialized graph and returns it
    * __isConnected()__\
    checks if there is a way to get from every node to any other node in graph
    * __shortestPath(int src, int dest)__\
    returns the shortest path from one node to the other, if it exists, in the form of a list
    containing all the nodes in the path
    * 	__shortestPathDist(int src, int dest)__\
    finds the shortest path, as in the smallest sum of the distance between nodes according to the edges 
    * 	__save(String path)__\
    saves the init graph in the algo into a file, the output file is in JSON format
    * __load(String path)__\
    loads graph from a file, according designated path only support for JSON formatted files
* __WGraph_DS__
    *  __addNode(node_data n)__\
    adds a new node to the graph
    *  __connect(int node1,int node2)__\
    create a connection between two nodes, given that the connection doesn't already exist
    *  __edgeSize()__\
    gets how many edges are in the graph
    *  __getMC()__\
    counts how many changes were made to the graph
    *  __getNode(int key)__\
    gets a node from the graph, given that the given key is of an existing node in the graph
    *  __getV()__\
    gets all the nodes in the graph
    *  __getV(int node_id)__\
    returns all the connected nodes to the node with given id
    *  __hasEdge(int node1, int node2)__\
    checks if there is a connection between two nodes
    *  __nodeSize()__\
    returns the number of nodes in graph
    *  __removeEdge(int node1, int node2)__\
    deletes the connection between nodes
    *  __removeNode(int key)__\
    deletes the node with given key, and all of its connections in other node
    *  __toString()__\
    return the graph in string form