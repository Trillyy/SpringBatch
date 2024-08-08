package com.batchexample.demo.writers;

import com.batchexample.demo.entities.TestTable;
import com.batchexample.demo.enums.StepsEnum;
import com.batchexample.demo.models.TestCsv;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Log4j2
@Component
public class DbWriter {

    private final EntityManagerFactory entityManagerFactory;

    @Bean
    @StepScope
    public ItemWriter<TestTable> itemWriter()
            throws UnexpectedInputException, ParseException {
        log.info("Starting writer " + StepsEnum.SAVE_DB.getCode());

        JpaItemWriter<TestTable> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);

        return items -> {
            try {
                log.info("Writing items: {}", items);
                writer.write(items);
            } catch (Exception e) {
                log.error("Error writing items: {}", e.getMessage(), e);
                throw e; // Re-throw to ensure batch job fails appropriately
            }
        };
    }
}
