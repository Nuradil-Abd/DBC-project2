package java15;

import java15.config.DatabaseConnection;
import java15.models.Employee;
import java15.models.Job;
import java15.services.EmployeeService;
import java15.services.JobService;
import java15.services.impl.EmployeeServiceImpl;
import java15.services.impl.JobServiceImpl;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
       // DatabaseConnection.getConnection();
        JobService jobService = new JobServiceImpl();
        EmployeeService employeeService = new EmployeeServiceImpl();
//        System.out.println(jobService.createJobTable());

//        //add job
//        String result = jobService.addJob(new Job(
//                "Mentor", "JavaScript", "Frontend Developer", 3
//        ));
//        System.out.println(result);

        //get job by id
//        System.out.println(jobService.getJobById(12L));

        //sort by exp
//        System.out.println(jobService.sortByExperience("desc"));

        //delete description column
//        System.out.println(jobService.deleteDescriptionColumn());

        //create table  employee
//        System.out.println(employeeService.createEmployee());

        //add employee
        System.out.println(employeeService.addEmployee(new Employee(
                "Atai","Abdyrazakov",
                19,"atai@gmail.com", 2L
        )));

        // drop table Employees
//        System.out.println(employeeService.dropTable());

        // clean table employees
//        System.out.println(employeeService.cleanTable());

        //get employees
        System.out.println("All Employees "+ employeeService.getAllEmployees());

        //find employee by email
        System.out.println("Employee with email atai@gmail.com: ");
        System.out.println(employeeService.findByEmail("atai@gmail.com"));

        //get employee by id
        System.out.println("Employee with 5 id :");
        System.out.println(employeeService.getEmployeeById(5L));

        //get employee by position
        System.out.println("Employees with position Mentor ");
        System.out.println(employeeService.getEmployeeByPosition("Mentor"));
    }
}
