/**
 * Philippe Legault - 6376254
 *
 * CSI 4106 - Artificial Intelligence I
 * University of Ottawa
 * February 2015
 */

package cc.legault.csi4106.a1.searchAlgorithms;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.jgrapht.alg.NeighborIndex;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.*;

/**
 * Executes a Breadth-First Search on a given graph.
 * @param <V> The type of the vertices.
 */
public class BFS<V> implements UninformedSearchAlgorithm<V> {

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

        //Used to traverse the tree in BFS
        Queue<V> queue = new LinkedList<>();
        queue.add(origin);

        while(!queue.isEmpty()){

            //Extract the head of the queue
            V currentNode = queue.remove();

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
                    queue.add(adjacentVertex);
                    totalNumberOfNodesGenerated++;
                }

            maxNumberOfNodesInMemory = Math.max(maxNumberOfNodesInMemory, parent.size());
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
