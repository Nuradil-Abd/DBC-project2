package java15.dao.impl;

import java15.dao.EmployeeDao;
import java15.dao.JobDao;
import java15.models.Employee;
import java15.models.Job;


import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EmployeeDaoImpl implements EmployeeDao {

    private final Connection con;
    private final JobDao jobDao;



    public EmployeeDaoImpl(Connection con) {
        this.con = con;
        this.jobDao = new JobDaoImpl(con);
    }


    @Override
    public void createEmployee() {
        String sql = """
                create table if not exists employees (
                id serial primary key,
                firstName varchar not null,
                lastName varchar not null,
                 age int,
                 email varchar unique not null,
                 job_id int references jobs(id))""";

        try(Statement st = con.createStatement()) {
            st.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void addEmployee(Employee employee) {
        String sql = "insert into employees (firstName, lastName, age, email,job_id) values (?, ?, ?, ?, ?)";

        try(PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, employee.getFirstName());
            ps.setString(2, employee.getLastName());
            ps.setInt(3, employee.getAge());
            ps.setString(4, employee.getEmail());
            ps.setLong(5, employee.getJobId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void dropTable() {
        String sql = "drop table if exists employees";

        try(Statement st = con.createStatement()){
            st.executeUpdate(sql);
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void cleanTable() {

        String sql = "truncate table  employees";

        try(Statement st = con.createStatement()) {
            st.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void updateEmployee(Long id, Employee employee) {
        String sql = "update employees set firstName = ?, lastName = ?, age = ?, email = ? ,job_id = ? where id = ?";


        try(PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, employee.getFirstName());
            ps.setString(2, employee.getLastName());
            ps.setInt(3, employee.getAge());
            ps.setString(4, employee.getEmail());
            ps.setLong(5, employee.getJobId());
            ps.setLong(6, id);
            int row = ps.executeUpdate();
            if (row == 0) {
                throw new RuntimeException("employee with id " + id + " was not updated");

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String sql = "select * from employees";
        try(Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql)){
            while (rs.next()) {
                employees.add(mapToRow(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return employees;
    }

    @Override
    public Employee findByEmail(String email) {
        String sql = "select * from employees where email = ?";

        try(PreparedStatement ps = con.prepareStatement(sql)) {
           ps.setString(1, email);
           ResultSet rs = ps.executeQuery();

           if (rs.next()) {
               return mapToRow(rs);
           }else {
               return null;
           }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Map<Employee, Job> getEmployeeById(Long employeeId) {
        String sql = "select * from employees where id = ?";
        Map<Employee, Job> employeeJobMap = new HashMap<>();

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, employeeId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Employee employee = mapToRow(rs);
                Job job = jobDao.getJobById(employee.getJobId()); // Получение работы
                employeeJobMap.put(employee, job); // Добавление в Map
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return employeeJobMap;
    }





    @Override
    public List<Employee> getEmployeeByPosition(String position) {
        List<Employee> employees = new ArrayList<>();
        String sql = "select e.* from employees e join jobs j on e.job_id = j.id where j.position = ?";

        try(PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, position);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                employees.add(mapToRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return employees;
    }
    private Employee mapToRow(ResultSet rs) throws SQLException {
        Long id = rs.getLong("id");
        String firstName = rs.getString("firstName");
        String lastName = rs.getString("lastName");
        int age = rs.getInt("age");
        String email = rs.getString("email");
        Long jobId = rs.getLong("job_id");

        return new Employee(id, firstName, lastName, age, email, jobId);
    }

}
