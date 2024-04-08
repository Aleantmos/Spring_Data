package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.JobsProjection;
import softuni.exam.models.dto.jobDTO.JobSeedDTO;
import softuni.exam.models.dto.jobDTO.JobSeedRootDTO;
import softuni.exam.models.entity.Job;
import softuni.exam.repository.JobRepository;
import softuni.exam.service.CompanyService;
import softuni.exam.service.JobService;
import softuni.exam.util.MyValidation;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class JobServiceImpl implements JobService {
    public static final String JOB_FILE_PATH = "src/main/resources/files/xml/jobs.xml";
    private final JobRepository jobRepository;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final MyValidation myValidation;
    private final CompanyService companyService;

    public JobServiceImpl(JobRepository jobRepository, XmlParser xmlParser, ModelMapper modelMapper, MyValidation myValidation, CompanyService companyService) {
        this.jobRepository = jobRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.myValidation = myValidation;
        this.companyService = companyService;
    }

    @Override
    public boolean areImported() {
        return jobRepository.count() > 0;
    }

    @Override
    public String readJobsFileContent() throws IOException {
        return Files.readString(Path.of(JOB_FILE_PATH));
    }

    @Override
    public String importJobs() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        xmlParser.fromFile(JOB_FILE_PATH, JobSeedRootDTO.class)
                .getJobs()
                .forEach(jobSeedDTO -> {
                    boolean filtered = validateJob(jobSeedDTO);

                    if (filtered) {
                        Job job = modelMapper.map(jobSeedDTO, Job.class);

                        job.setCompanies(companyService.getCompanyById(jobSeedDTO.getCompanyId()));

                        jobRepository.save(job);

                        sb.append(String.format("Successfully imported job %s",
                                        job.getTitle()))
                                .append(System.lineSeparator());
                    } else {
                        sb.append("Invalid job")
                                .append(System.lineSeparator());
                    }

                });

        return sb.toString().trim();
    }

    private boolean validateJob(JobSeedDTO jobSeedDTO) {
        return myValidation.isValid(jobSeedDTO);
    }

    @Override
    public String getBestJobs() {
        List<JobsProjection> theBestJobs =
                jobRepository.getTheBestJobs();

        StringBuilder sb = new StringBuilder();
        for (JobsProjection theBestJob : theBestJobs) {
            sb.append(String.format(
                            """
                            Job title %s
                            -Salary: %s$¬¬
                            --Hours a week: %sh.
                            """,
                    theBestJob.getTitle(), theBestJob.getSalary(), theBestJob.getJobHoursWeek()
            ));
        }

        return sb.toString().trim();
    }
}
