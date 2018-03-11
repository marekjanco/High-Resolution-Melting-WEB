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
    public List<NumberArrayDTO> getAll() {
        List<NumberArray> list = numberArrayRepository.findAll();
        List<NumberArrayDTO> ret = new ArrayList<>();
        for (NumberArray na : list) {
            NumberArrayDTO dto = new NumberArrayDTO();
            dto.data = this.getDataAsDoubleList(na.getNumbers());
            dto.name = na.getName();
            ret.add(dto);
        }
        return ret;
    }

    @Override
    public List<Double> findValuesByName(String name) {
        List<NumberArray> list = numberArrayRepository.findByName(name);
        if (list == null) {
            return null;
        }
        return this.getDataAsDoubleList(list.get(0).getNumbers());
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
        List<String> names = this.getAllNames();
        if(names.contains(dto.name)){
            throw new IllegalArgumentException("dataset with name "+dto.name+" already exists");
        }
        if(dto.name.equals("X-axis")){
            throw new IllegalArgumentException("dataset with name: 'X-axis' cannot be created");
        }
        NumberArray na = new NumberArray();
        na.setName(dto.name);
        String formattedData = this.parseDataToDbFormat(dto.data);
        na.setNumbers(formattedData);

        numberArrayRepository.save(na);
        numberArrayRepository.flush();
    }

    @Override
    public void delete(NumberArrayDTO dto) {
        if(dto == null || dto.name == null){
            throw new IllegalArgumentException("cannot delete number array with name: "+dto.name);
        }
        if(dto.name.equals("X-axis")){
            throw new IllegalArgumentException("dataset with name: 'X-axis' cannot be deleted");
        }
        List<NumberArray> na = numberArrayRepository.findByName(dto.name);
        if(na == null || na.size() > 2){
            throw new IllegalArgumentException("cannot delete number array with name: "+dto.name);
        }
        numberArrayRepository.delete(na);
    }

    private List<Double> getDataAsDoubleList(String data){
        List<Double> ret = new ArrayList<>();
        String[] numbers = data.split(",");
        List<String> strList = Arrays.asList(numbers);
        for(String str: strList){
            Double x = Double.parseDouble(str);
            ret.add(x);
        }
        return ret;
    }

    private String parseDataToDbFormat(List<Double> list){
        String str = list.toString();
        str = str.replaceAll(", ",",");
        str = str.replace("[","");
        str = str.replace("]","");
        return str;
    }
}
