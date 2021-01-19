package com.javaEE.project.csvreaders;

import com.javaEE.project.domain.Application;
import com.javaEE.project.domain.Person;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class CSVReaderApplications {
    public List readCSV() throws FileNotFoundException {
        String filename = "src/main/resources/Domains.csv";
        FileReader reader = new FileReader(filename);
        CsvToBean<Application> csvtobean = new CsvToBeanBuilder<Application>(reader).withType(Application.class).withSeparator(',').withIgnoreLeadingWhiteSpace(true).build();
        return csvtobean.parse();
    }

    public List readCSVOnUpload(String source) throws FileNotFoundException{
        String filename = source;
        FileReader reader = new FileReader(filename);
        CsvToBean<Application> csvtobean = new CsvToBeanBuilder<Application>(reader).withType(Application.class).withSeparator(',').withIgnoreLeadingWhiteSpace(true).build();
        return csvtobean.parse();
    }

}
