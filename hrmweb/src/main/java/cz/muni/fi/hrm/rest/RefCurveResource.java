package cz.muni.fi.hrm.rest;

import cz.muni.fi.hrm.dto.RefCurveDTO;
import cz.muni.fi.hrm.service.RefCurveService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("/refCurve")
public class RefCurveResource {

    static final String GET_ALL_NAMES = "getAllNames";
    static final String FIND_BY_NAME = "findByName";

    @Inject
    private RefCurveService refCurveService;

    @RequestMapping(value = "/" + GET_ALL_NAMES, method = RequestMethod.GET)
    public List<RefCurveDTO> getAll() {
        List<RefCurveDTO> names = refCurveService.getNamesAndAcronyms();
        return names;
    }

    @RequestMapping(value = "/" + FIND_BY_NAME, method = RequestMethod.GET)
    public List<Double> findByName(@RequestParam(value = "name") String name) {
        List<Double> tokens = refCurveService.findValuesByName(name);
        return tokens;
    }
}
