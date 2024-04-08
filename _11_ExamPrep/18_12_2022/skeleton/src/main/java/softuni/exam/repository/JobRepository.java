package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.dto.JobsProjection;
import softuni.exam.models.entity.Job;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {


    @Query("select j.title as title, round(j.salary, 2) as salary, j.hoursWeek as jobHoursWeek from Job j " +
            "where j.salary >= 5000 " +
            "and " +
            "j.hoursWeek <= 30 " +
            "order by j.salary desc")
    List<JobsProjection> getTheBestJobs();
}
