package com.matsta25.efairy.config;

import com.matsta25.efairy.model.Horoscope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener extends JobExecutionListenerSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationListener.class);

    private final JdbcTemplate jdbcTemplate;

    public NotificationListener(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            LOGGER.info("Job finished! Verifying results");

            jdbcTemplate
                    .query(
                            "SELECT zodiac_sign, date, content FROM Horoscope",
                            (rs, row) ->
                                    new Horoscope(
                                            rs.getString(1),
                                            rs.getDate(2).toLocalDate(),
                                            rs.getString(3)))
                    .forEach(horoscope -> LOGGER.info("Found <" + horoscope + "> in database."));
        }
    }
}
