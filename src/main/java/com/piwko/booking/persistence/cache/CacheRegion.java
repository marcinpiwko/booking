package com.piwko.booking.persistence.cache;

public enum CacheRegion {

    COMPANIES,
    SERVICES,
    SPECIALIZATIONS,
    SUB_SPECIALIZATIONS,
    LOCATIONS;

    public static class Constants {

        private Constants() {}

        public static final String COMPANIES_VALUE = "COMPANIES";
        public static final String SERVICES_VALUE = "SERVICES";
        public static final String SPECIALIZATIONS_VALUE = "SPECIALIZATIONS";
        public static final String SUB_SPECIALIZATIONS_VALUE = "SUB_SPECIALIZATIONS";
        public static final String LOCATIONS_VALUE = "LOCATIONS";
    }
}
