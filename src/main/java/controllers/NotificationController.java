package controllers;

import models.Notification;
import services.NotificationService;

public class NotificationController {
    private NotificationService notificationService;

    public NotificationController() {
        notificationService = new NotificationService();
    }

    public void sendCustomerNotification(Notification notification) {
        notificationService.sendCustomerNotification(notification);
    }

    public void sendEmployeeNotification(Notification notification) {
        notificationService.sendEmployeeNotification(notification);
    }
}
