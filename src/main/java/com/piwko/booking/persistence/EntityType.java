package com.piwko.booking.persistence;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntityType {

    private Class<?> type;

    private String name;

    public EntityType(Class<?> type) {
        this.type = type;
        this.name = type.getSimpleName();
    }

}
