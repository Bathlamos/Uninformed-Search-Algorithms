package cc.legault.csi4106.a1;

import javax.swing.*;
import java.awt.*;

public class Window {

    private JComboBox sourceCity;
    private JButton executeButton;
    private JLabel destinationCity;
    private JComboBox searchAlgorithm;
    private GraphVisualizer cityMapWrapper;
    private JPanel wrapper;

    public Window(){
        cityMapWrapper = new GraphVisualizer();
        wrapper.add(cityMapWrapper, BorderLayout.CENTER);
        cityMapWrapper.setGraph(SampleData.generateRomanianCities());
    }

    public JPanel getWrapper(){
        return wrapper;
    }
}
