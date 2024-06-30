package views;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {

    public MainView() {
        // Set up the frame
        setTitle("ShipShape Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create a panel for the buttons
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10));
        panel.setBackground(new Color(60, 63, 65));

        // Create buttons for each functionality with custom styles
        JButton manageOrdersButton = createStyledButton("Manage Customer Orders");
        JButton manageSuppliersButton = createStyledButton("Manage Suppliers");
        JButton manageInventoryButton = createStyledButton("Manage Inventory");
        JButton manageEmployeesButton = createStyledButton("Manage Employees");
        JButton sendNotificationsButton = createStyledButton("Send Notifications");

        // Add action listeners to the buttons
        manageOrdersButton.addActionListener(e -> openFrame(new CustomerOrderView(), "Customer Orders"));
        manageSuppliersButton.addActionListener(e -> openFrame(new SupplierView(), "Suppliers"));
        manageInventoryButton.addActionListener(e -> openFrame(new InventoryView(), "Inventory"));
        manageEmployeesButton.addActionListener(e -> openFrame(new EmployeeView(), "Employees"));
        sendNotificationsButton.addActionListener(e -> new NotificationView().setVisible(true));

        // Add buttons to the panel
        panel.add(manageOrdersButton);
        panel.add(manageSuppliersButton);
        panel.add(manageInventoryButton);
        panel.add(manageEmployeesButton);
        panel.add(sendNotificationsButton);

        // Add a title label
        JLabel titleLabel = new JLabel("Welcome to ShipShape Management", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // Set up the main container
        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        container.setBackground(new Color(43, 43, 43));
        container.add(titleLabel, BorderLayout.NORTH);
        container.add(panel, BorderLayout.CENTER);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 18));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(75, 110, 175));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void openFrame(JPanel panel, String title) {
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.add(panel);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainView().setVisible(true));
    }
}
