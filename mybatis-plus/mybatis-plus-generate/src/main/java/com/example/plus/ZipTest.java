package com.example.plus;

import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipTest {
    /*public static void main(String[] args) throws Exception {
        byte[] buffer = new byte[1024];
        //生成的ZIP文件名为Demo.zip
        String strZipName = "Demo.zip";
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(strZipName));
        //需要同时下载的两个文件result.txt ，source.txt
        //File[] file1 = {new File("F:/spring-boot-guest/src")};
        List<String> filePathList = readFilesDir("H:/mybatis-plus-generate/src/main");
        for(int i=0;i<filePathList.size();i++) {
            File file = new File(filePathList.get(i));
            FileInputStream fis = new FileInputStream(file);
            out.putNextEntry(new ZipEntry(file.getName()));
            int len;
            //读入需要下载的文件的内容，打包到zip文件
            while((len = fis.read(buffer))>0) {
                out.write(buffer,0,len);
            }
            out.closeEntry();
            fis.close();
        }
        out.close();
        System.out.println("生成Demo.zip成功");
    }*/

    /**
     * 读取文件夹下的所有文件的内容
     * @param path
     */
    public static List<String> readFilesDir(String path){
        LinkedList<File> dirlist = new LinkedList<>();
        ArrayList<String> filelist = new ArrayList<>();
        File dir = new File(path);
        File[] files = dir.listFiles();

        for(File file : files){
            if(file.isDirectory()){
                dirlist.add(file);
            }else{
                //处理文件内容
                filelist.add(file.getAbsolutePath());
                System.out.println(file.getAbsolutePath());
            }
        }

        File temp;
        while(!dirlist.isEmpty()){
            temp = dirlist.removeFirst();
            if(temp.isDirectory()){
                files = temp.listFiles();
                if(files == null) continue;
                for(File file : files){
                    if(file.isDirectory()){
                        dirlist.add(file);
                    }else{
                        //处理文件内容
                        filelist.add(file.getAbsolutePath());
                        System.out.println(file.getAbsolutePath());
                    }
                }
            }else{
                //处理文件内容,这种情况好像不会发生
                System.out.println("-------------");
            }
        }
        return filelist;
    }

    public static boolean packageZip(File zipFile, String dirPath){
        //图片打包操作
        ZipOutputStream zipStream = null;
        FileInputStream zipSource = null;
        BufferedInputStream bufferStream = null;
        try {
            zipStream = new ZipOutputStream(new FileOutputStream(zipFile));// 用这个构造最终压缩包的输出流
//            zipSource = null;// 将源头文件格式化为输入流
            List<String> filePathList = readFilesDir(dirPath);
            for (String picKey : filePathList) {

                File file = new File(picKey);
                zipSource = new FileInputStream(file);

                byte[] bufferArea = new byte[1024 * 10];// 读写缓冲区

                // 压缩条目不是具体独立的文件，而是压缩包文件列表中的列表项，称为条目，就像索引一样
                ZipEntry zipEntry = new ZipEntry(file.getName());
                zipStream.putNextEntry(zipEntry);// 定位到该压缩条目位置，开始写入文件到压缩包中

                bufferStream = new BufferedInputStream(zipSource, 1024 * 10);// 输入缓冲流
                int read = 0;

                // 在任何情况下，b[0] 到 b[off] 的元素以及 b[off+len] 到 b[b.length-1]
                // 的元素都不会受到影响。这个是官方API给出的read方法说明，经典！
                while ((read = bufferStream.read(bufferArea, 0, 1024 * 10)) != -1) {
                    zipStream.write(bufferArea, 0, read);
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        } finally {
            // 关闭流
            try {
                if (null != bufferStream)
                    bufferStream.close();
                if (null != zipStream)
                    zipStream.close();
                if (null != zipSource)
                    zipSource.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                //e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    /*public static void main(String[] args) throws IOException {
        List<String> listKey = new ArrayList<>();
        //对目录下的所有文件打包zip
        //listKey.add("H:/mybatis-plus-generate/src/main");
        String dirPath = ResourceUtils.getURL("").getPath() + "src/main/java";
        String zipName = "download_code_" + System.currentTimeMillis() + ".zip";
        File zipFile = new File("D:/" + zipName);// 最终打包的压缩包
        System.out.println("zipFile exists: " + zipFile.exists());
        System.out.println("zipFile size: " + zipFile.length());
        packageZip(zipFile,dirPath);
        System.out.println("zipFile exists2: " + zipFile.exists());
        System.out.println("zipFile size: " + zipFile.length());
    }*/

    public static void copyDir(String oldPath, String newPath) throws IOException {
        File file = new File(oldPath);
        String[] filePath = file.list();

        if (!(new File(newPath)).exists()) {
            (new File(newPath)).mkdir();
        }

        for (int i = 0; i < filePath.length; i++) {
            if ((new File(oldPath + file.separator + filePath[i])).isDirectory()) {
                copyDir(oldPath  + file.separator  + filePath[i], newPath  + file.separator + filePath[i]);
            }

            if (new File(oldPath  + file.separator + filePath[i]).isFile()) {
                copyFile(oldPath + file.separator + filePath[i], newPath + file.separator + filePath[i]);
            }

        }
    }

    public static void copyFile(String oldPath, String newPath) throws IOException {
        File oldFile = new File(oldPath);
        File file = new File(newPath);
        FileInputStream in = new FileInputStream(oldFile);
        FileOutputStream out = new FileOutputStream(file);;

        byte[] buffer=new byte[2097152];

        while((in.read(buffer)) != -1){
            out.write(buffer);
        }
    }


    public static void main(String[] args) throws IOException {
        //Scanner sc = new Scanner(System.in);
        //System.out.println("请输入源目录：");
        String sourcePath = "H:/mybatis-plus-generate/src/main/java";
        //System.out.println("请输入新目录：");
        String path = "D:/download";

        //String sourcePath = "D://aa";
        //String path = "D://bb";

        copyDir(sourcePath, path);
    }

}
