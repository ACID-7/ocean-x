package database;

import models.Inventory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class InventoryDAO {

    // Add Inventory Item
    public void addInventory(Inventory inventory) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO inventory (partName, quantity) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, inventory.getPartName());
            statement.setInt(2, inventory.getQuantity());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update Inventory Item
    public void updateInventory(Inventory inventory) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "UPDATE inventory SET partName = ?, quantity = ? WHERE partId = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, inventory.getPartName());
            statement.setInt(2, inventory.getQuantity());
            statement.setInt(3, inventory.getPartId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Remove Inventory Item
    public void removeInventory(int partId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM inventory WHERE partId = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, partId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get all Inventory Items
    public List<Inventory> getAllInventory() {
        List<Inventory> inventoryList = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM inventory";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int partId = resultSet.getInt("partId");
                String partName = resultSet.getString("partName");
                int quantity = resultSet.getInt("quantity");
                Inventory inventory = new Inventory(partId, partName, quantity);
                inventoryList.add(inventory);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inventoryList;
    }
}
