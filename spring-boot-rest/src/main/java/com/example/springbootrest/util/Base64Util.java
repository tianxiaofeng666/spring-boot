package com.example.springbootrest.util;

import org.apache.commons.codec.binary.Base64;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;

public class Base64Util {

    /**
     * File转Base64
     * @param file
     * @return
     */
    public static String file2Base64(File file) {
        if(file==null) {
            return null;
        }
        String base64 = null;
        FileInputStream fin = null;
        try {
            fin = new FileInputStream(file);
            byte[] buff = new byte[fin.available()];
            fin.read(buff);
            base64 = Base64.encodeBase64String(buff);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fin != null) {
                try {
                    fin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return base64;
    }

    /**
     * Base64转File
     * @param base64
     * @return
     */
    public static File base64ToFile(String base64) {
        if(base64==null||"".equals(base64)) {
            return null;
        }
        byte[] buff=Base64.decodeBase64(base64);
        File file=null;
        FileOutputStream fout=null;
        try {
            file = File.createTempFile("tmp", ".jpg");
            fout=new FileOutputStream(file);
            fout.write(buff);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fout!=null) {
                try {
                    fout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }

    public static MultipartFile base64Convert(String base64) {
        //base64编码后的图片有头信息所以要分离出来 [0]data:image/png;base64, 图片内容为索引[1]
        String[] baseStrs = base64.split(",");
        //取索引为1的元素进行处理
        byte[] b = Base64.decodeBase64(baseStrs[1]);
        for (int i = 0; i < b.length; ++i) {
            if (b[i] < 0) {
                b[i] += 256;
            }
        }
        //处理过后的数据通过Base64DecodeMultipartFile转换为MultipartFile对象
        return new Base64DecodeMultipartFile(b,baseStrs[0]);
    }
}
