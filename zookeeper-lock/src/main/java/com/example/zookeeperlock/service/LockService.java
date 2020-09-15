package com.example.zookeeperlock.service;

import com.example.zookeeperlock.listener.ConnectionWatcher;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LockService extends ConnectionWatcher {

    public String join(String groupPath) throws KeeperException, InterruptedException {
        String path = groupPath + "/lock-id-";

        //创建一个顺序临时节点
        String createPath = zk.create(path,null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);

        System.out.println("Created: " + createPath);
        return createPath;
    }

    /**
     * 校验节点是否存在
     */
    public boolean isExist(String myName) throws KeeperException, InterruptedException {
        Stat aa = zk.exists(myName, true);
        if(aa == null){
            return false;
        }
        return true;
    }

    /**
     * 检查本客户端是否得到了分布式锁
     */
    public boolean checkState(String groupPath, String myName) throws KeeperException, InterruptedException {
        List<String> childList = zk.getChildren(groupPath,false);

        String[] myStr = myName.split("-");
        long myId = Long.parseLong(myStr[2]);

        boolean minId = true;
        for (String childName : childList) {
            String[] str = childName.split("-");
            long id = Long.parseLong(str[2]);
            if(id < myId){
                minId = false;
                break;
            }
        }
        if(minId){
            System.out.println(new Date() + "我得到了分布锁，哈哈！ myId:" + myId);
            return true;
        }else {
            System.out.println(new Date() + "继续努力吧，  myId:" + myId);
            return false;
        }
    }

    /**
     * 若本客户端没有得到分布式锁,监听比自己要小1的节点变化
     */
    public boolean listenNode(final String groupPath, final String myName) throws KeeperException, InterruptedException {
        List<String> childList = zk.getChildren(groupPath,false);

        String[] myStr = myName.split("-");
        long myId = Long.parseLong(myStr[2]);

        List<Long> idList = new ArrayList<>();
        Map<Long, String> sessionMap = new HashMap<>();

        for (String childName : childList) {
            String[] str = childName.split("-");
            long id = Long.parseLong(str[2]);
            idList.add(id);
            sessionMap.put(id,str[1] + "-" + str[2]);
        }
        Collections.sort(idList);

        int i = idList.indexOf(myId);
        if(i <= 0){
            throw new IllegalArgumentException("数据错误");
        }

        //得到前面的一个节点
        long headId = idList.get(i-1);

        String headPath = groupPath + "/lock-" + sessionMap.get(headId);
        System.out.println("添加监听: " + headPath);

        final boolean[] result = {false};

        Stat stat = zk.exists(headPath, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println("已经触发了" + event.getType() + "事件！");

                try {
                    if (checkState(groupPath,myName)) result[0] = true;
                    else result[0] = false;
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        return result[0];
    }
}
