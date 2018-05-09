package cz.muni.fi.hrm.dto;

import java.util.ArrayList;
import java.util.List;

public class ErrorMarginDTO {
    public String name;
    public List<Double> values;

    public ErrorMarginDTO(){
        this.name = null;
        this.values = new ArrayList<>();
    }

    public List<Double> getValues() {
        return values;
    }
    public void setValues(List<Double> values) {
        this.values = values;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
