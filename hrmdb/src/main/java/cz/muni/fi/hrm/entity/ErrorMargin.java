package cz.muni.fi.hrm.entity;

import cz.muni.fi.hrm.dbconfig.ArrayConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "ERROR_MARGIN")
public class ErrorMargin {

    @Id
    @SequenceGenerator(name = "SEQ_GLOBAL_HIBERNATE", sequenceName = "SEQ_GLOBAL_HIBERNATE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GLOBAL_HIBERNATE")
    @Column(name = "ID", insertable = false, updatable = false, nullable = false, unique = true)
    private Long id;

    @Column(name = "VALUES", nullable = false)
    @Convert(converter = ArrayConverter.class)
    private List<Double> values;
}
