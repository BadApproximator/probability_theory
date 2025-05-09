// Description: This program simulates the game of heads and tails when you specify "tosses per game" and "number of games". It plots the distribution for your winnings.

// HTWin.java

// Written by Julian Devlin, 8/97, for the text book
// "Introduction to Probability," by Charles M. Grinstead & J. Laurie Snell

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class HTWinSwing extends JFrame {
    private SpikeGraph sg; // Custom graphing component
    private JLabel message, message2;
    private JTextField numFlipsField, numGamesField;
    private JButton simulateButton;

    public HTWinSwing() {
        setTitle("Heads-Tails Win Simulation");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        message = new JLabel("Tosses per game =");
        message2 = new JLabel("Number of games =");
        numFlipsField = new JTextField(4);
        numGamesField = new JTextField(4);
        simulateButton = new JButton("Simulate");

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());
        controlPanel.add(message);
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

    private void simulate() {
        try {
            int nf = Integer.parseInt(numFlipsField.getText().trim());
            int ng = Integer.parseInt(numGamesField.getText().trim());

            int[] results = new int[2 * nf + 1];

            for (int i = 0; i < ng; i++) {
                int yCoord = 0;
                for (int j = 0; j < nf; j++) {
                    yCoord += Math.random() < 0.5 ? 1 : -1;
                }
                results[nf + yCoord]++;
            }

            sg.updateGraph(results, -nf, nf);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HTWinSwing frame = new HTWinSwing();
            frame.setVisible(true);
        });
    }
}

// Custom graphing component
class SpikeGraph extends JPanel {
    private int[] values;
    private int minX, maxX;

    public void updateGraph(int[] values, int minX, int maxX) {
        this.values = values;
        this.minX = minX;
        this.maxX = maxX;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (values == null) return;

        int width = getWidth();
        int height = getHeight();

        int maxY = 0;
        for (int value : values) {
            if (value > maxY) maxY = value;
        }

        g.drawString("X: " + minX + " to " + maxX, 10, height - 10);
        g.drawString("Y: 0 to " + maxY, 10, height - 25);

        for (int i = 0; i < values.length; i++) {
            int x = (int) ((i / (double) values.length) * width);
            int y = height - (int) ((values[i] / (double) maxY) * height);
            g.drawLine(x, height, x, y);
        }
    }
}
