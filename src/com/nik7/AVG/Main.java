package com.nik7.AVG;

import com.nik7.AVG.csv.CSV_Reader;
import com.nik7.AVG.csv.DataOutput;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

/**
 * This program is thought to calculate the minimum value end the avg with values relative similar
 * to the minimum value itself, in particular if 'u want to to use the "-p" parameter.
 * <p/>
 * Program usage:
 * <br/>
 * <br/>
 * Required parameter:<br/>
 * -i inputFile.csv <br/>
 * <br/>
 * Optionals parameters:<br/>
 * -o outputFile  <em>(If 'u want to specified the output file name)</em><br/>
 * -p  <em>(If 'u want avg without values that are obviously wrong)</em>
 */
public class Main {

    public static void main(String[] args) throws Exception {

        Map<String, DataModel> myData = new TreeMap<>();
        CSV_Reader csvReader = null;
        List<DataOutput> dataOutputs = new LinkedList<>();
        String[] line;
        String inputFileName = null;
        String outputFileName = "output.txt";
        PrintWriter writer;
        boolean purged = false;


        if (args.length < 1) {
            throw new Exception("Error: NO INPUT PARAMETERS");

        } else if (args.length < 3) {
            throw new Exception("Error: NOT ENOUGH INPUT PARAMETERS");
        }

        for (int i = 0; i < args.length; i++) {

            switch (args[i]) {

                case "-i":
                    inputFileName = args[i + 1];
                    break;

                case "-p":
                    purged = true;
                    break;

                case "-o":
                    outputFileName = args[i + 1];
                    break;
            }
        }

        if (inputFileName == null) {
            throw new Exception("Error: NO INPUT FILE!");
        }

        try {
            csvReader = CSV_Reader.getInstance(inputFileName);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        do {
            if (csvReader == null) throw new AssertionError();
            line = csvReader.getNextLine();
            if (line != null) {
                if (!isNumeric(line[1])) {
                    continue;
                }
                String fileName = line[0];
                double cost = new Double(line[1]);
                DataModel thisElement = myData.get(fileName);

                if (thisElement == null) {
                    myData.put(fileName, new DataModel(fileName));
                    thisElement = myData.get(fileName);
                }

                thisElement.addToCostList(cost);
            }

        }
        while (line != null);

        for (DataModel dataModel : myData.values()) {

            double[] avg_min = elaborateCost(dataModel.getCostList(), purged);

            dataOutputs.add(new DataOutput(dataModel.getFileName(), avg_min[0], avg_min[1]));

        }

        writer = new PrintWriter(outputFileName);

        for (DataOutput dataOutput : dataOutputs) {
            writer.println(dataOutput.getFileName());
            writer.println("\t min: " + dataOutput.getMin());
            writer.println("\t avg: " + dataOutput.getAvg());
            writer.println();
        }

        writer.close();
        System.out.println("Done!");


    }

    private static double[] elaborateCost(List<Double> listCost, boolean purged) {

        double[] result = new double[2];

        result[1] = Collections.min(listCost);

        result[0] = 0;
        int size = listCost.size();

        if (purged) {
            for (double v : listCost) {
                if (v / result[1] > 100000) {
                    size--;
                }
            }
        }

        for (Double v : listCost) {

            if (!(v / result[1] > 100000) || !purged) {
                result[0] += (v / size);
            }
        }

        return result;


    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }


}
