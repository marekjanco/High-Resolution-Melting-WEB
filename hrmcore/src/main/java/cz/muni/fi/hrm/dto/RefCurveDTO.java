package cz.muni.fi.hrm.dto;

import java.util.ArrayList;
import java.util.List;

public class RefCurveDTO {
    public String name;
    public String acronym;
    public String note;
    public List<Double> values;

    public RefCurveDTO(String name, String acronym, String note, List<Double> values){
        this.name = name;
        this.acronym = acronym;
        this.note = note;
        this.values = values;
    }

    public RefCurveDTO(){
        this(null, null, null, new ArrayList<>());
    }
}
