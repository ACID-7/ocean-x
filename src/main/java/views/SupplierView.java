package views;

import controllers.SupplierController;
import models.Part;
import models.Supplier;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SupplierView extends JPanel {
    private SupplierController supplierController;
    private JTable suppliersTable;
    private SupplierTableModel supplierTableModel;

    public SupplierView() {
        supplierController = new SupplierController();
        setLayout(new BorderLayout());
        setBackground(new Color(50, 50, 50));

        // Setup UI components and layout
        JButton addButton = createStyledButton("Add Supplier");
        JButton updateButton = createStyledButton("Update Supplier");
        JButton removeButton = createStyledButton("Remove Supplier");

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(60, 63, 65));
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(removeButton);

        add(buttonPanel, BorderLayout.SOUTH);

        supplierTableModel = new SupplierTableModel(supplierController.getAllSuppliers());
        suppliersTable = new JTable(supplierTableModel);
        suppliersTable.setFillsViewportHeight(true);
        suppliersTable.setRowHeight(30);
        suppliersTable.setFont(new Font("Arial", Font.PLAIN, 14));
        suppliersTable.setSelectionBackground(new Color(75, 110, 175));
        suppliersTable.setSelectionForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(suppliersTable);
        scrollPane.setBackground(new Color(43, 43, 43));
        add(scrollPane, BorderLayout.CENTER);

        addButton.addActionListener(e -> {
            Supplier supplier = showSupplierInputDialog();
            if (supplier != null) {
                supplierController.addSupplier(supplier);
                refreshSupplierTable();
            }
        });

        updateButton.addActionListener(e -> {
            int selectedRow = suppliersTable.getSelectedRow();
            if (selectedRow >= 0) {
                Supplier supplier = supplierTableModel.getSupplierAt(selectedRow);
                supplier = showSupplierInputDialog(supplier);
                if (supplier != null) {
                    supplierController.updateSupplier(supplier);
                    refreshSupplierTable();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please select a supplier to update.");
            }
        });

        removeButton.addActionListener(e -> {
            int selectedRow = suppliersTable.getSelectedRow();
            if (selectedRow >= 0) {
                Supplier supplier = supplierTableModel.getSupplierAt(selectedRow);
                supplierController.removeSupplier(supplier.getSupplierId());
                refreshSupplierTable();
            } else {
                JOptionPane.showMessageDialog(null, "Please select a supplier to remove.");
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

    private void refreshSupplierTable() {
        List<Supplier> suppliers = supplierController.getAllSuppliers();
        supplierTableModel.setSuppliers(suppliers);
        supplierTableModel.fireTableDataChanged();
    }

    private Supplier showSupplierInputDialog() {
        return showSupplierInputDialog(null);
    }

    private Supplier showSupplierInputDialog(Supplier supplier) {
        JTextField nameField = new JTextField(20);
        JTextField contactInfoField = new JTextField(20);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Contact Info:"));
        panel.add(contactInfoField);

        panel.add(new JLabel("Supplied Parts:"));
        List<JCheckBox> partCheckBoxes = new ArrayList<>();
        List<Part> availableParts = supplierController.getAllParts();

        for (Part part : availableParts) {
            JCheckBox checkBox = new JCheckBox(part.getName());
            if (supplier != null && supplier.getSuppliedParts().contains(part)) {
                checkBox.setSelected(true);
            }
            partCheckBoxes.add(checkBox);
            panel.add(checkBox);
        }

        if (supplier != null) {
            nameField.setText(supplier.getName());
            contactInfoField.setText(supplier.getContactInfo());
        }

        int result = JOptionPane.showConfirmDialog(null, panel, supplier == null ? "Add New Supplier" : "Update Supplier", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            String contactInfo = contactInfoField.getText();
            List<Part> suppliedParts = new ArrayList<>();
            for (int i = 0; i < availableParts.size(); i++) {
                if (partCheckBoxes.get(i).isSelected()) {
                    suppliedParts.add(availableParts.get(i));
                }
            }
            return new Supplier(supplier != null ? supplier.getSupplierId() : 0, name, contactInfo, suppliedParts);
        } else {
            return null;
        }
    }

    // Inner class for the table model
    public class SupplierTableModel extends AbstractTableModel {
        private List<Supplier> suppliers;
        private final String[] columnNames = {"Supplier ID", "Name", "Contact Info", "Supplied Parts"};

        public SupplierTableModel(List<Supplier> suppliers) {
            this.suppliers = suppliers;
        }

        public void setSuppliers(List<Supplier> suppliers) {
            this.suppliers = suppliers;
        }

        @Override
        public int getRowCount() {
            return suppliers.size();
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
            Supplier supplier = suppliers.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return supplier.getSupplierId();
                case 1:
                    return supplier.getName();
                case 2:
                    return supplier.getContactInfo();
                case 3:
                    return supplier.getSuppliedParts().stream()
                            .map(Part::getName)
                            .collect(Collectors.joining(", "));
                default:
                    return null;
            }
        }

        public Supplier getSupplierAt(int rowIndex) {
            return suppliers.get(rowIndex);
        }
    }
}
