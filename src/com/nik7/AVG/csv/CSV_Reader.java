package com.nik7.AVG.csv;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Nicolò on 10/01/2015.
 *
 * @author Nicolò
 */
public class CSV_Reader {

    private static CSV_Reader me = null;
    private BufferedReader inputFile = null;

    private CSV_Reader(String inputFileName) throws FileNotFoundException {

        this.inputFile = new BufferedReader(new FileReader(inputFileName));
    }

    public static CSV_Reader getInstance(String inputFileName) throws FileNotFoundException {

        if (me == null) {
            me = new CSV_Reader(inputFileName);
        }

        return me;
    }

    public String[] getNextLine() throws IOException {

        String line;
        String[] result = null;

        if ((line = inputFile.readLine()) != null) {

            String csvSplit = ";";
            result = line.split(csvSplit);

            result[1] = result[1].replace(',', '.');

        } else {

            if (inputFile != null) {
                inputFile.close();
            }
        }

        return result;
    }

}
