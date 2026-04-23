package com.utils;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtil {

    static final String FILE_PATH = "src/test/resources/TestData.xlsx";

    public static Object[][] getSheetData(String sheetName) {
        List<Object[]> data = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(FILE_PATH);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);

            if (sheet == null) {
                throw new RuntimeException("Sheet '" + sheetName + "' not found in " + FILE_PATH);
            }

            DataFormatter formatter = new DataFormatter();
            int rowCount = sheet.getPhysicalNumberOfRows();

            for (int i = 1; i < rowCount; i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }

                String username = formatter.formatCellValue(row.getCell(0)).trim();
                String password = formatter.formatCellValue(row.getCell(1)).trim();
                String runFlag = formatter.formatCellValue(row.getCell(2)).trim();

                if ("yes".equalsIgnoreCase(runFlag)) {
                    data.add(new Object[]{username, password});
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to read Excel file: " + e.getMessage(), e);
        }

        return data.toArray(new Object[0][]);
    }
}