package vn.edu.ptit.sqa.util.converter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ConverterUtil {
    public static final ObjectMapper _OBJECT_MAPPER = new ObjectMapper()
            .enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)
            .enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT)
            .enable(DeserializationFeature.USE_LONG_FOR_INTS)
            ;

    public static <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        if (CollectionUtils.isEmpty(source)) return new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        return source
                .stream()
                .map(element -> modelMapper.map(element, targetClass))
                .collect(Collectors.toList());
    }


    public static <T> T mappingToObject(Object sourceObj, Class<T> targetClass) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(sourceObj, targetClass);
    }
}
