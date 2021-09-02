package com.yoni.javaworkshopprojectclient.functionalintefaces;

@FunctionalInterface
public interface TriConsumer<T1, T2, T3> {
    void accept(T1 obj1, T2 obj2, T3 obj3);
}

