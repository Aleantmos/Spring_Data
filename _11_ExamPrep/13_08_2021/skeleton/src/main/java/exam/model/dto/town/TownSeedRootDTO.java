package exam.model.dto.town;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "towns")
@XmlAccessorType(XmlAccessType.FIELD)
public class TownSeedRootDTO {
    @XmlElement(name = "town")
    private List<TownSeedDTO> towns;

    public List<TownSeedDTO> getTowns() {
        return towns;
    }

    public void setTowns(List<TownSeedDTO> towns) {
        this.towns = towns;
    }
}
