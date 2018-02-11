package cz.muni.fi.hrm.rest;

import cz.muni.fi.hrm.entity.NumberArray;
import cz.muni.fi.hrm.repository.NumberArrayRepository;
import cz.muni.fi.hrm.service.NumberArrayService;
import javafx.util.Pair;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/numberArray")
public class NumberArrayResource {

    static final String GET_ALL_NAMES = "getAllNames";
    static final String FIND_BY_NAME = "findByName";
    static final String COMPUTE = "compute";

    @Inject
    private NumberArrayService numberArrayService;

    @RequestMapping(value = "/" + GET_ALL_NAMES, method = RequestMethod.GET)
    public List<String> getAll() {
        List<String> names = numberArrayService.getAllNames();
        return names;
    }

    @RequestMapping(value = "/" + FIND_BY_NAME, method = RequestMethod.GET)
    public List<String> findByName(@RequestParam(value = "name") String name) {
        List<String> tokens = numberArrayService.findValuesByName(name);
        return tokens;
    }

    @RequestMapping(value = "/" + COMPUTE, method = RequestMethod.GET)
    public Object[] compute(@RequestParam(value = "data") List<Double> data) {
        return numberArrayService.compute(data);
    }

}
