/**
 * Philippe Legault - 6376254
 *
 * CSI 4106 - Artificial Intelligence I
 * University of Ottawa
 * February 2015
 */

package cc.legault.csi4106.a1.searchAlgorithms;

import com.google.common.collect.Sets;
import org.jgrapht.alg.NeighborIndex;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.*;

/**
 * Executes a Depth-First Search on a given graph.
 * @param <V> The type of the vertices.
 */
public class DFS<V> implements UninformedSearchAlgorithm<V> {

    private int maxNumberOfNodesInMemory = 0;
    private int totalNumberOfNodesGenerated = 0;

    /**
     * @inheritDoc
     */
    @Override
    public <E extends DefaultWeightedEdge> List<E> findPath(SimpleWeightedGraph<V, E> graph, V origin, V destination) {
        maxNumberOfNodesInMemory = 0;
        totalNumberOfNodesGenerated = 0;
        List<E> edges = findPath(graph, origin, destination, Sets.newHashSet(origin), 0);
        if(edges == null)
            throw new RuntimeException("No path exist from the origin to the destination");
        return edges;
    }

    private <E extends DefaultWeightedEdge> List<E> findPath(SimpleWeightedGraph<V, E> graph, V origin, V destination, Set<V> visited, int numNodesGenerated) {

        if(destination == origin)
            return new LinkedList<E>();

        //Add the adjacent vertices to the queue,
        // if they haven't been visited yet
        NeighborIndex<V, E> neighbourIndex = new NeighborIndex<>(graph);
        for(V adjacentVertex: neighbourIndex.neighborsOf(origin))
            if(!visited.contains(adjacentVertex)){
                visited.add(adjacentVertex);
                totalNumberOfNodesGenerated++;
                maxNumberOfNodesInMemory = Math.max(maxNumberOfNodesInMemory, numNodesGenerated + 1);
                List<E> edges = findPath(graph, adjacentVertex, destination, visited, numNodesGenerated + 1);
                if(edges != null){
                    //We've found the destination.
                    edges.add(0, graph.getEdge(origin, adjacentVertex));
                    return edges;
                }
            }

        return null;
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
