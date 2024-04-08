package softuni.exam.models.dto.companyDTO;

import softuni.exam.config.LocalDateAdapter;

import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@XmlAccessorType(XmlAccessType.FIELD)
public class CompanySeedDTO {

    @XmlElement(name = "companyName")
    @Size(min = 2, max = 40)
    private String companyName;
    @XmlElement(name = "companyName")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate dateEstablished;
    @XmlElement(name = "website")
    @Size(min = 2, max = 30)
    private String website;
    @XmlElement(name = "countryId")
    private Long countryId;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public LocalDate getDateEstablished() {
        return dateEstablished;
    }

    public void setDateEstablished(LocalDate dateEstablished) {
        this.dateEstablished = dateEstablished;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }
}
