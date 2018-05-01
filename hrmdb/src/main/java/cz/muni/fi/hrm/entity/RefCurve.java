package cz.muni.fi.hrm.entity;


import com.sun.istack.internal.NotNull;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "REFERENCE_CURVE")
public class RefCurve {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", insertable = false, updatable = false, nullable = false, unique = true)
    private Long id;

    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    @Column(name = "ACRONYM")
    private String acronym;

    @Column(name = "NOTE")
    private String note;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Double> values;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private ErrorMargin errorMargin;

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

    public ErrorMargin getErrorMargin() {
        return errorMargin;
    }
    public void setErrorMargin(ErrorMargin errorMargin) {
        this.errorMargin = errorMargin;
    }

}
