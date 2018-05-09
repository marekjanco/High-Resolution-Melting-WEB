package cz.muni.fi.hrm.service;

import cz.muni.fi.hrm.dto.RefCurveDTO;
import cz.muni.fi.hrm.entity.RefCurve;
import cz.muni.fi.hrm.repository.RefCurveRepository;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RefCurveServiceImpl implements RefCurveService {

    @Inject
    private RefCurveRepository refCurveRepository;

    @Inject
    private DozerBeanMapper mapper;

    private static Logger logger = LoggerFactory.getLogger(RefCurveServiceImpl.class);

    @Override
    public List<RefCurveDTO> getNamesAndAcronyms() {
        logger.debug("getting names of curves from db");
        List<RefCurveDTO> refCurves = new ArrayList<>();
        List<RefCurve> names = refCurveRepository.findAll();
        for(RefCurve curve: names){
            refCurves.add(this.convertToDtoWithoutValues(curve));
        }
        return refCurves;
    }

    @Override
    public List<RefCurveDTO> getAll() {
        logger.debug("getting all curves from db");
        List<RefCurve> list = refCurveRepository.findAll();
        return list.stream()
                .map(curve -> convertToDto(curve))
                .collect(Collectors.toList());
    }

    @Override
    public RefCurveDTO getTemperature() {
        logger.debug("getting temperature from db");
        return convertToDto(refCurveRepository.findTemperature());
    }

    @Override
    public RefCurveDTO findByName(String name) {
        if(name == null){
            throw new IllegalArgumentException("reference curve with name null doesn't exist");
        }
        logger.debug("getting "+name+" from db");
        RefCurve curve = refCurveRepository.findByName(name);
        return this.convertToDto(curve);
    }

    @Override
    public void createOrUpdate(List<RefCurveDTO> dtos) {
        if(dtos == null){
            throw new IllegalArgumentException("cannot create reference curves, they were null");
        }
        if(dtos.size() == 0){
            return;
        }
        logger.debug("creating curves ",dtos);
        List<RefCurve> toCreate = new ArrayList<>();
        for(RefCurveDTO dto: dtos){
            RefCurve curve = this.convertFromDto(dto);
            toCreate.add(curve);
        }
        refCurveRepository.deleteAll();
        refCurveRepository.save(toCreate);
        refCurveRepository.flush();
    }

    @Override
    public void create(RefCurve curve) {
        logger.debug("creating curve");
        if(curve == null || curve.getName() == null || curve.getValues() == null){
            throw new IllegalArgumentException("cannot delete reference curve because name or values are null");
        }
        this.refCurveRepository.saveAndFlush(curve);
    }

    @Override
    public void delete(RefCurveDTO dto) {
        if(dto == null || dto.getName() == null){
            throw new IllegalArgumentException("cannot delete reference curve because it is null or its name was null");
        }
        logger.debug("deleting "+dto.getName());
        RefCurve curve = refCurveRepository.findByName(dto.name);
        refCurveRepository.delete(curve);
    }

    private RefCurveDTO  convertToDto(RefCurve refCurve) {
        RefCurveDTO dto = mapper.map(refCurve, RefCurveDTO.class);
        return dto;
    }

    private RefCurveDTO convertToDtoWithoutValues(RefCurve refCurve) {
        refCurve.setValues(new ArrayList<>());
        refCurve.setErrorMargin(null);
        RefCurveDTO dto = mapper.map(refCurve, RefCurveDTO.class);
        return dto;
    }

    private RefCurve convertFromDto(RefCurveDTO refCurve) {
        return mapper.map(refCurve, RefCurve.class);
    }

}
