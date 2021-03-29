package com.matsta25.efairy.config;

import com.matsta25.efairy.model.Horoscope;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import javax.sql.DataSource;

import static com.matsta25.efairy.service.BatchJobService.FILE_PATH;


@Configuration
@EnableBatchProcessing
public class BatchConfig {

    //        TODO: find best place to aggregate batches files

    public static final String DELIMITER = ";";
    public static final String[] FIELD_NAMES_TO_READ = {"zodiacSign", "date", "content"};
    public static final int CHUNK_SIZE = 10;

    JobBuilderFactory jobBuilderFactory;

    StepBuilderFactory stepBuilderFactory;

    public BatchConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    @StepScope
    public FlatFileItemReader<Horoscope> reader(@Value("#{jobParameters[filePath]}") String filePath) {
        return new FlatFileItemReaderBuilder<Horoscope>()
                .name("horoscopeItemReader")
                .resource(new FileSystemResource(filePath))
                .delimited()
                .names(FIELD_NAMES_TO_READ)
                .lineMapper(lineMapper())
                .fieldSetMapper(
                        new BeanWrapperFieldSetMapper<Horoscope>() {{
                            setTargetType(Horoscope.class);
                        }}
                )
                .build();
    }

    @Bean
    public LineMapper<Horoscope> lineMapper() {
        final DefaultLineMapper<Horoscope> defaultLineMapper = new DefaultLineMapper<>();
        final DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

        lineTokenizer.setDelimiter(DELIMITER);
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames(FIELD_NAMES_TO_READ);

        final HoroscopeFieldSetMapper fieldSetMapper = new HoroscopeFieldSetMapper();
        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);

        return defaultLineMapper;
    }

    @Bean
    public HoroscopeProcessor processor() {
        return new HoroscopeProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Horoscope> writer(final DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Horoscope>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO Horoscope(zodiac_sign, date, content) VALUES (:zodiacSign, :date, :content)")
                .dataSource(dataSource)
                .build();
    }

    @Bean
    public Step stepOne(JdbcBatchItemWriter<Horoscope> writer) {
        return stepBuilderFactory.get("stepOne")
                .<Horoscope, Horoscope> chunk(CHUNK_SIZE)
                .reader(reader(FILE_PATH))
                .processor(processor())
                .writer(writer)
                .build();
    }

    @Bean
    public Job importHoroscopeJob(NotificationListener listener, Step stepOne) {
        return jobBuilderFactory.get("importHoroscopeJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(stepOne)
                .end()
                .build();
    }
}
