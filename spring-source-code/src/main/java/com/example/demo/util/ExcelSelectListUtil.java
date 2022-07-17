package com.example.demo.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;

/**
 * @author shs-cyhlwzytxf
 */
public class ExcelSelectListUtil {

    /**
     * 下拉框长度，超过该长度，需要新建sheet来存储
     */
    private static final Integer DROP_DOWN_LENGTH = 30;

    /**
     * 存储下拉框列表值的sheet名称
     */
    private static final String SHEET_NAME = "hidden_";

    /**
     * firstRow 開始行號 根据此项目，默认为2(下标0开始)
     * lastRow  根据此项目，默认为最大65535
     * firstCol 区域中第一个单元格的列号 (下标0开始)
     * lastCol 区域中最后一个单元格的列号
     * strings 下拉内容, 当下拉框内容过多时，需要生成一个sheet来存储下拉列表值
     * */
    public static void selectList(Workbook workbook, int firstCol, int lastCol, String[] strings ){
        Sheet sheet = workbook.getSheetAt(0);
        if(strings.length >= DROP_DOWN_LENGTH){
            //创建下拉列表值存储工作表
            String hiddenName = SHEET_NAME + firstCol;
            Sheet hiddenSheet = workbook.createSheet(hiddenName);
            // 循环往该sheet中设置添加下拉列表的值
            for (int i = 0; i < strings.length; i++) {
                Row row = hiddenSheet.createRow(i);
                Cell cell = row.createCell((int)0);
                cell.setCellValue(strings[i]);
            }
            CellRangeAddressList regions = new CellRangeAddressList(2, 65535, firstCol, lastCol);
            DataValidationHelper dataValidationHelper = hiddenSheet.getDataValidationHelper();
            DataValidationConstraint formulaListConstraint = dataValidationHelper.createFormulaListConstraint(hiddenName + "!$A$1:$A$" + strings.length);
            DataValidation dataValidation = dataValidationHelper.createValidation(formulaListConstraint, regions);
            // 将sheet设置为隐藏
            workbook.setSheetHidden(workbook.getSheetIndex(hiddenName), true);
            sheet.addValidationData(dataValidation);
        }else{
            //  生成下拉列表
            //  只对(x，x)单元格有效
            CellRangeAddressList regions = new CellRangeAddressList(2, 65535, firstCol, lastCol);
            DataValidationHelper dataValidationHelper = sheet.getDataValidationHelper();
            DataValidationConstraint createExplicitListConstraint = dataValidationHelper.createExplicitListConstraint(strings);
            DataValidation dataValidation = dataValidationHelper.createValidation(createExplicitListConstraint, regions);
            //  对sheet页生效
            sheet.addValidationData(dataValidation);
        }

    }

    /**
     * firstRow 開始行號 根据此项目，默认为2(下标0开始)
     * lastRow  根据此项目，默认为最大65535
     * firstCol 区域中第一个单元格的列号 (下标0开始)
     * lastCol 区域中最后一个单元格的列号
     * strings 下拉内容, 当下拉框内容过多时，需要生成一个sheet来存储下拉列表值
     * sheetIndex   sheet 序号
     * */
    public static void selectListSheet(Workbook workbook, int firstCol, int lastCol, String[] strings, int sheetIndex){
        Sheet sheet = workbook.getSheetAt(sheetIndex);
        if(strings.length >= DROP_DOWN_LENGTH) {
            //创建下拉列表值存储工作表
            String hiddenName = SHEET_NAME + firstCol;
            Sheet hiddenSheet = workbook.createSheet(hiddenName);
            // 循环往该sheet中设置添加下拉列表的值
            for (int i = 0; i < strings.length; i++) {
                Row row = hiddenSheet.createRow(i);
                Cell cell = row.createCell((int)0);
                cell.setCellValue(strings[i]);
            }
            CellRangeAddressList regions = new CellRangeAddressList(2, 65535, firstCol, lastCol);
            DataValidationHelper dataValidationHelper = hiddenSheet.getDataValidationHelper();
            DataValidationConstraint formulaListConstraint = dataValidationHelper.createFormulaListConstraint(hiddenName + "!$A$1:$A$" + strings.length);
            DataValidation dataValidation = dataValidationHelper.createValidation(formulaListConstraint, regions);
            // 将sheet设置为隐藏
            workbook.setSheetHidden(workbook.getSheetIndex(hiddenName), true);
            sheet.addValidationData(dataValidation);
        }else{
            //  生成下拉列表
            //  只对(x，x)单元格有效
            CellRangeAddressList regions = new CellRangeAddressList(2, 65535, firstCol, lastCol);
            DataValidationHelper dataValidationHelper = sheet.getDataValidationHelper();
            DataValidationConstraint createExplicitListConstraint = dataValidationHelper.createExplicitListConstraint(strings);
            DataValidation dataValidation = dataValidationHelper.createValidation(createExplicitListConstraint, regions);
            //  对sheet页生效
            sheet.addValidationData(dataValidation);
        }

    }

    /**
     * 创建省市区sheet
     * @param workbook
     * @param firstCol
     * @param lastCol
     * @param strings
     * @param sheetIndex
     */
    public static void createAreaSheet(Workbook workbook, int firstCol, int lastCol, String[] strings, int sheetIndex, int firstRow) {

        Sheet sheet = workbook.getSheetAt(sheetIndex);
        if(strings.length >= DROP_DOWN_LENGTH) {
            //创建下拉列表值存储工作表
            String hiddenName = SHEET_NAME + firstCol;
            Sheet hiddenSheet = workbook.createSheet(hiddenName);
            // 循环往该sheet中设置添加下拉列表的值
            for (int i = 0; i < strings.length; i++) {
                Row row = hiddenSheet.createRow(i);
                Cell cell = row.createCell((int)0);
                cell.setCellValue(strings[i]);
            }
            CellRangeAddressList regions = new CellRangeAddressList(firstRow, 65535, firstCol, lastCol);
            DataValidationHelper dataValidationHelper = hiddenSheet.getDataValidationHelper();
            DataValidationConstraint formulaListConstraint = dataValidationHelper.createFormulaListConstraint(hiddenName + "!$A$1:$A$" + strings.length);
            DataValidation dataValidation = dataValidationHelper.createValidation(formulaListConstraint, regions);
            // 将sheet设置为隐藏
            workbook.setSheetHidden(workbook.getSheetIndex(hiddenName), true);
            sheet.addValidationData(dataValidation);
        } else {
            //  生成下拉列表
            //  只对(x，x)单元格有效
            CellRangeAddressList regions = new CellRangeAddressList(firstRow, 65535, firstCol, lastCol);
            DataValidationHelper dataValidationHelper = sheet.getDataValidationHelper();
            DataValidationConstraint createExplicitListConstraint = dataValidationHelper.createExplicitListConstraint(strings);
            DataValidation dataValidation = dataValidationHelper.createValidation(createExplicitListConstraint, regions);
            //  对sheet页生效
            sheet.addValidationData(dataValidation);

        }
    }

}
