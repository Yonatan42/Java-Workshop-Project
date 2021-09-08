package com.yoni.javaworkshopprojectserver.utils;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CollectionUtils {

    private CollectionUtils(){}

    public static  <TIn, TOut> List<TOut> convertCollection(Collection<TIn> list, Function<TIn, TOut> converter){
        return list
                .stream()
                .map(converter)
                .collect(Collectors.toList());
    }
}
