package com.ytooo.utils;


import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class BeanMapper {

    private static Mapper MAPPER = DozerBeanMapperBuilder.buildDefault();

    public static <T> T map(Object source, Class<T> destinationClass) {

        if (source == null) {

            return null;

        }

        return MAPPER.map(source, destinationClass);

    }



    public static void map(Object source, Object destination) {

        MAPPER.map(source, destination);

    }



    public static <T> List<T> mapList(Collection sourceList, Class<T> destinationClass) {

        List<T> destinationList = new ArrayList<>();

        for (Object sourceObject : sourceList) {

            destinationList.add(MAPPER.map(sourceObject, destinationClass));

        }

        return destinationList;

    }

}