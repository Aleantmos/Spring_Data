package com.example.football.util.adapters;

import com.example.football.models.enums.PositionENUM;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class PositionEnumTypeAdapter extends XmlAdapter<String, PositionENUM> {
    @Override
    public PositionENUM unmarshal(String s) throws Exception {
        return PositionENUM.valueOf(s);
    }

    @Override
    public String marshal(PositionENUM positionENUM) throws Exception {
        return positionENUM.name();
    }
}
