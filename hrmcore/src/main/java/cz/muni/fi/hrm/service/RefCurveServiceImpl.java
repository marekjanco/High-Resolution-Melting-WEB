package cz.muni.fi.hrm.service;


import com.sun.javafx.scene.control.skin.VirtualFlow;
import cz.muni.fi.hrm.dto.RefCurveDTO;
import cz.muni.fi.hrm.entity.RefCurve;
import cz.muni.fi.hrm.repository.RefCurveRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RefCurveServiceImpl implements RefCurveService {

    @Inject
    private RefCurveRepository refCurveRepository;

    @Inject
    private ModelMapper modelMapper;

    @Override
    public List<RefCurveDTO> getNamesAndAcronyms() {
        List<RefCurveDTO> refCurves = new ArrayList<>();
        List<String> names = refCurveRepository.findAllNames();
        List<String> acronyms = refCurveRepository.findAllAcronyms();
        for(int i = 0; i < names.size() || i < acronyms.size(); ++i){
            refCurves.add(new RefCurveDTO(names.get(i), acronyms.get(i), null, null));
        }
        return refCurves;
    }

    @Override
    public List<RefCurveDTO> getAll() {
        List<RefCurve> list = refCurveRepository.findAll();
        return list.stream()
                .map(curve -> convertToDto(curve))
                .collect(Collectors.toList());
    }

    @Override
    public RefCurveDTO getTemperature() {
        return convertToDto(refCurveRepository.findTemperature());
    }

    @Override
    public List<Double> findValuesByName(String name) {
        RefCurve curve = refCurveRepository.findByName(name);
        if (curve == null) {
            return new ArrayList<Double>(){};
        }
        return curve.getValues();
    }

    @Override
    public void create(RefCurveDTO dto) {
        /*
        if(dto.data == null || dto.name == null){
            throw new IllegalArgumentException("cannot create reference curve with name: "+dto.name+" and data: "+dto.data);
        }
        List<RefCurveDTO> names = this.getAllNames();
        for(RefCurveDTO nameDTO: names){
            if(nameDTO.name.equals(dto.name)){
                throw new IllegalArgumentException("dataset with name "+dto.name+" already exists");
            }
        }
        RefCurve na = new RefCurve();
        na.setName(dto.name);
        na.setAcronym(dto.acronym);
        na.setNote(dto.note);
        //String formattedData = this.parseDataToDbFormat(dto.data);
        //na.setNumbers(formattedData);

        refCurveRepository.save(na);
        refCurveRepository.flush();
        */
    }

    @Override
    public void update(RefCurveDTO dto) {
        /*
        if(dto.data == null || dto.name == null){
            throw new IllegalArgumentException("cannot update reference curve with name: "+dto.name+" and data: "+dto.data);
        }
        List<RefCurve> list = refCurveRepository.findByName(dto.name);
        RefCurve na = list.get(0);
        //String formattedData = this.parseDataToDbFormat(dto.data);
        //na.setNumbers(formattedData);

        refCurveRepository.save(na);
        refCurveRepository.flush();
        */
    }

    @Override
    public void delete(RefCurveDTO dto) {
        if(dto == null || dto.name == null){
            throw new IllegalArgumentException("cannot delete reference curve with name: "+dto.name);
        }
        RefCurve curve = refCurveRepository.findByName(dto.name);
        refCurveRepository.delete(curve);
    }

    private RefCurveDTO  convertToDto(RefCurve refCurve) {
         return modelMapper.map(refCurve, RefCurveDTO.class);
    }
}
