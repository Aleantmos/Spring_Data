package exam.config.adapters;

import exam.model.enums.WarrantyType;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class WarrantyTypeAdapter extends XmlAdapter<String, WarrantyType> {

    @Override
    public WarrantyType unmarshal(String s) throws Exception {
        return WarrantyType.valueOf(s);
    }

    @Override
    public String marshal(WarrantyType warrantyType) throws Exception {
        return warrantyType.name();
    }
}
