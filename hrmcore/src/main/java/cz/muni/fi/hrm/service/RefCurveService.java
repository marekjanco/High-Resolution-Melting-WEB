package cz.muni.fi.hrm.service;

import cz.muni.fi.hrm.dto.RefCurveDTO;
import cz.muni.fi.hrm.entity.RefCurve;

import java.util.List;

public interface RefCurveService {

    public List<RefCurveDTO> getNamesAndAcronyms();
    public List<RefCurveDTO> getAll();
    public RefCurveDTO getTemperature();
    public List<Double> findValuesByName(String name);

    public void createOrUpdate(List<RefCurveDTO> dtos);
    public void create(RefCurve curve);
    public void delete(RefCurveDTO dto);

}
