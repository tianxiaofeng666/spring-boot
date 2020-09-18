package com.example.rabbitmqproducer.util;

import org.apache.zookeeper.*;

import java.io.IOException;

/**
 * id生成器
 */
public class SeqUtil implements Watcher{

    private static ZooKeeper zk = null;

    public static long generateId(){
        try {
            zk = new ZooKeeper("127.0.0.1:2181",5000, new SeqUtil());
            String path = "/sequence/seq-id-";
            String createPath =zk.create(path,null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            String[] myStr = createPath.split("-");
            long myId =Long.parseLong(myStr[2]);
            return myId;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
        return -1L;
    }

    @Override
    public void process(WatchedEvent watchedEvent) {

    }
}
