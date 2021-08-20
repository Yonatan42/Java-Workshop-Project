package com.yoni.javaworkshopprojectclient.utils;

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
}
