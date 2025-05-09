// Description: This program plots your fortune in the game of heads and tails. You specify the "Number of coin tosses".

// HTSimulation.java

// Written by Julian Devlin, 8/97, for the text book
// "Introduction to Probability," by Charles M. Grinstead & J. Laurie Snell

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class HTSimulationSwing extends JFrame {
    private LineGraph lg; // Custom graphing component
    private JLabel message;
    private JTextField numField;
    private JButton simulateButton;

    public HTSimulationSwing() {
        setTitle("Heads-Tails Simulation");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        message = new JLabel("Number of coin tosses =");
        numField = new JTextField(4);
        simulateButton = new JButton("Simulate");

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());
        controlPanel.add(message);
        controlPanel.add(numField);
        controlPanel.add(simulateButton);

        lg = new LineGraph(); // Initialize empty graph

        simulateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simulate();
            }
        });

        add(lg, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
    }

    // Simulation method
    private void simulate() {
        try {
            int n = Integer.parseInt(numField.getText().trim());
            ArrayList<Float> xCoords = new ArrayList<>();
            ArrayList<Float> yCoords = new ArrayList<>();

            float yCoord = 0.0f;
            xCoords.add(0f);
            yCoords.add(0f);

            for (int i = 0; i < n; i++) {
                yCoord += Math.random() < 0.5 ? 1 : -1;
                xCoords.add((float) (i + 1));
                yCoords.add(yCoord);
            }

            lg.updateGraph(xCoords, yCoords);
            message.setText("Number of coin tosses = " + n);

        } catch (NumberFormatException ex) {
            message.setText("Please enter a valid number.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HTSimulationSwing frame = new HTSimulationSwing();
            frame.setVisible(true);
        });
    }
}

// Custom graphing component
class LineGraph extends JPanel {
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
        g2d.setColor(Color.BLUE);

        int width = getWidth();
        int height = getHeight();

        float xMax = xCoords.get(xCoords.size() - 1);
        float yMax = Math.max(Math.abs(yCoords.stream().max(Float::compare).orElse(0f)),
                              Math.abs(yCoords.stream().min(Float::compare).orElse(0f)));

        g2d.drawString("X: 0 - " + (int)xMax, 10, height - 10);
        g2d.drawString("Y: -" + (int)yMax + " to " + (int)yMax, 10, height - 25);

        for (int i = 1; i < xCoords.size(); i++) {
            int x1 = (int) ((xCoords.get(i - 1) / xMax) * width);
            int y1 = (int) (height / 2 - (yCoords.get(i - 1) / yMax) * (height / 2));
            int x2 = (int) ((xCoords.get(i) / xMax) * width);
            int y2 = (int) (height / 2 - (yCoords.get(i) / yMax) * (height / 2));

            g2d.drawLine(x1, y1, x2, y2);
        }
    }
}
