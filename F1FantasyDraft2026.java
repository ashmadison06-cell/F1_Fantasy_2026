
/**
 * @author A. Mad.
 * @version 1.0.0
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

 public class F1FantasyDraft2026 {
 
    static class Driver {
        String name;
        int cost;
        int points;

        Driver(String name, int cost, int points) {
            this.name = name;
            this.cost = cost;
            this.points = points;
        }

        @Override
        public String toString() {
            return name + " ($" + cost + "M)";
        }
    }

    private static final int BUDGET = 100;

    private JFrame frame;
    private JComboBox<Driver> driverDropdown;
    private JLabel budgetLabel;
    private JTextArea selectedArea;
    private JButton pickButton;
    private JButton removeButton;
    private JButton captainButton;

    private int remainingBudget = BUDGET;
    private java.util.List<Driver> selectedDrivers = new ArrayList<>();

    private java.util.List<Driver> drivers = Arrays.asList(
            // ===== TIER 1 – 2025 Title Contenders =====
    new Driver("Max Verstappen", 37, 510),
    new Driver("Lando Norris", 35, 485),
    new Driver("Charles Leclerc", 33, 460),
    new Driver("Oscar Piastri", 32, 445),
    new Driver("Lewis Hamilton", 31, 430),
    
    // ===== TIER 2 – Regular Podium / Strong 2025 Points =====
    new Driver("George Russell", 27, 395),
    new Driver("Carlos Sainz", 26, 380),
    new Driver("Fernando Alonso", 24, 355),
    new Driver("Sergio Perez", 23, 340),
    
    // ===== TIER 3 – Consistent 2025 Scorers =====
    new Driver("Pierre Gasly", 20, 315),
    new Driver("Kimi Antonelli", 19, 300),
    new Driver("Alex Albon", 18, 285),
    new Driver("Esteban Ocon", 17, 270),
    new Driver("Lance Stroll", 16, 255),
    
    // ===== TIER 4 – Lower Midfield / Rookies =====
    new Driver("Nico Hulkenberg", 14, 235),
    new Driver("Liam Lawson", 13, 220),
    new Driver("Isack Hadjar", 12, 210),
    new Driver("Ollie Bearman", 11, 200),
    new Driver("Franco Colapinto", 10, 190),
    new Driver("Gabriel Bortoleto", 9, 180),
    new Driver("Arvid Lindblad", 8, 170),
    new Driver("Valtteri Bottas", 7, 160)
    
        );

    public F1FantasyDraft2026() {
        frame = new JFrame("2026 F1 Fantasy Draft");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new GridLayout(2, 1));
        budgetLabel = new JLabel("Budget: $" + remainingBudget + "M");
        topPanel.add(budgetLabel);

        driverDropdown = new JComboBox<>(drivers.toArray(new Driver[0]));
        topPanel.add(driverDropdown);

        frame.add(topPanel, BorderLayout.NORTH);

        selectedArea = new JTextArea();
        selectedArea.setEditable(false);
        frame.add(new JScrollPane(selectedArea), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        pickButton = new JButton("Pick Driver");
        captainButton = new JButton("Finish and Choose Captain");
        captainButton.setEnabled(false);
        removeButton = new JButton("Remove Driver");
        removeButton.addActionListener(e -> removeDriver());


        bottomPanel.add(pickButton);
        bottomPanel.add(removeButton);
        bottomPanel.add(captainButton);

        frame.add(bottomPanel, BorderLayout.SOUTH);

        pickButton.addActionListener(e -> pickDriver());
        captainButton.addActionListener(e -> chooseCaptain());

        frame.setVisible(true);
    }
    private void removeDriver() {

    if (selectedDrivers.isEmpty()) {
        JOptionPane.showMessageDialog(frame, "No drivers to remove!");
        return;
    }

    Driver driverToRemove = (Driver) JOptionPane.showInputDialog(
            frame,
            "Select driver to remove:",
            "Remove Driver",
            JOptionPane.PLAIN_MESSAGE,
            null,
            selectedDrivers.toArray(),
            selectedDrivers.get(0)
    );

    if (driverToRemove == null) return;

    // Refund cost
    remainingBudget += driverToRemove.cost;

    // Remove driver
    selectedDrivers.remove(driverToRemove);

    // Update UI
    budgetLabel.setText("Budget: $" + remainingBudget + "M");
    updateSelectedArea();

    // Re-enable picking if needed
    if (selectedDrivers.size() < 5) {
        pickButton.setEnabled(true);
        captainButton.setEnabled(false);
    }

    JOptionPane.showMessageDialog(frame,
            driverToRemove.name + " removed.\nBudget refunded: $" + driverToRemove.cost + "M");
  }

    private void pickDriver() {
        Driver selected = (Driver) driverDropdown.getSelectedItem();

        if (selectedDrivers.contains(selected)) {
            JOptionPane.showMessageDialog(frame, "Driver already selected!");
            return;
        }

        if (selected.cost > remainingBudget) {
            JOptionPane.showMessageDialog(frame, "Not enough budget!");
            return;
        }

        if (selectedDrivers.size() >= 5) {
            JOptionPane.showMessageDialog(frame, "You already have 5 drivers!");
            return;
        }

        selectedDrivers.add(selected);
        remainingBudget -= selected.cost;
        budgetLabel.setText("Budget: $" + remainingBudget + "M");

        updateSelectedArea();

        if (selectedDrivers.size() == 5) {
            pickButton.setEnabled(false);
            captainButton.setEnabled(true);
        }
    }

    private void updateSelectedArea() {
        StringBuilder sb = new StringBuilder("Selected Drivers:\n");
        for (Driver d : selectedDrivers) {
            sb.append("- ").append(d.name).append("  $" + d.cost + "M\n");
        }
        selectedArea.setText(sb.toString());
    }

    private void chooseCaptain() {
    Driver captain = (Driver) JOptionPane.showInputDialog(
            frame,
            "Choose your captain (Costs 25% extra):",
            "Captain Selection",
            JOptionPane.PLAIN_MESSAGE,
            null,
            selectedDrivers.toArray(),
            selectedDrivers.get(0)
    );

    if (captain == null) return;

    // Calculate captain cost (25% rounded up)
    int captainCost = (int) Math.ceil(captain.cost * 0.25);

    // Check if budget allows captain selection
    if (captainCost > remainingBudget) {
        JOptionPane.showMessageDialog(frame,
                "Not enough remaining budget to make " + captain.name +
                " captain!\nCaptain Cost: $" + captainCost + "M\nRemaining: $" + remainingBudget + "M");
        return;
    }

    remainingBudget -= captainCost;
    budgetLabel.setText("Budget: $" + remainingBudget + "M");

    int totalPoints = 0;
    for (Driver d : selectedDrivers) {
        if (d.equals(captain)) {
            totalPoints += d.points * 2;
        } else {
            totalPoints += d.points;
        }
    }

    StringBuilder result = new StringBuilder();
    result.append("🏎️ FINAL TEAM 🏎️\n\n");

    for (Driver d : selectedDrivers) {
        result.append(d.name);
        if (d.equals(captain)) {
            result.append(" (Captain +$").append(captainCost).append("M)");
        }
        result.append("\n");
    }

    result.append("\nCaptain Cost: $").append(captainCost).append("M");
    result.append("\nProjected Points: ").append(totalPoints);
    result.append("\nBudget Remaining: $").append(remainingBudget).append("M");

    JOptionPane.showMessageDialog(frame, result.toString());
  }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(F1FantasyDraft2026::new);
    }
}

