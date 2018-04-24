package cz.muni.fi.hrm.service;

import cz.muni.fi.hrm.dto.ResultDTO;

import java.util.List;

public interface ComputationService {
    public ResultDTO compareDataWithRefCurves(List<Double> data);
}
