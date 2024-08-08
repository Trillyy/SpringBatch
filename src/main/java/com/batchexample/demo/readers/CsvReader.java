package com.batchexample.demo.readers;

import com.batchexample.demo.enums.StepsEnum;
import com.batchexample.demo.models.TestCsv;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.core.io.Resource;
import jakarta.transaction.Transaction;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Log4j2
public class CsvReader {

    @Value("classpath:files/read/test.csv")
    private Resource inputCsv;

    @Bean
    @StepScope
    public ItemReader<TestCsv> itemReader()
            throws UnexpectedInputException, ParseException, IOException {
        log.info("Starting reader " + StepsEnum.LOAD_CSV.getCode());
        FlatFileItemReader<TestCsv> reader = new FlatFileItemReader<>();
        reader.setResource(inputCsv);

        log.info("Reading file " + inputCsv.getFilename());
        log.info("File size: " + inputCsv.getFile().length() + " bytes");

        DefaultLineMapper<TestCsv> defaultLineMapper = defaultLineMapper();
        reader.setLineMapper((line, lineNumber) -> {
            log.info("Reading line " + lineNumber + ": " + line);
            return defaultLineMapper.mapLine(line, lineNumber);
        });
        reader.setLinesToSkip(1);

        reader.setSkippedLinesCallback(line -> log.info("Skipping line: " + line));
        return reader;
    }

    private static DefaultLineMapper<TestCsv> defaultLineMapper() {
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setNames("name", "surname");
        DefaultLineMapper<TestCsv> defaultLineMapper = new DefaultLineMapper<>();
        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(fieldSet -> {
            TestCsv testCsv = new TestCsv();
            testCsv.setName(fieldSet.readString(0));
            testCsv.setSurname(fieldSet.readString(1));
            return testCsv;
        });
        return defaultLineMapper;
    }
}
