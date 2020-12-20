package com.piwko.booking.service.imports.processor;

import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.piwko.booking.persistence.model.Company;
import com.piwko.booking.persistence.model.Employee;
import com.piwko.booking.service.imports.dto.EmployeeCsv;
import com.piwko.booking.service.imports.translator.EmployeeCsvTranslator;
import com.piwko.booking.service.interfaces.EmployeeService;
import com.piwko.booking.util.exception.ResourceNotFoundException;
import com.piwko.booking.util.exception.ResourceNotUniqueException;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class EmployeeImportProcessor extends AbstractImportProcessor<EmployeeCsv> {

    private final EmployeeService employeeService;

    private final EmployeeCsvTranslator employeeCsvTranslator;

    public EmployeeImportProcessor(EmployeeService employeeService, EmployeeCsvTranslator employeeCsvTranslator) {
        super(EmployeeCsv.class);
        this.employeeService = employeeService;
        this.employeeCsvTranslator = employeeCsvTranslator;
    }

    @Override
    protected void save(List<EmployeeCsv> employeesCsv, Company company) throws ResourceNotFoundException, ResourceNotUniqueException {
        List<Employee> employees = new LinkedList<>();
        for (EmployeeCsv employeeCsv : employeesCsv) {
            employees.add(employeeCsvTranslator.translate(employeeCsv, company));
        }
        employeeService.createEmployees(employees);
    }

    @Override
    protected CsvSchema createCsvSchema() {
        return CsvSchema.builder()
                .addColumn("firstName", CsvSchema.ColumnType.STRING)
                .addColumn("lastName", CsvSchema.ColumnType.STRING)
                .addColumn("code", CsvSchema.ColumnType.STRING)
                .addColumn("locationCode", CsvSchema.ColumnType.STRING)
                .addColumn("serviceCodes", CsvSchema.ColumnType.ARRAY)
                .addColumn("workingHours", CsvSchema.ColumnType.ARRAY)
                .build();
    }
}
