package cz.muni.fi.hrm.repository;

import cz.muni.fi.hrm.entity.RefCurve;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefCurveRepository extends JpaRepository<RefCurve, Long> {
    RefCurve findByName(String name);

    @Query("SELECT c FROM RefCurve c WHERE c.name <> 'temperature'")
    List<RefCurve> findAll();

    @Query("SELECT c FROM RefCurve c WHERE c.name = 'temperature'")
    RefCurve findTemperature();
}
