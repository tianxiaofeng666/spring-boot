package com.example.plus.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipGenerateUtils {

    private ZipGenerateUtils() {

    }
    /**
     * 把文件打成压缩包并保存在本地硬盘
     *
     * @param srcfiles
     * @param zipPath
     */
    public static void ZipFiles(List srcfiles, String zipPath) {

        byte[] buf = new byte[4096];
        ZipOutputStream out = null;
        try {
            // 创建zip输出流
            out = new ZipOutputStream(new FileOutputStream(zipPath));

            // 循环将源文件列表添加到zip文件中
            for (int i = 0; i < srcfiles.size(); i++) {
                File file = new File((String) srcfiles.get(i));
                FileInputStream in = new FileInputStream(file);
                String fileName = file.getName();

                // 将文件名作为zip的Entry存入zip文件中
                out.putNextEntry(new ZipEntry(fileName));
                int len;
                while ( (len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                out.closeEntry();
                in.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != out) {
                try {
                    out.close();
                    out = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 把文件列表打成压缩包并输出到客户端浏览器中
     *
     * @param request
     * @param response
     * @param srcFiles
     * @param downloadZipFileName
     */
    public static void ZipFiles(HttpServletRequest request, HttpServletResponse response, List srcFiles, String downloadZipFileName) {

        byte[] buf = new byte[4096];
        try {
            // Create the ZIP file
            // ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipPath));
            ZipOutputStream out = new ZipOutputStream(response.getOutputStream());//--设置成这样可以不用保存在本地,再输出,通过response流输出,直接输出到客户端浏览器中。

            // Compress the files
            if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
                downloadZipFileName = new String(downloadZipFileName.getBytes("GB2312"),"ISO-8859-1");
            } else {
                // 对文件名进行编码处理中文问题
                downloadZipFileName = java.net.URLEncoder.encode(downloadZipFileName, "UTF-8");// 处理中文文件名的问题
                downloadZipFileName = new String(downloadZipFileName.getBytes("UTF-8"), "GBK");// 处理中文文件名的问题
            }

            response.reset(); // 重点突出
            response.setCharacterEncoding("UTF-8"); // 重点突出
            response.setContentType("application/x-msdownload");// 不同类型的文件对应不同的MIME类型 // 重点突出

            // inline在浏览器中直接显示，不提示用户下载
            // attachment弹出对话框，提示用户进行下载保存本地
            // 默认为inline方式
            response.setHeader("Content-Disposition", "attachment;filename=" + downloadZipFileName);

            for (int i = 0; i < srcFiles.size(); i++) {

                File file = new File((String) srcFiles.get(i));
                FileInputStream in = new FileInputStream(file);
                // Add ZIP entry to output stream.
                String fileName = file.getName();
                out.putNextEntry(new ZipEntry(fileName));
                // Transfer bytes from the file to the ZIP file
                int len;
                while ( (len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                // Complete the entry
                out.closeEntry();
                in.close();
            }
            // Complete the ZIP file
            out.close();
            System.out.println("压缩完成.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 把文件目录打成压缩包并输出到客户端浏览器中
     *
     * @param request
     * @param response
     * @param sourcePath
     * @param downloadZipFileName
     */
    public static void createZip(HttpServletRequest request, HttpServletResponse response, String sourcePath, String downloadZipFileName) {

        byte[] buf = new byte[4096];
        FileOutputStream fos = null;
        ZipOutputStream out = null;
        try {
            out = new ZipOutputStream(response.getOutputStream());//--设置成这样可以不用保存在本地,再输出,通过response流输出,直接输出到客户端浏览器中。
            //out.setEncoding("gbk");//此处修改字节码方式。
            if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
                downloadZipFileName = new String(downloadZipFileName.getBytes("GB2312"),"ISO-8859-1");
            } else {
                // 对文件名进行编码处理中文问题
                downloadZipFileName = java.net.URLEncoder.encode(downloadZipFileName, "UTF-8");// 处理中文文件名的问题
                downloadZipFileName = new String(downloadZipFileName.getBytes("UTF-8"), "GBK");// 处理中文文件名的问题
            }

            response.reset(); // 重点突出
            response.setCharacterEncoding("UTF-8"); // 重点突出
            response.setContentType("application/x-msdownload");// 不同类型的文件对应不同的MIME类型 // 重点突出

            // 默认为inline方式
            response.setHeader("Content-Disposition", "attachment;filename=" + downloadZipFileName);
            writeZip(new File(sourcePath),"",out);
            out.flush();
            out.close();
            System.out.println("当前目录以及子目录和文件已经压缩完成。。。。");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(out != null) {
                try {
                    out.close();
                    out = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(fos != null) {
                try {
                    fos.close();
                    fos = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 创建zip文件
     * @param file 文件或者目录
     * @param parentPath 父路径（默认为""）
     * @param zos ZipOutputStream
     */
    private static void writeZip(File file, String parentPath, ZipOutputStream zos) {
        if(file.exists()){
            if(file.isDirectory()){//处理文件夹
                parentPath += file.getName() + File.separator;
                File [] files=file.listFiles();
                if(files.length != 0) {
                    for(File f:files){
                        String[] str = {"CodeGenerateUtil.java","ZipGenerateUtils.java","CodeController.java","CodeGenerator.java","ZipTest.java","DataBaseUtil.java"};
                        List<String> strList = Arrays.asList(str);
                        //上述文件不打包
                        if(strList.contains(f.getName())){
                            continue;
                        }
                        writeZip(f, parentPath, zos);
                    }
                } else {//空目录则创建当前目录
                    try {
                        zos.putNextEntry(new ZipEntry(parentPath));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                FileInputStream fis=null;
                try {
                    fis=new FileInputStream(file);
                    ZipEntry ze = new ZipEntry(parentPath + file.getName());
                    zos.putNextEntry(ze);
                    byte [] content=new byte[1024];
                    int len;
                    while((len=fis.read(content))!=-1){
                        zos.write(content,0,len);
                        zos.flush();
                    }

                } catch (FileNotFoundException e) {
                    System.out.println("创建ZIP文件失败");
                } catch (IOException e) {
                    System.out.println("创建ZIP文件失败");
                }finally{
                    try {
                        if(fis!=null){
                            fis.close();
                        }
                    }catch(IOException e){
                        System.out.println("创建ZIP文件失败");
                    }
                }
            }
        }
    }

    /**
     *删除文件夹
     * @param folderPath  文件夹完整绝对路径
     */
    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); //删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            File myFilePath = new File(filePath);
            myFilePath.delete(); //删除空文件夹
            System.out.println("目录：" + folderPath + "删除完成！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除指定文件夹下所有文件
     * @param path 文件夹完整绝对路径
     * @return
     */
    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);//再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }

    public static void main(String args[]){
        //delFolder("H:/mybatis-plus-generate/src/main/java/com/example/plus/user");
        System.out.println("deleted");
        String[] str = {"CodeGenerateUtil.java","ZipGenerateUtils.java","CodeController.java","CodeGenerator.java","ZipTest.java"};
        String aa = "ZipTest.java";
        List<String> strList = Arrays.asList(str);
        System.out.println(strList.contains(aa));
    }
}