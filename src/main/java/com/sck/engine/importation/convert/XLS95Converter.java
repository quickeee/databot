package com.sck.engine.importation.convert;

import com.sck.engine.config.ConversionConfig;
import com.sck.engine.model.data.DataField;
import com.sck.engine.model.data.DataRow;
import com.sck.engine.utility.DirectoryFile;
import jxl.*;
import jxl.read.biff.BiffException;

import java.io.IOException;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;

/**
 * Created by KINCERS on 12/11/2015.
 */
public class XLS95Converter {


    private ConversionConfig conversionConfig;


    public XLS95Converter(ConversionConfig conversionConfig) {
        this.conversionConfig = conversionConfig;
    }

    public List<DataRow> convert(DirectoryFile file) throws BiffException, IOException {
        List<DataRow> dataRowList;

        Workbook workbook = Workbook.getWorkbook(file.getFilePath().toFile());

        dataRowList = processWorkbook(workbook);

        workbook.close();

        return dataRowList;
    }

    // Get all sheets
    private List<DataRow> processWorkbook(Workbook workbook) throws IOException {

        List<DataRow> dataRowList = new ArrayList<>();

        int sheetCount = workbook.getNumberOfSheets();
        Sheet sheet;

        if(conversionConfig.getGetAllSheets()) {
            for(int i=0;i<sheetCount;i++) {
                sheet = workbook.getSheet(i);
                dataRowList.addAll(processSheet(sheet));
            }

        }else {
            // TODO - Throw an error about a non-existent sheet index or something...
            if(conversionConfig.getSheetIndex() > (sheetCount-1)) {
                sheet = workbook.getSheet(0);
            }else {
                sheet = workbook.getSheet(conversionConfig.getSheetIndex());
            }
            dataRowList.addAll(processSheet(sheet));
        }

        return dataRowList;
    }

    // Loop over sheets
    private List<DataRow> processSheet(Sheet sheet) {

        List<DataRow> dataRowList = new ArrayList<>();

        String currentSheetName = sheet.getName();
        System.out.println(currentSheetName);

        int rowCount = sheet.getRows();

        for(int i=0;i<rowCount;i++) {

            DataRow dataRow = new DataRow();
            dataRow.setParentName(currentSheetName);

            Cell[] cells = sheet.getRow(i);

            for(Cell cell : cells) {

                CellType cellType = cell.getType();

                if(cellType.equals(CellType.BOOLEAN)) {
                    DataField<Boolean> dataFieldBoolean = new DataField<>();
                    BooleanCell booleanCell = (BooleanCell) cell;
                    System.out.print("[BOOLEAN]"+booleanCell.getValue());
                    dataFieldBoolean.setContent(booleanCell.getValue());
                    dataRow.getFields().add(dataFieldBoolean);

                }else if(cellType.equals(CellType.NUMBER)) {
                    DataField<Double> dataFieldNumber = new DataField<>();
                    NumberCell numberCell = (NumberCell) cell;
                    System.out.print("[NUMBER]"+numberCell.getValue());
                    dataFieldNumber.setContent(numberCell.getValue());
                    dataRow.getFields().add(dataFieldNumber);

                }else if(cellType.equals(CellType.DATE)) {
                    DataField<Date> dataFieldDate = new DataField<>();
                    DateCell dateCell = (DateCell) cell;
                    System.out.print("[DATE]"+dateCell.getDate());
                    dataFieldDate.setContent(dateCell.getDate());
                    dataRow.getFields().add(dataFieldDate);

                }else if(cellType.equals(CellType.LABEL)) {
                    DataField<String> dataFieldString = new DataField<>();
                    LabelCell labelCell = (LabelCell) cell;
                    System.out.print("[STRING]"+labelCell.getString());
                    dataFieldString.setContent(labelCell.getString());
                    dataRow.getFields().add(dataFieldString);

                }else if(cellType.equals(CellType.EMPTY)) {
                    DataField<String> dateFieldEmpty = new DataField<>();
                    System.out.print("[EMPTY]");
                    dateFieldEmpty.setEmpty(true);
                    dataRow.getFields().add(dateFieldEmpty);

                }else {
                    DataField<String> dataFieldUnknown = new DataField<>();
                    System.out.print("[UNKNOWN]"+cell.getContents());
                    dataFieldUnknown.setContent("[UNKNOWN]");
                    dataFieldUnknown.setEmpty(true);
                    dataRow.getFields().add(dataFieldUnknown);
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
