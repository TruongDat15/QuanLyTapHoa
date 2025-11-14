//package com.example.InventoryService.helper;
//
//import com.example.InventoryService.dto.request.ExcelRowRequest;
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//public class ExcelHelper {
//    public static List<ExcelRowRequest> parseExcel(MultipartFile file, List<String> errors) throws IOException {
//        List<ExcelRowRequest> rows = new ArrayList<>();
//
//        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
//            Sheet sheet = workbook.getSheetAt(0);
//            int lastRow = sheet.getLastRowNum();
//
//            for (int i = 1; i <= lastRow; i++) {
//                Row row = sheet.getRow(i);
//                if (row == null) continue;
//
//                ExcelRowRequest dto = new ExcelRowRequest();
//                dto.setRowNumber(i + 1);
//
//                try {
//                    dto.setProductBarCode(getCellString(row.getCell(0)));
//                    dto.setProductName(getCellString(row.getCell(1)));
//                    dto.setProductUnit(getCellString(row.getCell(2)));
//                    dto.setQuantity((int) getCellNumeric(row.getCell(3)));
//                    dto.setUnitPrice(Double.valueOf(getCellNumeric(row.getCell(4))));
//                } catch (Exception e) {
//                    errors.add("Row " + dto.getRowNumber() + ": " + e.getMessage());
//                    continue;
//                }
//
//                // validate cơ bản
//                if (dto.getProductBarCode() == null || dto.getProductBarCode().isBlank())
//                    errors.add("Row " + dto.getRowNumber() + ": productCode rỗng");
//                if (dto.getQuantity() == null || dto.getQuantity() <= 0)
//                    errors.add("Row " + dto.getRowNumber() + ": quantity phải > 0");
//                if (dto.getUnitPrice() == null || dto.getUnitPrice().doubleValue() < 0)
//                    errors.add("Row " + dto.getRowNumber() + ": unitPrice phải >= 0");
//
//                rows.add(dto);
//            }
//        }
//
//        return rows;
//    }
//
//    private static String getCellString(Cell cell) {
//        if (cell == null) return null;
//        cell.setCellType(CellType.STRING);
//        return Optional.ofNullable(cell.getStringCellValue()).map(String::trim).orElse(null);
//    }
//
//    private static double getCellNumeric(Cell cell) {
//        if (cell == null) return 0;
//        if (cell.getCellType() == CellType.NUMERIC) return cell.getNumericCellValue();
//        String str = cell.getStringCellValue().trim();
//        if (str.isEmpty()) return 0;
//        return Double.parseDouble(str);
//    }
//}

package com.example.InventoryService.helper;

import com.example.InventoryService.dto.request.ExcelRowRequest;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExcelHelper {

    public static List<ExcelRowRequest> parseExcel(MultipartFile file, List<String> errors) throws IOException {
        List<ExcelRowRequest> rows = new ArrayList<>();

        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            int lastRow = sheet.getLastRowNum();

            for (int i = 1; i <= lastRow; i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                ExcelRowRequest dto = new ExcelRowRequest();
                dto.setRowNumber(i + 1);

                try {
                    dto.setProductBarCode(getCellStringAlways(row.getCell(0))); // ✅ luôn đọc dạng chuỗi
                    dto.setProductName(getCellString(row.getCell(1)));
                    dto.setProductUnit(getCellString(row.getCell(2)));
                    dto.setQuantity(getCellNumeric(row.getCell(3)).intValue()); // ✅ ép kiểu an toàn
                    dto.setUnitPrice(getCellNumeric(row.getCell(4)));
                } catch (Exception e) {
                    errors.add("Row " + dto.getRowNumber() + ": " + e.getMessage());
                    continue;
                }

                // Validate cơ bản
                if (dto.getProductBarCode() == null || dto.getProductBarCode().isBlank())
                    errors.add("Row " + dto.getRowNumber() + ": productCode rỗng");
                if (dto.getQuantity() == null || dto.getQuantity() <= 0)
                    errors.add("Row " + dto.getRowNumber() + ": quantity phải > 0");
                if (dto.getUnitPrice() == null || dto.getUnitPrice().doubleValue() < 0)
                    errors.add("Row " + dto.getRowNumber() + ": unitPrice phải >= 0");

                rows.add(dto);
            }
        }

        return rows;
    }

    // ✅ Đọc barcode luôn là chuỗi (kể cả nếu ô là kiểu số)
    private static String getCellStringAlways(Cell cell) {
        if (cell == null) return null;

        if (cell.getCellType() == CellType.NUMERIC) {
            // Dùng DecimalFormat để tránh E12
            DecimalFormat df = new DecimalFormat("0");
            return df.format(cell.getNumericCellValue());
        } else {
            cell.setCellType(CellType.STRING);
            return Optional.ofNullable(cell.getStringCellValue()).map(String::trim).orElse(null);
        }
    }

    private static String getCellString(Cell cell) {
        if (cell == null) return null;
        cell.setCellType(CellType.STRING);
        return Optional.ofNullable(cell.getStringCellValue()).map(String::trim).orElse(null);
    }

    private static Double getCellNumeric(Cell cell) {
        if (cell == null) return 0.0;
        if (cell.getCellType() == CellType.NUMERIC) return cell.getNumericCellValue();

        String str = cell.getStringCellValue().trim();
        if (str.isEmpty()) return 0.0;
        return Double.parseDouble(str);
    }
}
