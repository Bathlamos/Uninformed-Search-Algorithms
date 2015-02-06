package cc.legault.csi4106.a1.searchAlgorithms;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.graph.UndirectedGraphUnion;

import java.util.List;

public interface UninformedSearchAlgorithm<V> {

    public <E extends DefaultWeightedEdge> List<E> findPath(SimpleWeightedGraph<V, E> graph, V origin, V destination);

    public int getMaximumNodesInMemory();

    public int getTotalNumberOfNodesGenerated();

}
