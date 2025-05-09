// Description: This program simulates the game of heads or tails where you specify the "tosses per game" and the "number of games." The program then plots the distribution for the times that you are in the lead.

// HTLead.java

// Written by Julian Devlin, 8/97, for the text book
// "Introduction to Probability," by Charles M. Grinstead & J. Laurie Snell

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class HTLeadSwing extends JFrame {
    private SpikeGraph sg; // Custom graphing component
    private JLabel message1, message2;
    private JTextField numFlipsField, numGamesField;
    private JButton simulateButton;

    public HTLeadSwing() {
        setTitle("HT Lead Simulation");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        message1 = new JLabel("Tosses per game =");
        message2 = new JLabel("Number of games =");
        numFlipsField = new JTextField(3);
        numGamesField = new JTextField(4);
        simulateButton = new JButton("Simulate");

        JPanel controlPanel = new JPanel(new FlowLayout());
        controlPanel.add(message1);
        controlPanel.add(numFlipsField);
        controlPanel.add(message2);
        controlPanel.add(numGamesField);
        controlPanel.add(simulateButton);

        sg = new SpikeGraph(); // Initialize empty graph

        simulateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simulate();
            }
        });

        add(sg, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
    }

    // Simulation method
    private void simulate() {
        try {
            int nf = Integer.parseInt(numFlipsField.getText().trim());
            int ng = Integer.parseInt(numGamesField.getText().trim());

            ArrayList<Float> xCoords = new ArrayList<>();
            ArrayList<Float> yCoords = new ArrayList<>();

            for (int i = 0; i <= nf; i++) {
                xCoords.add((float) i);
                yCoords.add(0f);
            }

            for (int i = 0; i < ng; i++) {
                int yCoord = 0, inLead = 0;
                for (int j = 0; j < nf; j++) {
                    yCoord += Math.random() < 0.5 ? 1 : -1;
                    if (yCoord > 0) inLead++;
                }
                yCoords.set(inLead, yCoords.get(inLead) + 1);
            }

            sg.updateGraph(xCoords, yCoords);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HTLeadSwing frame = new HTLeadSwing();
            frame.setVisible(true);
        });
    }
}

// Custom graphing component
class SpikeGraph extends JPanel {
    private java.util.List<Float> xCoords = new ArrayList<>();
    private java.util.List<Float> yCoords = new ArrayList<>();

    public void updateGraph(java.util.List<Float> x, java.util.List<Float> y) {
        this.xCoords = x;
        this.yCoords = y;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (xCoords.size() < 2) return;

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.RED);

        int width = getWidth();
        int height = getHeight();

        float yMax = yCoords.stream().max(Float::compare).orElse(1f);

        for (int i = 0; i < xCoords.size(); i++) {
            int x = (int) ((xCoords.get(i) / xCoords.size()) * width);
            int barHeight = (int) ((yCoords.get(i) / yMax) * height);
            g2d.fillRect(x, height - barHeight, width / xCoords.size(), barHeight);
        }
    }
}
