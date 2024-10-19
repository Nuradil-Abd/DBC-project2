package java15.services.impl;

import java15.config.DatabaseConnection;
import java15.dao.JobDao;
import java15.dao.impl.JobDaoImpl;
import java15.models.Job;
import java15.services.JobService;

import java.sql.Connection;
import java.util.List;

public class JobServiceImpl implements JobService {

    private JobDao jobDao;
    public JobServiceImpl() {
        this.jobDao = new JobDaoImpl(DatabaseConnection.getConnection());
    }

    @Override
    public String createJobTable() {
         jobDao.createJobTable();
         return "table created";
    }


    @Override
    public String addJob(Job job) {
        try {
            jobDao.addJob(job);
            return "job added";
        } catch (Exception e) {
            return "job not added: " + e.getMessage();
        }
    }

    @Override
    public Job getJobById(Long jobId) {
        try{
        return jobDao.getJobById(jobId);
        } catch (Exception e) {
            System.out.println("error getting job: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Job> sortByExperience(String ascOrDesc) {
        try {
        return jobDao.sortByExperience(ascOrDesc);
        } catch (Exception e) {
            System.out.println("error when sorted " + e.getMessage());
            return List.of();
        }
    }

    @Override
    public Job getJobByEmployeeId(Long employeeId) {

        return null;
    }

    @Override
    public String deleteDescriptionColumn() {
        try {
            jobDao.deleteDescriptionColumn();
            return "column description deleted";
        } catch (Exception e) {
            return "error deleting column description: " + e.getMessage();
        }
    }
}
