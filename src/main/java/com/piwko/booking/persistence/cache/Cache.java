package com.piwko.booking.persistence.cache;

import com.piwko.booking.persistence.model.CodeNameEntity;
import lombok.Getter;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Cache<V extends CodeNameEntity> {

     private final Map<Long, V> cacheById = new ConcurrentHashMap<>();

     private final Map<String, V> cacheByCode = new ConcurrentHashMap<>();

     protected Cache() {}

     public synchronized Optional<V> get(Long id) {
        return Optional.ofNullable(cacheById.get(id));
     }

     public synchronized Optional<V> get(String code) {
        return Optional.ofNullable(cacheByCode.get(code));
    }

     public synchronized Collection<V> getAll() {
         return cacheById.values();
     }

     public synchronized Long put(V value) {
         if (cacheById.containsKey(value.getId())) {
             cacheById.replace(value.getId(), value);
         } else {
             cacheById.put(value.getId(), value);
         }
         if (cacheByCode.containsKey(value.getCode())) {
             cacheByCode.replace(value.getCode(), value);
         } else {
             cacheByCode.put(value.getCode(), value);
         }
         return value.getId();
     }

    public synchronized void remove(Long id) {
        V value = cacheById.get(id);
        cacheByCode.remove(value.getCode(), value);
        cacheById.remove(id);
    }

    public synchronized void remove(String code) {
         V value = cacheByCode.get(code);
         cacheById.remove(value.getId(), value);
         cacheByCode.remove(code);
    }

    public synchronized void clear() {
        cacheById.clear();
        cacheByCode.clear();
    }

    public synchronized boolean existsById(Long id) {
         return cacheById.get(id) != null;
    }

    public synchronized boolean existsByCode(String code) {
         return cacheByCode.get(code) != null;
    }
}
