package cc.legault.csi4106.a1;

import javax.swing.*;

/**
 * Created by Philippe on 2/3/2015.
 */
public class Main {

    public static void main(String[] args){

        JFrame frame = new JFrame("CSI 4106 Assignment 1 -- Uninformed Search Algorithms");
        frame.setContentPane(new Window().getWrapper());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }

}
