package cz.muni.fi.hrm.entity;

import javax.validation.constraints.NotNull;
import cz.muni.fi.hrm.entity.partials.CurveValue;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "REFERENCE_CURVE")
public class RefCurve {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CURVE_ID", insertable = false, updatable = false, nullable = false, unique = true)
    private Long id;

    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    @Column(name = "ACRONYM", nullable = false)
    private String acronym;

    @Column(name = "NOTE")
    private String note;

    @Column(name = "NUMBER_OF_SAMPLES")
    private Integer numberOfSamples;

    @NotNull
    @OneToMany(mappedBy = "curve", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<CurveValue> values;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "curve", cascade = CascadeType.ALL)
    private ErrorMargin errorMargin;

    public RefCurve(){
        this(null, null, null, null, null, new ErrorMargin());
    }

    public RefCurve(Long id, String name, String acronym, String note, List<CurveValue> values, ErrorMargin margin){
        this.id = id;
        this.name = name;
        this.acronym = acronym;
        this.note = note;
        this.values = values;
        this.errorMargin = margin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<Double> getValues() {
        if(this.values == null) return null;
        List<Double> ret = this.values.stream().map(value -> value.getValue()).collect(Collectors.toList());
        return ret;
    }

    public void setValues(List<Double> values) {
        List<CurveValue> ret =
                values.stream().map(value -> new CurveValue(value, this)).collect(Collectors.toList());
        this.values = ret;
    }

    public ErrorMargin getErrorMargin() {
        return errorMargin;
    }

    public void setErrorMargin(ErrorMargin errorMargin) {
        this.errorMargin = errorMargin;
    }

    public Integer getNumberOfSamples() {
        return numberOfSamples;
    }

    public void setNumberOfSamples(Integer numberOfSamples) {
        this.numberOfSamples = numberOfSamples;
    }

    @Override
    public String toString() {
        return "RefCurve{" +
                "name='" + name + '\'' +
                ", acronym='" + acronym + '\'' +
                ", note='" + note + '\'' +
                ", numberOfSamples=" + numberOfSamples +
                ", values=" + values +
                ", errorMargin=" + errorMargin +
                '}';
    }
}