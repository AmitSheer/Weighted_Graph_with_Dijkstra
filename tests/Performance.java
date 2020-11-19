package ex1.tests;

import ex1.src.WGraph_DS;
import org.junit.jupiter.api.Test;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Performance extends BaseTest {
    @Test
    void maxSizeGraphTimeCheck(){
        int maxNodes = (int)Math.pow(10,6);
        int maxEdges = maxNodes*10;
        System.out.println("Specific for 10^6 nodes and 10 times more edges");
        Timestamp n1 = Timestamp.from(Instant.now());
        graphCreatorForPerformanceTests2(1,maxNodes,maxEdges,1 );
        Timestamp n2 = Timestamp.from(Instant.now());
        System.out.println("started on: "+n1);
        System.out.println("ended on: "+n2);
        assertTrue((n2.getTime()-n1.getTime())/1000.0<10.0);
        System.out.println(graph.edgeSize());
        System.out.println(graph.nodeSize());
        System.out.println("Common Graph Creator");
        n1 = Timestamp.from(Instant.now());
        graph =new WGraph_DS();
        graphCreator(1,maxNodes,maxEdges,1 );
        n2 = Timestamp.from(Instant.now());
        System.out.println("started on: "+n1);
        System.out.println("ended on: "+n2);
        System.out.println(graph.edgeSize());
        System.out.println(graph.nodeSize());
    }

    /**
     * creates all nodes. when more then 10 nodes are created
     * it will start creating edges between the current created node to a random 10 other nodes.
     * after will create edges from first 10 nodes to other nodes, and the first node
     * will be connected to other node
     * @param seed for the random
     * @param size number of nodes to create
     * @param edges number of edges
     * @param weight
     */
    public void graphCreatorForPerformanceTests2(int seed, int size,int edges, int weight){
        graph = new WGraph_DS();
        Random _rnd = new Random(seed);
        Random rndWeight = new Random(weight);
        int key;
        graph.addNode(0);
        //create all of the nodes
        for (int i = 1; i < size; i++) {
            graph.addNode(i);
            //when more then 10 nodes exist
            if(i>9) {
                for (int j = 0; j < 10; j++){
                    key = _rnd.nextInt(i);
                    //check if has an edge or if the random key is same as current node key
                    while (graph.hasEdge(i, key) || i == key) {
                        key = _rnd.nextInt(i);
                    }
                    graph.connect(i, key, rndWeight.nextDouble());
                }
            }
            else{
                    key = _rnd.nextInt(i);
                    while (graph.hasEdge(i, key) || i == key) {
                        key = _rnd.nextInt(i);
                    }
                    graph.connect(i, key, rndWeight.nextDouble());
                }
        }
        //adds 9 edges to first 10 nodes
        int a = _rnd.nextInt(size);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9; j++) {
                key = _rnd.nextInt(size);
                while (graph.hasEdge(a, key) || a == key) {
                    key = _rnd.nextInt(size);
                }
                graph.connect(a, key, rndWeight.nextDouble());
            }
        }
        key = _rnd.nextInt(size);
        while (graph.hasEdge(0, key) || 0 == key) {
            key = _rnd.nextInt(size);
        }
        graph.connect(0, key, rndWeight.nextDouble());
    }
}
