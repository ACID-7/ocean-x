package controllers;

import models.CustomerOrder;
import services.CustomerOrderService;
import services.Notification;
import java.util.List;

public class CustomerOrderController {
    private final CustomerOrderService customerOrderService;

    public CustomerOrderController() {
        customerOrderService = new CustomerOrderService();
    }

    public void addOrder(CustomerOrder order) {
        customerOrderService.createOrder(order);
        sendNotification(order, "Order Created", "Your order has been created and is currently pending.");
    }

    public void updateOrder(CustomerOrder order) {
        customerOrderService.updateOrder(order);
        if ("Ready for Collection".equals(order.getStatus())) {
            sendNotification(order, "Order Ready for Collection", "Your order is ready for collection.");
        }
    }

    public void removeOrder(int orderId) {
        customerOrderService.removeOrder(orderId);
    }

    public List<CustomerOrder> getAllOrders() {
        return customerOrderService.getAllOrders();
    }

    private void sendNotification(CustomerOrder order, String subject, String body) {
        try {
            Notification notification = new Notification();
            notification.sendNotification(order.getCustomerEmail(), subject, "Dear " + order.getCustomerName() + ", " + body);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
