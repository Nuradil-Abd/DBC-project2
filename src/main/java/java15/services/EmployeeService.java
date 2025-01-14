package java15.services;

import java15.models.Employee;
import java15.models.Job;

import java.util.List;
import java.util.Map;

public interface EmployeeService {
    String createEmployee();
    String addEmployee(Employee employee);
    String dropTable();
    String cleanTable();
    String updateEmployee(Long id,Employee employee);
    List<Employee> getAllEmployees();
    Employee findByEmail(String email);
    Map<Employee, Job> getEmployeeById(Long employeeId);
    List<Employee> getEmployeeByPosition(String position);
}
