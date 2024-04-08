package softuni.exam.models.dto.jobDTO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "jobs")
@XmlAccessorType(XmlAccessType.FIELD)
public class JobSeedRootDTO {

    @XmlElement(name = "job")
    private List<JobSeedDTO> jobs;

    public List<JobSeedDTO> getJobs() {
        return jobs;
    }

    public void setJobs(List<JobSeedDTO> jobs) {
        this.jobs = jobs;
    }
}
