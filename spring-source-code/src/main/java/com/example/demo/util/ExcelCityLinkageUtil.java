package com.example.demo.util;

import com.example.demo.model.response.QueryAreaInfoResp;
import com.example.demo.model.vo.ExcelCityCellRangeVo;
import com.example.demo.model.vo.ExcelCityDataValidationVo;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFDataValidationConstraint;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author shs-cyhlwzypf
 * excel创建省市区级联信息
 * @date 2022-01-29
 */
public class ExcelCityLinkageUtil {

    public static void createExcelCitySelect(Workbook book, List<Map<Integer, List<ExcelCityCellRangeVo>>> cellRangeList,
                                             List<Map<Integer, List<ExcelCityDataValidationVo>>> dataValidationList, List<QueryAreaInfoResp> areaList) {

        //  创建一个隐藏的省市区信息sheet并设值
        Sheet hideSheet = book.createSheet("area");
        book.setSheetHidden(book.getSheetIndex(hideSheet), false);
        String[] provinceArr = pushAreaInfoToSheet(book, hideSheet, areaList);

        //  设置省份的校验信息
        setProvinceSelect(book, cellRangeList, provinceArr);

        //  设置下拉框有效性
        setDataValidation(book, dataValidationList);
    }

    /**
     * 设置省市区信息
     * @param book
     * @param hideSheet
     * @param areaList
     */
    public static String[] pushAreaInfoToSheet(Workbook book, Sheet hideSheet, List<QueryAreaInfoResp> areaList) {
        //  设置省市区信息
        List<String> areaFatherNameList = areaList.stream().map(QueryAreaInfoResp::getName).collect(Collectors.toList());
        Map<String,String[]> areaMap = new HashMap<String, String[]>();

        String[] provinceArr = areaList.stream().map(QueryAreaInfoResp::getName).collect(Collectors.toList()).toArray(new String[]{});
        areaList.forEach(provinceItem -> {
            if (provinceItem.getChildren() != null && provinceItem.getChildren().size() > 0) {
                provinceItem.getChildren().forEach(cityItem -> {

                    areaFatherNameList.add(cityItem.getName());
                    areaMap.put(provinceItem.getName(), provinceItem.getChildren().stream().map(QueryAreaInfoResp::getName)
                            .collect(Collectors.toList()).toArray(new String[]{}));

                    if (cityItem.getChildren() != null && cityItem.getChildren().size() > 0) {
                        areaMap.put(cityItem.getName(), cityItem.getChildren().stream().map(QueryAreaInfoResp::getName)
                                .collect(Collectors.toList()).toArray(new String[]{}));
                    }
                });
            }
        });
        String[] areaFatherNameArr = areaFatherNameList.toArray(new String[]{});

        int rowId = 0;
        // 设置第一行，存省的信息
        Row provinceRow = hideSheet.createRow(rowId++);
        provinceRow.createCell(0).setCellValue("省列表");
        for(int i = 0; i < provinceArr.length; i ++){
            Cell provinceCell = provinceRow.createCell(i + 1);
            provinceCell.setCellValue(provinceArr[i]);
        }
        // 将具体的数据写入到每一行中，行开头为父级区域，后面是子区域。
        for(int i = 0;i < areaFatherNameArr.length;i++){
            String key = areaFatherNameArr[i];
            String[] son = areaMap.get(key);
            if (son == null || son.length == 0){ continue;}

            Row row = hideSheet.createRow(rowId++);
            row.createCell(0).setCellValue(key);
            for(int j = 0; j < son.length; j ++){
                Cell cell = row.createCell(j + 1);
                cell.setCellValue(son[j]);
            }

            // 添加名称管理器
            String range = getRange(1, rowId, son.length);
            Name name = book.createName();
            //key不可重复
            name.setNameName(key);
            String formula = "area!" + range;
            name.setRefersToFormula(formula);
        }
        return provinceArr;
    }

    /**
     *
     * @param book
     * @param sheetIndexList
     *  List<Map<sheet的序号, List<ExcelCityCellRangeVo>>>
     * @param provinceArr
     */
    public static void setProvinceSelect(Workbook book, List<Map<Integer, List<ExcelCityCellRangeVo>>> sheetIndexList, String[] provinceArr) {
        if (sheetIndexList != null && sheetIndexList.size() > 0) {
            sheetIndexList.forEach(item -> {
                item.forEach((sheetIndex, excelCityCellRangeList) -> {
                    XSSFSheet sheetPro = (XSSFSheet) book.getSheetAt(sheetIndex);
                    XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper(sheetPro);
                    //  省规则
                    DataValidationConstraint provConstraint = dvHelper.createExplicitListConstraint(provinceArr);

                    if (excelCityCellRangeList != null && excelCityCellRangeList.size() > 0) {
                        excelCityCellRangeList.forEach(excelCityCellRangeVo -> {
                            CellRangeAddressList provRangeAddressList = new CellRangeAddressList(excelCityCellRangeVo.getFirstRow(),
                                    excelCityCellRangeVo.getLastRow(), excelCityCellRangeVo.getFirstCol(), excelCityCellRangeVo.getLastCol());
                            DataValidation provinceDataValidation = dvHelper.createValidation(provConstraint, provRangeAddressList);
                            //  验证
                            provinceDataValidation.createErrorBox("error", "请选择正确的省份");
                            provinceDataValidation.setShowErrorBox(true);
                            provinceDataValidation.setSuppressDropDownArrow(true);
                            sheetPro.addValidationData(provinceDataValidation);
                        });
                    }
                });
            });
        }
    }

