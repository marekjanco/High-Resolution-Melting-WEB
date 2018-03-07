package cz.muni.fi.hrm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "NUMBER_ARRAY")
public class NumberArray {
    @Id
    @SequenceGenerator(name = "SEQ_GLOBAL_HIBERNATE", sequenceName = "SEQ_GLOBAL_HIBERNATE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GLOBAL_HIBERNATE")
    @Column(name = "ID", insertable = false, updatable = false, nullable = false, unique = true)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "NUMBERS")
    private String numbers;

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

    public String getNumbers() {
        return numbers;
    }

    public void setNumbers(String numbers) {
        this.numbers = numbers;
    }
}
