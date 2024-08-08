package com.batchexample.demo.jobs;

import com.batchexample.demo.entities.TestTable;
import com.batchexample.demo.enums.JobsEnum;
import com.batchexample.demo.models.TestCsv;
import com.batchexample.demo.processors.TestCsvToTestTableProcessor;
import com.batchexample.demo.readers.CsvReader;
import com.batchexample.demo.writers.DbWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.IOException;

@Configuration
@Log4j2
@RequiredArgsConstructor
public class CsvToTxtChunkJob {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final CsvReader csvReader;
    private final TestCsvToTestTableProcessor testCsvToTestTableProcessor;
    private final DbWriter dbWriter;


    @Bean
    public Job chunkJob() throws IOException {
        JobBuilder jobBuilder = new JobBuilder(JobsEnum.CSV_TO_DB_CHUNK.getCode(), jobRepository);
        log.info("Starting job " + JobsEnum.CSV_TO_DB_CHUNK.getCode());
        return jobBuilder
                .start(firstChunkStep())
                .build();
    }

    private Step firstChunkStep() throws IOException {
        log.info("Starting step 1");
        StepBuilder stepBuilder = new StepBuilder("Step 1", jobRepository);
        return stepBuilder.<TestCsv, TestTable>chunk(1, transactionManager)
                .reader(csvReader.itemReader())
                .processor(testCsvToTestTableProcessor.itemProcessor())
                .writer(dbWriter.itemWriter())
                .build();
    }
}
