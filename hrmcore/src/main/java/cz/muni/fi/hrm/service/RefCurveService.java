package cz.muni.fi.hrm.service;

import cz.muni.fi.hrm.dto.RefCurveDTO;
import cz.muni.fi.hrm.entity.RefCurve;

import java.util.List;

/**
 * this service provides mainly CRUD operations on Ref Curve entity
 */
public interface RefCurveService {

    /**
     *
     * @return returns names and acronyms of all reference curves that are in db
     */
    List<RefCurveDTO> getNamesAndAcronyms();
    List<RefCurveDTO> getAll();
    RefCurveDTO getTemperature();
    RefCurveDTO findByName(String name);

    void createOrUpdate(List<RefCurveDTO> dtos);
    void create(RefCurve curve);
    void delete(RefCurveDTO dto);

}
