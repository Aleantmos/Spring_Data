package softuni.exam.models.dto.astronomers;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "astronomers")
@XmlAccessorType(XmlAccessType.FIELD)
public class AstronomerSeedRootDTO {

    @XmlElement(name = "astronomer")
    private List<AstronomerSeedDTO> astronomerSeedDTO;

    public List<AstronomerSeedDTO> getAstronomerSeedDTO() {
        return astronomerSeedDTO;
    }

    public void setAstronomerSeedDTO(List<AstronomerSeedDTO> astronomerSeedDTO) {
        this.astronomerSeedDTO = astronomerSeedDTO;
    }
}
