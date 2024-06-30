package services;

import database.EmployeeDAO;
import models.Employee;
import java.util.List;

public class EmployeeService {
    private EmployeeDAO employeeDAO;

    public EmployeeService() {
        employeeDAO = new EmployeeDAO();
    }

    public void createEmployee(Employee employee) {
        employeeDAO.addEmployee(employee);
    }

    public void updateEmployee(Employee employee) {
        employeeDAO.updateEmployee(employee);
    }

    public void removeEmployee(int employeeId) {
        employeeDAO.removeEmployee(employeeId);
    }

    public List<Employee> getAllEmployees() {
        return employeeDAO.getAllEmployees();
    }
}
