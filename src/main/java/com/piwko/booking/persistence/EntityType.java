package com.piwko.booking.persistence;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Table;

@Getter
@Setter
public class EntityType {

    private Class<?> type;

    private String name;

    private String tableName;

    public EntityType(Class<?> type) {
        this.type = type;
        this.name = type.getSimpleName();
        this.tableName = type.getAnnotation(Table.class).name();
    }
}
