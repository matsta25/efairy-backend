package com.matsta25.efairy.service;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.util.UUID;
import org.apache.commons.io.FileUtils;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class BatchJobService {

    public static final String FILE_PATH = "filePath";

    private final JobLauncher jobLauncher;
    private final Job processJob;

    public BatchJobService(JobLauncher jobLauncher, Job processJob) {
        this.jobLauncher = jobLauncher;
        this.processJob = processJob;
    }

    public void invokeImportHoroscope(MultipartFile file) throws Exception {
        JobParameters jobParameter =
                new JobParametersBuilder()
                        .addString(FILE_PATH, getFileLocation(file.getResource()))
                        .toJobParameters();
        jobLauncher.run(processJob, jobParameter);
    }

    private String getFileLocation(Resource resource) {
        File temporaryFile;
        try {
            temporaryFile = Files.createTempFile("prefix", UUID.randomUUID().toString()).toFile();
        } catch (IOException e) {
            throw new UncheckedIOException("Couldn't create temp file", e);
        }

        try {
            FileUtils.copyInputStreamToFile(resource.getInputStream(), temporaryFile);
        } catch (IOException e) {
            FileUtils.deleteQuietly(temporaryFile);
            throw new UncheckedIOException(
                    "Couldn't copy copy of resource '"
                            + resource
                            + "' to file '"
                            + temporaryFile
                            + "'",
                    e);
        }

        return temporaryFile.toPath().toString();
    }
}
