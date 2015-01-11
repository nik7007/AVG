package com.nik7.AVG.csv;

/**
 * Created by Nicolò on 10/01/2015.
 *
 * @author Nicolò
 */
public class DataOutput {

    private String fileName;
    private double avg;
    private double min;

    public DataOutput(String fileName, double avg, double min) {
        this.fileName = fileName;
        this.avg = avg;
        this.min = min;
    }

    public String getFileName() {
        return fileName;
    }

    public double getAvg() {
        return avg;
    }

    public double getMin() {
        return min;
    }
}
