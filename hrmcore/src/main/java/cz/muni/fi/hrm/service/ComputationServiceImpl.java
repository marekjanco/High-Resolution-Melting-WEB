package cz.muni.fi.hrm.service;

import cz.muni.fi.hrm.dto.RefCurveDTO;
import cz.muni.fi.hrm.dto.ResultDTO;
import cz.muni.fi.hrm.entity.RefCurve;
import cz.muni.fi.hrm.repository.RefCurveRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;


@Service
public class ComputationServiceImpl implements ComputationService {
    @Inject
    private RefCurveRepository refCurveRepository;

    @Override
    public ResultDTO compareDataWithRefCurves(List<RefCurveDTO> data) {
        if(data == null){
            throw new IllegalArgumentException("cannot compute when no data are loaded");
        }
        RefCurve dbTemperature = refCurveRepository.findTemperature();
        /*RefCurveDTO temperature = this.findTemperatureIfPresent(data);
        if(temperature != null){
            data.remove(temperature);
            dbTemperature = refCurveRepository.findTemperature();
        }
        */
        RefCurveDTO averageCurve = this.createAverageCurve(data);
        List<RefCurve> refData = refCurveRepository.findAll();

        ResultDTO result = new ResultDTO(0.0, 0, 100, null, null);
        int counter = 0;
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
        return result;
    }

    private ResultDTO compareCurvesOnInterval(RefCurve refCurve, RefCurveDTO averageCurve,
                                              RefCurve dbTemperature, RefCurveDTO temperature) {
        int match = 0;
        int all = 0;
        for(int i = 0; i < temperature.getValues().size(); ++i){
            if(averageCurve.getValues().get(i) == null){
                continue;
            }
            Double value = averageCurve.getValues().get(i);
            Double margin = refCurve.getErrorMargin().getValues().get(i);
            if(refCurve.getValues().get(i) - margin <= value && value <= refCurve.getValues().get(i) + margin ){
                ++match;
            }
            all++;
        }
        return new ResultDTO(match*100.0/all, match, all,
                refCurve.getName(), averageCurve);
    }

    private RefCurveDTO findTemperatureIfPresent(List<RefCurveDTO> data) {
        for(int i = 0; i < data.size(); ++i){
            if("temperature".equals(data.get(i).getName().toLowerCase())){
                return data.get(i);
            }
        }
        return null;
    }

    private ResultDTO compareCurves(RefCurve refCurve, RefCurveDTO averageCurve) {
        int match = 0;
        int all = 0;
        for(int i = 0; i < refCurve.getValues().size(); ++i){
            if(i >= averageCurve.getValues().size() || averageCurve.getValues().get(i) == null){
                continue;
            }
            Double value = averageCurve.getValues().get(i);
            Double margin = refCurve.getErrorMargin().getValues().get(i);
            if(refCurve.getValues().get(i) - margin <= value && value <= refCurve.getValues().get(i) + margin ){
                ++match;
            }
            all++;
        }
        return new ResultDTO(match*100.0/all, match, all,
                refCurve.getName(), averageCurve);
    }


    @Override
    public RefCurveDTO createAverageCurve(List<RefCurveDTO> data){
        RefCurveDTO ret = new RefCurveDTO();
        ret.name = "average_curve";
        ret.acronym = "avg_c";
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
        return ret;
    }
}