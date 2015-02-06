package cc.legault.csi4106.a1;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.UnmodifiableUndirectedGraph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window implements ActionListener {

    private JComboBox sourceCity, destinationCity;
    private JButton executeButton;
    private JComboBox searchAlgorithm;
    private GraphVisualizer cityMapWrapper;
    private JPanel wrapper;

    private UnmodifiableUndirectedGraph<String, DefaultWeightedEdge> graph;

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

        for(Algorithm algorithm: Algorithm.values())
            searchAlgorithm.addItem(algorithm.getName());

        cityMapWrapper.setGraph(graph);
        actionPerformed(null);

    }

    public JPanel getWrapper(){
        return wrapper;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        cityMapWrapper.setSource((String) sourceCity.getSelectedItem());
        cityMapWrapper.setTarget((String) destinationCity.getSelectedItem());
    }
}
