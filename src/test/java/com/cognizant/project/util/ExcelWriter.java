package com.cognizant.project.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelWriter {
    private static final Logger log = LogManager.getLogger(ExcelWriter.class);
    private static final String FILE_PATH = "target/generatedData.xlsx";

    private static final Workbook workbook = new XSSFWorkbook();
    private static final Sheet hospitalSheet = workbook.createSheet("Hospitals");
    private static final Sheet citiesSheet = workbook.createSheet("Top Cities");

    static {
        // Create header rows once
        hospitalSheet.createRow(0).createCell(0).setCellValue("Hospital Name");
        citiesSheet.createRow(0).createCell(0).setCellValue("City Name");
    }

    public static void writeHospitals(List<String> hospitals) {
        int rowNum = 1;
        for (String hospital : hospitals) {
            hospitalSheet.createRow(rowNum++).createCell(0).setCellValue(hospital);
        }
        log.info("Written " + hospitals.size() + " hospitals to Excel");
        save();
    }

    public static void writeCities(List<String> cities) {
        int rowNum = 1;
        for (String city : cities) {
            citiesSheet.createRow(rowNum++).createCell(0).setCellValue(city);
        }
        log.info("Written " + cities.size() + " cities to Excel");
        save();
    }

    private static void save() {
        try (FileOutputStream fos = new FileOutputStream(FILE_PATH)) {
            workbook.write(fos);
        } catch (IOException e) {
            log.error("Failed to save Excel file: " + e.getMessage());
        }
    }
}