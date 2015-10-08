package com.aurovo.tack.interfaces;

public interface BaseEntityDao<T,K> {
    T findById(K id);
    K save(T Object);
    T update(T object);
    //delete
}