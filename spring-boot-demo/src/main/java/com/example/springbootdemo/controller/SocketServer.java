package com.example.springbootdemo.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
    public static void main(String[] args) {
        try {
            // 创建服务端socket
            ServerSocket serverSocket = new ServerSocket(8088);

            // 创建客户端socket
            Socket socket = new Socket();

            //循环监听等待客户端的连接
            while(true){
                // 监听客户端
                socket = serverSocket.accept();

                /*ServerThread thread = new ServerThread(socket);
                thread.start();*/
                InputStream in = socket.getInputStream();
                byte[] b = new byte[1024];
                //读取客户端数据
                int len = in.read(b);
                String str = new String(b,0,len);
                System.out.println("客户端数据：" + str);
                //发送数据到客户端
                OutputStream out = socket.getOutputStream();
                String response = "收到客户端数据 " + str + " ，服务器连接成功并返回！";
                out.write(response.getBytes());

                InetAddress address=socket.getInetAddress();
                System.out.println("当前客户端的IP："+address.getHostAddress());
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
}
