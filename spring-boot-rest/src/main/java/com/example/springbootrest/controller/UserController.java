package com.example.springbootrest.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.analysis.ExcelAnalyserImpl;
import com.alibaba.excel.analysis.v07.XlsxRowHandler;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterTableBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.springbootrest.listener.CustomCellWriteHandler;
import com.example.springbootrest.listener.MemberListener;
import com.example.springbootrest.listener.MemberListener11;
import com.example.springbootrest.listener.SelectWriteHandler;
import com.example.springbootrest.pojo.DemoData;
import com.example.springbootrest.pojo.Member;
import com.example.springbootrest.pojo.Policy;
import com.example.springbootrest.qrcode.QRCodeUtil;
import com.example.springbootrest.util.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class UserController {

    private static final String path = "com.example.springbootrest.pojo.";

    @Autowired
    private RestTemplate restTemplate;
    /**
     * 根据companyId查询渠道名称
     */
    @RequestMapping("/queryAllMessage")
    public List<JSONObject> getCompanyNameByCompanyId(@RequestBody JSONObject json) {
        JSONObject result = new JSONObject();
        //String companyId = json.getString("companyId");
        //String companyName = userService.getCompanyNameByCompanyId(companyId);
        String url = "http://10.253.116.46:8789/cs/queryAllMessage.json";
        String response = restTemplate.postForObject(url, json, String.class);
        //ResponseEntity<String> res = restTemplate.postForEntity(url, json, String.class);
        //String body = res.getBody();
        JSONObject obj = JSON.parseObject(response);
        Object content = obj.get("content");
        JSONObject aa = JSON.parseObject(content.toString());
        Object bb = aa.get("msg");
        JSONArray cc = JSON.parseArray(bb.toString());
        List<JSONObject> dd = JSONObject.parseArray(JSONObject.toJSONString(cc), JSONObject.class);
        return dd;
    }

    /**
     * 测试导出,有参考意义
     */
    @RequestMapping("/testExport")
    public String testExport(@RequestBody JSONObject json) {
        String fileName = "F:\\excel\\" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写
        String modelType = json.getString("modelType");
        String modelName = path + modelType;
        Class<?> modelClass = null;
        try {
            modelClass = Class.forName(modelName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return e.getMessage();
        }
        ExcelWriter excelWriter = EasyExcel.write(fileName, modelClass)
                .registerWriteHandler(new CustomCellWriteHandler())
                .build();
        WriteSheet writeSheet = EasyExcel.writerSheet("模板").build();
        List list = new ArrayList();
        if("DemoData".equals(modelType)){
            for(int i=0; i<5; i++){
                DemoData data = new DemoData();
                data.setId(i);
                data.setName("小明");
                data.setAge(18);
                data.setSex("男");
                list.add(data);
            }
        }else{
            for(int i=0; i<10; i++){
                Policy policy = new Policy();
                policy.setId(i);
                policy.setPolicyNO("IH111");
                policy.setOwner("17521046077");
                list.add(policy);
            }
        }
        for(int i=0; i<2; i++){
            List oneList = getDataList(i,list);
            excelWriter.write(oneList, writeSheet);
        }
        /// 千万别忘记finish 会帮忙关闭流
        excelWriter.finish();
        return "导出成功!";
    }

    /**
     * 测试导出,生成excel文件并保存到linux服务器目录下
     */
    @RequestMapping("/testExportExcel")
    public String testExportExcel(@RequestBody JSONObject json) {
        //文件名
        String fileName = System.currentTimeMillis() + ".xlsx";
        //文件路径
        //String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        //创建文件
        File dest = new File(docBase + date + "/",fileName);
        //判断文件父目录是否存在
        if (!dest.getParentFile().exists()) {
            System.out.print("创建上级目录。。。");
            dest.getParentFile().mkdir();
        }
        // 这里 需要指定写用哪个class去写
        String modelType = json.getString("modelType");
        String modelName = path + modelType;
        Class<?> modelClass = null;
        try {
            modelClass = Class.forName(modelName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return e.getMessage();
        }
        ExcelWriter excelWriter = EasyExcel.write(docBase + date + "/" + fileName, modelClass).build();
        WriteSheet writeSheet = EasyExcel.writerSheet("模板").build();
        List list = new ArrayList();
        if("DemoData".equals(modelType)){
            for(int i=0; i<5; i++){
                DemoData data = new DemoData();
                data.setId(i);
                data.setName("小明");
                data.setAge(18);
                data.setSex("男");
                list.add(data);
            }
        }else{
            for(int i=0; i<10; i++){
                Policy policy = new Policy();
                policy.setId(i);
                policy.setPolicyNO("IH111");
                policy.setOwner("17521046077");
                list.add(policy);
            }
        }
        excelWriter.write(list, writeSheet);
        /// 千万别忘记finish 会帮忙关闭流
        excelWriter.finish();
        return "导出成功,路径：" + filePath + date + "/" + fileName;
    }

    private List getDataList(int i, List list) {
        if(i == 0){
           List aa = new ArrayList();
           aa.add(list.get(0));
           aa.add(list.get(1));
           aa.add(list.get(2));
           aa.add(list.get(3));
           aa.add(list.get(4));
           return aa;
        }else{
            List aa = new ArrayList();
            aa.add(list.get(5));
            aa.add(list.get(6));
            aa.add(list.get(7));
            aa.add(list.get(8));
            aa.add(list.get(9));
            return aa;
        }
    }


    /**
     * 异步读取,新建一个监听类
     * @param json
     * @return
     */
    @RequestMapping("testRead")
    public JSONObject testRead(@RequestBody JSONObject json){
        JSONObject result = new JSONObject();
        String fileName = "D:\\excel\\template.xlsx";
        MemberListener11 lister = new MemberListener11();
        ExcelReader excelReader = EasyExcel.read(fileName, Member.class,lister).build();
        ReadSheet readSheet = EasyExcel.readSheet(0).build();
        excelReader.read(readSheet);
        List<Member> data = lister.getList();
        result.put("result","success");
        result.put("content",data);
        // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
        excelReader.finish();
        return result;
    }

    @RequestMapping("testMultipartFile")
    public JSONObject uploadExcel(@RequestParam("file") MultipartFile file) throws IOException {
        JSONObject result = new JSONObject();
        List<Member> data = ExcelImportUtil.readExcel(file, new Member());
        result.put("result","success");
        result.put("content",data);
        return result;
    }

    /**
     * 下载excel模板
     */
    @RequestMapping("/excel/template")
    public String downloadTemplate(HttpServletResponse response) {
        String fileName = "D:\\excel\\" + "导入用户模板" + ".xlsx";
        //String fileName = "导入用户模板";
        String sheetName = "子账户模板";
        List<Member> memberList = new ArrayList<>();
        memberList.add(new Member(111,"子账户1","17521046001"));
        memberList.add(new Member(222,"子账户2","17521046002"));
        try {
            ExcelExportUtil.writeExcel(response, memberList, fileName, sheetName, Member.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "模板下载成功...";
    }

    //导入数据,单sheet,默认第一个sheet(EasyExcel 2.0版本往后读取excel),同步读取，读取所有的数据再进行后续处理
    @RequestMapping("/excel/import")
    public JSONObject importData(@RequestParam("file") MultipartFile file) throws Exception {
        JSONObject res = new JSONObject();
        List<Member> memberList = null;
        memberList = EasyExcel.read(new BufferedInputStream(file.getInputStream())).head(Member.class).sheet().doReadSync();
        //checkImportData(memberList);
        res.put("result","success");
        res.put("content",memberList);
        return res;
    }

    /**
     * 多sheet导入
     */
    @RequestMapping("/excel/importMore")
    public JSONObject importMoreData(@RequestParam("file") MultipartFile file) {
        JSONObject res = new JSONObject();
        List<Member> memberList = new ArrayList<Member>();
        try {
            ExcelReader excelReader = EasyExcel.read(new BufferedInputStream(file.getInputStream())).head(Member.class).build();
            List<ReadSheet> sheetList = excelReader.excelExecutor().sheetList();
            for(ReadSheet sheet : sheetList){
                List<Member> list = new ArrayList<Member>();
                list = EasyExcel.read(new BufferedInputStream(file.getInputStream())).head(Member.class).sheet(sheet.getSheetNo()).doReadSync();
                memberList.addAll(list);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        res.put("result","success");
        res.put("content",memberList);
        return res;
    }

    @Value("${file.docBase}")
    private String docBase;

    @Value("${file.path}")
    private String filePath;

    /**
     * 上传图片/视频/excel, 上传到linux服务器 /usr/application/uploadfile/目录下，前端通过 http://ip:port/upload/**.jpg来访问
     */
    @RequestMapping("/uploadFile")
    public String uploadFile(@RequestParam("file") MultipartFile file){
        if(file.isEmpty()){
            return "上传文件不能为空";
        }
        //获取文件名
        String fileName = file.getOriginalFilename();

        fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_" + fileName;
        System.out.print("（加个时间戳，尽量避免文件名称重复）保存的文件名为: "+fileName+"\n");
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        //创建文件路径
        File dest = new File(docBase + date + "/",fileName);

        //判断文件是否已经存在
        if (dest.exists()) {
            return "文件已经存在";
        }

        //判断文件父目录是否存在
        if (!dest.getParentFile().exists()) {
            System.out.print("创建上级目录。。。");
            dest.getParentFile().mkdir();
        }

        try {
            //上传文件
            file.transferTo(dest); //保存文件
            //url="http://localhost:8080/images/"+fileName;
            //int jieguo= shiPinService.insertUrl(fileName,path,url);
            //System.out.print("插入结果"+jieguo+"\n");
            //System.out.print("保存的完整url===="+url+"\n");

        } catch (IOException e) {
            return "上传失败";
        }

        return "上传成功,路径：" + filePath + date + "/" + fileName;
    }

    /**
     * 上传图片或视频，上传到本地任一目录或者是项目所在某一目录下
     */
    @RequestMapping("/uploadFile11")
    public String uploadFile11(@RequestParam("file") MultipartFile file) throws FileNotFoundException {
        if(file.isEmpty()){
            return "上传文件不能为空";
        }
        //获取文件名
        String fileName = file.getOriginalFilename();

        fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_" + fileName;
        System.out.print("（加个时间戳，尽量避免文件名称重复）保存的文件名为: "+fileName+"\n");
        //项目目录下
        File upload = new File(ResourceUtils.getURL("").getPath(),"src/main/resources/fileUpload");
        if(!upload.exists()) {
            upload.mkdirs();
        }
        //创建文件路径
        File dest = new File(upload.getAbsolutePath(),fileName);
        //任一目录下
        /*String path = "F:\\fileUpload";
        File dest = new File(path,fileName);*/
        try {
            //上传文件
            file.transferTo(dest); //保存文件
        } catch (IOException e) {
            return "上传失败";
        }
        return "上传成功，保存文件路径：" + upload+ "/" + fileName;
    }

    /**
     * 某一列带下拉框的导出，需要自定义一个处理器进行处理
     */
    @RequestMapping("/testExportSelect")
    public String testExportSelect(@RequestBody JSONObject json) {
        String fileName = "F:\\excel\\" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写
        String modelType = json.getString("modelType");
        String modelName = path + modelType;
        Class<?> modelClass = null;
        try {
            modelClass = Class.forName(modelName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return e.getMessage();
        }
        //ExcelWriter excelWriter = EasyExcel.write(fileName, modelClass).build();
        //WriteSheet writeSheet = EasyExcel.writerSheet("模板").build();
        List list = new ArrayList();
        DemoData data = new DemoData();
        data.setId(1);
        data.setName("小明阿达发发发的发");
        data.setAge(18);
        data.setSex("男");
        data.setCity("河南");
        list.add(data);
        EasyExcelFactory.write(fileName,modelClass)
                .registerWriteHandler(new SelectWriteHandler())
                .registerWriteHandler(new CustomCellWriteHandler())
                .sheet("带下拉框")
                .doWrite(list);

        return "导出成功!";
    }

    /**
     * 图片经base64编码后再上传，上传到本地任一目录或者是项目所在某一目录下
     */
    @RequestMapping("/uploadFileBase64")
    public String uploadFileBase64(@RequestBody JSONObject json) throws FileNotFoundException {
        String base64Val = json.getString("base64Val");
        File file = Base64Util.base64ToFile(base64Val);
        MultipartFile multiFile = FileUtil.fileToMultipartFile(file);
        //MultipartFile multiFile = Base64Util.base64Convert(base64Val);
        //获取文件名
        String fileName = multiFile.getOriginalFilename();
        System.out.println("文件名：" + fileName);
        //任一目录下
        String filePath11 = "F:\\fileUpload";
        File dest = new File(filePath11,fileName);
        try {
            //上传文件
            multiFile.transferTo(dest); //保存文件
        } catch (IOException e) {
            return "上传失败";
        }
        return "上传成功，保存文件路径：" + filePath11+ "/" + fileName;
    }

    /**
     * 根据文件路径，把文件转换为base64编码
     * @param json
     * @return
     */
    @RequestMapping("/fileToBase64")
    public String fileToBase64(@RequestBody JSONObject json){
        String filePath = json.getString("filePath");
        return Base64UtilNew.encryptToBase64(filePath);
    }

    /**
     * 生成二维码，并把图片嵌入到二维码里面
     * @param file
     * @return
     */
    @RequestMapping("/createQRCode")
    public String createQRCode(@RequestParam("file") MultipartFile file) throws Exception{
        if(file.isEmpty()){
            return "上传文件不能为空";
        }
        //获取文件名
        String fileName = file.getOriginalFilename();

        fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_" + fileName;
        System.out.print("（加个时间戳，尽量避免文件名称重复）保存的文件名为: "+fileName+"\n");
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        //创建文件路径
        File dest = new File(docBase + date + "/",fileName);
        //判断文件是否已经存在
        if (dest.exists()) {
            return "文件已经存在";
        }
        //判断文件父目录是否存在
        if (!dest.getParentFile().exists()) {
            System.out.print("创建上级目录。。。");
            dest.getParentFile().mkdir();
        }
        try {
            //上传文件
            file.transferTo(dest);
        } catch (IOException e) {
            return "上传失败";
        }
        // 存放在二维码中的内容
        String text = "https://qqe2.com/";
        // 嵌入二维码的图片路径
        String imgPath = docBase + date + "/" + fileName;
        // 生成的二维码的路径及名称
        String name = System.currentTimeMillis() + ".jpg";
        String destPath = docBase + date + "/" + name;
        //生成二维码
        QRCodeUtil.encode(text, imgPath, destPath, true);
        // 解析二维码
        String str = QRCodeUtil.decode(destPath);
        // 打印出解析出的内容
        System.out.println(str);
        return str + " *** " + "二维码路径：" + filePath + date + "/" + name;
    }
}
