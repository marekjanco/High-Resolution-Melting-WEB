package cz.muni.fi.hrm.entity;

import com.sun.istack.internal.NotNull;
import cz.muni.fi.hrm.dbconfig.ArrayConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "REFERENCE_CURVE")
public class RefCurve {
    @Id
    @SequenceGenerator(name = "SEQ_GLOBAL_HIBERNATE", sequenceName = "SEQ_GLOBAL_HIBERNATE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GLOBAL_HIBERNATE")
    @Column(name = "ID", insertable = false, updatable = false, nullable = false, unique = true)
    private Long id;

    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    @Column(name = "ACRONYM")
    private String acronym;

    @Column(name = "NOTE")
    private String note;

    @Column(name = "VALUES", nullable = false)
    @Convert(converter = ArrayConverter.class)
    private List<Double> values;

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
        return values;
    }
    public void setValues(List<Double> values) {
        this.values = values;
    }
}
