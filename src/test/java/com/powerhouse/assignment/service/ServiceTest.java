package com.powerhouse.assignment.service;

import com.powerhouse.assignment.exception.ValidationException;
import com.powerhouse.assignment.service.FileProcessingService;
import com.powerhouse.assignment.service.GenericService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;

@RunWith(SpringJUnit4ClassRunner.class)
public class ServiceTest {

    @InjectMocks
    private GenericService genericService;

    @InjectMocks
    private FileProcessingService fileProcessingService;

    @Test
    public void testFileuploadAndRetriveService() throws ValidationException {

        ClassLoader classLoader = getClass().getClassLoader();
        File fractionFile = new File(classLoader.getResource("fraction.csv").getFile());
        File meterReadingFile = new File(classLoader.getResource("meterReading.csv").getFile());

        fileProcessingService.cleanCache();
        Assert.assertFalse(fileProcessingService.isStaticFilePresent());
        fileProcessingService.processFile(fractionFile,meterReadingFile);
        Assert.assertTrue(fileProcessingService.isStaticFilePresent());
        Assert.assertEquals(24, genericService.retrieveConsumption("Jan"));
        Assert.assertEquals(3, genericService.retrieveConsumption("Dec"));
        fileProcessingService.cleanCache();
        Assert.assertFalse(fileProcessingService.isStaticFilePresent());
    }
}
