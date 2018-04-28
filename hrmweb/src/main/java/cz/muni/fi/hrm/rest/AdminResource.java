package cz.muni.fi.hrm.rest;

import cz.muni.fi.hrm.dto.RefCurveDTO;
import cz.muni.fi.hrm.service.RefCurveService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("/admin/refCurve")
public class AdminResource {

    static final String GET_ALL = "getAll";
    static final String CREATE = "create";
    static final String UPDATE = "update";
    static final String DELETE = "delete";

    @Inject
    private RefCurveService refCurveService;

    @RequestMapping(value = "/" + GET_ALL, method = RequestMethod.GET)
    public List<RefCurveDTO> getAll() {
        return refCurveService.getAll();
    }

    @RequestMapping(value = "/" + CREATE, method = RequestMethod.PUT)
    public void create(@RequestBody RefCurveDTO dto) {
        refCurveService.create(dto);
    }

    @RequestMapping(value = "/" + UPDATE, method = RequestMethod.PUT)
    public void update(@RequestBody RefCurveDTO dto) {
        refCurveService.update(dto);
    }

    @RequestMapping(value = "/" + DELETE, method = RequestMethod.PUT)
    public void delete(@RequestBody RefCurveDTO dto) {
        refCurveService.delete(dto);
    }

}
