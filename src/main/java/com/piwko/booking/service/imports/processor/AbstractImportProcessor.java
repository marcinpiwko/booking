package com.piwko.booking.service.imports.processor;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.piwko.booking.persistence.model.Company;
import com.piwko.booking.util.CollectionsUtil;
import com.piwko.booking.util.exception.ApplicationException;
import com.piwko.booking.util.exception.ResourceNotFoundException;
import com.piwko.booking.util.exception.ResourceNotUniqueException;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Log4j2
public abstract class AbstractImportProcessor<T> {

    private final Class<T> clazz;

    private final ObjectReader objectReader;

    protected AbstractImportProcessor(Class<T> clazz) {
        this.clazz = clazz;
        this.objectReader = initObjectReader();
    }

    public void process(MultipartFile file, Company company) throws ResourceNotFoundException, ResourceNotUniqueException {
        log.info("Start import " + file.getOriginalFilename() + " file");
        long startTime = System.currentTimeMillis();
        try {
            List<T> entities = parseCsvFile(file);
            if (CollectionsUtil.isEmpty(entities)) {
                log.info("Nothing to process");
                return;
            }
            log.info("Found " + entities.size() + " records to process");
            save(entities, company);
        } catch (Exception e) {
            log.error(e);
            throw e;
        } finally {
            log.info("End of import " + file.getOriginalFilename() + " file, execution time: " + (System.currentTimeMillis() - startTime));
        }
    }

    protected List<T> parseCsvFile(MultipartFile file) {
        try {
            return objectReader.<T>readValues(file.getInputStream()).readAll();
        } catch (Exception e) {
            throw new ApplicationException("Fail to parse file " + file.getOriginalFilename(), e);
        }
    }

    protected ObjectReader initObjectReader() {
        CsvMapper csvMapper = new CsvMapper();
        csvMapper.enable(CsvParser.Feature.TRIM_SPACES);
        csvMapper.enable(CsvParser.Feature.SKIP_EMPTY_LINES);
        return csvMapper.readerFor(clazz).with(createCsvSchema());
    }

    protected abstract void save(List<T> entities, Company company) throws ResourceNotFoundException, ResourceNotUniqueException;

    protected abstract CsvSchema createCsvSchema();

}
