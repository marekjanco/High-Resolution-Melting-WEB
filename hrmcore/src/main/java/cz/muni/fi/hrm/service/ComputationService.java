package cz.muni.fi.hrm.service;

import cz.muni.fi.hrm.dto.RefCurveDTO;
import cz.muni.fi.hrm.dto.ResultDTO;

import java.util.List;

/**
 * this service takes care of computing of result with reference data
 */
public interface ComputationService {
    /**
     * Method takes user data, create average curve that compares with other curves saved in db
     * @param data user data that should be used for comparing
     * @return result of comparing with reference curves
     */
    ResultDTO compareDataWithRefCurves(List<RefCurveDTO> data);

    /**
     * create compute average of user data to one curve
     * @param data user data
     * @return computed average curve
     */
    RefCurveDTO createAverageCurve(List<RefCurveDTO> data);
}
