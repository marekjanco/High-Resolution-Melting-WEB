package cz.muni.fi.hrm.repository;

import cz.muni.fi.hrm.entity.NumberArray;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NumberArrayRepository extends JpaRepository<NumberArray, Long> {
    List<NumberArray> findByName(String name);
}
