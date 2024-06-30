package models;

public class CustomerOrder {
    private int orderId;
    private String customerName;
    private String orderType; // Repair, Repainting, Spare parts
    private String status;
    private String customerEmail;

    // Constructor
    public CustomerOrder(int orderId, String customerName, String orderType, String status, String customerEmail) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.orderType = orderType;
        this.status = status;
        this.customerEmail = customerEmail;
    }

    // Getters and Setters
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }
}
