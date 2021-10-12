package com.example.springbootrest.qrcode;

/**
 * @author shs-cyhlwzytxf
 */
public class QrCodeTest {
    public static void main(String[] args) throws Exception {
        // 存放在二维码中的内容
        String text = "https://qqe2.com/";
        // 嵌入二维码的图片路径
        String imgPath = "F:/photo/qrCode/person.jpg";
        //tring imgPath = "https://pic.cnblogs.com/face/1042250/20201017013944.png";
        // 生成的二维码的路径及名称
        String destPath = "F:/photo/qrCode/" + System.currentTimeMillis() + ".jpg";
        //生成二维码
        QRCodeUtil.encode(text, imgPath, destPath, true);
        //QRCodeUtil.encode(text,destPath);
        // 解析二维码
        String str = QRCodeUtil.decode(destPath);
        // 打印出解析出的内容
        System.out.println(str);

    }

}
