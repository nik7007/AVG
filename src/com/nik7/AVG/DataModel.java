package com.nik7.AVG;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Nicolò on 10/01/2015.
 *
 * @author Nicolò
 */
public class DataModel {

    private String fileName;
    private List<Double> costList;

    public DataModel(String fileName) {
        this.fileName = fileName;
        costList = new LinkedList<>();
    }

    public boolean addToCostList(double cost) {
        return costList.add(cost);
    }

    public String getFileName() {
        return fileName;
    }

    public List<Double> getCostList() {
        return costList;
    }
}
