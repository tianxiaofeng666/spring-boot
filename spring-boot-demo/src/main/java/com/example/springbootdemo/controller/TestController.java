package com.example.springbootdemo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.springbootdemo.pojo.User;
import com.example.springbootdemo.util.HttpRequestUtil;
import com.example.springbootdemo.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class TestController {
    private final static Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    RestTemplate restTemplate;

    @PostMapping({"/test33","/test44"})
    public String test(@RequestBody JSONObject json){

        return "hello";
    }

    @PostMapping("/api")
    public JSONObject api(@RequestBody JSONObject json){
        JSONObject obj = new JSONObject();
        String name = json.getString("name");
        String sex = json.getString("sex");
        JSONObject result = new JSONObject();
        result.put("id",111);
        obj.put("code",0);
        obj.put("result",result);
        return obj;
    }

    public static void main(String[] args) throws Exception{

        /*String url = "http://localhost:2222/api";
        User user = new User();
        user.setName("xiaofeng");
        user.setSex("男");
        String param11 = "id=" + user.getName();
        log.info(url + "?" + param11.toString());

        String param22 = "faceDbId=" + 111;
        if(StringUtils.isNotBlank("张三")){
            param22 += "&name=" + "张三";
        }
        param22 += "&pageNo=" + 1 + "&pageSize=" + 10;
        log.info(url + "?" + param22.toString());

        String token = "aaaaaaa";
        String param = JSONObject.toJSONString(user);
        log.info(param);
        String res = HttpUtils.doPost(url,param,token);
        log.info(res);
        String id = JSONObject.parseObject(res).getJSONObject("result").getString("id");
        log.info("主键:" + id);*/

        /*String url = "http://localhost:2222/postFormData";
        Map<String, String> map = new HashMap<>();
        map.put("name","12qaz");
        String result = HttpUtil.postForm(url, map);
        Map<String, Object> map11 = new HashMap<>();
        map11.put("name","23we");
        String response = HttpUtil.postFormDataWithCookie(url, map11, null, "123456");
        System.out.println(result + "  &&&  " + response);*/

        // TODO 自动生成的方法存根

        String readline = null;
        String inTemp = null;
        //String outTemp = null;
        String turnLine = "\n";
        final String client = "Client:";
        final String server = "Server:";

        int port = 4001;
        byte ipAddressTemp[] = {127, 0, 0, 1};
        InetAddress ipAddress = InetAddress.getByAddress(ipAddressTemp);

        //首先直接创建socket,端口号1~1023为系统保存，一般设在1023之外
        Socket socket = new Socket(ipAddress, port);

        //创建三个流，系统输入流BufferedReader systemIn，socket输入流BufferedReader socketIn，socket输出流PrintWriter socketOut;
        BufferedReader systemIn = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter socketOut = new PrintWriter(socket.getOutputStream());

        while(readline != "bye"){

            System.out.println(client);
            readline = systemIn.readLine();
            //System.out.println(readline);

            socketOut.println(readline);
            socketOut.flush();    //赶快刷新使Server收到，也可以换成socketOut.println(readline, ture)

            //outTemp = readline;
            inTemp = socketIn.readLine();

            //System.out.println(client + outTemp);
            System.out.println(server + turnLine + inTemp);

        }

        systemIn.close();
        socketIn.close();
        socketOut.close();
        socket.close();


    }

    @GetMapping("/getApi")
    public String getApi(@RequestBody JSONObject json){
        String name = json.getString("name");
        return name;
    }

    @PostMapping("/postFormData")
    public String postFormData(User user){
        String name = user.getName();
        return name;
    }


    @PostMapping("/aa")
    public String aa(){
        String url = "http://localhost:2222/getApi";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        JSONObject obj = new JSONObject();
        obj.put("name","xiaoming11");
        HttpEntity<String> httpEntity = new HttpEntity<>(JSONObject.toJSONString(obj),httpHeaders);
        String result = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class).getBody();
        String token = JWT.create().sign(Algorithm.HMAC256("cua505da9e-01f9-413f-9e42-6c25c3b5380a"));
        String token11 = JWT.create().withAudience("65").sign(Algorithm.HMAC256("cua505da9e-01f9-413f-9e42-6c25c3b5380a"));
        System.out.println("token:" + token);
        System.out.println("token11:" + token11);
        log.info("请求url：{},入参:{}",url,obj);
        log.info("当前线程22：" + Thread.currentThread().getName());
        return result;
    }

    @RequestMapping("/testSocketClient")
    public String testSocketClient(@RequestBody JSONObject json) throws Exception{
        String action = json.getString("action");
        String onCommand = null;
        String offCommand = null;
        if("rise".equals(action)){
            //上升
            onCommand = "C9 05 00 00 FF 00 9C 72";
            offCommand = "C9 05 00 00 00 00 DD 82";
        }else if("drop".equals(action)){
            //下降
            onCommand = "C9 05 00 01 FF 00 CD B2";
            offCommand = "C9 05 00 01 00 00 8C 42";
        }else if("stop".equals(action)){
            //急停
            onCommand = "0xC90x050x000x020xFF0x000x3D0xB2";
            offCommand = "C9 05 00 02 00 00 7C 42";
        }
        int port =1234;
        String host = "127.0.0.1";
        Socket socket = new Socket(host,port);
        //创建字节IO流输出
        OutputStream out = socket.getOutputStream();
        //OutputStreamWriter osw = new OutputStreamWriter(out);
        //BufferedWriter bw = new BufferedWriter(osw);
        //指令发送到服务器
        //out.write(toByteArray("C9 05 00 00 FF 00 9C 72"));
        out.write(hexStringToByteArray("C9 05 00 00 FF 00 9C 72"));
        //out.write(hexStrToBinaryStr("C9 05 00 00 FF 00 9C 72"));
        //睡眠1秒
        Thread.sleep(1000);
        out.write(hexStringToByteArray("C90500000000DD82"));
        //接收服务器返回数据IO流
        InputStream in =socket.getInputStream();
        byte[] b = new byte[1024];
        int len = in.read(b);
        System.out.println("长度qqq：" + len);
        String result = new String(b,0,len);
        socket.close();
        return result;
    }

    public static byte[] hexStringToByteArray(String hexString) {
        hexString = hexString.replaceAll(" ", "");
        System.out.println(hexString);
        int len = hexString.length();
        byte[] bytes = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            // 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个字节
            bytes[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4) + Character
                    .digit(hexString.charAt(i+1), 16));
        }
        return bytes;
    }

    public static byte[] toByteArray(String hexString) {
        hexString = hexString.replaceAll(" ", "");
        final byte[] byteArray = new byte[hexString.length() / 2];
        int k = 0;
        for (int i = 0; i < byteArray.length; i++) {//因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
            byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
            byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
            byteArray[i] = (byte) (high << 4 | low);
            k += 2;
        }
        return byteArray;
    }

    /**
     * 将十六进制的字符串转换成字节数组
     *
     * @param hexString
     * @return
     */
    public static byte[] hexStrToBinaryStr(String hexString) {

        hexString = hexString.replaceAll(" ", "");

        int len = hexString.length();
        int index = 0;

        byte[] bytes = new byte[len / 2];

        while (index < len) {

            String sub = hexString.substring(index, index + 2);

            bytes[index/2] = (byte)Integer.parseInt(sub,16);

            index += 2;
        }


        return bytes;
    }

    /**
     * 将字节数组转换成十六进制的字符串
     *
     * @return
     */
    public static String BinaryToHexString(byte[] bytes) {
        String hexStr = "0123456789ABCDEF";
        String result = "";
        String hex = "";
        for (byte b : bytes) {
            hex = String.valueOf(hexStr.charAt((b & 0xF0) >> 4));
            hex += String.valueOf(hexStr.charAt(b & 0x0F));
            result += hex + " ";
        }
        return result;
    }

    @PostMapping("/testRPC")
    public String testRPC(){
        String url = "https://58.19.177.202:9999/ipms/car/find/2";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("accessToken","99+qyLP/eTznVYNYkkFYVzMe5bgP6Xcxp5Vz+WhnpL8Y=");
        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);;
        ResponseEntity<String> res = restTemplate.exchange(url, HttpMethod.GET,requestEntity,String.class);
        String result = res.getBody();
        return result;
    }


}
