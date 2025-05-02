package br.univesp.pi.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;

@Component
public class MapperUtil {

    @Autowired
    private ModelMapper modelMapper;

    public <E, D> D map(E source, Class<D> targetClass) {
        if (source == null || targetClass == null) {
            return null;
        }
        return modelMapper.map(source, targetClass);
    }

    public <E, D> List<D> mapList(List<E> sourceList, Class<D> targetClass) {
        if (sourceList == null || targetClass == null) {
            return Collections.emptyList();
        }
        return sourceList.stream()
                .map(element -> modelMapper.map(element, targetClass))
                .toList();
    }
}
