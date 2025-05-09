// RandomNumbers.java
// This applet simply prints out a series of random numbers between 0 and 1

// Written by Julian Devlin, 8/97, for the text book
// "Introduction to Probability," by Charles M. Grinstead & J. Laurie Snell

// Packages we need
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class RandomNumbersSwing extends JFrame {
    private JTextArea disp;          // Area to display random numbers
    private JTextField numField;     // Text field for input
    private JButton goButton;        // Button to generate numbers
    private Random randGen;          // Random number generator

    public RandomNumbersSwing() {
        setTitle("Random Numbers Generator");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        disp = new JTextArea(20, 30);
        disp.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(disp);

        numField = new JTextField("100", 4);
        goButton = new JButton("Go");

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());
        controlPanel.add(new JLabel("No."));
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

    // Generate n random floats from 0 to 1 and print them in the disp area
    private void generate(int n) {
        for (int i = 0; i < n; i++) {
            float randFloat = randGen.nextFloat();
            disp.append(String.format("%.6f", randFloat));
            if (i % 3 == 2) {
                disp.append("\n");
            } else {
                disp.append("\t");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RandomNumbersSwing frame = new RandomNumbersSwing();
            frame.setVisible(true);
        });
    }
}
