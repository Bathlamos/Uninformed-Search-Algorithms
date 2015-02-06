package cc.legault.csi4106.a1;

import cc.legault.csi4106.a1.searchAlgorithms.BFS;
import cc.legault.csi4106.a1.searchAlgorithms.UninformedSearchAlgorithm;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.graph.UnmodifiableUndirectedGraph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class Window implements ActionListener {

    private JComboBox sourceCity, destinationCity;
    private JButton executeButton;
    private JComboBox searchAlgorithm;
    private GraphVisualizer cityMapWrapper;
    private JPanel wrapper;

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
        if(source == sourceCity)
            cityMapWrapper.setSource((String) sourceCity.getSelectedItem());
        else if(source == destinationCity)
            cityMapWrapper.setTarget((String) destinationCity.getSelectedItem());
        else if(source == executeButton)
            executeSearchAlgorithm();
    }

    private void executeSearchAlgorithm(){

        UninformedSearchAlgorithm<String> algorithm;

        switch(Algorithm.fromName((String) searchAlgorithm.getSelectedItem())){
            case BFS:
                algorithm = new BFS<>();
                break;
            case DFS:
            case UNIFORM_COST:
            default:
                throw new RuntimeException("Selected algorithm has no implementation");
        }

        List<DefaultWeightedEdge> selectedEdges = algorithm.findPath(graph,
                (String) sourceCity.getSelectedItem(),
                (String) destinationCity.getSelectedItem());

        cityMapWrapper.setSelectedEdges(selectedEdges);
    }
}
