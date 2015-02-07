package cc.legault.csi4106.a1;

import cc.legault.csi4106.a1.searchAlgorithms.BFS;
import cc.legault.csi4106.a1.searchAlgorithms.DFS;
import cc.legault.csi4106.a1.searchAlgorithms.UniformCostSearch;
import cc.legault.csi4106.a1.searchAlgorithms.UninformedSearchAlgorithm;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Window implements ActionListener {

    private JComboBox sourceCity, destinationCity;
    private JButton executeButton;
    private JComboBox searchAlgorithm;
    private GraphVisualizer cityMapWrapper;
    private JPanel wrapper;
    private JLabel numberOfNodesGenerated;
    private JLabel maxNumberOfNodes;
    private JLabel path;

    private SimpleWeightedGraph<String, DefaultWeightedEdge> graph;

    public Window(){
        cityMapWrapper = new GraphVisualizer();
        wrapper.add(cityMapWrapper, BorderLayout.CENTER);

        graph = SampleData.generateRomanianCities();

        //Populate the JComboBoxes
        for(String vertex: graph.vertexSet()){
            sourceCity.addItem(vertex);
            destinationCity.addItem(vertex);
        }

        sourceCity.addActionListener(this);
        destinationCity.addActionListener(this);
        executeButton.addActionListener(this);

        for(Algorithm algorithm: Algorithm.values())
            searchAlgorithm.addItem(algorithm.getName());

        cityMapWrapper.setGraph(graph);

        sourceCity.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null) {});
        destinationCity.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null) {});

    }

    public JPanel getWrapper(){
        return wrapper;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(source == sourceCity || source == destinationCity) {
            cityMapWrapper.setTraversedCities(
                    Lists.newArrayList((String) sourceCity.getSelectedItem(), (String) destinationCity.getSelectedItem()));
            cityMapWrapper.setSelectedEdges(new HashSet<DefaultWeightedEdge>());
        }else if(source == executeButton)
            executeSearchAlgorithm();
    }

    private void executeSearchAlgorithm(){

        UninformedSearchAlgorithm<String> algorithm;

        switch(Algorithm.fromName((String) searchAlgorithm.getSelectedItem())){
            case BFS:
                algorithm = new BFS<>();
                break;
            case DFS:
                algorithm = new DFS<>();
                break;
            case UNIFORM_COST:
                algorithm = new UniformCostSearch<>();
                break;
            default:
                throw new RuntimeException("Selected algorithm has no implementation");
        }

        String origin =  (String) sourceCity.getSelectedItem();
        String destination = (String) destinationCity.getSelectedItem();

        List<DefaultWeightedEdge> selectedEdges = algorithm.findPath(graph, origin, destination);

        cityMapWrapper.setSelectedEdges(selectedEdges);
        numberOfNodesGenerated.setText(String.format("%d nodes generated", algorithm.getTotalNumberOfNodesGenerated()));
        maxNumberOfNodes.setText(String.format("%d nodes at once in memory", algorithm.getMaximumNodesInMemory()));

        List<String> traversedCities = getTraversedCities(selectedEdges, origin, destination);
        path.setText("<html><body>" + Joiner.on(",<br />").join(traversedCities) + "</body></html>");
        cityMapWrapper.setTraversedCities(traversedCities);
    }

    private List<String> getTraversedCities(List<DefaultWeightedEdge> selectedEdges, String origin, String destination){
        String currentVertex = origin;
        List<String> list = new LinkedList<>();
        list.add(currentVertex);
        Iterator<DefaultWeightedEdge> edgesIt = selectedEdges.iterator();

        while(!currentVertex.equals(destination) && edgesIt.hasNext()){
            currentVertex = Graphs.getOppositeVertex(graph, edgesIt.next(), currentVertex);
            list.add(currentVertex);
        }

        return list;
    }
}
