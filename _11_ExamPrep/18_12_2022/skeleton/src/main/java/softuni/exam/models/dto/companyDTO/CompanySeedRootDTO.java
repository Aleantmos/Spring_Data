package softuni.exam.models.dto.companyDTO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "companies")
@XmlAccessorType(XmlAccessType.FIELD)
public class CompanySeedRootDTO {
    @XmlElement(name = "company")
    private List<CompanySeedDTO> companies;

    public List<CompanySeedDTO> getCompanies() {
        return companies;
    }

    public void setCompanies(List<CompanySeedDTO> companies) {
        this.companies = companies;
    }
}
