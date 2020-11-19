package ex1.src;


import java.io.Serializable;
import java.util.*;

public class WGraph_DS implements weighted_graph,Serializable{
    //Holds the nodes in order to have access to the nodes in O(1)
    private final HashMap<Integer,node_info> nodes;
    //counts the number of edges
    private Integer edgeCount;
    //counts the numbers of changes in graph
    private Integer mc;

    /**
     * init
     */
    public WGraph_DS(){
        this.nodes = new HashMap<>();
        this.edgeCount=0;
        this.mc = 0;
    }

    /**
     * returns nodes data with specific key
     * @param key - the node_id
     * @return
     */
    @Override
    public node_info getNode(int key) {
        return this.nodes.get(key);
    }

    /**
     * checks if there is an edge between two nodes
     * @param node1
     * @param node2
     * @return
     */
    @Override
    public boolean hasEdge(int node1, int node2) {
        try{
            return ((NodeInfo)this.nodes.get(node1)).hasNi(node2) && ((NodeInfo)this.nodes.get(node2)).hasNi(node1);
        }catch (NullPointerException e ){
            return false;
        }
    }

    /**
     * gets the value of an edge between two nodes
     * @param node1 node key
     * @param node2 node key
     * @return the value of the edge
     */
    @Override
    public double getEdge(int node1, int node2) {
        return (this.hasEdge(node1,node2))?((NodeInfo)this.nodes.get(node1)).getNi(node2):-1;
    }

    /**
     * adds a new node to the graph
     */
    @Override
    public void addNode(int key) {
        this.nodes.putIfAbsent(key,new NodeInfo(key));
        this.mc++;
    }

    /**
     * creates new connection between nodes
     * @param node1
     * @param node2
     * @param w weight of an edge
     */
    @Override
    public void connect(int node1, int node2, double w) {
        if (w>=0&&node1!=node2&&this.nodes.containsKey(node1)&&this.nodes.containsKey(node2)) {
            try{
                //checks if this is a new edge
                if(!hasEdge(node1,node2)){
                    this.edgeCount++;
                    this.mc++;
                    ((NodeInfo)this.nodes.get(node1)).addNi(node2,w);
                    ((NodeInfo)this.nodes.get(node2)).addNi(node1,w);
                }else if (getEdge(node1,node2)!=w) {
                    this.mc++;
                    ((NodeInfo)this.nodes.get(node1)).addNi(node2,w);
                    ((NodeInfo)this.nodes.get(node2)).addNi(node1,w);
                }
            }catch(NullPointerException ignored){

            }
        }
    }

    /**
     *
     * @return all of the nodes in O(1)
     */
    @Override
    public Collection<node_info> getV() {
        return this.nodes.values();
    }

    /**
     * gets all of the neighbors nodes
     * @param node_id id of node
     * @return
     */
    @Override
    public Collection<node_info> getV(int node_id) {
        List<node_info> connectedNodes = new ArrayList<>();
        try {
            //gets all of the connected nodes keys and gets their correlating nodes and puts them in a list
            ((NodeInfo)this.nodes.get(node_id)).getAllNi().keySet().forEach((key)->connectedNodes.add(nodes.get(key)));
            return connectedNodes;
        }catch (NullPointerException e){
            return connectedNodes;
        }

    }

    /**
     * deletes specific node from graph
     * and removes all of the edges associated with it
     * @param key of node to remove
     * @return the removed node
     */
    @Override
    public node_info removeNode(int key) {
        node_info node = nodes.get(key);
        try{
            Collection<Integer> niKeys  = ((NodeInfo)node).getAllNi().keySet();
            //remove all connected edges
            while(niKeys.iterator().hasNext()){
                removeEdge(niKeys.iterator().next(),key);
            }
            //remove node from value list, and nodes
            nodes.remove(key);
            this.mc++;
        }catch(Exception ignore){
        }
        return node;
    }

    /**
     * delete an edge from node
     * @param node1
     * @param node2
     */
    @Override
    public void removeEdge(int node1, int node2) {
        if (hasEdge(node1, node2)){
            ((NodeInfo)this.nodes.get(node1)).removeNi(node2);
            ((NodeInfo)this.nodes.get(node2)).removeNi(node1);
            this.edgeCount--;
            this.mc++;
        }
    }

    @Override
    public int nodeSize() {
        return this.nodes.size();
    }

    @Override
    public int edgeSize() {
        return this.edgeCount;
    }

    @Override
    public int getMC() {
        return this.mc;
    }

    @Override
    public String toString() {
        return "WGraph_DS{" +
                "nodes=" + nodes.toString() +
                ", edgeCount=" + edgeCount +
                ", mc=" + mc +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return o.toString().equals(this.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(nodes, edgeCount, mc);
    }

    static class NodeInfo implements node_info, Serializable {
        //makes sure there are no repeating node keys
        private static int nodeCounter=0;
        private final Integer key;
        private String info;
        private double tag;
        //holds all of the connections to the node and their weight
        private HashMap<Integer,Double> ni;

        public NodeInfo(int key){
            this.key = key;
            this.info = String.valueOf(key);
            this.tag = Integer.MAX_VALUE;
            nodeCounter= nodeCounter + key;
            ni = new HashMap<>();
        }

        public NodeInfo(){
            this.key = nodeCounter;
            this.info = String.valueOf(this.key);
            this.tag = Integer.MAX_VALUE;
            nodeCounter++;
            ni = new HashMap<>();
        }

        public NodeInfo(node_info node){
            this.key = node.getKey();
            this.info = String.valueOf(this.key);
            this.tag = Integer.MAX_VALUE;
            nodeCounter= nodeCounter + this.key;
            ni = new HashMap<>();
        }

        public boolean hasNi(int key) {
            return ni.containsKey(key);
        }

        public HashMap<Integer, Double> getAllNi() {
            return ni;
        }


        public double getNi(int key) {
            return ni.get(key);
        }

        public void removeNi(int key) {
            ni.remove(key);
        }

        /**
         * adds the node as neighbors
         * then sends current node to be added as neighbors at the other node
         * @param key node to add as neighbors
         * @param dist the distance between the two nodes
         */
        public void addNi(int key,double dist) {
            ni.put(key, dist);
        }

        @Override
        public int getKey() {
            return this.key;
        }

        @Override
        public String getInfo() {
            return this.info;
        }

        @Override
        public void setInfo(String s) {
            this.info = s;
        }

        @Override
        public double getTag() {
            return this.tag;
        }

        @Override
        public void setTag(double t) {
            this.tag = t;
        }

        @Override
        public String toString() {
            return "NodeInfo{" +
                    "key=" + key +
                    ", info='" + info + '\'' +
                    ", tag=" + tag +
                    ", ni=" + ni.toString() +
                    '}';
        }
    }
}

