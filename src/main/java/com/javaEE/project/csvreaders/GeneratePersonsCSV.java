package com.javaEE.project.csvreaders;

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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class GeneratePersonsCSV {

    public static void export(List<Person> persons) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, InterruptedException{

        Writer writer  = new FileWriter("src/main/resources/outputs/persons-generated.csv");

        StatefulBeanToCsv<Person> beanToCsv = new StatefulBeanToCsvBuilder<Person>(writer)
                .withSeparator(',')
                .withLineEnd(CSVWriter.DEFAULT_LINE_END)
                .withOrderedResults(true)
                .build();


        beanToCsv.write(persons);
        beanToCsv.getCapturedExceptions();
        writer.close();
        log.info("Exported users to CSV");

}


}
