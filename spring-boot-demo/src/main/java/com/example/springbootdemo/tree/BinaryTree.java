package com.example.springbootdemo.tree;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.util.DigestUtils;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Slf4j
public class BinaryTree {
    private static Node root;

    /** 获取 accessToken **/
    private static final String URI_DH_GET_ACCESS_TOKEN = "%s/ipms/subSystem/generate/token";

    private Node addNode(Node current, int value) {
        if (current == null) {
            return new Node(value);
        }
        if (value < current.data) {
            current.left = addNode(current.left, value);
        } else if (value > current.data) {
            current.right = addNode(current.right, value);
        } else {
            return current;
        }
        return current;
    }
    public void addNode(int value) {
        root = addNode(root, value);
    }

    private boolean containNode(Node current, int value) {
        if (current == null) {
            return false;
        }
        if (value == current.data) {
            return true;
        }
        return value < current.data ? containNode(current.left, value) : containNode(current.right, value);
    }
    public boolean containNode(int value) {
        return containNode(root, value);
    }

    public void traverseInOrder(Node root) {
        if (root != null) {
            traverseInOrder(root.left);
            System.out.print("中序遍历："+ root.data + "  ");
            traverseInOrder(root.right);
        }
    }

    public void traversePreOrder(Node root) {
        if (root != null) {
            System.out.print("先序遍历：" + root.data + "  ");
            traversePreOrder(root.left);
            traversePreOrder(root.right);
        }
    }

    public void traversePostOrder(Node root) {
        if (root != null) {
            traversePostOrder(root.left);
            traversePostOrder(root.right);
            System.out.print("后序遍历：" + root.data + "  ");
        }
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        BinaryTree bt = new BinaryTree();
        bt.addNode(6);
        bt.addNode(4);
        bt.addNode(8);
        bt.addNode(10);
        boolean aa = bt.containNode(8);
        System.out.println(aa);
        bt.traverseInOrder(root);
        System.out.println();
        bt.traversePreOrder(root);
        System.out.println();
        bt.traversePostOrder(root);

        String a = "%s/fastgate/visitor";
        String b = "http://localhost:8080";
        String url = String.format( a, b);
        System.out.println(url);

        String imageUrl = "http://10.9.101.30:9000/datapark/2021-05-07/1620372376259400.jpg";
        int indexLine = imageUrl.lastIndexOf("/");
        boolean isLegal = indexLine == -1 || imageUrl.length() - indexLine == 1;
        if (isLegal) {
            System.out.println("图片路径不合法");
        } else {
            String parentPath = "./";
            String imageName = imageUrl.substring(indexLine + 1);
            System.out.println(parentPath + imageName);
        }

        String pwd = "*Ab123456";
        String pp = DigestUtils.md5DigestAsHex(pwd.getBytes("utf-8"));
        System.out.println(pp);

        String cookie = "JSESSIONID=F2FC64D90B63AC01D61D89575ED8A265; Path=/fastgate; HttpOnly";
        System.out.println(cookie.substring(0,cookie.indexOf(";")));


        String startDateString = "Wed Oct 31 2018 00:00:00 GMT+0800 (中国标准时间)";
        startDateString = startDateString .replace("GMT", "").replaceAll("\\(.*\\)", "").trim() ;
        DateTimeFormatter df = DateTimeFormatter.ofPattern("EEE MMM dd yyyy HH:mm:ss Z",new Locale("en", "US"));
        LocalDateTime ldt = LocalDateTime.parse(startDateString,df);
        System.out.println("开始时间：" + ldt);

        ZoneId zone = ZoneId.systemDefault();
        log.info("zone:" + zone);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("EEE MMM dd yyyy HH:mm:ss 'GMT'Z",new Locale("en", "US"));
        ZonedDateTime zoneInBeijing = ZonedDateTime.now();
        log.info("zoneInBe:" + zoneInBeijing);
        LocalDateTime localDateTime = LocalDateTime.now();
        log.info("localDate:" + localDateTime);
        log.info("开始，{}，结束，{}",localDateTime.plusDays(-1),localDateTime.plusDays(-7));
        String qq = zoneInBeijing.format(dateTimeFormatter);
        String ww = zoneInBeijing.plusHours(2).format(dateTimeFormatter);
        log.info("qq:{},ww:{}",qq,ww);
        //String ee = localDateTime.format(dateTimeFormatter);
        //log.info("ee:{}",ee);

        //String qaz = getCookie();
        //log.info("cookie:" + qaz);

        Map<String, Object> map = new HashMap<>();
        map.put("Keyword","");
        map.put("page",1);
        map.put("size",10);
        String url1 = "https://jyhcu.com:18088/video/fastgate/groups?sort=desc&conditionParam=";
        log.info("权限组：" + url1 + JSONObject.toJSONString(map));

        String url122 = String.format( URI_DH_GET_ACCESS_TOKEN, "http://127.0.0.1:8091/mock/vehicle");
        String param = "userName=" + "admin";
        log.info("获取accessToken:" + param);

        log.info("当前类路径：" + BinaryTree.class.getPackage().getName());
    }

    public static String getCookie() {
        String cookie = null;
        HttpURLConnection conn = null;

        try {
            Map<String, Object> auth = new HashMap();
            auth.put("Id", "admin");
            auth.put("Pwd", DigestUtils.md5DigestAsHex("*Ab123456".getBytes("utf-8")));
            Assert.isTrue(auth.containsKey("Id"), "参数不可为空：Id");
            Assert.isTrue(auth.containsKey("Pwd"), "参数不可为空：Pwd");
            URL url = new URL("https://jyhcu.com:18088/video" + "/fastgate/user/login");
            conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            String param = JSON.toJSONString(auth);
            byte[] writebytes = param.getBytes();
            conn.setRequestProperty("Content-Length", String.valueOf(writebytes.length));
            OutputStream os = conn.getOutputStream();
            os.write(writebytes);
            os.flush();
            os.close();
            cookie = conn.getHeaderField("Set-Cookie");
            log.info("获取宇视登录cookie:{}", cookie);
        } catch (Exception var11) {
            log.error("FastgateSDK Exception", var11);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }

        }

        return cookie;
    }
}
