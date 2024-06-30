package views;

import controllers.InventoryController;
import models.Inventory;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.List;

public class InventoryView extends JPanel {
    private InventoryController inventoryController;
    private JTable inventoryTable;
    private InventoryTableModel inventoryTableModel;

    public InventoryView() {
        inventoryController = new InventoryController();
        setLayout(new BorderLayout());
        setBackground(new Color(50, 50, 50));

        // Setup UI components and layout
        JButton addButton = createStyledButton("Add Inventory Item");
        JButton updateButton = createStyledButton("Update Inventory Item");
        JButton removeButton = createStyledButton("Remove Inventory Item");

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(60, 63, 65));
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(removeButton);

        add(buttonPanel, BorderLayout.SOUTH);

        inventoryTableModel = new InventoryTableModel(inventoryController.getAllInventory());
        inventoryTable = new JTable(inventoryTableModel);
        inventoryTable.setFillsViewportHeight(true);
        inventoryTable.setRowHeight(30);
        inventoryTable.setFont(new Font("Arial", Font.PLAIN, 14));
        inventoryTable.setSelectionBackground(new Color(75, 110, 175));
        inventoryTable.setSelectionForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(inventoryTable);
        scrollPane.setBackground(new Color(43, 43, 43));
        add(scrollPane, BorderLayout.CENTER);

        addButton.addActionListener(e -> {
            Inventory inventory = showInventoryInputDialog();
            if (inventory != null) {
                inventoryController.addInventory(inventory);
                refreshInventoryTable();
            }
        });

        updateButton.addActionListener(e -> {
            int selectedRow = inventoryTable.getSelectedRow();
            if (selectedRow >= 0) {
                Inventory inventory = inventoryTableModel.getInventoryAt(selectedRow);

                String quantityStr = JOptionPane.showInputDialog(
                        null,
                        "Enter new quantity:",
                        "Update Quantity",
                        JOptionPane.PLAIN_MESSAGE
                );

                if (quantityStr != null) {
                    try {
                        int newQuantity = Integer.parseInt(quantityStr);
                        inventory.setQuantity(newQuantity);
                        inventoryController.updateInventory(inventory);
                        refreshInventoryTable();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number.");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please select an inventory item to update.");
            }
        });

        removeButton.addActionListener(e -> {
            int selectedRow = inventoryTable.getSelectedRow();
            if (selectedRow >= 0) {
                Inventory inventory = inventoryTableModel.getInventoryAt(selectedRow);
                inventoryController.removeInventory(inventory.getPartId());
                refreshInventoryTable();
            } else {
                JOptionPane.showMessageDialog(null, "Please select an inventory item to remove.");
            }
        });
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(75, 110, 175));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void refreshInventoryTable() {
        List<Inventory> inventoryList = inventoryController.getAllInventory();
        inventoryTableModel.setInventory(inventoryList);
        inventoryTableModel.fireTableDataChanged();
    }

    private Inventory showInventoryInputDialog() {
        JTextField partNameField = new JTextField(20);
        JTextField quantityField = new JTextField(20);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Part Name:"));
        panel.add(partNameField);
        panel.add(new JLabel("Quantity:"));
        panel.add(quantityField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Add New Inventory Item", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String partName = partNameField.getText();
            int quantity;
            try {
                quantity = Integer.parseInt(quantityField.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid quantity. Please enter a number.");
                return null;
            }
            return new Inventory(0, partName, quantity);
        } else {
            return null;
        }
    }

    // Inner class for the table model
    private static class InventoryTableModel extends AbstractTableModel {
        private List<Inventory> inventoryList;
        private final String[] columnNames = {"Part ID", "Part Name", "Quantity"};

        public InventoryTableModel(List<Inventory> inventoryList) {
            this.inventoryList = inventoryList;
        }

        public void setInventory(List<Inventory> inventoryList) {
            this.inventoryList = inventoryList;
        }

        @Override
        public int getRowCount() {
            return inventoryList.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Inventory inventory = inventoryList.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return inventory.getPartId();
                case 1:
                    return inventory.getPartName();
                case 2:
                    return inventory.getQuantity();
                default:
                    return null;
            }
        }

        public Inventory getInventoryAt(int rowIndex) {
            return inventoryList.get(rowIndex);
        }
    }
}
