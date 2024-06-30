package models;

public class Employee {
    private int employeeId;
    private String name;
    private String role;
    private String availability;
    private String email;

    // Constructor
    public Employee(int employeeId, String name, String role, String availability, String email) {
        this.employeeId = employeeId;
        this.name = name;
        this.role = role;
        this.availability = availability;
        this.email = email;
    }

    // Getters and Setters
    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
