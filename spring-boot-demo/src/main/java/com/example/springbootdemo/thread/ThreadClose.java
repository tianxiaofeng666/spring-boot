package com.example.springbootdemo.thread;

/**
 * 线程优雅结束
 */
public class ThreadClose{

    //设置标志位
    public static class DemoThread extends Thread{
        public volatile boolean flag = true;

        @Override
        public void run(){
            while(flag){
                System.out.println("线程正在执行");
            }
        }

        public void shutdown(){
            this.flag = false;
        }
    }

    //调用interrupt方法
    public static class Worker extends Thread{

        @Override
        public void run(){
            try{
                while(true){
                    if(Thread.interrupted()){
                        System.out.println("检测到中断标志为true，抛一个异常");
                        throw new InterruptedException();
                    }
                    System.out.println("线程正在执行");
                }
            }catch (InterruptedException e){
                System.out.println("接收到了抛出的异常，直接结束线程");
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        /*DemoThread thread = new DemoThread();
        thread.start();

        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        thread.shutdown();*/

        Worker worker = new Worker();
        worker.start();

        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        worker.interrupt();
    }


}
