package database;

import models.Notification;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class NotificationDAO {
    public void sendNotification(Notification notification) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO notifications (recipient, message, notificationType) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, notification.getRecipient());
            statement.setString(2, notification.getMessage());
            statement.setString(3, notification.getNotificationType());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
