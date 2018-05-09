package cz.muni.fi.hrm.rest;

import cz.muni.fi.hrm.dto.RefCurveDTO;
import cz.muni.fi.hrm.dto.ResultDTO;
import cz.muni.fi.hrm.service.ComputationService;
import cz.muni.fi.hrm.service.RefCurveService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("/computation")
public class ComputationResource {
    static final String COMPARE = "compareData";
    static final String AVERAGE_CURVE = "getAverageCurve";

    @Inject
    private ComputationService computationService;

    @RequestMapping(value = "/" + COMPARE, method = RequestMethod.POST)
    public @ResponseBody
    ResultDTO compute(@RequestBody List<RefCurveDTO> data) {
        return computationService.compareDataWithRefCurves(data);
    }

    @RequestMapping(value = "/" + AVERAGE_CURVE, method = RequestMethod.POST)
    public @ResponseBody
    RefCurveDTO getAverageCurve(@RequestBody List<RefCurveDTO> data) {
        return computationService.createAverageCurve(data);
    }

}
