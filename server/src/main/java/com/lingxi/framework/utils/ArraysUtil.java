package com.lingxi.framework.utils;

import com.lingxi.framework.domain.callback.Callback;
import com.lingxi.framework.domain.callback.Callback2;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Array;
import java.util.*;

@Slf4j
public class ArraysUtil {


    /**
     * 根據集合 返回一個集合CALLBACK值
     * @param collection
     * @param callback
     * @param <T>
     * @param <V>
     * @return
     */
    public static <T,V> List<V> map(Collection<T> collection, Callback<T,V> callback){
        List<V> list = new ArrayList<>();
        if(collection == null) return  list;

        Iterator<T> iterator = collection.iterator();
        while (iterator.hasNext()){
            list.add(callback.call(iterator.next()));
        }
        return list;
    }

    /**
     * 根據集合 返回一個集合CALLBACK值
     * @param collection
     * @param callback
     * @param <T>
     * @param <V>
     * @return
     */
    public static <T> void forEach(Collection<T> collection, Callback2<T> callback){
        if(collection == null) return;
        Iterator<T> iterator = collection.iterator();
        while (iterator.hasNext()){
            callback.call(iterator.next());
        }
    }

    public static <T>  List<T> asList(T... array){
        return Arrays.asList(array);
    }

    public static <T> List<T> filter(Collection<T> collection, Callback<T,Boolean> callback){
        List<T> list = new ArrayList<>();
        if(collection == null) return  list;

        Iterator<T> iterator = collection.iterator();
        while (iterator.hasNext()){
            T t = iterator.next();
                Boolean value = callback.call(t);
                if(value !=null && value) {
                    list.add(t);
                }
        }
        return list;
    }
}
