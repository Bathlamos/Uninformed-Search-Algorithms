package cc.legault.csi4106.a1;

import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.Edge;
import org.jgrapht.Graph;
import com.mxgraph.layout.mxFastOrganicLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxEventSource.mxIEventListener;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;
import org.jgrapht.graph.DefaultWeightedEdge;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class GraphVisualizer extends JPanel{

    public void setGraph(Graph<String, DefaultWeightedEdge> graph){
        final mxGraph xGraph = new mxGraph();

        mxStylesheet stylesheet = new mxStylesheet();

        Map<String, Object> edgeStyle = stylesheet.getDefaultEdgeStyle();
        edgeStyle.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_CONNECTOR);
        edgeStyle.put(mxConstants.STYLE_ENDARROW, mxConstants.NONE);
        edgeStyle.put(mxConstants.STYLE_STROKECOLOR, "#7f8c8d");
        edgeStyle.put(mxConstants.STYLE_NOLABEL, true);

        Map<String, Object> unselectedEdgeStyle = new HashMap<String, Object>(edgeStyle);
        unselectedEdgeStyle.put(mxConstants.STYLE_STROKECOLOR, "#2980b9");
        stylesheet.putCellStyle("selected", unselectedEdgeStyle);

        Map<String, Object> doubledEdgeStyle = new HashMap<>(edgeStyle);
        doubledEdgeStyle.put(mxConstants.STYLE_STROKECOLOR, "#c0392b");
        stylesheet.putCellStyle("unselected", doubledEdgeStyle);

        Map<String, Object> vertexStyle = stylesheet.getDefaultVertexStyle();
        vertexStyle.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE);
        vertexStyle.put(mxConstants.STYLE_FILLCOLOR, "#000000");
        vertexStyle.put(mxConstants.STYLE_FONTCOLOR, "#ffffff");
        stylesheet.putCellStyle("vertex", vertexStyle);

        xGraph.setStylesheet(stylesheet);

        Map<String, mxCell> vertices = new HashMap<>();
        final Object parent = xGraph.getDefaultParent();
        for(String vertex: graph.vertexSet()){
            mxCell cell = (mxCell) xGraph.insertVertex(parent, vertex, vertex, 0, 0, 20, 20, "vertex");
            for(Entry<String, mxCell> entry: vertices.entrySet()){
                int numEdgeSelect = graph.getAllEdges(entry.getKey(), vertex).size();
                if(numEdgeSelect == 1)
                    xGraph.insertEdge(parent, null, "Edge", entry.getValue(), cell, "single");
                else if(numEdgeSelect == 2)
                    xGraph.insertEdge(parent, null, "Edge", entry.getValue(), cell, "double");
            }
            cell.setId(vertex);
            vertices.put(vertex, cell);
        }

        // Define layout
        mxFastOrganicLayout layout = new mxFastOrganicLayout(xGraph);

        // Set all properties
        //layout.setForceConstant(100);
        layout.setDisableEdgeStyle(true);

        // Layout graph
        layout.execute(xGraph.getDefaultParent());


        mxGraphComponent graphComponent = new mxGraphComponent(xGraph);
        graphComponent.setBorder(new EmptyBorder(10, 10, 10, 10));
        graphComponent.setBackground(Color.white);
        graphComponent.getViewport().setBackground(Color.white);

        xGraph.setCellsEditable(false);
        xGraph.setCellsMovable(true);
        xGraph.setCellsResizable(false);
        xGraph.setCellsSelectable(true);

        /*xGraph.addListener(mxEvent.CELLS_MOVED, new mxIEventListener() {

            @Override
            public void invoke(Object sender, mxEventObject evt) {
                //Realign all cells in reference graph
                for(Object o: xGraph.getChildVertices(parent)){
                    mxCell cell = (mxCell) o;
                    XY xy = cellPositions.get(cell.getId());
                    xy.x = cell.getGeometry().getX();
                    xy.y = cell.getGeometry().getY();
                }
            }
        });*/

        add(graphComponent);
        revalidate();
    }

}
