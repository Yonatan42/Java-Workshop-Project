package com.yoni.javaworkshopprojectclient.functionalintefaces;

@FunctionalInterface
public interface BiConsumer<T1, T2> {

    void accept(T1 obj1, T2 obj2);

}
