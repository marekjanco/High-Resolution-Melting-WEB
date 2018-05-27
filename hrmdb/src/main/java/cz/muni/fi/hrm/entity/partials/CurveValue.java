package cz.muni.fi.hrm.entity.partials;

import cz.muni.fi.hrm.entity.RefCurve;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * entities used because hibernate had problems creating table for List of Double, so this entity represents
 * Double value
 */
@Entity
@Table(name = "ref_curve_value")
public class CurveValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", insertable = false, updatable = false, nullable = false, unique = true)
    private Long id;

    @Column(name = "value", nullable = false)
    private Double value;

    @ManyToOne
    @JoinColumn(name="CURVE_ID", nullable=false)
    private RefCurve curve;

    public CurveValue() {
    }

    public CurveValue(Double value, RefCurve curve) {
        this.value = value;
        this.curve = curve;
    }

    public Double getValue() {
        return value;
    }

    public RefCurve getRefCurve() {
        return curve;
    }

    @Override
    public String toString() {
        return  ""+value;
    }
}
