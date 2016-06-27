package com.sck.engine.importation.convert;

import com.sck.engine.config.ConversionConfig;
import com.sck.engine.model.data.DataField;
import com.sck.engine.model.data.DataRow;
import com.sck.engine.utility.DirectoryFile;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by KINCERS on 12/11/2015.
 */
public class XLSXConverter {

    private FileInputStream fileInputStream;
    private ConversionConfig conversionConfig;

    public XLSXConverter(ConversionConfig conversionConfig) {
        this.conversionConfig = conversionConfig;
    }

    public List<DataRow> convert(DirectoryFile file) throws IOException {
        List<DataRow> dataRowList;

        fileInputStream = new FileInputStream(file.getFilePath().toFile());

        dataRowList = processWorkbook();

        return dataRowList;
    }

    // Get all sheets
    private List<DataRow> processWorkbook() throws IOException {

        List<DataRow> dataRowList = new ArrayList<>();

        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        XSSFSheet sheet;

        int sheetCount = workbook.getNumberOfSheets();

        if(conversionConfig.getGetAllSheets()) {
            Iterator iterator = workbook.sheetIterator();
            while (iterator.hasNext()) {
                dataRowList.addAll(processSheet((XSSFSheet) iterator.next()));
            }
        }else {
            // TODO - Throw an error about a non-existent sheet index or something...
            if(conversionConfig.getSheetIndex() > (sheetCount-1)) {
                sheet = workbook.getSheetAt(0);
            }else {
                sheet = workbook.getSheetAt(conversionConfig.getSheetIndex());
            }
            dataRowList.addAll(processSheet(sheet));
        }

        return dataRowList;
    }

    // Loop over sheets
    private List<DataRow> processSheet(XSSFSheet sheet) {

        List<DataRow> dataRowList = new ArrayList<>();

        String currentSheetName = sheet.getSheetName();
        System.out.println(currentSheetName);

        Iterator<Row> rowIterator = sheet.iterator();

        while(rowIterator.hasNext()) {

            DataRow dataRow = new DataRow();
            dataRow.setParentName(currentSheetName);

            Row row = rowIterator.next();

            Iterator<Cell> cellIterator = row.cellIterator();

            while (cellIterator.hasNext()) {

                Cell cell = cellIterator.next();

                CellReference cellReference = new CellReference(row.getRowNum(), cell.getColumnIndex());

                System.out.print("|"+cellReference.formatAsString().replaceAll("\\$","")+"|");

                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_BOOLEAN:
                        DataField<Boolean> dataFieldBoolean = new DataField<>();
                        System.out.print("[BOOLEAN]"+cell.getBooleanCellValue());
                        dataFieldBoolean.setContent(cell.getBooleanCellValue());
                        dataRow.getFields().add(dataFieldBoolean);

                        break;
                    case Cell.CELL_TYPE_NUMERIC:

                        if (DateUtil.isCellDateFormatted(cell)) {
                            DataField<Date> dataFieldDate = new DataField<>();
                            System.out.print("[DATE]"+cell.getDateCellValue());
                            dataFieldDate.setContent(cell.getDateCellValue());
                            dataRow.getFields().add(dataFieldDate);
                        }else {
                            DataField<Double> dataFieldNumber = new DataField<>();
                            System.out.print("[NUMBER]"+cell.getNumericCellValue());
                            dataFieldNumber.setContent(cell.getNumericCellValue());
                            dataRow.getFields().add(dataFieldNumber);
                        }

                        break;
                    case Cell.CELL_TYPE_STRING:
                        DataField<String> dataFieldString = new DataField<>();
                        System.out.print("[STRING]"+cell.getStringCellValue());
                        dataFieldString.setContent(cell.getStringCellValue());
                        dataRow.getFields().add(dataFieldString);

                        break;

                    case Cell.CELL_TYPE_BLANK:
                        DataField<String> dataFieldEmpty = new DataField<>();
                        System.out.print("[EMPTY]");
                        dataFieldEmpty.setEmpty(true);
                        break;
                    case Cell.CELL_TYPE_FORMULA:

                        String cellFormula = cell.getCellFormula();
                        System.out.print("[FORMULA]"+cellFormula);

                        switch (cell.getCachedFormulaResultType()) {
                            case Cell.CELL_TYPE_BOOLEAN:
                                DataField<Boolean> dataFieldBooleanFormula = new DataField<>();
                                System.out.print("[BOOLEAN]"+cell.getBooleanCellValue());
                                dataFieldBooleanFormula.setContent(cell.getBooleanCellValue());
                                dataFieldBooleanFormula.setContentFormula(cellFormula);
                                dataRow.getFields().add(dataFieldBooleanFormula);

                                break;
                            case Cell.CELL_TYPE_NUMERIC:

                                if (DateUtil.isCellDateFormatted(cell)) {
                                    DataField<Date> dataFieldDateFormula = new DataField<>();
                                    System.out.print("[DATE]"+cell.getDateCellValue());
                                    dataFieldDateFormula.setContent(cell.getDateCellValue());
                                    dataFieldDateFormula.setContentFormula(cellFormula);
                                    dataRow.getFields().add(dataFieldDateFormula);
                                }else {
                                    DataField<Double> dataFieldNumberFormula = new DataField<>();
                                    System.out.print("[NUMBER]"+cell.getNumericCellValue());
                                    dataFieldNumberFormula.setContent(cell.getNumericCellValue());
                                    dataFieldNumberFormula.setContentFormula(cellFormula);
                                    dataRow.getFields().add(dataFieldNumberFormula);
                                }

                                break;
                            case Cell.CELL_TYPE_STRING:
                                DataField<String> dataFieldStringFormula = new DataField<>();
                                System.out.print("[STRING]"+cell.getStringCellValue());
                                dataFieldStringFormula.setContent(cell.getStringCellValue());
                                dataFieldStringFormula.setContentFormula(cellFormula);
                                dataRow.getFields().add(dataFieldStringFormula);

                                break;

                            case Cell.CELL_TYPE_BLANK:
                                DataField<String> dataFieldEmptyFormula = new DataField<>();
                                System.out.print("[EMPTY]");
                                dataFieldEmptyFormula.setContentFormula(cellFormula);
                                dataFieldEmptyFormula.setEmpty(true);
                                break;

                            default:
                                DataField<String> dataFieldUnknownFormula = new DataField<>();
                                System.out.print("[UNKNOWN]");
                                dataFieldUnknownFormula.setContentFormula(cellFormula);
                                dataFieldUnknownFormula.setContent("[UNKNOWN]");
                                dataFieldUnknownFormula.setEmpty(true);
                                break;
                        }

                        break;
                    default:
                        DataField<String> dataFieldUnknown = new DataField<>();
                        System.out.print("[UNKNOWN]");
                        dataFieldUnknown.setContent("[UNKNOWN]");
                        dataFieldUnknown.setEmpty(true);
                        break;

                }

                System.out.print(", ");
            }

            System.out.println();

            // Make sure the whole row isnt empty
            for(DataField field : dataRow.getFields()) {
                if(!field.isEmpty()) {
                    dataRowList.add(dataRow);
                    break;
                }
            }

        }

        return dataRowList;
    }

}