    /**
     * 影响单元格所在列，即此单元格由哪个单元格影响联动
     * List<Map<sheet的序号, List<ExcelCityDataValidationVo>>>
     */
    public static void setDataValidation(Workbook book, List<Map<Integer, List<ExcelCityDataValidationVo>>> sheetIndexList) {
        if (sheetIndexList != null) {
            sheetIndexList.forEach(item -> {
                item.forEach((sheetIndex, excelCityDataValidationList) -> {
                    XSSFSheet sheetPro = (XSSFSheet) book.getSheetAt(sheetIndex);

                    if (excelCityDataValidationList != null) {
                        for (int k = 4; k < 2000; k++) {
                            for (int i = 0; i < excelCityDataValidationList.size(); i++) {

                                ExcelCityDataValidationVo vo = excelCityDataValidationList.get(i);
                                XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper(sheetPro);
                                DataValidation dataValidationList = getDataValidationByFormula(
                                        "INDIRECT($" + vo.getOffset() + (k) + ")", k, vo.getColNum(), dvHelper);
                                sheetPro.addValidationData(dataValidationList);
                            }
                        }
                    }
                });
            });
        }
    }

    /**
     * 加载下拉列表内容
     * @param formulaString
     * @param naturalRowIndex
     * @param naturalColumnIndex
     * @param dvHelper
     * @return
     */
    private static DataValidation getDataValidationByFormula(String formulaString, int naturalRowIndex, int naturalColumnIndex,
                                                             XSSFDataValidationHelper dvHelper) {
        // 加载下拉列表内容
        XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint) dvHelper.createFormulaListConstraint(formulaString);

        // 设置数据有效性加载在哪个单元格上，四个参数分别是：起始行、终止行、起始列、终止列
        int firstRow = naturalRowIndex -1;
        int lastRow = naturalRowIndex - 1;
        int firstCol = naturalColumnIndex - 1;
        int lastCol = naturalColumnIndex - 1;
        CellRangeAddressList regions = new CellRangeAddressList(firstRow, lastRow, firstCol, lastCol);

        // 数据有效性对象
        XSSFDataValidation data_validation_list = (XSSFDataValidation) dvHelper.createValidation(dvConstraint, regions);
        data_validation_list.setEmptyCellAllowed(false);
        if (data_validation_list instanceof XSSFDataValidation) {
            data_validation_list.setSuppressDropDownArrow(true);
            data_validation_list.setShowErrorBox(true);
        } else {
            data_validation_list.setSuppressDropDownArrow(false);
        }
        // 设置输入信息提示信息
        data_validation_list.createPromptBox("下拉选择提示", "请使用下拉方式选择合适的值！");
        // 设置输入错误提示信息
        //data_validation_list.createErrorBox("选择错误提示", "你输入的值未在备选列表中，请下拉选择合适的值！");
        return data_validation_list;
    }

    /**
     *  计算formula
     * @param offset 偏移量，如果给0，表示从A列开始，1，就是从B列
     * @param rowId 第几行
     * @param colCount 一共多少列
     * @return 如果给入参 1,1,10. 表示从B1-K1。最终返回 $B$1:$K$1
     *
     */
    public static String getRange(int offset, int rowId, int colCount) {
        char start = (char)('A' + offset);
        if (colCount <= 25) {
            char end = (char)(start + colCount - 1);
            return "$" + start + "$" + rowId + ":$" + end + "$" + rowId;
        } else {
            char endPrefix = 'A';
            char endSuffix = 'A';
            if ((colCount - 25) / 26 == 0 || colCount == 51) {// 26-51之间，包括边界（仅两次字母表计算）
                if ((colCount - 25) % 26 == 0) {// 边界值
                    endSuffix = (char)('A' + 25);
                } else {
                    endSuffix = (char)('A' + (colCount - 25) % 26 - 1);
                }
            } else {// 51以上
                if ((colCount - 25) % 26 == 0) {
                    endSuffix = (char)('A' + 25);
                    endPrefix = (char)(endPrefix + (colCount - 25) / 26 - 1);
                } else {
                    endSuffix = (char)('A' + (colCount - 25) % 26 - 1);
                    endPrefix = (char)(endPrefix + (colCount - 25) / 26);
                }
            }
            return "$" + start + "$" + rowId + ":$" + endPrefix + endSuffix + "$" + rowId;
        }
    }
}
