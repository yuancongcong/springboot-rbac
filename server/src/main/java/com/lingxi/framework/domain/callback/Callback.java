package com.lingxi.framework.domain.callback;

public interface Callback<T,V> {

    V call(T obj);
}
