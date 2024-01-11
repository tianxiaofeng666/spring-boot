package com.example.demo.service;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.alibaba.fastjson.JSON;
import com.example.demo.config.MinioProperties;
import com.example.demo.constant.DateTimeConstant;
import com.example.demo.entity.Student;
import com.example.demo.entity.Teacher;
import com.example.demo.model.response.ExportStudentCodeResp;
import com.example.demo.model.response.QueryAreaInfoResp;
import com.example.demo.model.vo.ExcelCityCellRangeVo;
import com.example.demo.model.vo.ExcelCityDataValidationVo;
import com.example.demo.util.*;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author shs-cyhlwzytxf
 */
@Slf4j
@Service
public class ExportService {

    @Resource
    private HttpServletResponse response;

    public void exportExcel(){
        try{
            List<Student> list = new ArrayList<>();
            Student stu1 = new Student("张三","男","111222");
            Student stu2 = new Student("李四","女","3334445");
            list.add(stu1);
            list.add(stu2);
            ExcelUtils.exportExcel(list,"学生信息","学生信息", Student.class, String.valueOf(System.currentTimeMillis()),response);
        }catch (Exception e){
            log.error("报错");
        }
    }

    public void exportExcelZip(){
        List<Student> list = new ArrayList<>();
        Student stu1 = new Student("张三","男","111222");
        Student stu2 = new Student("李四","女","3334445");
        list.add(stu1);
        list.add(stu2);
        ExportParams exportParams = new ExportParams("学生信息", "学生信息", ExcelType.XSSF);
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, Student.class, list);
        try{
            ExportZipUtil.exportExcelZip(workbook,response);
        }catch (Exception e){
            log.error("报错");
        }
    }

    public void exportQrCodeExcel(){
        List<String> imgList = Arrays.asList("STU97ac35b68c0740d1a-031420193005041626.png",
                "STUf927cbb4a0ac44b1a-3101012010320200133.png",
                "STU97ac35b68c0740d1a-031420193005041626.png",
                "STU97ac35b68c0740d1a-031420193005041626.png",
                "STU97ac35b68c0740d1a-031420193005041626.png");
        //由于按两列展示，按步长为2拆分成多个小集合
        List<List<String>> list = Lists.partition(imgList, 2);
        List<ExportStudentCodeResp> respList = new ArrayList<>();
        list.forEach(item -> {
            ExportStudentCodeResp resp = new ExportStudentCodeResp();
            if(item.size() == 2){
                InputStream inputStream1 = CommonFileUtils.getObject(MinioProperties.getBucketName(),item.get(0));
                resp.setInputStream1(inputStream1);
                InputStream inputStream2 = CommonFileUtils.getObject(MinioProperties.getBucketName(),item.get(1));
                resp.setInputStream2(inputStream2);
            }else{
                InputStream inputStream = CommonFileUtils.getObject(MinioProperties.getBucketName(),item.get(0));
                resp.setInputStream1(inputStream);
            }
            respList.add(resp);
        });
        try {
            ExportZipUtil.exportQrCodeExcel(respList,response);
        }catch (Exception e){
            log.error("报错");
        }
    }

    public void exportQrCodeMultiSheetExcel(){
        Map<String,Map<String,String>> imgMap = new LinkedHashMap<>();
        Map<String,String> map1 = new HashMap();
        map1.put("测试11","STU97ac35b68c0740d1a-031420193005041626.png");
        map1.put("测试12","STUf927cbb4a0ac44b1a-3101012010320200133.png");
        imgMap.put("一（1）班",map1);
        Map<String,String> map2 = new HashMap();
        map2.put("测试21","STU97ac35b68c0740d1a-031420193005041626.png");
        imgMap.put("一（2）班",map2);
        Map<String,String> map3 = new HashMap();
        map3.put("测试31","STU97ac35b68c0740d1a-031420193005041626.png");
        map3.put("测试32","STUf927cbb4a0ac44b1a-3101012010320200133.png");
        map3.put("测试33","STUf927cbb4a0ac44b1a-3101012010320200133.png");
        imgMap.put("一（3）班",map3);
        Map<String,List<ExportStudentCodeResp>> sheetMap = new LinkedHashMap<>();
        for (Map.Entry<String, Map<String, String>> entry : imgMap.entrySet()){
            String className = entry.getKey();
            Map<String, String> stuInfos = entry.getValue();
            List<ExportStudentCodeResp> classImgList = new ArrayList<>();
            List<String> imgList = stuInfos.values().stream().collect(Collectors.toList());
            List<List<String>> list = Lists.partition(imgList, 2);
            list.forEach(item -> {
                ExportStudentCodeResp resp = new ExportStudentCodeResp();
                if(item.size() == 2){
                    InputStream inputStream1 = CommonFileUtils.getObject(MinioProperties.getBucketName(),item.get(0));
                    resp.setInputStream1(inputStream1);
                    InputStream inputStream2 = CommonFileUtils.getObject(MinioProperties.getBucketName(),item.get(1));
                    resp.setInputStream2(inputStream2);
                }else{
                    InputStream inputStream = CommonFileUtils.getObject(MinioProperties.getBucketName(),item.get(0));
                    resp.setInputStream1(inputStream);
                }
                classImgList.add(resp);
            });
            sheetMap.put(className,classImgList);
        }
        try {
            ExportZipUtil.exportQrCodeMultiSheetExcel(sheetMap,response);
        }catch (Exception e){
            log.error("报错");
        }
    }

    /**
     * 导出图片到压缩包
     */
    public void exportImageZip(){
        try{
            //图片存放路径
            List<String> pathList = Arrays.asList("qrCode/31032719910512222X.png","qrCode/411327199105121111.png","2022-06-19/1655643499340023.jpg");
            Map<String,String> headImgMap = new HashMap<>(16);
            for (int i=0; i<pathList.size(); i++){
                //String url = URLDecoder.decode(pathList.get(i), "UTF-8");
                headImgMap.put("张三" + i,pathList.get(i));
            }
            ExportZipUtil.downloadImage(headImgMap,response);
        }catch (Exception e){
            log.error("报错");
        }
    }

    /**
     * 导出图片到压缩包，并分类创建目录
     * 一（1）班，有两个同学有二维码
     * 一（2）班，有一个
     * 一（3）班，有三个
     */
    public void exportImageDirectoryZip(){
        Map<String,Map<String,String>> imgMap = new HashMap<>();
        Map<String,String> map1 = new HashMap();
        map1.put("测试11","STU97ac35b68c0740d1a-031420193005041626.png");
        map1.put("测试12","STUf927cbb4a0ac44b1a-3101012010320200133.png");
        imgMap.put("一（1）班",map1);
        Map<String,String> map2 = new HashMap();
        map2.put("测试21","STU97ac35b68c0740d1a-031420193005041626.png");
        imgMap.put("一（2）班",map2);
        Map<String,String> map3 = new HashMap();
        map3.put("测试31","STU97ac35b68c0740d1a-031420193005041626.png");
        map3.put("测试32","STUf927cbb4a0ac44b1a-3101012010320200133.png");
        map3.put("测试33","STUf927cbb4a0ac44b1a-3101012010320200133.png");
        imgMap.put("一（3）班",map3);
        try{
            ExportZipUtil.downloadImageDirectory(imgMap,response);
        }catch (Exception e){
            log.error("报错");
        }

    }

    /**
     * 导出excel和图片到压缩包
     */
    public void exportExcelAndImageZip(){
        try{
            List<Student> list = new ArrayList<>();
            Student stu1 = new Student("张三","男","111222");
            Student stu2 = new Student("李四","女","3334445");
            list.add(stu1);
            list.add(stu2);
            List<String> pathList = Arrays.asList("2023-03-22/1679447956238108.jpg","2023-03-22/1679448263959621.jpg");
            Map<String,String> headImgMap = new HashMap<>(16);
            for (int i=0; i<pathList.size(); i++){
                headImgMap.put("张三" + i,pathList.get(i));
            }
            ExportZipUtil.downLoadExcelAndImage(list,"学生信息","学生信息",Student.class, LocalDateTime.now().format(DateTimeConstant.EXPORT_FILENAME_TIME_FORMAT), headImgMap,response);
        }catch (Exception e){
            log.error("报错");
        }
    }

    /**
     * 下载模板
     */
    public void downloadTemplate(){
        try{
            List<Teacher> list = new ArrayList<>();
            Map<Integer, String[]> selectMap = new HashMap<>(16);
            selectMap.put(1,new String[]{"男","女"});
            ExportParams exportParams = new ExportParams("教师", "教师", ExcelType.XSSF);
            exportParams.setStyle(CustomExcelExportStyler.class);
            Workbook workbook = ExcelExportUtil.exportExcel(exportParams, Teacher.class, list);
            for (Map.Entry<Integer, String[]> entry : selectMap.entrySet()) {
                int column = entry.getKey();
                String[] strings = entry.getValue();
                ExcelSelectListUtil.selectList(workbook, column, column, strings);
            }
            //设置省市县级联
            ExcelCityLinkageUtil.createExcelCitySelect(workbook, getTeacherCellRangeList(), getTeacherDataValidationList(), getAreaList());
            ExcelUtils.downLoadExcel("1111", response, workbook);
        }catch (Exception e){
            log.error("下载模板报错");
        }
    }

    /**
     * 需要校验的省份规则，起始行，终止行，起始列，终止列(省份下拉框所在列)
     * @return
     */
    private List<Map<Integer, List<ExcelCityCellRangeVo>>> getTeacherCellRangeList() {
        List<Map<Integer, List<ExcelCityCellRangeVo>>> list = new ArrayList<>();
        Map<Integer, List<ExcelCityCellRangeVo>> dataMap = new HashMap<>();
        List<ExcelCityCellRangeVo> excelCityCellRangeList = new ArrayList<>();
        excelCityCellRangeList.add(new ExcelCityCellRangeVo(3, 10000, 2, 2));
        dataMap.put(0, excelCityCellRangeList);
        list.add(dataMap);
        return list;
    }

    /**
     * offset：省市所在的位置
     * colNum: 级联影响到的列的位置，省影响市，市影响县
     * @return
     */
    private List<Map<Integer, List<ExcelCityDataValidationVo>>> getTeacherDataValidationList() {
        List<Map<Integer, List<ExcelCityDataValidationVo>>> list = new ArrayList<>();
        Map<Integer, List<ExcelCityDataValidationVo>> dataMap = new HashMap<>();
        List<ExcelCityDataValidationVo> excelCityCellRangeList = new ArrayList<>();
        excelCityCellRangeList.add(new ExcelCityDataValidationVo("C", 4));
        excelCityCellRangeList.add(new ExcelCityDataValidationVo("D", 5));
        dataMap.put(0, excelCityCellRangeList);
        list.add(dataMap);
        return list;
    }

    private List<QueryAreaInfoResp> getAreaList(){
        String areaStr = "[{\"adcode\":\"410000\",\"children\":[{\"adcode\":\"411300\",\"children\":[{\"adcode\":\"411303\",\"level\":\"district\",\"name\":\"卧龙区\",\"parentAdcode\":\"411300\"},{\"adcode\":\"411323\",\"level\":\"district\",\"name\":\"西峡县\",\"parentAdcode\":\"411300\"},{\"adcode\":\"411328\",\"level\":\"district\",\"name\":\"唐河县\",\"parentAdcode\":\"411300\"},{\"adcode\":\"411321\",\"level\":\"district\",\"name\":\"南召县\",\"parentAdcode\":\"411300\"},{\"adcode\":\"411324\",\"level\":\"district\",\"name\":\"镇平县\",\"parentAdcode\":\"411300\"},{\"adcode\":\"411326\",\"level\":\"district\",\"name\":\"淅川县\",\"parentAdcode\":\"411300\"},{\"adcode\":\"411325\",\"level\":\"district\",\"name\":\"内乡县\",\"parentAdcode\":\"411300\"},{\"adcode\":\"411302\",\"level\":\"district\",\"name\":\"宛城区\",\"parentAdcode\":\"411300\"},{\"adcode\":\"411329\",\"level\":\"district\",\"name\":\"新野县\",\"parentAdcode\":\"411300\"},{\"adcode\":\"411330\",\"level\":\"district\",\"name\":\"桐柏县\",\"parentAdcode\":\"411300\"},{\"adcode\":\"411381\",\"level\":\"district\",\"name\":\"邓州市\",\"parentAdcode\":\"411300\"},{\"adcode\":\"411322\",\"level\":\"district\",\"name\":\"方城县\",\"parentAdcode\":\"411300\"},{\"adcode\":\"411327\",\"level\":\"district\",\"name\":\"社旗县\",\"parentAdcode\":\"411300\"}],\"level\":\"city\",\"name\":\"南阳市\",\"parentAdcode\":\"410000\"},{\"adcode\":\"410100\",\"children\":[{\"adcode\":\"410185\",\"level\":\"district\",\"name\":\"登封市\",\"parentAdcode\":\"410100\"},{\"adcode\":\"410184\",\"level\":\"district\",\"name\":\"新郑市\",\"parentAdcode\":\"410100\"},{\"adcode\":\"410106\",\"level\":\"district\",\"name\":\"上街区\",\"parentAdcode\":\"410100\"},{\"adcode\":\"410122\",\"level\":\"district\",\"name\":\"中牟县\",\"parentAdcode\":\"410100\"},{\"adcode\":\"410181\",\"level\":\"district\",\"name\":\"巩义市\",\"parentAdcode\":\"410100\"},{\"adcode\":\"410108\",\"level\":\"district\",\"name\":\"惠济区\",\"parentAdcode\":\"410100\"},{\"adcode\":\"410104\",\"level\":\"district\",\"name\":\"管城回族区\",\"parentAdcode\":\"410100\"},{\"adcode\":\"410105\",\"level\":\"district\",\"name\":\"金水区\",\"parentAdcode\":\"410100\"},{\"adcode\":\"410102\",\"level\":\"district\",\"name\":\"中原区\",\"parentAdcode\":\"410100\"},{\"adcode\":\"410103\",\"level\":\"district\",\"name\":\"二七区\",\"parentAdcode\":\"410100\"},{\"adcode\":\"410183\",\"level\":\"district\",\"name\":\"新密市\",\"parentAdcode\":\"410100\"},{\"adcode\":\"410182\",\"level\":\"district\",\"name\":\"荥阳市\",\"parentAdcode\":\"410100\"}],\"level\":\"city\",\"name\":\"郑州市\",\"parentAdcode\":\"410000\"},{\"adcode\":\"411200\",\"children\":[{\"adcode\":\"411224\",\"level\":\"district\",\"name\":\"卢氏县\",\"parentAdcode\":\"411200\"},{\"adcode\":\"411202\",\"level\":\"district\",\"name\":\"湖滨区\",\"parentAdcode\":\"411200\"},{\"adcode\":\"411221\",\"level\":\"district\",\"name\":\"渑池县\",\"parentAdcode\":\"411200\"},{\"adcode\":\"411281\",\"level\":\"district\",\"name\":\"义马市\",\"parentAdcode\":\"411200\"},{\"adcode\":\"411203\",\"level\":\"district\",\"name\":\"陕州区\",\"parentAdcode\":\"411200\"},{\"adcode\":\"411282\",\"level\":\"district\",\"name\":\"灵宝市\",\"parentAdcode\":\"411200\"}],\"level\":\"city\",\"name\":\"三门峡市\",\"parentAdcode\":\"410000\"},{\"adcode\":\"410400\",\"children\":[{\"adcode\":\"410403\",\"level\":\"district\",\"name\":\"卫东区\",\"parentAdcode\":\"410400\"},{\"adcode\":\"410423\",\"level\":\"district\",\"name\":\"鲁山县\",\"parentAdcode\":\"410400\"},{\"adcode\":\"410411\",\"level\":\"district\",\"name\":\"湛河区\",\"parentAdcode\":\"410400\"},{\"adcode\":\"410481\",\"level\":\"district\",\"name\":\"舞钢市\",\"parentAdcode\":\"410400\"},{\"adcode\":\"410422\",\"level\":\"district\",\"name\":\"叶县\",\"parentAdcode\":\"410400\"},{\"adcode\":\"410402\",\"level\":\"district\",\"name\":\"新华区\",\"parentAdcode\":\"410400\"},{\"adcode\":\"410404\",\"level\":\"district\",\"name\":\"石龙区\",\"parentAdcode\":\"410400\"},{\"adcode\":\"410482\",\"level\":\"district\",\"name\":\"汝州市\",\"parentAdcode\":\"410400\"},{\"adcode\":\"410421\",\"level\":\"district\",\"name\":\"宝丰县\",\"parentAdcode\":\"410400\"},{\"adcode\":\"410425\",\"level\":\"district\",\"name\":\"郏县\",\"parentAdcode\":\"410400\"}],\"level\":\"city\",\"name\":\"平顶山市\",\"parentAdcode\":\"410000\"},{\"adcode\":\"411500\",\"children\":[{\"adcode\":\"411521\",\"level\":\"district\",\"name\":\"罗山县\",\"parentAdcode\":\"411500\"},{\"adcode\":\"411527\",\"level\":\"district\",\"name\":\"淮滨县\",\"parentAdcode\":\"411500\"},{\"adcode\":\"411524\",\"level\":\"district\",\"name\":\"商城县\",\"parentAdcode\":\"411500\"},{\"adcode\":\"411502\",\"level\":\"district\",\"name\":\"浉河区\",\"parentAdcode\":\"411500\"},{\"adcode\":\"411525\",\"level\":\"district\",\"name\":\"固始县\",\"parentAdcode\":\"411500\"},{\"adcode\":\"411503\",\"level\":\"district\",\"name\":\"平桥区\",\"parentAdcode\":\"411500\"},{\"adcode\":\"411526\",\"level\":\"district\",\"name\":\"潢川县\",\"parentAdcode\":\"411500\"},{\"adcode\":\"411523\",\"level\":\"district\",\"name\":\"新县\",\"parentAdcode\":\"411500\"},{\"adcode\":\"411522\",\"level\":\"district\",\"name\":\"光山县\",\"parentAdcode\":\"411500\"},{\"adcode\":\"411528\",\"level\":\"district\",\"name\":\"息县\",\"parentAdcode\":\"411500\"}],\"level\":\"city\",\"name\":\"信阳市\",\"parentAdcode\":\"410000\"},{\"adcode\":\"410300\",\"children\":[{\"adcode\":\"410323\",\"level\":\"district\",\"name\":\"新安县\",\"parentAdcode\":\"410300\"},{\"adcode\":\"410324\",\"level\":\"district\",\"name\":\"栾川县\",\"parentAdcode\":\"410300\"},{\"adcode\":\"410307\",\"level\":\"district\",\"name\":\"偃师区\",\"parentAdcode\":\"410300\"},{\"adcode\":\"410304\",\"level\":\"district\",\"name\":\"瀍河回族区\",\"parentAdcode\":\"410300\"},{\"adcode\":\"410311\",\"level\":\"district\",\"name\":\"洛龙区\",\"parentAdcode\":\"410300\"},{\"adcode\":\"410325\",\"level\":\"district\",\"name\":\"嵩县\",\"parentAdcode\":\"410300\"},{\"adcode\":\"410305\",\"level\":\"district\",\"name\":\"涧西区\",\"parentAdcode\":\"410300\"},{\"adcode\":\"410329\",\"level\":\"district\",\"name\":\"伊川县\",\"parentAdcode\":\"410300\"},{\"adcode\":\"410308\",\"level\":\"district\",\"name\":\"孟津区\",\"parentAdcode\":\"410300\"},{\"adcode\":\"410303\",\"level\":\"district\",\"name\":\"西工区\",\"parentAdcode\":\"410300\"},{\"adcode\":\"410302\",\"level\":\"district\",\"name\":\"老城区\",\"parentAdcode\":\"410300\"},{\"adcode\":\"410326\",\"level\":\"district\",\"name\":\"汝阳县\",\"parentAdcode\":\"410300\"},{\"adcode\":\"410327\",\"level\":\"district\",\"name\":\"宜阳县\",\"parentAdcode\":\"410300\"},{\"adcode\":\"410328\",\"level\":\"district\",\"name\":\"洛宁县\",\"parentAdcode\":\"410300\"}],\"level\":\"city\",\"name\":\"洛阳市\",\"parentAdcode\":\"410000\"},{\"adcode\":\"419001\",\"level\":\"city\",\"name\":\"济源市\",\"parentAdcode\":\"410000\"},{\"adcode\":\"411400\",\"children\":[{\"adcode\":\"411423\",\"level\":\"district\",\"name\":\"宁陵县\",\"parentAdcode\":\"411400\"},{\"adcode\":\"411481\",\"level\":\"district\",\"name\":\"永城市\",\"parentAdcode\":\"411400\"},{\"adcode\":\"411422\",\"level\":\"district\",\"name\":\"睢县\",\"parentAdcode\":\"411400\"},{\"adcode\":\"411426\",\"level\":\"district\",\"name\":\"夏邑县\",\"parentAdcode\":\"411400\"},{\"adcode\":\"411425\",\"level\":\"district\",\"name\":\"虞城县\",\"parentAdcode\":\"411400\"},{\"adcode\":\"411424\",\"level\":\"district\",\"name\":\"柘城县\",\"parentAdcode\":\"411400\"},{\"adcode\":\"411421\",\"level\":\"district\",\"name\":\"民权县\",\"parentAdcode\":\"411400\"},{\"adcode\":\"411402\",\"level\":\"district\",\"name\":\"梁园区\",\"parentAdcode\":\"411400\"},{\"adcode\":\"411403\",\"level\":\"district\",\"name\":\"睢阳区\",\"parentAdcode\":\"411400\"}],\"level\":\"city\",\"name\":\"商丘市\",\"parentAdcode\":\"410000\"},{\"adcode\":\"410200\",\"children\":[{\"adcode\":\"410221\",\"level\":\"district\",\"name\":\"杞县\",\"parentAdcode\":\"410200\"},{\"adcode\":\"410205\",\"level\":\"district\",\"name\":\"禹王台区\",\"parentAdcode\":\"410200\"},{\"adcode\":\"410212\",\"level\":\"district\",\"name\":\"祥符区\",\"parentAdcode\":\"410200\"},{\"adcode\":\"410222\",\"level\":\"district\",\"name\":\"通许县\",\"parentAdcode\":\"410200\"},{\"adcode\":\"410203\",\"level\":\"district\",\"name\":\"顺河回族区\",\"parentAdcode\":\"410200\"},{\"adcode\":\"410202\",\"level\":\"district\",\"name\":\"龙亭区\",\"parentAdcode\":\"410200\"},{\"adcode\":\"410204\",\"level\":\"district\",\"name\":\"鼓楼区\",\"parentAdcode\":\"410200\"},{\"adcode\":\"410223\",\"level\":\"district\",\"name\":\"尉氏县\",\"parentAdcode\":\"410200\"},{\"adcode\":\"410225\",\"level\":\"district\",\"name\":\"兰考县\",\"parentAdcode\":\"410200\"}],\"level\":\"city\",\"name\":\"开封市\",\"parentAdcode\":\"410000\"},{\"adcode\":\"410600\",\"children\":[{\"adcode\":\"410622\",\"level\":\"district\",\"name\":\"淇县\",\"parentAdcode\":\"410600\"},{\"adcode\":\"410621\",\"level\":\"district\",\"name\":\"浚县\",\"parentAdcode\":\"410600\"},{\"adcode\":\"410603\",\"level\":\"district\",\"name\":\"山城区\",\"parentAdcode\":\"410600\"},{\"adcode\":\"410602\",\"level\":\"district\",\"name\":\"鹤山区\",\"parentAdcode\":\"410600\"},{\"adcode\":\"410611\",\"level\":\"district\",\"name\":\"淇滨区\",\"parentAdcode\":\"410600\"}],\"level\":\"city\",\"name\":\"鹤壁市\",\"parentAdcode\":\"410000\"},{\"adcode\":\"411700\",\"children\":[{\"adcode\":\"411729\",\"level\":\"district\",\"name\":\"新蔡县\",\"parentAdcode\":\"411700\"},{\"adcode\":\"411726\",\"level\":\"district\",\"name\":\"泌阳县\",\"parentAdcode\":\"411700\"},{\"adcode\":\"411728\",\"level\":\"district\",\"name\":\"遂平县\",\"parentAdcode\":\"411700\"},{\"adcode\":\"411725\",\"level\":\"district\",\"name\":\"确山县\",\"parentAdcode\":\"411700\"},{\"adcode\":\"411702\",\"level\":\"district\",\"name\":\"驿城区\",\"parentAdcode\":\"411700\"},{\"adcode\":\"411724\",\"level\":\"district\",\"name\":\"正阳县\",\"parentAdcode\":\"411700\"},{\"adcode\":\"411722\",\"level\":\"district\",\"name\":\"上蔡县\",\"parentAdcode\":\"411700\"},{\"adcode\":\"411727\",\"level\":\"district\",\"name\":\"汝南县\",\"parentAdcode\":\"411700\"},{\"adcode\":\"411723\",\"level\":\"district\",\"name\":\"平舆县\",\"parentAdcode\":\"411700\"},{\"adcode\":\"411721\",\"level\":\"district\",\"name\":\"西平县\",\"parentAdcode\":\"411700\"}],\"level\":\"city\",\"name\":\"驻马店市\",\"parentAdcode\":\"410000\"},{\"adcode\":\"410500\",\"children\":[{\"adcode\":\"410527\",\"level\":\"district\",\"name\":\"内黄县\",\"parentAdcode\":\"410500\"},{\"adcode\":\"410506\",\"level\":\"district\",\"name\":\"龙安区\",\"parentAdcode\":\"410500\"},{\"adcode\":\"410503\",\"level\":\"district\",\"name\":\"北关区\",\"parentAdcode\":\"410500\"},{\"adcode\":\"410581\",\"level\":\"district\",\"name\":\"林州市\",\"parentAdcode\":\"410500\"},{\"adcode\":\"410523\",\"level\":\"district\",\"name\":\"汤阴县\",\"parentAdcode\":\"410500\"},{\"adcode\":\"410502\",\"level\":\"district\",\"name\":\"文峰区\",\"parentAdcode\":\"410500\"},{\"adcode\":\"410522\",\"level\":\"district\",\"name\":\"安阳县\",\"parentAdcode\":\"410500\"},{\"adcode\":\"410526\",\"level\":\"district\",\"name\":\"滑县\",\"parentAdcode\":\"410500\"},{\"adcode\":\"410505\",\"level\":\"district\",\"name\":\"殷都区\",\"parentAdcode\":\"410500\"}],\"level\":\"city\",\"name\":\"安阳市\",\"parentAdcode\":\"410000\"},{\"adcode\":\"411600\",\"children\":[{\"adcode\":\"411681\",\"level\":\"district\",\"name\":\"项城市\",\"parentAdcode\":\"411600\"},{\"adcode\":\"411602\",\"level\":\"district\",\"name\":\"川汇区\",\"parentAdcode\":\"411600\"},{\"adcode\":\"411625\",\"level\":\"district\",\"name\":\"郸城县\",\"parentAdcode\":\"411600\"},{\"adcode\":\"411628\",\"level\":\"district\",\"name\":\"鹿邑县\",\"parentAdcode\":\"411600\"},{\"adcode\":\"411623\",\"level\":\"district\",\"name\":\"商水县\",\"parentAdcode\":\"411600\"},{\"adcode\":\"411603\",\"level\":\"district\",\"name\":\"淮阳区\",\"parentAdcode\":\"411600\"},{\"adcode\":\"411622\",\"level\":\"district\",\"name\":\"西华县\",\"parentAdcode\":\"411600\"},{\"adcode\":\"411621\",\"level\":\"district\",\"name\":\"扶沟县\",\"parentAdcode\":\"411600\"},{\"adcode\":\"411627\",\"level\":\"district\",\"name\":\"太康县\",\"parentAdcode\":\"411600\"},{\"adcode\":\"411624\",\"level\":\"district\",\"name\":\"沈丘县\",\"parentAdcode\":\"411600\"}],\"level\":\"city\",\"name\":\"周口市\",\"parentAdcode\":\"410000\"},{\"adcode\":\"410800\",\"children\":[{\"adcode\":\"410883\",\"level\":\"district\",\"name\":\"孟州市\",\"parentAdcode\":\"410800\"},{\"adcode\":\"410882\",\"level\":\"district\",\"name\":\"沁阳市\",\"parentAdcode\":\"410800\"},{\"adcode\":\"410804\",\"level\":\"district\",\"name\":\"马村区\",\"parentAdcode\":\"410800\"},{\"adcode\":\"410822\",\"level\":\"district\",\"name\":\"博爱县\",\"parentAdcode\":\"410800\"},{\"adcode\":\"410803\",\"level\":\"district\",\"name\":\"中站区\",\"parentAdcode\":\"410800\"},{\"adcode\":\"410825\",\"level\":\"district\",\"name\":\"温县\",\"parentAdcode\":\"410800\"},{\"adcode\":\"410823\",\"level\":\"district\",\"name\":\"武陟县\",\"parentAdcode\":\"410800\"},{\"adcode\":\"410802\",\"level\":\"district\",\"name\":\"解放区\",\"parentAdcode\":\"410800\"},{\"adcode\":\"410821\",\"level\":\"district\",\"name\":\"修武县\",\"parentAdcode\":\"410800\"},{\"adcode\":\"410811\",\"level\":\"district\",\"name\":\"山阳区\",\"parentAdcode\":\"410800\"}],\"level\":\"city\",\"name\":\"焦作市\",\"parentAdcode\":\"410000\"},{\"adcode\":\"410700\",\"children\":[{\"adcode\":\"410704\",\"level\":\"district\",\"name\":\"凤泉区\",\"parentAdcode\":\"410700\"},{\"adcode\":\"410711\",\"level\":\"district\",\"name\":\"牧野区\",\"parentAdcode\":\"410700\"},{\"adcode\":\"410724\",\"level\":\"district\",\"name\":\"获嘉县\",\"parentAdcode\":\"410700\"},{\"adcode\":\"410725\",\"level\":\"district\",\"name\":\"原阳县\",\"parentAdcode\":\"410700\"},{\"adcode\":\"410727\",\"level\":\"district\",\"name\":\"封丘县\",\"parentAdcode\":\"410700\"},{\"adcode\":\"410781\",\"level\":\"district\",\"name\":\"卫辉市\",\"parentAdcode\":\"410700\"},{\"adcode\":\"410726\",\"level\":\"district\",\"name\":\"延津县\",\"parentAdcode\":\"410700\"},{\"adcode\":\"410783\",\"level\":\"district\",\"name\":\"长垣市\",\"parentAdcode\":\"410700\"},{\"adcode\":\"410702\",\"level\":\"district\",\"name\":\"红旗区\",\"parentAdcode\":\"410700\"},{\"adcode\":\"410782\",\"level\":\"district\",\"name\":\"辉县市\",\"parentAdcode\":\"410700\"},{\"adcode\":\"410721\",\"level\":\"district\",\"name\":\"新乡县\",\"parentAdcode\":\"410700\"},{\"adcode\":\"410703\",\"level\":\"district\",\"name\":\"卫滨区\",\"parentAdcode\":\"410700\"}],\"level\":\"city\",\"name\":\"新乡市\",\"parentAdcode\":\"410000\"},{\"adcode\":\"410900\",\"children\":[{\"adcode\":\"410927\",\"level\":\"district\",\"name\":\"台前县\",\"parentAdcode\":\"410900\"},{\"adcode\":\"410923\",\"level\":\"district\",\"name\":\"南乐县\",\"parentAdcode\":\"410900\"},{\"adcode\":\"410926\",\"level\":\"district\",\"name\":\"范县\",\"parentAdcode\":\"410900\"},{\"adcode\":\"410922\",\"level\":\"district\",\"name\":\"清丰县\",\"parentAdcode\":\"410900\"},{\"adcode\":\"410902\",\"level\":\"district\",\"name\":\"华龙区\",\"parentAdcode\":\"410900\"},{\"adcode\":\"410928\",\"level\":\"district\",\"name\":\"濮阳县\",\"parentAdcode\":\"410900\"}],\"level\":\"city\",\"name\":\"濮阳市\",\"parentAdcode\":\"410000\"},{\"adcode\":\"411100\",\"children\":[{\"adcode\":\"411122\",\"level\":\"district\",\"name\":\"临颍县\",\"parentAdcode\":\"411100\"},{\"adcode\":\"411104\",\"level\":\"district\",\"name\":\"召陵区\",\"parentAdcode\":\"411100\"},{\"adcode\":\"411102\",\"level\":\"district\",\"name\":\"源汇区\",\"parentAdcode\":\"411100\"},{\"adcode\":\"411121\",\"level\":\"district\",\"name\":\"舞阳县\",\"parentAdcode\":\"411100\"},{\"adcode\":\"411103\",\"level\":\"district\",\"name\":\"郾城区\",\"parentAdcode\":\"411100\"}],\"level\":\"city\",\"name\":\"漯河市\",\"parentAdcode\":\"410000\"},{\"adcode\":\"411000\",\"children\":[{\"adcode\":\"411003\",\"level\":\"district\",\"name\":\"建安区\",\"parentAdcode\":\"411000\"},{\"adcode\":\"411024\",\"level\":\"district\",\"name\":\"鄢陵县\",\"parentAdcode\":\"411000\"},{\"adcode\":\"411081\",\"level\":\"district\",\"name\":\"禹州市\",\"parentAdcode\":\"411000\"},{\"adcode\":\"411082\",\"level\":\"district\",\"name\":\"长葛市\",\"parentAdcode\":\"411000\"},{\"adcode\":\"411025\",\"level\":\"district\",\"name\":\"襄城县\",\"parentAdcode\":\"411000\"},{\"adcode\":\"411002\",\"level\":\"district\",\"name\":\"魏都区\",\"parentAdcode\":\"411000\"}],\"level\":\"city\",\"name\":\"许昌市\",\"parentAdcode\":\"410000\"}],\"level\":\"province\",\"name\":\"河南省\",\"parentAdcode\":\"000000\"}]";
        return JSON.parseArray(areaStr,QueryAreaInfoResp.class);
    }

    public void multiSheetExport(){
        try{
            List<Map<String, Object>> exportParamList = new ArrayList<>();
            //第一个sheet
            List<Student> stuList = new ArrayList<>();
            Student stu1 = new Student("张三","男","111222");
            Student stu2 = new Student("李四","女","3334445");
            stuList.add(stu1);
            stuList.add(stu2);
            ExportParams stuParams = new ExportParams("学生信息","学生信息", ExcelType.XSSF);
            Map<String, Object> stuMap = new HashMap<>(16);
            stuMap.put("title",stuParams);
            stuMap.put("data",stuList);
            stuMap.put("entity",Student.class);
            exportParamList.add(stuMap);
            //第二个sheet
            List<Teacher> teaList = new ArrayList<>();
            Teacher tea1 = new Teacher("晓峰","男","河南省","南阳市","内乡县");
            Teacher tea2 = new Teacher("哈哈","女","河南省","许昌市","禹州市");
            teaList.add(tea1);
            teaList.add(tea2);
            ExportParams teaParams = new ExportParams("教师工信息","教师工信息",ExcelType.XSSF);
            teaParams.setStyle(CustomExcelExportStyler.class);
            Map<String, Object> teaMap = new HashMap<>(16);
            teaMap.put("title",teaParams);
            teaMap.put("data",teaList);
            teaMap.put("entity",Teacher.class);
            exportParamList.add(teaMap);
            ExcelUtils.multiSheetExport(exportParamList, String.valueOf(System.currentTimeMillis()), response);
        }catch (Exception e){
            log.error("下载报错");
        }
    }
}
