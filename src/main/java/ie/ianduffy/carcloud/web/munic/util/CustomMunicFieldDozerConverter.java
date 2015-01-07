package ie.ianduffy.carcloud.web.munic.util;

import ie.ianduffy.carcloud.web.munic.dto.FieldDTO;
import ie.ianduffy.carcloud.web.munic.dto.FieldDTOStringValue;
import ie.ianduffy.carcloud.web.munic.factory.FieldDTOFactory;

import org.dozer.DozerConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CustomMunicFieldDozerConverter extends DozerConverter<Object, List> {

    public CustomMunicFieldDozerConverter() {
        super(Object.class, List.class);
    }

    @Override
    public List convertTo(Object o, List list) {
        List<FieldDTO> fields = new ArrayList<>();
        Map<String, Map<String, String>> fieldsData = (Map<String, Map<String, String>>) o;
        if (fieldsData.size() > 0) {
            Set<String> keys = fieldsData.keySet();
            for (String key : keys) {
                String keyData = fieldsData.get(key).get("b64_value");
                fields.add(FieldDTOFactory
                               .getFieldDTO(new FieldDTO(key, new FieldDTOStringValue(keyData))));
            }
        }
        return fields;
    }

    @Override
    public Object convertFrom(List list, Object o) {
        return null;
    }
}
