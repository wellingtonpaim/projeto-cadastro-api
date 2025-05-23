package br.univesp.pi.util;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;

@Component
public class MapperUtil {

    private final ModelMapper modelMapper;

    public MapperUtil() {
        this.modelMapper = new ModelMapper();
        configurarConversoes();
    }

    private void configurarConversoes() {
        // Conversor de BigDecimal para Double com 2 casas decimais
        Converter<BigDecimal, Double> bigDecimalToDoubleConverter = new Converter<>() {
            @Override
            public Double convert(MappingContext<BigDecimal, Double> context) {
                BigDecimal source = context.getSource();
                if (source == null) return null;
                return source.setScale(2, RoundingMode.HALF_UP).doubleValue();
            }
        };

        // Registrar conversão global
        modelMapper.createTypeMap(BigDecimal.class, Double.class);
        modelMapper.addConverter(bigDecimalToDoubleConverter);
    }

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
