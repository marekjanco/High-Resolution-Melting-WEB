package cz.muni.fi.hrm.rest;

import cz.muni.fi.hrm.dto.ResultDTO;
import cz.muni.fi.hrm.service.ComputationService;
import cz.muni.fi.hrm.service.RefCurveService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("/computation")
public class ComputationResource {
    static final String COMPARE = "compareData";

    @Inject
    private ComputationService computationService;

    @RequestMapping(value = "/" + COMPARE, method = RequestMethod.GET)
    public ResultDTO compute(@RequestParam(value = "data") List<Double> data) {
        return computationService.compareDataWithRefCurves(data);
    }

}
