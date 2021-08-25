package com.yoni.javaworkshopprojectclient.functionalintefaces;

@FunctionalInterface
public interface BiFunction<T1, T2, T3> {
    T3 apply(T1 obj1, T2 obj2);
}
