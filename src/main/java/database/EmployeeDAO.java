package database;

import models.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    // Add Employee
    public void addEmployee(Employee employee) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO employees (name, role, availability, email) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, employee.getName());
            statement.setString(2, employee.getRole());
            statement.setString(3, employee.getAvailability());
            statement.setString(4, employee.getEmail());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update Employee
    public void updateEmployee(Employee employee) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "UPDATE employees SET name = ?, role = ?, availability = ?, email = ? WHERE employeeId = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, employee.getName());
            statement.setString(2, employee.getRole());
            statement.setString(3, employee.getAvailability());
            statement.setString(4, employee.getEmail());
            statement.setInt(5, employee.getEmployeeId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Remove Employee
    public void removeEmployee(int employeeId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM employees WHERE employeeId = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, employeeId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get all Employees
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM employees";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int employeeId = resultSet.getInt("employeeId");
                String name = resultSet.getString("name");
                String role = resultSet.getString("role");
                String availability = resultSet.getString("availability");
                String email = resultSet.getString("email");
                Employee employee = new Employee(employeeId, name, role, availability, email);
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }
}
