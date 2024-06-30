package database;

import models.CustomerOrder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CustomerOrderDAO {

    // Add Customer Order
    public void addCustomerOrder(CustomerOrder order) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO customer_orders (customerName, orderType, status, customerEmail) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, order.getCustomerName());
            statement.setString(2, order.getOrderType());
            statement.setString(3, order.getStatus());
            statement.setString(4, order.getCustomerEmail());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update Customer Order
    public void updateCustomerOrder(CustomerOrder order) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "UPDATE customer_orders SET customerName = ?, orderType = ?, status = ?, customerEmail = ? WHERE orderId = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, order.getCustomerName());
            statement.setString(2, order.getOrderType());
            statement.setString(3, order.getStatus());
            statement.setString(4, order.getCustomerEmail());
            statement.setInt(5, order.getOrderId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Remove Customer Order
    public void removeCustomerOrder(int orderId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM customer_orders WHERE orderId = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, orderId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get all Customer Orders
    public List<CustomerOrder> getAllCustomerOrders() {
        List<CustomerOrder> orders = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM customer_orders";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int orderId = resultSet.getInt("orderId");
                String customerName = resultSet.getString("customerName");
                String orderType = resultSet.getString("orderType");
                String status = resultSet.getString("status");
                String customerEmail = resultSet.getString("customerEmail");
                CustomerOrder order = new CustomerOrder(orderId, customerName, orderType, status, customerEmail);
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }
}
