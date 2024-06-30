package database;

import models.Part;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PartDAO {

    // Get all parts
    public List<Part> getAllParts() {
        List<Part> parts = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM parts";
            PreparedStatement statement = connection.prepareStatement(sql);
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
