package java15.services;

import java15.models.Job;

import java.util.List;

public interface JobService {
    String createJobTable();
    String addJob(Job job);
    Job getJobById(Long jobId);
    List<Job> sortByExperience(String ascOrDesc);
    Job getJobByEmployeeId(Long employeeId);
    String deleteDescriptionColumn();
}
