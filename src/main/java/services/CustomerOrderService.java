package services;

import database.CustomerOrderDAO;
import models.CustomerOrder;

import java.util.List;

public class CustomerOrderService {
    private final CustomerOrderDAO customerOrderDAO;
    private final Notification notificationService;

    public CustomerOrderService() {
        customerOrderDAO = new CustomerOrderDAO();
        try {
            notificationService = new Notification();
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize Notification service", e);
        }
    }

    public void createOrder(CustomerOrder order) {
        customerOrderDAO.addCustomerOrder(order);
    }

    public void updateOrder(CustomerOrder order) {
        customerOrderDAO.updateCustomerOrder(order);
        // Send notification if status is updated
        try {
            String subject = "Order Status Updated";
            String message = "Dear " + order.getCustomerName() + ",\n\nYour order status has been updated to " + order.getStatus() + ".";
            notificationService.sendNotification(order.getCustomerEmail(),subject, message );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeOrder(int orderId) {
        customerOrderDAO.removeCustomerOrder(orderId);
    }

    public List<CustomerOrder> getAllOrders() {
        return customerOrderDAO.getAllCustomerOrders();
    }
}
