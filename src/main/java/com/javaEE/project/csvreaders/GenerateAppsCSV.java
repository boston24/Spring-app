package com.javaEE.project.csvreaders;

import com.javaEE.project.domain.Application;
import com.javaEE.project.domain.Person;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.extern.slf4j.Slf4j;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

@Slf4j
public class GenerateAppsCSV {
    public static void export(List<Application> apps) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, InterruptedException{

        Writer writer  = new FileWriter("src/main/resources/outputs/apps-generated.csv");

        StatefulBeanToCsv<Application> beanToCsv = new StatefulBeanToCsvBuilder<Application>(writer)
                .withSeparator(',')
                .withLineEnd(CSVWriter.DEFAULT_LINE_END)
                .withOrderedResults(true)
                .build();


        beanToCsv.write(apps);
        beanToCsv.getCapturedExceptions();
        writer.close();
        log.info("Exported apps to CSV");

    }
}
