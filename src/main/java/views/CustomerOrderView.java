package views;

import controllers.CustomerOrderController;
import models.CustomerOrder;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.List;

public class CustomerOrderView extends JPanel {
    private CustomerOrderController customerOrderController;
    private JTable ordersTable;
    private OrderTableModel orderTableModel;

    public CustomerOrderView() {
        customerOrderController = new CustomerOrderController();
        setLayout(new BorderLayout());
        setBackground(new Color(43, 43, 43));

        // Setup UI components and layout
        JButton addButton = createStyledButton("Add Order");
        JButton updateButton = createStyledButton("Update Order");
        JButton removeButton = createStyledButton("Remove Order");

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(60, 63, 65));
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(removeButton);

        add(buttonPanel, BorderLayout.SOUTH);

        orderTableModel = new OrderTableModel(customerOrderController.getAllOrders());
        ordersTable = new JTable(orderTableModel);
        ordersTable.setFillsViewportHeight(true);
        ordersTable.setRowHeight(30);
        ordersTable.setFont(new Font("Arial", Font.PLAIN, 14));
        ordersTable.setSelectionBackground(new Color(75, 110, 175));
        ordersTable.setSelectionForeground(Color.WHITE);
        ordersTable.getColumnModel().getColumn(4).setPreferredWidth(200);
        ordersTable.getColumnModel().getColumn(0).setPreferredWidth(50);

        JScrollPane scrollPane = new JScrollPane(ordersTable);
        scrollPane.setBackground(new Color(43, 43, 43));
        add(scrollPane, BorderLayout.CENTER);

        addButton.addActionListener(e -> {
            CustomerOrder order = showOrderInputDialog();
            if (order != null) {
                customerOrderController.addOrder(order);
                refreshOrderTable();
            }
        });

        updateButton.addActionListener(e -> {
            int selectedRow = ordersTable.getSelectedRow();
            if (selectedRow >= 0) {
                CustomerOrder order = orderTableModel.getOrderAt(selectedRow);
                CustomerOrder updatedOrder = showOrderUpdateDialog(order);
                if (updatedOrder != null) {
                    customerOrderController.updateOrder(updatedOrder);
                    refreshOrderTable();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please select an order to update.");
            }
        });

        removeButton.addActionListener(e -> {
            int selectedRow = ordersTable.getSelectedRow();
            if (selectedRow >= 0) {
                CustomerOrder order = orderTableModel.getOrderAt(selectedRow);
                customerOrderController.removeOrder(order.getOrderId());
                refreshOrderTable();
            } else {
                JOptionPane.showMessageDialog(null, "Please select an order to remove.");
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

    private void refreshOrderTable() {
        List<CustomerOrder> orders = customerOrderController.getAllOrders();
        orderTableModel.setOrders(orders);
        orderTableModel.fireTableDataChanged();
    }

    private CustomerOrder showOrderInputDialog() {
        JTextField customerNameField = new JTextField(20);
        JComboBox<String> orderTypeBox = new JComboBox<>(new String[]{"Repairs", "Repainting", "Spare parts"});
        JTextField emailField = new JTextField(20);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Customer Name:"));
        panel.add(customerNameField);
        panel.add(new JLabel("Order Type:"));
        panel.add(orderTypeBox);
        panel.add(new JLabel("Customer Email:"));
        panel.add(emailField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Add New Order", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String customerName = customerNameField.getText();
            String orderType = (String) orderTypeBox.getSelectedItem();
            String email = emailField.getText();
            return new CustomerOrder(0, customerName, orderType, "Pending", email);
        } else {
            return null;
        }
    }

    private CustomerOrder showOrderUpdateDialog(CustomerOrder order) {
        JTextField customerNameField = new JTextField(order.getCustomerName(), 20);
        JComboBox<String> orderTypeBox = new JComboBox<>(new String[]{"Repairs", "Repainting", "Spare parts"});
        orderTypeBox.setSelectedItem(order.getOrderType());
        JComboBox<String> statusBox = new JComboBox<>(new String[]{"Pending", "Ready", "Shipped"});
        statusBox.setSelectedItem(order.getStatus());
        JTextField emailField = new JTextField(order.getCustomerEmail(), 20);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Customer Name:"));
        panel.add(customerNameField);
        panel.add(new JLabel("Order Type:"));
        panel.add(orderTypeBox);
        panel.add(new JLabel("Status:"));
        panel.add(statusBox);
        panel.add(new JLabel("Customer Email:"));
        panel.add(emailField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Update Order", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String customerName = customerNameField.getText();
            String orderType = (String) orderTypeBox.getSelectedItem();
            String status = (String) statusBox.getSelectedItem();
            String email = emailField.getText();
            return new CustomerOrder(order.getOrderId(), customerName, orderType, status, email);
        } else {
            return null;
        }
    }

    // Inner class for the table model
    private static class OrderTableModel extends AbstractTableModel {
        private List<CustomerOrder> orders;
        private final String[] columnNames = {"Order ID", "Customer Name", "Order Type", "Status", "Customer Email"};

        public OrderTableModel(List<CustomerOrder> orders) {
            this.orders = orders;
        }

        public void setOrders(List<CustomerOrder> orders) {
            this.orders = orders;
        }

        @Override
        public int getRowCount() {
            return orders.size();
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
            CustomerOrder order = orders.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return order.getOrderId();
                case 1:
                    return order.getCustomerName();
                case 2:
                    return order.getOrderType();
                case 3:
                    return order.getStatus();
                case 4:
                    return order.getCustomerEmail();
                default:
                    return null;
            }
        }

        public CustomerOrder getOrderAt(int rowIndex) {
            return orders.get(rowIndex);
        }
    }
}
