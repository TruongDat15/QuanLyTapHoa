package com.example.demo.helper;



import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ExcelTemplateHelper {

    public static ByteArrayInputStream createImportTemplate() throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Import Template");

            // Tạo style header
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.WHITE.getIndex());
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);

            // Header
            Row header = sheet.createRow(0);
            String[] headers = {"Barcode", "Tên sản phẩm", "Đơn vị", "Số lượng", "Đơn giá"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = header.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
                sheet.autoSizeColumn(i);
            }

            // Ví dụ mẫu
            Object[][] sampleData = {
                    {"8935001200012", "Bánh Chocopie", "Hộp", 10, 15000},
                    {"8935001300048", "Nước C2", "Chai", 20, 8000}
            };

            int rowIdx = 1;
            for (Object[] dataRow : sampleData) {
                Row row = sheet.createRow(rowIdx++);
                for (int col = 0; col < dataRow.length; col++) {
                    row.createCell(col).setCellValue(String.valueOf(dataRow[col]));
                }
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
}
