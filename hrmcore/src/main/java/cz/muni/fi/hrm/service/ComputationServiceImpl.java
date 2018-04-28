package cz.muni.fi.hrm.service;

import cz.muni.fi.hrm.dto.RefCurveDTO;
import cz.muni.fi.hrm.dto.ResultDTO;
import cz.muni.fi.hrm.entity.RefCurve;
import cz.muni.fi.hrm.repository.RefCurveRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;


@Service
public class ComputationServiceImpl implements ComputationService {
    @Inject
    private RefCurveRepository refCurveRepository;

    @Override
    public ResultDTO compareDataWithRefCurves(List<RefCurveDTO> data) {
        if(data == null){
            //FIXME vynimka
        }
        RefCurveDTO averageCurve = this.createAverageCurve(data);
        List<RefCurve> refData = refCurveRepository.findAll();
        return new ResultDTO(97, 97, 100);
    }

    private RefCurveDTO createAverageCurve(List<RefCurveDTO> data){
        RefCurveDTO ret = new RefCurveDTO();
        ret.name = "average_curve";
        ret.acronym = "avg_c";
        for(int i = 0; i < data.get(0).values.size(); ++i){
            Double sum = 0.0;
            for(int j = 0; j < data.size(); ++j){
                sum += data.get(j).values.get(i);
            }
            ret.values.add(sum/data.size());
        }
        return ret;
    }
}
