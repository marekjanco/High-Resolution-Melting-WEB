package cz.muni.fi.hrm.entity.partials;

import cz.muni.fi.hrm.entity.ErrorMargin;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "error_margin_value")
public class MarginValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", insertable = false, updatable = false, nullable = false, unique = true)
    private Long id;

    @Column(name = "value", nullable = false)
    private Double value;

    @ManyToOne
    @JoinColumn(name="MARGIN_ID", nullable=false)
 //   @Column(name = "error_margin", nullable = false)
    private ErrorMargin margin;

    public MarginValue() {
    }

    public MarginValue(Double value, ErrorMargin margin) {
        this.value = value;
        this.margin = margin;
    }

    public Double getValue() {
        return value;
    }

    public ErrorMargin getErrorMargin() {
        return margin;
    }
}