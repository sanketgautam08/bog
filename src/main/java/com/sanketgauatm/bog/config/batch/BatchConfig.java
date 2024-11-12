package com.sanketgauatm.bog.config.batch;

import com.sanketgauatm.bog.model.BatchRoom;
import com.sanketgauatm.bog.repo.BatchRoomRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.support.SynchronizedItemStreamReader;
import org.springframework.batch.item.support.builder.SynchronizedItemStreamReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class BatchConfig {

    private final BatchRoomRepo batchRoomRepo;
    private final PlatformTransactionManager transactionManager;
    private final JobRepository jobRepository;
    private final DataSource dataSource;


    @Bean
    public FlatFileItemReader<BatchRoom> itemReader() {
        FlatFileItemReader<BatchRoom> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new FileSystemResource("src/main/resources/rooms.csv"));
        itemReader.setName("csvBatchRoomReader");
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(lineMapper());
        return itemReader;
    }

//    @Bean
//    public RepositoryItemReader<BatchRoom> repositoryItemReader() {
//        RepositoryItemReader<BatchRoom> repositoryItemReader = new RepositoryItemReader<>();
//        repositoryItemReader.setName("repositoryItemReader");
//        repositoryItemReader.setRepository(batchRoomRepo);
//        repositoryItemReader.setMethodName("findAll");
//        repositoryItemReader.setSort(Map.of("name", Sort.Direction.ASC));
//        return repositoryItemReader;
//    }

//    @Bean
//    public JdbcCursorItemReader<BatchRoom> jdbcCursorItemReader() {
//        return new JdbcCursorItemReaderBuilder<BatchRoom>()
//                .name("jdbc reader")
//                .dataSource(dataSource)
//                .sql("select * from batch_room")
//                .rowMapper(new DataClassRowMapper<>(BatchRoom.class))
//                .build();
//    }
    @Bean
    public SynchronizedItemStreamReader<BatchRoom> jdbcCursorItemReader() {
        JdbcCursorItemReader<BatchRoom> itemReader =  new JdbcCursorItemReaderBuilder<BatchRoom>()
                .name("jdbc reader")
                .dataSource(dataSource)
                .sql("select * from batch_room")
                .rowMapper(new DataClassRowMapper<>(BatchRoom.class))
                .build();
        return new SynchronizedItemStreamReaderBuilder<BatchRoom>()
                .delegate(itemReader)
                .build();
    }

    @Bean
    public BatchRoomNameAppendProcessor batchRoomNameAppendProcessor() {
        return new BatchRoomNameAppendProcessor();
    }

    @Bean
    public BatchRoomProcessor batchRoomProcessor() {
        return new BatchRoomProcessor();
    }

    @Bean
    public RepositoryItemWriter<BatchRoom> itemWriterJpa() {
        RepositoryItemWriter<BatchRoom> itemWriter = new RepositoryItemWriter<>();
        itemWriter.setRepository(batchRoomRepo);
        itemWriter.setMethodName("save");
        return itemWriter;
    }

    @Bean
    public JdbcBatchItemWriter<BatchRoom> itemWriter() {
        return new JdbcBatchItemWriterBuilder<BatchRoom>()
                .dataSource(dataSource)
                .sql("INSERT INTO batch_room (id,name,location) VALUES (:id, :name, :location)")
                .beanMapped()
                .build();
    }

    @Bean
    public JdbcBatchItemWriter<BatchRoom> itemWriterUpdater() {
        return new JdbcBatchItemWriterBuilder<BatchRoom>()
                .dataSource(dataSource)
                .sql("UPDATE batch_room SET name = :name WHERE id = :id")
                .beanMapped()
                .build();
    }


    @Bean
    public Step importFromCsvStep(){
        return new StepBuilder("importFromCsvStep", jobRepository)
                .<BatchRoom, BatchRoom> chunk(1000, transactionManager)
                .reader(itemReader())
                .processor(batchRoomProcessor())
                .writer(itemWriterJpa())
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public Step appendNameStep(){
        return new StepBuilder("changeNameStep", jobRepository)
                .<BatchRoom, BatchRoom> chunk(1000, transactionManager)
                .reader(jdbcCursorItemReader())
                .processor(batchRoomNameAppendProcessor())
                .writer(itemWriterUpdater())
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public Job allSteps(){
        return new JobBuilder("allOperations", jobRepository)
                .start(importFromCsvStep())
                .next(appendNameStep())
                .build();
    }
    @Bean
    public Job csvToDbJob(){
        return new JobBuilder("importBatchRooms", jobRepository)
                .start(importFromCsvStep())
                .build();
    }

    @Bean
    public Job changeNameJob(){
        return new JobBuilder("changeName", jobRepository)
                .start(appendNameStep())
                .build();
    }

    @Bean
    public TaskExecutor taskExecutor(){
        SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
        asyncTaskExecutor.setConcurrencyLimit(10);
        return asyncTaskExecutor;
    }


    private LineMapper<BatchRoom> lineMapper() {
        DefaultLineMapper<BatchRoom> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("id","name","location");

        BeanWrapperFieldSetMapper<BatchRoom> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(BatchRoom.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }
}
