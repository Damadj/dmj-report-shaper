package dmj.report.shaper.core;

import dmj.report.shaper.model.CRMReport;
import dmj.report.shaper.util.TextFormatter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelLogic {

    public List<CRMReport> parseCompaniesReport(String filePath) {
        List<CRMReport> result = new ArrayList<>();
        TextFormatter formatter = new TextFormatter();
        try {

            FileInputStream excelFile = new FileInputStream(filePath);

            Workbook workbook = new XSSFWorkbook(excelFile);

            Sheet datatypeSheet = workbook.getSheetAt(0);
            for (Row nextRow : datatypeSheet) {
                if (nextRow.getRowNum() != 0) {

                    Iterator<Cell> cellIterator = nextRow.cellIterator();
                    CRMReport report = new CRMReport();

                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        int columnIndex = cell.getColumnIndex();
                        if (columnIndex == 2)
                            report.setId((long) cell.getNumericCellValue());
                        else if (columnIndex == 38)
                            report.setName(formatter.formatTextName(cell.getStringCellValue()));
                    }
                    result.add(report);
                }
            }

            workbook.close();
            excelFile.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void shapeFinalReport(String filePath, List<CRMReport> reports) {

        try {
            FileInputStream excelFile = new FileInputStream(filePath);
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() != 0) {

                    Iterator<Cell> cellIterator = row.cellIterator();

                    while (cellIterator.hasNext()) {

                        Cell cell = cellIterator.next();
                        if (cell.getColumnIndex() == 3) {
                            reports.forEach(report -> {
                                if (report.getId() == cell.getNumericCellValue()) {
                                    sheet.getRow(row.getRowNum()).getCell(6).setCellValue(report.getName());
                                }
                            });

                        }
                    }
                }
            }
            FileOutputStream outFile = new FileOutputStream(filePath);
            workbook.write(outFile);
            outFile.close();
            workbook.close();
            excelFile.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void shapeMonthlyReport(String filePath, String month) {
        try {
            FileInputStream excelFile = new FileInputStream(filePath);
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet sheet = workbook.getSheetAt(0);
            boolean isRemovable = false;

            for (int i = 0; i <= sheet.getLastRowNum(); i++) {
                if (i != 0) {
                    Iterator<Cell> cellIterator = sheet.getRow(i).cellIterator();

                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        if (cell.getColumnIndex() == 13) {
                            String q = cell.getStringCellValue();
                            q = q.substring(q.indexOf(".") + 1);
                            if (!q.equals(defineMonth(month))) isRemovable = true;
                        }
                    }
                    if (isRemovable) {
                        int rowIndex = sheet.getRow(i).getRowNum();
                        sheet.removeRow(sheet.getRow(i));
                        int lastRowNum = sheet.getLastRowNum();

                        if (rowIndex >= 0 && rowIndex <= lastRowNum)
                            sheet.shiftRows(rowIndex + 1, lastRowNum, -1);
                        isRemovable = false;
                        i -= 1;
                    }
                }
            }
            FileOutputStream outFile = new FileOutputStream(filePath.substring(0, filePath.indexOf(".")) + "_" + month + ".xlsx");
            workbook.write(outFile);
            outFile.close();
            workbook.close();
            excelFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String defineMonth(String month) {
        switch (month) {
            case "Январь":
                return "01";
            case "Февраль":
                return "02";
            case "Март":
                return "03";
            case "Апрель":
                return "04";
            case "Май":
                return "05";
            case "Июнь":
                return "06";
            case "Июль":
                return "07";
            case "Август":
                return "08";
            case "Сентябрь":
                return "09";
            case "Октябрь":
                return "10";
            case "Ноябрь":
                return "11";
            case "Декабрь":
                return "12";
        }
        return "01";
    }
}
