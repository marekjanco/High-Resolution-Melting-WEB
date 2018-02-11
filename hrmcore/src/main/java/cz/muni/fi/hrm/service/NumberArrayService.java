package cz.muni.fi.hrm.service;

import java.util.List;

public interface NumberArrayService {

    public List<String> getAllNames();
    public List<String> findValuesByName(String name);
    public Object[] compute(List<Double> data);

}
