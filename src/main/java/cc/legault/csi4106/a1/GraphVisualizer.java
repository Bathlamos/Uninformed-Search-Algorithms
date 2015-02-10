/**
 * Philippe Legault - 6376254
 *
 * CSI 4106 - Artificial Intelligence I
 * University of Ottawa
 * February 2015
 */

package cc.legault.csi4106.a1;

import org.jgrapht.Graph;
import com.mxgraph.layout.mxFastOrganicLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;
import org.jgrapht.graph.DefaultWeightedEdge;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Panel that display a representation of the world as a graph.
 * Vertices and edge weights are labeled. The style can be changed inside the class.
 */
public class GraphVisualizer extends JPanel {

    //Objects from the mxGraph library
    private mxGraphComponent graphComponent;
    private mxGraph xGraph;

    //Graph object from the jGraphT library
    private Graph<String, DefaultWeightedEdge> graph;

    //Maps the vertices of the jGraphT object to the mxGraph object
    private Map<String, mxCell> cells = new HashMap<>();

    /**
     * Constructor: prepares the JPanel
     */
    public GraphVisualizer(){
        setBackground(Color.white);
        setLayout(new BorderLayout());
    }

    /**
     * Marks a set of cities as included in the path.
     * @param cities The cities to highlight in the display
     */
    public void setTraversedCities(Iterable<String> cities){
        for(mxCell vertex: cells.values())
            vertex.setStyle("UnselectedVertex");
        for(String city: cities){
            if(cells.containsKey(city))
                cells.get(city).setStyle("SelectedVertex");
        }
        graphComponent.refresh();
    }

    /**
     * Marks a set of edges as included in the path.
     * @param edges The edges to highlight in the display.
     */
    public void setSelectedEdges(Iterable<DefaultWeightedEdge> edges){
        Object[] edgeCells = xGraph.getAllEdges(cells.values().toArray());
        for(Object edgeCell: edgeCells)
            ((mxCell) edgeCell).setStyle("UnselectedEdge");

        for(DefaultWeightedEdge edge: edges){
            mxCell source = cells.get(graph.getEdgeSource(edge));
            mxCell target = cells.get(graph.getEdgeTarget(edge));

            mxCell edgeCell = (mxCell)xGraph.getEdgesBetween(source, target)[0];
            edgeCell.setStyle("SelectedEdge");
        }
        graphComponent.refresh();
    }

    /**
     * Builds mxGraph objects to display in the JPanel.
     * @param graph The graph to display.
     */
    public void setGraph(Graph<String, DefaultWeightedEdge> graph){

        if(graphComponent != null)
            remove(graphComponent);

        this.graph = graph;
        xGraph = new mxGraph();

        //Styling
        mxStylesheet stylesheet = new mxStylesheet();

        //General styling
        Map<String, Object> edgeStyle = stylesheet.getDefaultEdgeStyle();
        edgeStyle.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_CONNECTOR);
        edgeStyle.put(mxConstants.STYLE_ENDARROW, mxConstants.NONE);
        edgeStyle.put(mxConstants.STYLE_STROKECOLOR, "#7f8c8d");
        edgeStyle.put(mxConstants.STYLE_NOLABEL, false);

        //Styling for unselected edges
        Map<String, Object> unselectedEdgeStyle = new HashMap<String, Object>(edgeStyle);
        unselectedEdgeStyle.put(mxConstants.STYLE_STROKECOLOR, "#2980b9");
        unselectedEdgeStyle.put(mxConstants.STYLE_LABEL_BACKGROUNDCOLOR, "#ffffff");
        stylesheet.putCellStyle("UnselectedEdge", unselectedEdgeStyle);

        //Styling for selected edges
        Map<String, Object> doubledEdgeStyle = new HashMap<>(unselectedEdgeStyle);
        doubledEdgeStyle.put(mxConstants.STYLE_STROKECOLOR, "#c0392b");
        doubledEdgeStyle.put(mxConstants.STYLE_FONTCOLOR, "#000000");
        stylesheet.putCellStyle("SelectedEdge", doubledEdgeStyle);

        //Styling for unselected vertices
        Map<String, Object> unselectedVertexStyle = stylesheet.getDefaultVertexStyle();
        unselectedVertexStyle.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
        unselectedVertexStyle.put(mxConstants.STYLE_SPACING, "1");
        unselectedVertexStyle.put(mxConstants.STYLE_FILLCOLOR, "#e1e1e1");
        unselectedVertexStyle.put(mxConstants.STYLE_STROKECOLOR, "#e1e1e1");
        unselectedVertexStyle.put(mxConstants.STYLE_FONTCOLOR, "#000000");
        stylesheet.putCellStyle("UnselectedVertex", unselectedVertexStyle);

        //Styling for selected vertices
        Map<String, Object> selectedVertexStyle = new HashMap<>(unselectedVertexStyle);
        selectedVertexStyle.put(mxConstants.STYLE_FILLCOLOR, "#000000");
        selectedVertexStyle.put(mxConstants.STYLE_FONTCOLOR, "#ffffff");
        stylesheet.putCellStyle("SelectedVertex", selectedVertexStyle);

        xGraph.setStylesheet(stylesheet);

        //Map the jGraphT vertices and edges to their mxGraph counterpart
        final Object parent = xGraph.getDefaultParent();
        for(String vertex: graph.vertexSet()){
            mxCell cell = (mxCell) xGraph.insertVertex(parent, vertex, vertex, 50, 50, 10, 10, "UnselectedVertex");
            xGraph.updateCellSize(cell);

            for(Entry<String, mxCell> entry: cells.entrySet()){
                DefaultWeightedEdge edge = graph.getEdge(entry.getKey(), vertex);
                if(edge != null)
                   xGraph.insertEdge(parent, null, (int) graph.getEdgeWeight(edge), entry.getValue(), cell,
                           "UnselectedEdge");

            }
            cell.setId(vertex);
            cells.put(vertex, cell);
        }

        //The cells must be resizable to contain their label
        xGraph.setAutoSizeCells(true);
        xGraph.setCellsResizable(true);

        graphComponent = new mxGraphComponent(xGraph);
        graphComponent.setBorder(new EmptyBorder(10, 10, 10, 10));
        graphComponent.setBackground(Color.white);
        graphComponent.getViewport().setBackground(Color.white);

        //Define layout
        mxFastOrganicLayout layout = new mxFastOrganicLayout(xGraph);
        //A min distance is required so that there are no collisions with the edge weight labels
        layout.setMinDistanceLimit(5);
        layout.execute(xGraph.getDefaultParent());

        xGraph.setCellsEditable(false);
        xGraph.setCellsMovable(false);
        xGraph.setCellsResizable(false);
        xGraph.setCellsSelectable(false);

        add(graphComponent, BorderLayout.CENTER);
        revalidate();
    }

}
