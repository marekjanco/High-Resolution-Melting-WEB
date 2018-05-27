package cz.muni.fi.hrm.entity;


import cz.muni.fi.hrm.entity.partials.MarginValue;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "ERROR_MARGIN")
public class ErrorMargin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MARGIN_ID", insertable = false, updatable = false, nullable = false, unique = true)
    private Long id;

    @LazyCollection(value = LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "margin", cascade = CascadeType.ALL)
    private List<MarginValue> values = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private RefCurve curve;

    public ErrorMargin(){
        this(null,null);
    }

    public ErrorMargin(Long id, List<MarginValue> values){
        this.id = id;
        this.values = values;
    }

    public Long getId() {
        return id;
    }

    public List<Double> getValues() {
        if(this.values == null) return null;
        List<Double> ret = this.values.stream().map(value -> value.getValue()).collect(Collectors.toList());
        return ret;
    }

    public void setValues(List<Double> values) {
        List<MarginValue> ret =
            values.stream().map(value -> new MarginValue(value,this)).collect(Collectors.toList());
        this.values = ret;
    }

    public void setCurve(RefCurve curve) {
        this.curve = curve;
    }

    @Override
    public String toString() {
        return "ErrorMargin{" +
                "id=" + id +
                ", values=" + values +
                ", curve=" + curve +
                '}';
    }
}
