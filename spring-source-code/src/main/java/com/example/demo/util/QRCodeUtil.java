package com.example.demo.util;

import cn.hutool.core.io.FileUtil;
import com.example.demo.exception.BusinessException;
import com.google.zxing.*;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Base64Utils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

/**
 * @author shs-cyhlwzytxf
 */
@Slf4j
public class QRCodeUtil {
    private static final String CHARSET = "utf-8";
    private static final String IMAGE_SUFFIX = "PNG";
    /** 二维码的宽高 */
    private static final int WIDTH = 300;
    private static final int HEIGHT = 300;
    /** 加文字二维码高 */
    private static final int WORD_HEIGHT = 320;

    /**
     * 生成二维码
     * @param content
     * @param words
     * @return
     */
    public static String createQRCode(String content, String words){
        try {
            String base64Str = createQRCodeBase64(content,words);
            MultipartFile multipartFile = Base64Util.base64Convert(base64Str);
            String path = CommonFileUtils.uploadQrCodeToMinio(multipartFile);
            return path;
        }catch (Exception e){
            log.error(e.getMessage());
            throw new BusinessException(500,"生成二维码报错！");
        }
    }

    /**
     * 生成二维码并自定义文件名
     * @param content
     * @param words
     * @param fileName
     * @return
     */
    public static String createQRCode(String content, String words, String fileName){
        try {
            String base64Str = createQRCodeBase64(content,words);
            MultipartFile multipartFile = Base64Util.base64Convert(base64Str);
            //MultipartFile转file重命名后再转MultipartFile
            String originalFilename = multipartFile.getOriginalFilename();
            fileName = fileName + originalFilename.substring(originalFilename.lastIndexOf("."));
            File file = FileUtil.rename(File.createTempFile("xxx", IMAGE_SUFFIX), fileName, true);
            multipartFile.transferTo(file);
            FileItem fileItem = createFileItem(file.getPath(), file.getName());
            MultipartFile newMultipartFile = new CommonsMultipartFile(fileItem);
            String path = CommonFileUtils.uploadQrCodeToMinio(newMultipartFile);
            Files.delete(file.toPath());
            return path;
        }catch (Exception e){
            log.error(e.getMessage());
            throw new BusinessException(500,"生成二维码报错！");
        }
    }

    /**
     *  生成二维码图片对应的base64编码
     * @param content 二维码的内容
     * @return
     */
    public static String createQRCodeBase64(String content, String word) {
        try {
            Map<EncodeHintType, Object> hintMap = new HashMap<>(16);
            //容错级别L>M>Q>H(级别越高扫描时间越长)
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
            //字符编码
            hintMap.put(EncodeHintType.CHARACTER_SET, CHARSET);
            //白边的宽度，可取0~4
            hintMap.put(EncodeHintType.MARGIN, 1);
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            //生成矩阵数据
            BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, WIDTH, HEIGHT, hintMap);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            // 开始利用二维码数据创建Bitmap图片，分别设为黑（0xFFFFFFFF）白（0xFF000000）两色
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
                }
            }
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            if(StringUtils.isNotBlank(word)){
                image = insertWords(image, word);
            }
            ImageIO.write(image, IMAGE_SUFFIX, outputStream);
            String resultImage = "data:image/png;base64," + Base64Utils.encodeToString(outputStream.toByteArray());
            return resultImage;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(500,"生成base64编码报错");
        }
    }

    public static FileItem createFileItem(String filePath, String fileName){
        FileItemFactory factory = new DiskFileItemFactory(16, null);
        FileItem item = factory.createItem("file", "text/plain", false,fileName);
        File newFile = new File(filePath);
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        try (FileInputStream fis = new FileInputStream(newFile);
             OutputStream os = item.getOutputStream()) {
            while ((bytesRead = fis.read(buffer, 0, 8192))!= -1)
            {
                os.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return item;
    }

    /**
     * 二维码底部加上文字
     * @param image 传入二维码对象
     * @param words 显示的文字内容
     * @return
     */
    private static BufferedImage insertWords(BufferedImage image,String words){
        BufferedImage outImage = new BufferedImage(WIDTH, WORD_HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D outg = outImage.createGraphics();
        // 画二维码到新的面板
        outg.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
        // 画文字到新的面板
        outg.setColor(Color.BLACK);
        // 字体、字型、字号
        outg.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        int strWidth = outg.getFontMetrics().stringWidth(words);
        int singleFD = outg.getFontMetrics().stringWidth("2");
        int grow = WIDTH/singleFD-1;
        //长度过长就截取超出二维码宽度部分换行
        if (strWidth > WIDTH) {
            String serialnum1 = words.substring(0, grow);
            String serialnum2 = words.substring(grow, words.length());
            outg.drawString(serialnum1, 5, image.getHeight() + (outImage.getHeight() - image.getHeight()) / 2 );
            BufferedImage outImage2 = new BufferedImage(WIDTH, WORD_HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D outg2 = outImage2.createGraphics();
            outg2.drawImage(outImage, 0, 0, outImage.getWidth(), outImage.getHeight(), null);
            outg2.setColor(Color.BLACK);
            // 字体、字型、字号
            outg2.setFont(new Font("微软雅黑", Font.PLAIN, 16));
            //参数：显示的内容、起始位置x、起始的y
            outg2.drawString(serialnum2,5, outImage.getHeight() + (outImage2.getHeight() - outImage.getHeight()) / 2 );
            outg2.dispose();
            outImage2.flush();
            outImage = outImage2;
        } else {
            // 画文字
            outg.drawString(words, (WIDTH - strWidth) / 2,
                    image.getHeight() + (outImage.getHeight() - image.getHeight()) / 3 );
        }
        outg.dispose();
        outImage.flush();
        image.flush();
        return outImage;
    }

}

