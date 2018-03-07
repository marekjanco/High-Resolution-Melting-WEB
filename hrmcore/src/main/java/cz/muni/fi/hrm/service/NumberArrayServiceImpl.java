package cz.muni.fi.hrm.service;


import cz.muni.fi.hrm.dto.NumberArrayDTO;
import cz.muni.fi.hrm.entity.NumberArray;
import cz.muni.fi.hrm.repository.NumberArrayRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class NumberArrayServiceImpl implements NumberArrayService{

    @Inject
    private NumberArrayRepository numberArrayRepository;

    @Override
    public List<String> getAllNames() {
        List<NumberArray> numberArrays = numberArrayRepository.findAll();
        List<String> names = new ArrayList<>();
        for(NumberArray item : numberArrays){
            names.add(item.getName());
        }
        names.remove(0);
        return names;
    }

    @Override
    public List<NumberArray> getAll() {
        return numberArrayRepository.findAll();
    }

    @Override
    public List<String> findValuesByName(String name) {
        List<NumberArray> list = numberArrayRepository.findByName(name);
        if (list == null) {
            return null;
        }
        String numbers = list.get(0).getNumbers();
        String[] tokens = numbers.split(",");
        return Arrays.asList(tokens);
    }

    @Override
    public Object[] compute(List<Double> data) {
        Double minDistance = 0.0;
        int ret = 0;
        List<NumberArray> list = numberArrayRepository.findAll();
        for(int i = 0; i < list.size(); ++i){
            String numbers = list.get(i).getNumbers();
            String[] tokens = numbers.split(",");
            Double match = 0.0;
            for(int j = 0; j < tokens.length; ++j){
                Double part = Double.parseDouble(tokens[j]);
                Double temp =  data.get(j) - part;
                match += Math.pow(temp,2);
            }
            match *= 1.0/tokens.length;
            match = Math.sqrt(match);
            if(i == 0 || match < minDistance){
                minDistance = match;
                ret = i;
            }
        }
        return new Object[]{minDistance, list.get(ret)};
    }

    @Override
    public void create(NumberArrayDTO dto) {
        if(dto.data == null || dto.name == null){
            throw new IllegalArgumentException("cannot create number array with name: "+dto.name+" and data: "+dto.data);
        }
        NumberArray na = new NumberArray();
        na.setName(dto.name);
        na.setNumbers(dto.data.toString());
        numberArrayRepository.save(na);
        numberArrayRepository.flush();
    }
}
