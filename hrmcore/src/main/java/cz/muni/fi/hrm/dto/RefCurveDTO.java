package cz.muni.fi.hrm.dto;

import java.util.ArrayList;
import java.util.List;

public class RefCurveDTO {
    public String name;
    public String acronym;
    public String note;
    public Integer numberOfSamples;
    public List<Double> values;
    public ErrorMarginDTO errorMargin;

    public RefCurveDTO(String name, String acronym, String note, Integer numberOfSamples
            , List<Double> values) {
        this.name = name;
        this.acronym = acronym;
        this.note = note;
        this.numberOfSamples = numberOfSamples;
        this.values = values;
        this.errorMargin = null;
    }

    public RefCurveDTO(String name, String acronym, String note, List<Double> values) {
        this(name, acronym, note, null, values);
    }

    public RefCurveDTO() {
        this(null, null, null, null, new ArrayList<>());
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

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public ErrorMarginDTO getErrorMargin() {
        return errorMargin;
    }

    public void setErrorMargin(ErrorMarginDTO errorMargin) {
        this.errorMargin = errorMargin;
    }

    public Integer getNumberOfSamples() {
        return numberOfSamples;
    }

    public void setNumberOfSamples(Integer numberOfSamples) {
        this.numberOfSamples = numberOfSamples;
    }
}
