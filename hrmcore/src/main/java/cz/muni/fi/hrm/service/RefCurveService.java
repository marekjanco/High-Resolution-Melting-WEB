package cz.muni.fi.hrm.service;

import cz.muni.fi.hrm.dto.RefCurveDTO;

import java.util.List;

public interface RefCurveService {

    public List<RefCurveDTO> getNamesAndAcronyms();
    public List<RefCurveDTO> getAll();
    public RefCurveDTO getTemperature();
    public List<Double> findValuesByName(String name);

    public void create(RefCurveDTO dto);
    public void update(RefCurveDTO dto);
    public void delete(RefCurveDTO dto);

}
