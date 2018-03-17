package cz.muni.fi.hrm.rest;

import cz.muni.fi.hrm.dto.NumberArrayDTO;
import cz.muni.fi.hrm.service.NumberArrayService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminResource {

    static final String GET_ALL = "getAll";
    static final String CREATE = "create";
    static final String UPDATE = "update";
    static final String DELETE = "delete";

    @Inject
    private NumberArrayService numberArrayService;

    @RequestMapping(value = "/" + GET_ALL, method = RequestMethod.GET)
    public List<NumberArrayDTO> getAll() {
        return numberArrayService.getAll();
    }

    @RequestMapping(value = "/" + CREATE, method = RequestMethod.PUT)
    public void create(@RequestBody NumberArrayDTO dto) {
        numberArrayService.create(dto);
    }

    @RequestMapping(value = "/" + UPDATE, method = RequestMethod.PUT)
    public void update(@RequestBody NumberArrayDTO dto) {
        numberArrayService.update(dto);
    }

    @RequestMapping(value = "/" + DELETE, method = RequestMethod.PUT)
    public void delete(@RequestBody NumberArrayDTO dto) {
        numberArrayService.delete(dto);
    }

}
