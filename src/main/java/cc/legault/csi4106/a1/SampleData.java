package cc.legault.csi4106.a1;

import org.jgraph.graph.Edge;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.*;

public class SampleData {

    private static String[] romanianCities = {"Oradea", "Zerind", "Arad", "Timisoara", "Lugoj", "Mehadia",
            "Drobeta", "Craiova", "Pitesti", "Rimnicu Vilcea", "Sibiu", "Fagaras", "Bucharest", "Giurgiu",
            "Urziceni", "Hirsova", "Eforie", "Vaslui", "Iasi", "Neamt"};

    public static SimpleWeightedGraph<String, DefaultWeightedEdge> generateRomanianCities(){
        SimpleWeightedGraph<String, DefaultWeightedEdge> graph = new SimpleWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);

        for(String city: romanianCities)
            graph.addVertex(city);


        addEdge(graph, "Oradea", "Zerind", 71);
        addEdge(graph, "Arad", "Zerind", 75);
        addEdge(graph, "Arad", "Timisoara", 118);
        addEdge(graph, "Lugoj", "Timisoara", 111);
        addEdge(graph, "Lugoj", "Mehadia", 70);
        addEdge(graph, "Drobeta", "Mehadia", 75);
        addEdge(graph, "Drobeta", "Craiova", 120);
        addEdge(graph, "Pitesti", "Craiova", 138);
        addEdge(graph, "Pitesti", "Rimnicu Vilcea", 97);
        addEdge(graph, "Craiova", "Rimnicu Vilcea", 146);
        addEdge(graph, "Sibiu", "Rimnicu Vilcea", 80);
        addEdge(graph, "Sibiu", "Oradea", 151);
        addEdge(graph, "Sibiu", "Arad", 140);
        addEdge(graph, "Sibiu", "Fagaras", 99);
        addEdge(graph, "Bucharest", "Fagaras", 211);
        addEdge(graph, "Bucharest", "Pitesti", 101);
        addEdge(graph, "Bucharest", "Giurgiu", 90);
        addEdge(graph, "Bucharest", "Urziceni", 85);
        addEdge(graph, "Hirsova", "Urziceni", 98);
        addEdge(graph, "Hirsova", "Eforie", 86);
        addEdge(graph, "Vaslui", "Urziceni", 142);
        addEdge(graph, "Vaslui", "Iasi", 92);
        addEdge(graph, "Neamt", "Iasi", 87);

        return graph;
    }

    private static void addEdge(WeightedGraph<String, DefaultWeightedEdge> graph, String source, String target, int weight){
        DefaultWeightedEdge e = graph.addEdge(source, target);
        graph.setEdgeWeight(e, weight);
    }

}