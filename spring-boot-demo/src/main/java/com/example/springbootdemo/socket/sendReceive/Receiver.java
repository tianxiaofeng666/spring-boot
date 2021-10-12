package com.example.springbootdemo.socket.sendReceive;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Receiver {

    public static void main(String[] args) throws Exception {
        Receiver receiver = new Receiver(8888);
        receiver.receive();
    }

    private final ServerSocket serverSocket;

    public Receiver(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    private void receive() throws IOException {
        System.out.println("等待客户端连接...");
        Socket socket = serverSocket.accept();
        try (DataInputStream dis = new DataInputStream(socket.getInputStream())) {
            byte[] bytes = new byte[1024]; // 假设发送的字节数不超过 1024 个
            int size = dis.read(bytes); // size 是读取到的字节数

            String hex = bytesToHex(bytes, 0, size);
            System.out.println("接收到的byte数组的十六进制：" + hex);
        }
    }

    /**
     * 将 byte 数组转化为十六进制字符串
     *
     * @param bytes byte[] 数组
     * @param begin 起始位置
     * @param end 结束位置
     * @return byte 数组的十六进制字符串表示
     */
    private String bytesToHex(byte[] bytes, int begin, int end) {
        StringBuilder hexBuilder = new StringBuilder(2 * (end - begin));
        for (int i = begin; i < end; i++) {
            hexBuilder.append(Character.forDigit((bytes[i] & 0xF0) >> 4, 16)); // 转化高四位
            hexBuilder.append(Character.forDigit((bytes[i] & 0x0F), 16)); // 转化低四位
            //hexBuilder.append(' '); // 加一个空格将每个字节分隔开
        }
        return hexBuilder.toString().toUpperCase();
    }
}
