/**
 * Philippe Legault - 6376254
 *
 * CSI 4106 - Artificial Intelligence I
 * University of Ottawa
 * February 2015
 */

package cc.legault.csi4106.a1.searchAlgorithms;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.graph.UndirectedGraphUnion;

import java.util.List;

/**
 * Interface for search algorithms, that find a path from one city to the next.
 * @param <V> The type of the vertices.
 */
public interface UninformedSearchAlgorithm<V> {

    /**
     * Finds a path in the graph from the origin to the destination.
     * @param graph The world to search.
     * @param origin The vertex where the path starts.
     * @param destination The vertex where the path ends.
     * @param <E> The type of weighted edge.
     * @return A list of edges in the path, from the origin to the destination.
     */
    public <E extends DefaultWeightedEdge> List<E> findPath(SimpleWeightedGraph<V, E> graph, V origin, V destination);

    /**
     * Returns the maximum number of nodes held in memory at a given time, after the execution of the algorithm.
     * @return The maximum number of nodes in memory.
     */
    public int getMaximumNodesInMemory();

    /**
     * Returns the total number of nodes generated, after the execution of the algorithm.
     * @return The total number of nodes generated.
     */
    public int getTotalNumberOfNodesGenerated();

}
