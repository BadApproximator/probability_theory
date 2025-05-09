// Description: You play a game in which a pair of dice are tossed a number of times. You win if double six occurs at least once. You want to know how many tosses you should demand to make this a favorable game. This program allows you to simulate the case of 24 tosses and 25 tosses to check if 24 games makes an unfavorable game and 25 a favorable game as claimed by early gamblers.

// DeMere2.java
// This applet rolls a pair of dice in sets of 24 or 25, seeing if a 
// pair of sixes comes up in each set.

// Written by Julian Devlin, 8/97, for the text book
// "Introduction to Probability," by Charles M. Grinstead & J. Laurie Snell

// Packages we need
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class DeMere2Swing extends JFrame {
    private JTextArea disp;          // Area to display percentage
    private JTextField numField;     // Text field for input
    private JButton go24Button, go25Button; // Buttons for 24 and 25 rolls
    private Random randGen;          // Random number generator

    public DeMere2Swing() {
        setTitle("DeMere's Double Sixes Game");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        disp = new JTextArea(10, 30);
        disp.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(disp);

        numField = new JTextField("30", 4);
        go24Button = new JButton("24 times");
        go25Button = new JButton("25 times");

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());
        controlPanel.add(new JLabel("No. of games"));
        controlPanel.add(numField);
        controlPanel.add(go24Button);
        controlPanel.add(go25Button);

        go24Button.addActionListener(e -> generateGames(24));
        go25Button.addActionListener(e -> generateGames(25));

        randGen = new Random(); // Create random number generator

        add(scrollPane, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
    }

    // Generate n sets of rolls, and print average wins (double sixes)
    private void generateGames(int sets) {
        disp.setText("");
        try {
            int n = Integer.parseInt(numField.getText());
            int count = 0;

            for (int i = 0; i < n; i++) { // Num games
                for (int j = 0; j < sets; j++) { // Rolls per game
                    int randInt1 = randGen.nextInt(6) + 1;
                    int randInt2 = randGen.nextInt(6) + 1;
                    if (randInt1 == 6 && randInt2 == 6) { // Double sixes
                        count++;
                        break; // Exit loop when double sixes are found
                    }
                }
            }

            float percent = ((float) count / n) * 100;
            disp.append(String.format("%.2f%% win rate.", percent));

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DeMere2Swing frame = new DeMere2Swing();
            frame.setVisible(true);
        });
    }
}
