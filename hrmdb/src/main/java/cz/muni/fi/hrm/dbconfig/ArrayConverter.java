package cz.muni.fi.hrm.dbconfig;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.ArrayList;
import java.util.List;

@Converter(autoApply = true)
public class ArrayConverter implements AttributeConverter<List<Double>, Object> {

    @Override
    public Object convertToDatabaseColumn(List<Double> doubles) {
        Object[] objects = new Object[doubles.size()];
        for(int i = 0; i < doubles.size(); ++i){
            objects[i] = doubles.get(i);
        }
        return objects;
    }

    @Override
    public List<Double> convertToEntityAttribute(Object o) {
        Object[] objects = (Object[]) o;
        List<Double> doubles = new ArrayList<>();
        for (Object object : objects) {
            doubles.add(Double.parseDouble(object.toString()));
        }
        return doubles;
    }
}
