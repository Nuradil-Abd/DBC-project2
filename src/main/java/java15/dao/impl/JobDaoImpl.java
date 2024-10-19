package java15.dao.impl;

import java15.dao.JobDao;
import java15.models.Job;
import org.w3c.dom.ls.LSOutput;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JobDaoImpl implements JobDao {

    private final Connection con;

    public JobDaoImpl(Connection con) {
        this.con = con;
    }


    @Override
    public void createJobTable() {
        String query = """
                create table if not exists jobs (
                id serial primary key,
                position varchar check( position  in  ('Mentor','Management','Instructor')),
                description varchar check( description in  ('Backend Developer','Frontend Developer')),
                profession varchar check( profession  in  ('Java','JavaScript')),
                experience int)
                """;

        try(Statement st = con.createStatement()) {
            st.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addJob(Job job) {
        String sql = "insert into jobs (position, description, profession, experience) values (?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, job.getPosition());
            ps.setString(2, job.getDescription());
            ps.setString(3, job.getProfession());
            ps.setInt(4, job.getExperience());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Job getJobById(Long jobId) {
        String sql = "select * from jobs where id = ?";

        try(PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, jobId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return new Job(
                        rs.getLong("id"),
                        rs.getString("position"),
                        rs.getString("description"),
                        rs.getString("profession"),
                        rs.getInt("experience")
                );
            }else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Job> sortByExperience(String ascOrDesc) {
        String sql = "select * from jobs order by experience "
                     +(ascOrDesc.equalsIgnoreCase("asc") ? "asc" : "desc");
        List<Job> jobs = new ArrayList<>();

        try(Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql)){

            while(rs.next()){
                Job job = new Job(
                        rs.getLong("id"),
                        rs.getString("position"),
                        rs.getString("description"),
                        rs.getString("profession"),
                        rs.getInt("experience")
                );
                jobs.add(job);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return jobs;
    }

    @Override
    public Job getJobByEmployeeId(Long employeeId) {

        return null;
    }

    @Override
    public void deleteDescriptionColumn() {
       if(doesColumnExist("description")) {
           String sql = "alter table jobs drop column if exists description";
           try (Statement st = con.createStatement()) {
               st.executeUpdate(sql);
           } catch (SQLException e) {
               throw new RuntimeException(e);
           }
       }else {
           System.out.println("No description column found");
       }

    }

    public boolean doesColumnExist(String columnName) {
        String sql = "select column_name from information_schema.columns where table_name = 'jobs' and column_name = ?";
        try(PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, columnName);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
