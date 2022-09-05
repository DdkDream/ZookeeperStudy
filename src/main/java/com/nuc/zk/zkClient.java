package com.nuc.zk;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class zkClient {

    // 注意：逗号左右不能有空格
    private String connectString = "hadoop101:2181,hadoop102:2181,hadoop102:2181";
    private int sessionTimeout = 20000;
    private ZooKeeper zkClient;

    @Before
    public void init() throws IOException {

        zkClient = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {

//                System.out.println("-----------------------");
//                List<String> children = null;
//                try {
//                    children = zkClient.getChildren("/", true);
//                    for (String child : children) {
//                        System.out.println(child);
//                    }
//                    System.out.println("-----------------------");
//                } catch (KeeperException e) {
//                    throw new RuntimeException(e);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
            }
        });
    }

    @Test
    public void create() throws InterruptedException, KeeperException {
        String nodeCreated = zkClient.create("/atguigu", "ss.avi".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    @Test
    public void getChildren() throws InterruptedException, KeeperException {
        List<String> children = zkClient.getChildren("/", true);
        for (String child : children) {
            System.out.println(child);
        }

        // 延时
        Thread.sleep(Long.MAX_VALUE);
    }

    @Test
    public void exist() throws InterruptedException, KeeperException {

        Stat stat = zkClient.exists("/atguigu", false);

        System.out.println(stat == null ? "not exist" : "exist");
    }
}
