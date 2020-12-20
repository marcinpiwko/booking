package com.piwko.booking.persistence.cache;

import com.piwko.booking.persistence.model.*;
import lombok.Getter;

@Getter
public class GlobalCache {

    private final Cache<Company> companies = new Cache<>();

    private final Cache<Location> locations = new Cache<>();

    private final Cache<Specialization> specializations = new Cache<>();

    private final Cache<Service> services = new Cache<>();

    private final Cache<Employee> employees = new Cache<>();

    private static class InstanceHolder {
        public static final GlobalCache INSTANCE = new GlobalCache();
    }

    private GlobalCache() {}

    public static GlobalCache getInstance() {
        return InstanceHolder.INSTANCE;
    }
}
