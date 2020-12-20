package com.piwko.booking.service.imports.processor;

import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.piwko.booking.persistence.model.Company;
import com.piwko.booking.persistence.model.Service;
import com.piwko.booking.service.imports.dto.ServiceCsv;
import com.piwko.booking.service.imports.translator.ServiceCsvTranslator;
import com.piwko.booking.service.interfaces.ServiceService;
import com.piwko.booking.util.exception.ResourceNotUniqueException;

import java.util.LinkedList;
import java.util.List;

@org.springframework.stereotype.Service
public class ServiceImportProcessor extends AbstractImportProcessor<ServiceCsv> {

    private final ServiceService serviceService;

    private final ServiceCsvTranslator serviceCsvTranslator;

    public ServiceImportProcessor(ServiceService serviceService, ServiceCsvTranslator serviceCsvTranslator) {
        super(ServiceCsv.class);
        this.serviceService = serviceService;
        this.serviceCsvTranslator = serviceCsvTranslator;
    }

    @Override
    protected void save(List<ServiceCsv> servicesCsv, Company company) throws ResourceNotUniqueException {
        List<Service> services = new LinkedList<>();
        for (ServiceCsv serviceCsv : servicesCsv) {
            services.add(serviceCsvTranslator.translate(serviceCsv, company));
        }
        serviceService.createServices(services);
    }

    @Override
    protected CsvSchema createCsvSchema() {
        return CsvSchema.builder()
                .addColumn("code", CsvSchema.ColumnType.STRING)
                .addColumn("name", CsvSchema.ColumnType.STRING)
                .addColumn("description", CsvSchema.ColumnType.STRING)
                .addColumn("price", CsvSchema.ColumnType.NUMBER)
                .addColumn("duration", CsvSchema.ColumnType.NUMBER)
                .addColumn("available", CsvSchema.ColumnType.BOOLEAN)
                .build();
    }
}
