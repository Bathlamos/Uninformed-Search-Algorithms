/**
 * Philippe Legault - 6376254
 *
 * CSI 4106 - Artificial Intelligence I
 * University of Ottawa
 * February 2015
 */

package cc.legault.csi4106.a1;

import cc.legault.csi4106.a1.searchAlgorithms.BFS;
import cc.legault.csi4106.a1.searchAlgorithms.DFS;
import cc.legault.csi4106.a1.searchAlgorithms.UniformCostSearch;
import cc.legault.csi4106.a1.searchAlgorithms.UninformedSearchAlgorithm;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
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

/**
 * Main GUI Window. Build with IntelliJ GUI Builder.
 */
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

    /**
     * Constructor: binds the action listeners and instantiate the graph display
     */
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

        //Trigger the action listeners
        sourceCity.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null) {});
        destinationCity.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null) {});

    }

    /**
     * @return The top-level JPanel.
     */
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

    /**
     * Instantiate the proper Search Algorithm, executes it on the same thread and
     * highlights the path on the Window. Execution statistics are also displayed.
     */
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

        //Execution statistics and path highlighting
        cityMapWrapper.setSelectedEdges(selectedEdges);
        numberOfNodesGenerated.setText(String.format("%d nodes generated", algorithm.getTotalNumberOfNodesGenerated()));
        maxNumberOfNodes.setText(String.format("%d nodes at once in memory", algorithm.getMaximumNodesInMemory()));

        List<String> traversedCities = getTraversedCities(selectedEdges, origin, destination);
        path.setText("<html><body>" + Joiner.on(",<br />").join(traversedCities) + "</body></html>");
        cityMapWrapper.setTraversedCities(traversedCities);
    }

    /**
     * Computes the set of cities contained in a path
     * @param selectedEdges The edges in the path.
     * @param origin The city at the origin.
     * @param destination The destination city.
     * @return The List of cities that are traversed.
     */
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
