package com.piwko.booking.persistence;

import com.piwko.booking.util.StringUtil;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JPAUtil {

    public static final List<Class<? extends Annotation>> RELATIONAL_ANNOTATIONS = Arrays.asList(OneToOne.class, OneToMany.class, ManyToOne.class, ManyToMany.class);

    private JPAUtil() {}

    public static void initializeLazyProperties(Object obj) {
        if (obj == null || !obj.getClass().isAnnotationPresent(Entity.class)) return;
        Arrays.stream(obj.getClass().getDeclaredFields()).filter(f -> {
            for (Class<? extends Annotation> annotation : RELATIONAL_ANNOTATIONS) {
                if (f.isAnnotationPresent(annotation)) {
                    return true;
                }
            }
            return false;
        }).collect(Collectors.toList()).forEach(f -> initialize(obj, f));
    }

    private static void initialize(Object obj, Field field) {
        try {
            Hibernate.initialize(obj.getClass().getDeclaredMethod("get" + StringUtil.capitalize(field.getName())).invoke(obj));
            // TODO wołać rekurencyjnie dla kolejnych relacji?
        } catch(Exception e) {
            // log
        }
    }
}
