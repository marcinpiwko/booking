package com.piwko.booking.service.imports.processor;

import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.piwko.booking.persistence.model.Company;
import com.piwko.booking.persistence.model.Location;
import com.piwko.booking.service.imports.dto.LocationCsv;
import com.piwko.booking.service.imports.translator.LocationCsvTranslator;
import com.piwko.booking.service.interfaces.LocationService;
import com.piwko.booking.util.exception.ResourceNotFoundException;
import com.piwko.booking.util.exception.ResourceNotUniqueException;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class LocationImportProcessor extends AbstractImportProcessor<LocationCsv> {

    private final LocationService locationService;

    private final LocationCsvTranslator locationCsvTranslator;

    public LocationImportProcessor(LocationService locationService, LocationCsvTranslator locationCsvTranslator) {
        super(LocationCsv.class);
        this.locationService = locationService;
        this.locationCsvTranslator = locationCsvTranslator;
    }

    @Override
    protected void save(List<LocationCsv> locationsCsv, Company company) throws ResourceNotFoundException, ResourceNotUniqueException {
        List<Location> locations = new LinkedList<>();
        for (LocationCsv locationCsv : locationsCsv) {
            locations.add(locationCsvTranslator.translate(locationCsv, company));
        }
        locationService.createLocations(locations);
    }

    @Override
    protected CsvSchema createCsvSchema() {
        return CsvSchema.builder()
                .addColumn("code", CsvSchema.ColumnType.STRING)
                .addColumn("name", CsvSchema.ColumnType.STRING)
                .addColumn("street", CsvSchema.ColumnType.STRING)
                .addColumn("city", CsvSchema.ColumnType.STRING)
                .addColumn("country", CsvSchema.ColumnType.STRING)
                .addColumn("postalCode", CsvSchema.ColumnType.STRING)
                .addColumn("phoneNumber", CsvSchema.ColumnType.STRING)
                .addColumn("email", CsvSchema.ColumnType.STRING)
                .addColumn("workingHours", CsvSchema.ColumnType.ARRAY)
                .build();
    }
}
