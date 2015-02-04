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

public class GraphVisualizer extends JPanel {

    private mxGraphComponent graphComponent;

    public GraphVisualizer(){
        setBackground(Color.white);
        setLayout(new BorderLayout());
    }

    public void setGraph(Graph<String, DefaultWeightedEdge> graph, String source, String target){

        if(graphComponent != null)
            remove(graphComponent);

        final mxGraph xGraph = new mxGraph();

        mxStylesheet stylesheet = new mxStylesheet();

        Map<String, Object> edgeStyle = stylesheet.getDefaultEdgeStyle();
        edgeStyle.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_CONNECTOR);
        edgeStyle.put(mxConstants.STYLE_ENDARROW, mxConstants.NONE);
        edgeStyle.put(mxConstants.STYLE_STROKECOLOR, "#7f8c8d");
        edgeStyle.put(mxConstants.STYLE_NOLABEL, false);

        Map<String, Object> unselectedEdgeStyle = new HashMap<String, Object>(edgeStyle);
        unselectedEdgeStyle.put(mxConstants.STYLE_STROKECOLOR, "#2980b9");
        unselectedEdgeStyle.put(mxConstants.STYLE_LABEL_BACKGROUNDCOLOR, "#ffffff");
        edgeStyle.put(mxConstants.STYLE_FONTCOLOR, "#000000");
        stylesheet.putCellStyle("unselected", unselectedEdgeStyle);

        Map<String, Object> doubledEdgeStyle = new HashMap<>(edgeStyle);
        doubledEdgeStyle.put(mxConstants.STYLE_STROKECOLOR, "#c0392b");
        stylesheet.putCellStyle("selected", doubledEdgeStyle);

        Map<String, Object> unselectedVertexStyle = stylesheet.getDefaultVertexStyle();
        unselectedVertexStyle.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
        unselectedVertexStyle.put(mxConstants.STYLE_SPACING, "1");
        unselectedVertexStyle.put(mxConstants.STYLE_FILLCOLOR, "#e1e1e1");
        unselectedVertexStyle.put(mxConstants.STYLE_STROKECOLOR, "#e1e1e1");
        unselectedVertexStyle.put(mxConstants.STYLE_FONTCOLOR, "#000000");
        stylesheet.putCellStyle("UnselectedVertex", unselectedVertexStyle);

        Map<String, Object> selectedVertexStyle = new HashMap<>(unselectedVertexStyle);
        selectedVertexStyle.put(mxConstants.STYLE_FILLCOLOR, "#000000");
        selectedVertexStyle.put(mxConstants.STYLE_FONTCOLOR, "#ffffff");
        stylesheet.putCellStyle("SelectedVertex", selectedVertexStyle);

        xGraph.setStylesheet(stylesheet);

        Map<String, mxCell> vertices = new HashMap<>();
        final Object parent = xGraph.getDefaultParent();
        for(String vertex: graph.vertexSet()){

            String style = "UnselectedVertex";
            if (vertex.equals(source) || vertex.equals(target))
                style = "SelectedVertex";

            mxCell cell = (mxCell) xGraph.insertVertex(parent, vertex, vertex, 50, 50, 10, 10, style);
            xGraph.updateCellSize(cell);

            for(Entry<String, mxCell> entry: vertices.entrySet()){
                DefaultWeightedEdge edge = graph.getEdge(entry.getKey(), vertex);
                if(edge != null)
                    xGraph.insertEdge(parent, null, (int) graph.getEdgeWeight(edge), entry.getValue(), cell, "unselected");
                /*else if(numEdgeSelect == 2)
                    xGraph.insertEdge(parent, null, "Edge", entry.getValue(), cell, "double");*/
            }
            cell.setId(vertex);
            vertices.put(vertex, cell);
        }

        xGraph.setAutoSizeCells(true);
        xGraph.setCellsResizable(true);

        graphComponent = new mxGraphComponent(xGraph);
        graphComponent.setBorder(new EmptyBorder(10, 10, 10, 10));
        graphComponent.setBackground(Color.white);
        graphComponent.getViewport().setBackground(Color.white);

        // Define layout
        mxFastOrganicLayout layout = new mxFastOrganicLayout(xGraph);
        layout.execute(xGraph.getDefaultParent());

        xGraph.setCellsEditable(false);
        xGraph.setCellsMovable(false);
        xGraph.setCellsResizable(false);
        xGraph.setCellsSelectable(false);

        add(graphComponent, BorderLayout.CENTER);
        revalidate();
    }

}
