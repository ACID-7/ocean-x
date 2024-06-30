package database;

import models.Part;
import models.Supplier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAO {

    // Add Supplier
    public void addSupplier(Supplier supplier) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO suppliers (name, contactInfo) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, supplier.getName());
            statement.setString(2, supplier.getContactInfo());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int supplierId = generatedKeys.getInt(1);
                addSuppliedParts(supplierId, supplier.getSuppliedParts());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update Supplier
    public void updateSupplier(Supplier supplier) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "UPDATE suppliers SET name = ?, contactInfo = ? WHERE supplierId = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, supplier.getName());
            statement.setString(2, supplier.getContactInfo());
            statement.setInt(3, supplier.getSupplierId());
            statement.executeUpdate();

            removeSuppliedParts(supplier.getSupplierId());
            addSuppliedParts(supplier.getSupplierId(), supplier.getSuppliedParts());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Remove Supplier
    public void removeSupplier(int supplierId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM suppliers WHERE supplierId = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, supplierId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get all Suppliers
    public List<Supplier> getAllSuppliers() {
        List<Supplier> suppliers = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM suppliers";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int supplierId = resultSet.getInt("supplierId");
                String name = resultSet.getString("name");
                String contactInfo = resultSet.getString("contactInfo");
                List<Part> suppliedParts = getSuppliedParts(supplierId);
                Supplier supplier = new Supplier(supplierId, name, contactInfo, suppliedParts);
                suppliers.add(supplier);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return suppliers;
    }

    // Add supplied parts for a supplier
    private void addSuppliedParts(int supplierId, List<Part> parts) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO supplied_parts (supplierId, partId) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            for (Part part : parts) {
                statement.setInt(1, supplierId);
                statement.setInt(2, part.getPartId());
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Remove supplied parts for a supplier
    private void removeSuppliedParts(int supplierId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM supplied_parts WHERE supplierId = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, supplierId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get supplied parts for a supplier
    private List<Part> getSuppliedParts(int supplierId) {
        List<Part> parts = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT p.partId, p.name, p.description FROM parts p INNER JOIN supplied_parts sp ON p.partId = sp.partId WHERE sp.supplierId = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, supplierId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int partId = resultSet.getInt("partId");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                Part part = new Part(partId, name, description);
                parts.add(part);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return parts;
    }
}
