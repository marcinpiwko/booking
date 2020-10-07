package com.piwko.booking.persistence;

import com.piwko.booking.util.exception.ApplicationException;

import java.util.concurrent.ConcurrentHashMap;

public class EntityFactory {

    private static final ConcurrentHashMap<Class<?>, EntityType> entityTypesMap = new ConcurrentHashMap<>();

    private EntityFactory() {

    }

    public static <T> T newEntityInstance(Class<? extends T> entityClass) {
        try {
            return getEntityClass(entityClass).getDeclaredConstructor().newInstance();
        } catch(Exception e) {
            throw new ApplicationException("Invalid entity item class: " + entityClass, e);
        }
    }

    public static EntityType getEntityType(Class<?> entityClass) {
        return entityTypesMap.computeIfAbsent(entityClass, aClass -> new EntityType(entityClass));
    }

    @SuppressWarnings("unchecked")
    public static <T> Class<T> getEntityClass(Class<T> entityClass) {
        return (Class<T>) getEntityType(entityClass).getType();
    }

    @SuppressWarnings("unchecked")
    public static <T> Class<T> getEntityClass(String className) {
        try {
            return (Class<T>) getEntityType(Class.forName(className)).getType();
        } catch(ClassNotFoundException e) {
            throw new ApplicationException("Invalid entity item class: " + className, e);
        }
    }

    public static String getEntityName(Class<?> entityClass) {
        return getEntityType(entityClass).getName();
    }
}
