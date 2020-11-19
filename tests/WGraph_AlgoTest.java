package ex1.tests;


import ex1.src.node_info;
import ex1.src.weighted_graph;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@Tag("All")
class WGraph_AlgoTest extends BaseTest {

    @Test
    void copy() {
        graphCreator(1,10,10,1);
        algo.init(graph);
        weighted_graph copied = algo.copy();
        assertEquals(graph,copied);
    }

    @Test
    void isConnected() {
        graphCreator(1,10,0,1);
        for (int i = 1; i < 10; i++) {
            graph.connect(i-1,i,1);
        }
        algo.init(graph);
        assertTrue(algo.isConnected());
    }

    @Test
    void isConnectedWithNotConnectedGraph() {
        graphCreator(1,10,0,1);
        for (int i = 1; i < 10; i++) {
            graph.connect(i-1,i,1);
        }
        algo.init(graph);
        graph.removeNode(3);
        assertFalse(algo.isConnected());
    }

    @Test
    void isConnectedWithNotConnectedButThenConnectedGraph() {
        graphCreator(1,10,0,1);
        for (int i = 1; i < 9; i++) {
            graph.connect(i-1,i,1);
        }
        algo.init(graph);
        assertFalse(algo.isConnected());
        graph.connect(8,9,1);
        assertTrue(algo.isConnected());

    }

    @Test
    void shortestPathDist() {
        graphCreator(1,10,0,1);
        for (int i = 1; i < 10; i++) {
            graph.connect(i-1,i,1);
        }
        algo.init(graph);
        assertEquals(9,algo.shortestPathDist(0,9));
    }

    @Test
    void shortestPathDistWithLaterShorterPath() {
        graphCreator(1,10,0,1);
        for (int i = 1; i < 10; i++) {
            graph.connect(i-1,i,1);
        }
        graph.connect(0,9,20);
        algo.init(graph);
        assertEquals(9,algo.shortestPathDist(0,9));
        graph.connect(3,9,1);
        assertEquals(4,algo.shortestPathDist(0,9));
    }

    @Test
    void shortestPathDistWhenNoConnection() {
        graphCreator(1,10,0,1);
        for (int i = 1; i < 9; i++) {
            graph.connect(i-1,i,1);
        }
        algo.init(graph);
        assertEquals(-1,algo.shortestPathDist(0,9));
    }

    @Test
    void shortestPath() {
        graphCreator(1,10,0,1);
        for (int i = 1; i < 10; i++) {
            graph.connect(i-1,i,1);
        }
        algo.init(graph);
        List<node_info> path = algo.shortestPath(0,9);
        for (int i = 0; i < 10; i++) {
            assertEquals(i,path.get(i).getKey());
        }
        graph.connect(3,9,1);
        path = algo.shortestPath(0,9);
        for (int i = 0; i < 4; i++) {
            assertEquals(i,path.get(i).getKey());
        }
        assertEquals(9,path.get(4).getKey());
    }

    @Test
    void save() {
        graphCreator(1,10,0,1);
        for (int i = 1; i < 10; i++) {
            graph.connect(i-1,i,1);
        }
        algo.init(graph);
        assertTrue(algo.save(path+"/writeOver.json"));
    }

    @Test
    void saveOverExistingFile() {
        graphCreator(1,11,0,1);
        for (int i = 1; i < 10; i++) {
            graph.connect(i-1,i,1);
        }
        algo.init(graph);
        assertTrue(algo.save(path+"/writeOver.json"));

    }

    @Test
    void saveTextFile() {
        graphCreator(1,10,0,1);
        for (int i = 1; i < 10; i++) {
            graph.connect(i-1,i,1);
        }
        algo.init(graph);
        assertTrue(algo.save(path+"/writeOver.txt"));
    }


    @Test
    void load() {
        graphCreator(1,10,0,1);
        for (int i = 1; i < 10; i++) {
            graph.connect(i-1,i,1);
        }
        algo.init(graph);
        assertTrue(algo.load(path+ "/testFile.json"));
        assertEquals(graph, algo.getGraph());
    }

    @Test
    void loadEmptyFile() {
        graphCreator(1,10,10,1);
        algo.init(graph);
        assertFalse(algo.load(path+"/empty.json"));
        assertEquals(10,algo.getGraph().getV().size());
        assertEquals(10,algo.getGraph().edgeSize());
    }

    @Test
    void loadCorruptedFile() {
        graphCreator(1,10,10,1);
        algo.init(graph);
        assertFalse(algo.load(path+"/corruptedFile.json"));
        assertEquals(10,algo.getGraph().getV().size());
        assertEquals(10,algo.getGraph().edgeSize());
    }

    @Test
    void loadNotExistingFile() {
        graphCreator(1,10,0,1);
        for (int i = 1; i < 10; i++) {
            graph.connect(i-1,i,1);
        }
        algo.init(graph);
        assertFalse(algo.load(path+"/fake.json"));
    }

    @Test
    void loadTextFile() {
        graphCreator(1,10,0,1);
        for (int i = 1; i < 10; i++) {
            graph.connect(i-1,i,1);
        }
        algo.init(graph);
        assertTrue(algo.load(path+"/testFile.txt"));
        assertEquals(graph, algo.getGraph());
    }
}