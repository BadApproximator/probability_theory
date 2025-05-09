// CoinTosses.java
// This applet prints out random heads and tails, and keeps track
// of the number of heads.

// Written by Julian Devlin, 8/97, for the text book
// "Introduction to Probability," by Charles M. Grinstead & J. Laurie Snell

// Packages we need
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class CoinTossesSwing extends JFrame {
    private JTextArea disp;          // Area to display HT
    private JTextField numField;     // Text field for input
    private JButton goButton;        // Button to generate coin tosses
    private Random randGen;          // Random number generator

    public CoinTossesSwing() {
        setTitle("Coin Tosses Generator");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        disp = new JTextArea(20, 30);
        disp.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(disp);

        numField = new JTextField("100", 4);
        goButton = new JButton("Go");

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());
        controlPanel.add(new JLabel("No. of flips"));
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

    // Generate n random coin tosses and track the number of heads
    private void generate(int n) {
        int count = 0;
        for (int i = 0; i < n; i++) {
            if (randGen.nextFloat() < 0.5) {
                disp.append("H");
                count++;
            } else {
                disp.append("T");
            }

            if (i % 30 == 29) {
                disp.append("\n");
            }
        }

        float percent = ((float) count / n) * 100;
        disp.append(String.format("\n%d heads out of %d = %.2f %%", count, n, percent));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CoinTossesSwing frame = new CoinTossesSwing();
            frame.setVisible(true);
        });
    }
}
