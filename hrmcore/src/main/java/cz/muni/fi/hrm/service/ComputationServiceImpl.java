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

        ResultDTO result = new ResultDTO(0, 0, 100, null);
        int counter = 0;
        for(RefCurve refCurve : refData){
            ResultDTO tempResult = compareCurves(refCurve, averageCurve);
            if(tempResult.matchInPerc > result.getMatchInPerc()){
                result = tempResult;
            }
        }

        if(result.refCurveName == null){
            //FIXME vynimka
        }
        return result;
    }

    private ResultDTO compareCurves(RefCurve refCurve, RefCurveDTO averageCurve) {
        int match = 0;
        for(int i = 0; i < refCurve.getValues().size(); ++i){
            Double value = averageCurve.getValues().get(i);
            Double margin = refCurve.getErrorMargin().getValues().get(i);
            if(refCurve.getValues().get(i) - margin <= value && value <= refCurve.getValues().get(i) + margin ){
                ++match;
            }
        }
        return new ResultDTO(match/refCurve.getValues().size()*100, match, refCurve.getValues().size(), refCurve.getName());
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
