package views;

import controllers.EmployeeController;
import models.Employee;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.List;

public class EmployeeView extends JPanel {
    private EmployeeController employeeController;
    private JTable employeesTable;
    private EmployeeTableModel employeeTableModel;

    public EmployeeView() {
        employeeController = new EmployeeController();
        setLayout(new BorderLayout());
        setBackground(new Color(43, 43, 43));

        // Setup UI components and layout
        JButton addButton = createStyledButton("Add Employee");
        JButton updateButton = createStyledButton("Update Employee");
        JButton removeButton = createStyledButton("Remove Employee");

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(60, 63, 65));
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(removeButton);

        add(buttonPanel, BorderLayout.SOUTH);

        employeeTableModel = new EmployeeTableModel(employeeController.getAllEmployees());
        employeesTable = new JTable(employeeTableModel);
        employeesTable.setFillsViewportHeight(true);
        employeesTable.setRowHeight(30);
        employeesTable.setFont(new Font("Arial", Font.PLAIN, 14));
        employeesTable.setSelectionBackground(new Color(75, 110, 175));
        employeesTable.setSelectionForeground(Color.WHITE);
        employeesTable.getColumnModel().getColumn(4).setPreferredWidth(200);


        JScrollPane scrollPane = new JScrollPane(employeesTable);
        scrollPane.setBackground(new Color(43, 43, 43));
        add(scrollPane, BorderLayout.CENTER);

        addButton.addActionListener(e -> {
            Employee employee = showEmployeeInputDialog();
            if (employee != null) {
                employeeController.addEmployee(employee);
                refreshEmployeeTable();
            }
        });

        updateButton.addActionListener(e -> {
            int selectedRow = employeesTable.getSelectedRow();
            if (selectedRow >= 0) {
                Employee employee = employeeTableModel.getEmployeeAt(selectedRow);

                String[] options = {"In the office", "In the field", "In the ship"};
                String newStatus = (String) JOptionPane.showInputDialog(
                        null,
                        "Select new availability status:",
                        "Update Availability",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        options,
                        options[0]);

                if (newStatus != null) {
                    employee.setAvailability(newStatus);
                    employeeController.updateEmployee(employee);
                    refreshEmployeeTable();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please select an employee to update.");
            }
        });

        removeButton.addActionListener(e -> {
            int selectedRow = employeesTable.getSelectedRow();
            if (selectedRow >= 0) {
                Employee employee = employeeTableModel.getEmployeeAt(selectedRow);
                employeeController.removeEmployee(employee.getEmployeeId());
                refreshEmployeeTable();
            } else {
                JOptionPane.showMessageDialog(null, "Please select an employee to remove.");
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

    private void refreshEmployeeTable() {
        List<Employee> employees = employeeController.getAllEmployees();
        employeeTableModel.setEmployees(employees);
        employeeTableModel.fireTableDataChanged();
    }

    private Employee showEmployeeInputDialog() {
        JTextField nameField = new JTextField(20);
        JTextField roleField = new JTextField(20);
        JTextField emailField = new JTextField(20);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Employee Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Role:"));
        panel.add(roleField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Add New Employee", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            String role = roleField.getText();
            String email = emailField.getText();
            return new Employee(0, name, role, "Available", email);
        } else {
            return null;
        }
    }

    // Inner class for the table model
    private static class EmployeeTableModel extends AbstractTableModel {
        private List<Employee> employees;
        private final String[] columnNames = {"Employee ID", "Name", "Role", "Availability", "Email"};

        public EmployeeTableModel(List<Employee> employees) {
            this.employees = employees;
        }

        public void setEmployees(List<Employee> employees) {
            this.employees = employees;
        }

        @Override
        public int getRowCount() {
            return employees.size();
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
            Employee employee = employees.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return employee.getEmployeeId();
                case 1:
                    return employee.getName();
                case 2:
                    return employee.getRole();
                case 3:
                    return employee.getAvailability();
                case 4:
                    return employee.getEmail();
                default:
                    return null;
            }
        }

        public Employee getEmployeeAt(int rowIndex) {
            return employees.get(rowIndex);
        }
    }
}
