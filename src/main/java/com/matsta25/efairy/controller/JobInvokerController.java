package com.matsta25.efairy.controller;

import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/batch")
public class JobInvokerController {

    public static final String FILE_PATH = "filePath";
    JobLauncher jobLauncher;
    Job processJob;

    public JobInvokerController(JobLauncher jobLauncher, Job processJob) {
        this.jobLauncher = jobLauncher;
        this.processJob = processJob;
    }

    @PostMapping("/invoke-import-horoscope")
    @ApiOperation("This endpoint is providing import of the csv file with horoscopes to db with spring batch.")
    public String invokeImportHoroscope(
            @RequestParam("file") MultipartFile file
            ) throws Exception {

        JobParameters jobParameter = new JobParametersBuilder()
                .addString(FILE_PATH, getFileLocation(file.getResource()))
                .toJobParameters();
        jobLauncher.run(processJob, jobParameter);

        return "Job - import horoscope has been invoked.";
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
                    "Couldn't copy copy of resource '" + resource + "' to file '" + temporaryFile + "'", e);
        }

        return temporaryFile.toPath().toString();
    }
}
