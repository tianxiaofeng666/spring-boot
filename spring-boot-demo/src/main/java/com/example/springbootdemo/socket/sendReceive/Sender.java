package com.example.springbootdemo.socket.sendReceive;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

public class Sender {

    public static void main(String[] args) throws Exception {
        // 127.0.0.1 代表本机地址，在 8888 端口上监听
        Sender sender = new Sender("127.0.0.1", 8888);
        String command = "C9 05 00 00 FF 00 9C 72";
        byte[] aa = toByteArray(command);
        sender.send(aa);
        System.out.println("发送" + Arrays.toString(aa) + "完毕！");
    }

    private final String host;
    private final int port;

    public Sender(String host, int port) {
        this.host = host;
        this.port = port;
    }

    private void send(byte[] bytes) throws IOException {
        Socket socket = new Socket(host, port); // 建立和服务端的 socket

        try (DataOutputStream dos // 建立输出流
                     = new DataOutputStream(socket.getOutputStream())) {
            dos.write(bytes, 0, bytes.length); // 向输出流写入 bytes
        }
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
}
