// Description: This program simulates the horse races described in Chapter 1. You specify the "No. of races" and then the program prints the proportion of times each horse won.

// HorseRace.java

// Written by Julian Devlin, 8/97, for the text book
// "Introduction to Probability," by Charles M. Grinstead & J. Laurie Snell

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HorseRaceSwing extends JFrame {
    private JTextArea disp;
    private JTextField numField;
    private JButton goButton;

    private GeneralSimulation gs;

    public HorseRaceSwing() {
        setTitle("Horse Race Simulation");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        disp = new JTextArea(10, 15);
        disp.setEditable(false);

        numField = new JTextField("30", 4);
        goButton = new JButton("Go");

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());
        controlPanel.add(new JLabel("No. of races"));
        controlPanel.add(numField);
        controlPanel.add(goButton);

        add(new JScrollPane(disp), BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        float[] probs = {.3f, .4f, .2f, .1f};
        String[] names = {"Acorn", "Balky", "Chestnut", "Dolby"};

        gs = new GeneralSimulation(probs, names);

        goButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simulate();
            }
        });
    }

    private void simulate() {
        try {
            int n = Integer.parseInt(numField.getText().trim());
            gs.simulate(n);
            disp.setText("");
            for (int i = 0; i < gs.names.length; i++) {
                disp.append(gs.names[i] + " - " + gs.resultsPercent[i] + "%\n");
            }
        } catch (NumberFormatException e) {
            disp.setText("Please enter a valid number.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HorseRaceSwing frame = new HorseRaceSwing();
            frame.setVisible(true);
        });
    }
}

// GeneralSimulation class (to be implemented)
class GeneralSimulation {
    float[] probs;
    String[] names;
    float[] resultsPercent;

    public GeneralSimulation(float[] probs, String[] names) {
        this.probs = probs;
        this.names = names;
        this.resultsPercent = new float[names.length];
    }

    public void simulate(int n) {
        int[] results = new int[names.length];

        for (int i = 0; i < n; i++) {
            double r = Math.random();
            float cumulative = 0f;
            for (int j = 0; j < probs.length; j++) {
                cumulative += probs[j];
                if (r <= cumulative) {
                    results[j]++;
                    break;
                }
            }
        }

        for (int i = 0; i < names.length; i++) {
            resultsPercent[i] = (results[i] / (float) n) * 100;
        }
    }
}