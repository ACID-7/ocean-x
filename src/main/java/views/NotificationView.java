package views;

import controllers.NotificationController;
import models.Notification;

import javax.swing.*;
import java.awt.*;

public class NotificationView extends JFrame {
    private NotificationController notificationController;
    private JTextField recipientField;
    private JTextArea messageArea;
    private JComboBox<String> notificationTypeComboBox;

    public NotificationView() {
        notificationController = new NotificationController();
        setTitle("Send Notification");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(new Color(60, 63, 65));

        JLabel recipientLabel = new JLabel("Recipient:");
        recipientLabel.setForeground(Color.WHITE);
        panel.add(recipientLabel);

        recipientField = new JTextField();
        panel.add(recipientField);

        JLabel messageLabel = new JLabel("Message:");
        messageLabel.setForeground(Color.WHITE);
        panel.add(messageLabel);

        messageArea = new JTextArea(5, 20);
        panel.add(new JScrollPane(messageArea));

        JLabel notificationTypeLabel = new JLabel("Notification Type:");
        notificationTypeLabel.setForeground(Color.WHITE);
        panel.add(notificationTypeLabel);

        String[] notificationTypes = {"Email", "SMS"};
        notificationTypeComboBox = new JComboBox<>(notificationTypes);
        panel.add(notificationTypeComboBox);

        JButton sendButton = createStyledButton("Send Notification");
        sendButton.addActionListener(e -> {
            String recipient = recipientField.getText();
            String message = messageArea.getText();
            String notificationType = (String) notificationTypeComboBox.getSelectedItem();
            Notification notification = new Notification(0, recipient, message, notificationType);
            notificationController.sendCustomerNotification(notification);
            JOptionPane.showMessageDialog(null, "Notification sent successfully!");
        });

        panel.add(sendButton);
        add(panel);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(75, 110, 175));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
}
