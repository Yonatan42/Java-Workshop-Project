package com.yoni.javaworkshopprojectclient.events;

import androidx.collection.ArraySet;

public class Event<T extends EventListener> {
    private ArraySet<T> listeners = new ArraySet<>();

    public void addListener(T listener){
        listeners.add(listener);
    }

    public void removeListener(T listener){
        listeners.remove(listener);
    }

    public void clearListeners(){
        listeners.clear();
    }

    public void fire(Object... params){
        for (T listener: listeners){
            listener.fire(params);
        }
    }
}
