package cz.muni.fi.hrm.service;

import cz.muni.fi.hrm.dto.RefCurveDTO;
import cz.muni.fi.hrm.dto.ResultDTO;
import cz.muni.fi.hrm.entity.RefCurve;
import cz.muni.fi.hrm.repository.RefCurveRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;


@Service
public class ComputationServiceImpl implements ComputationService {
    @Inject
    private RefCurveRepository refCurveRepository;

    private static Logger logger = LoggerFactory.getLogger(ComputationServiceImpl.class);

    @Override
    public ResultDTO compareDataWithRefCurves(List<RefCurveDTO> data) {
        logger.debug("start comparing data with ref curves", data);
        if(data == null){
            throw new IllegalArgumentException("cannot compute when no data are loaded");
        }
        RefCurveDTO averageCurve = this.createAverageCurve(data);
        List<RefCurve> refData = refCurveRepository.findAll();

        ResultDTO result = new ResultDTO(0.0, 0, 100, null, null,null,null);
        for(RefCurve refCurve : refData){
            ResultDTO tempResult = null;
            tempResult = compareCurves(refCurve, averageCurve);
            if(tempResult.matchInPerc > result.getMatchInPerc()){
                result = tempResult;
            }
        }

        if(result.refCurveName == null){
            throw new IllegalArgumentException("some error occured while computing ...");
        }

        logger.debug("end of comparing data with ref curves", data);
        return result;
    }

    private ResultDTO compareCurves(RefCurve refCurve, RefCurveDTO averageCurve) {
        int match = 0;
        int all = 0;
        RefCurveDTO matched = new RefCurveDTO();
        RefCurveDTO notMatched = new RefCurveDTO();
        for(int i = 0; i < refCurve.getValues().size(); ++i){
            if(i >= averageCurve.getValues().size() || averageCurve.getValues().get(i) == null){
                matched.getValues().add(null);
                notMatched.getValues().add(null);
                continue;
            }
            Double value = averageCurve.getValues().get(i);
            Double margin = refCurve.getErrorMargin().getValues().get(i);
            if(refCurve.getValues().get(i) - margin <= value && value <= refCurve.getValues().get(i) + margin ){
                ++match;
                matched.getValues().add(averageCurve.getValues().get(i));
                notMatched.getValues().add(null);
            }else{
                notMatched.getValues().add(averageCurve.getValues().get(i));
                matched.getValues().add(null);
            }
            all++;
        }
        return new ResultDTO(match*100.0/all, match, all,
                refCurve.getName(), averageCurve, matched, notMatched);
    }


    @Override
    public RefCurveDTO createAverageCurve(List<RefCurveDTO> data){
        logger.debug("start creating average curve", data);
        if(data == null){
            throw new IllegalArgumentException("Trying to create average curve on null");
        }
        RefCurveDTO ret = new RefCurveDTO();
        ret.name = "average_curve";
        ret.acronym = "avg_c";
        if(data.size() == 0){
            return ret;
        }
        for(int i = 0; i < data.get(0).values.size(); ++i){
            Double sum = 0.0;
            boolean wasNull = false;
            for(int j = 0; j < data.size(); ++j){
                if(data.get(j).values.get(i) == null){
                    wasNull = true;
                }
                sum += (data.get(j).values.get(i) != null) ? data.get(j).values.get(i) : 0 ;
            }
            if(wasNull){
                ret.values.add(null);
            }else{
                ret.values.add(sum/data.size());
            }
        }
        logger.debug("end of creating average curve", data);
        return ret;
    }
}