package com.github.wp17.lina.config;

import com.github.wp17.lina.config.reader.CSVReader;
import com.github.wp17.lina.config.reader.ExcelReader;

import java.io.File;
import java.io.IOException;

public class ConfigApplication {
    private static File excelDir = new File("D:\\project\\lina\\config\\excel");
    private static File csvDir = new File("D:\\project\\lina\\config\\csv");
    private static String basePackage = "com.github.wp17.lina.config.template";
    public static void main(String[] args) throws IOException {
        if (args.length != 0) {
            excelDir = new File(args[0]);
            basePackage = args[1];
        }
        genClass();
    }

    public static void genClass() throws IOException {
        CSVReader.generateClass(csvDir, "common.csv", basePackage+".CommonTemplate");
        ExcelReader.generateClass(excelDir, "Lottery.xlsx", basePackage+".LotteryTemplate");
    }
}
