package models;

public class Notification {
    private int notificationId;
    private String recipient;
    private String message;
    private String notificationType; // e.g., Email, SMS

    public Notification(int notificationId, String recipient, String message, String notificationType) {
        this.notificationId = notificationId;
        this.recipient = recipient;
        this.message = message;
        this.notificationType = notificationType;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }
}
