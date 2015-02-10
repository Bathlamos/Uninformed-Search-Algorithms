/**
 * Philippe Legault - 6376254
 *
 * CSI 4106 - Artificial Intelligence I
 * University of Ottawa
 * February 2015
 */

package cc.legault.csi4106.a1.searchAlgorithms;

import org.jgrapht.alg.NeighborIndex;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.util.FibonacciHeap;
import org.jgrapht.util.FibonacciHeapNode;

import java.util.*;

/**
 * Executes a Uniform-Cost Search on a given graph.
 * @param <V> The type of the vertices.
 */
public class UniformCostSearch<V> implements UninformedSearchAlgorithm<V> {

    private int maxNumberOfNodesInMemory = 0;
    private int totalNumberOfNodesGenerated = 0;

    /**
     * @inheritDoc
     */
    @Override
    public <E extends DefaultWeightedEdge> List<E> findPath(SimpleWeightedGraph<V, E> graph, V origin, V destination) {

        //Keep tracks of the visited nodes and their direct ancestor
        Map<V, V> parent = new HashMap<>();
        parent.put(origin, null);

        maxNumberOfNodesInMemory = 1;
        totalNumberOfNodesGenerated = 1;

        // Priority Queue with O(1) running time for removeMin
        FibonacciHeap<V> heap = new FibonacciHeap<>();
        heap.insert(new FibonacciHeapNode<V>(origin), 0);

        while(!heap.isEmpty()){

            //Extract the head of the queue
            FibonacciHeapNode<V> currentHeapNode = heap.removeMin();
            V currentNode = currentHeapNode.getData();
            double currentCost = currentHeapNode.getKey();

            //Compare the head of the queue with the destination
            if(currentNode.equals(destination)){
                //We've found a path. Build and return it.
                List<E> edges = new LinkedList<>();
                while(!destination.equals(origin)){
                    V ancestor = parent.get(destination);
                    edges.add(0, graph.getEdge(destination, ancestor));
                    destination = ancestor;
                }
                return edges;
            }

            //Add the adjacent vertices to the queue,
            // if they haven't been visited yet
            NeighborIndex<V, E> neighbourIndex = new NeighborIndex<>(graph);
            for(V adjacentVertex: neighbourIndex.neighborsOf(currentNode))
                if(!parent.containsKey(adjacentVertex)){
                    parent.put(adjacentVertex, currentNode);
                    double edgeCost = graph.getEdgeWeight(graph.getEdge(currentNode, adjacentVertex));
                    heap.insert(new FibonacciHeapNode<V>(adjacentVertex), currentCost + edgeCost);
                    totalNumberOfNodesGenerated++;
                }

            maxNumberOfNodesInMemory = Math.max(maxNumberOfNodesInMemory, heap.size());
        }

        throw new RuntimeException("No path exist from the origin to the destination");
    }

    /**
     * @inheritDoc
     */
    public int getMaximumNodesInMemory(){
        return maxNumberOfNodesInMemory;
    }

    /**
     * @inheritDoc
     */
    public int getTotalNumberOfNodesGenerated(){
        return totalNumberOfNodesGenerated;
    }

}
