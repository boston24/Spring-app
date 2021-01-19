package com.javaEE.project.csvreaders;

import com.javaEE.project.domain.Person;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class CSVReaderPersons {
    public List readCSV() throws FileNotFoundException{
        String filename = "src/main/resources/Persons.csv";
        FileReader reader = new FileReader(filename);
        CsvToBean<Person> csvtobean = new CsvToBeanBuilder<Person>(reader).withType(Person.class).withSeparator(',').withIgnoreLeadingWhiteSpace(true).build();
        return csvtobean.parse();
    }

    public List readCSVOnUpload(String source) throws FileNotFoundException{
        String filename = source;
        FileReader reader = new FileReader(filename);
        CsvToBean<Person> csvtobean = new CsvToBeanBuilder<Person>(reader).withType(Person.class).withSeparator(',').withIgnoreLeadingWhiteSpace(true).build();
        return csvtobean.parse();
    }

}
