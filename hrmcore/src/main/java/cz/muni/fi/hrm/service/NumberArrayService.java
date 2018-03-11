package cz.muni.fi.hrm.service;

import cz.muni.fi.hrm.dto.NumberArrayDTO;
import cz.muni.fi.hrm.entity.NumberArray;

import java.util.List;

public interface NumberArrayService {

    public List<String> getAllNames();
    public List<NumberArrayDTO> getAll();
    public List<Double> findValuesByName(String name);
    public Object[] compute(List<Double> data);
    public void create(NumberArrayDTO dto);
    public void delete(NumberArrayDTO dto);

}
