package services;

import database.NotificationDAO;
import models.Notification;

public class NotificationService {
    private NotificationDAO notificationDAO;

    public NotificationService() {
        notificationDAO = new NotificationDAO();
    }

    public void sendCustomerNotification(Notification notification) {
        notificationDAO.sendNotification(notification);
    }

    public void sendEmployeeNotification(Notification notification) {
        notificationDAO.sendNotification(notification);
    }
}
