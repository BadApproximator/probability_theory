// Description: This program simulates a game in which you roll a die 4 times, and you win if a six turns up. You specify the "No. of games" and the program prints out the percentage of games that you win.

// DeMere1.java
// This applet rolls a die in sets of four, seeing if a six comes up
// in each one.

// Written by Julian Devlin, 8/97, for the text book
// "Introduction to Probability," by Charles M. Grinstead & J. Laurie Snell

// Packages we need
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class DeMere1Swing extends JFrame {
    private JTextArea disp;          // Area to display percentage
    private JTextField numField;     // Text field for input
    private JButton goButton;        // Button to generate games
    private Random randGen;          // Random number generator

    public DeMere1Swing() {
        setTitle("DeMere's Dice Game");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        disp = new JTextArea(10, 30);
        disp.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(disp);

        numField = new JTextField("30", 4);
        goButton = new JButton("Go");

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());
        controlPanel.add(new JLabel("No. of games"));
        controlPanel.add(numField);
        controlPanel.add(goButton);

        goButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                disp.setText(""); // Reset output window
                try {
                    int n = Integer.parseInt(numField.getText());
                    generate(n);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid number.");
                }
            }
        });

        randGen = new Random(); // Create random number generator

        add(scrollPane, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
    }

    // Generate n sets of 4 rolls and print average wins
    private void generate(int n) {
        int count = 0;

        for (int i = 0; i < n; i++) { // Num of games
            for (int j = 0; j < 4; j++) { // Four rolls each game
                if (randGen.nextInt(6) + 1 == 6) { // Check for a six (1-6)
                    count++;
                    break; // Exit loop when a six is found
                }
            }
        }

        float percent = ((float) count / n) * 100;
        disp.append(String.format("%.2f%% win rate.", percent));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DeMere1Swing frame = new DeMere1Swing();
            frame.setVisible(true);
        });
    }
}
