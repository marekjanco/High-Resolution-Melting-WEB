package cz.muni.fi.hrm.service;

import cz.muni.fi.hrm.dto.ResultDTO;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ComputationServiceImpl implements ComputationService {
    @Override
    public ResultDTO compareDataWithRefCurves(List<Double> data) {
        return new ResultDTO(97, 97, 100);
    }
}
