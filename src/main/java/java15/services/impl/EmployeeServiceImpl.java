package java15.services.impl;

import java15.config.DatabaseConnection;
import java15.dao.EmployeeDao;
import java15.dao.impl.EmployeeDaoImpl;
import java15.models.Employee;
import java15.models.Job;
import java15.services.EmployeeService;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class EmployeeServiceImpl  implements EmployeeService {
    private EmployeeDao emplDao;
    public EmployeeServiceImpl() {
        this.emplDao = new EmployeeDaoImpl(DatabaseConnection.getConnection());
    }

    @Override
    public String createEmployee() {
       try{
        emplDao.createEmployee();
        return "Table created successfully";
       } catch (Exception e) {
           return "Error creating employee";
       }



    }

    @Override
    public String addEmployee(Employee employee) {
        try {
            emplDao.addEmployee(employee);
            return "Employee added successfully";
        } catch (Exception e) {
            return "failed add employee: " + e.getMessage();
        }

    }

    @Override
    public String dropTable() {
        try {
            emplDao.dropTable();
            return "table dropped successfully";
        }catch (Exception e) {
            return "error dropping table";
        }
    }

    @Override
    public String cleanTable() {
        try {
            emplDao.cleanTable();
            return "table cleaned successfully";
        } catch (Exception e) {
            return "error cleaning table";
        }
    }

    @Override
    public String updateEmployee(Long id, Employee employee) {
        try{
            emplDao.updateEmployee(id, employee);
            return "Employee updated successfully";
        }catch (Exception e) {
            return "error updating employee";
        }
    }


    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> employees = emplDao.getAllEmployees();
        if (employees.isEmpty()) {
            System.out.println("List is empty");
        }
        return employees;
    }

    @Override
    public Employee findByEmail(String email) {
        Employee employee = emplDao.findByEmail(email);
        if (employee == null) {
            System.out.println("Employee with email " + email + " not found");
        }
        return employee;
    }

    @Override
    public Map<Employee, Job> getEmployeeById(Long employeeId) {
        Map<Employee, Job> employeeJobMap = emplDao.getEmployeeById(employeeId);
        if (employeeJobMap.isEmpty()) {
            System.out.println("Employee with id " + employeeId + " not found");
        }
        return employeeJobMap;
    }

    @Override
    public List<Employee> getEmployeeByPosition(String position) {
        List<Employee> employees = emplDao.getEmployeeByPosition(position);
        if (employees.isEmpty()) {
            System.out.println("Employee with position " + position + " not found");
        }
        return employees;
    }

}
