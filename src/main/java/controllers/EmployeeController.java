package controllers;

import models.Employee;
import services.EmployeeService;
import services.Notification;

import java.util.List;

public class EmployeeController {
    private EmployeeService employeeService;
    private Notification notificationService;

    public EmployeeController() {
        employeeService = new EmployeeService();
        try {
            notificationService = new Notification();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addEmployee(Employee employee) {
        employeeService.createEmployee(employee);
        try {
            notificationService.sendNotification(employee.getEmail(),
                    "Employee Added",
                    "Dear " + employee.getName() + ",\n\nYou have been successfully added as an employee.\n\nRole: " + employee.getRole());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateEmployee(Employee employee) {
        employeeService.updateEmployee(employee);
        try {
            notificationService.sendNotification(employee.getEmail(),
                    "Employee Updated",
                    "Dear " + employee.getName() + ",\n\nYour employee details have been updated.\n\nRole: " + employee.getRole() + "\nAvailability: " + employee.getAvailability());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeEmployee(int employeeId) {
        employeeService.removeEmployee(employeeId);
    }

    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }
}
