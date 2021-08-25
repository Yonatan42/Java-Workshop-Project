package com.yoni.javaworkshopprojectclient.utils;

import androidx.arch.core.util.Function;

import com.yoni.javaworkshopprojectclient.functionalintefaces.BiFunction;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class ListUtils {

    public static <T> List<T> combineLists(List<T> oldList, List<T> newList, Comparator<T> comparator){
        Set<T> set = new TreeSet<T>(comparator);
        set.addAll(newList);
        set.addAll(oldList);
        return new ArrayList<>(set);
    }

    public static <TOut, TIn> TOut reduce(List<TIn> list, TOut initVal, BiFunction<TOut, TIn, TOut> func){
        TOut acc = initVal;
        for (TIn elem: list){
            acc = func.apply(acc, elem);
        }
        return acc;
    }

    public static <T> String mapJoin(List<T> list, String deliminator, Function<T, String> map){
        StringBuilder stringBuilder = new StringBuilder();
        int size = list.size();
        for (int i = 0; i < size; i++){
            T elem = list.get(i);
            stringBuilder.append(map.apply(elem));
            if(i != size - 1){
                stringBuilder.append(deliminator);
            }
        }
        return stringBuilder.toString();
    }
}
