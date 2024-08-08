package com.batchexample.demo.processors;

import com.batchexample.demo.entities.TestTable;
import com.batchexample.demo.enums.StepsEnum;
import com.batchexample.demo.models.TestCsv;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Log4j2
public class TestCsvToTestTableProcessor {

    @Bean
    public ItemProcessor<TestCsv, TestTable> itemProcessor() {
        log.info("Starting processor");
        return test -> {
            TestTable testTable = new TestTable();
            testTable.setName(test.getName());
            testTable.setSurname(test.getSurname());

            log.info(testTable);

            return testTable;
        };
    }
}
