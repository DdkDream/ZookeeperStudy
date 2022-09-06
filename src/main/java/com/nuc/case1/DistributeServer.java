package com.nuc.case1;

import org.apache.zookeeper.*;

import java.io.IOException;

public class DistributeServer {

    private String connectString = "hadoop101:2181,hadoop102:2181,hadoop103:2181";
    private int sessionTimeout = 20000;
    private ZooKeeper zk;

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {

        DistributeServer server = new DistributeServer();
        // 1、获取zk连接
        server.getConnect();

        //2、注册服务器到zk集群
        server.regist(args[0]);

        // 3、启动业务逻辑（睡觉）
        server.business();

    }

    private void business() throws InterruptedException {
        Thread.sleep(Long.MAX_VALUE);
    }

    private void regist(String hostname) throws InterruptedException, KeeperException {
        String create = zk.create("/servers/" + hostname, hostname.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);

        System.out.println(hostname + " is online");
    }

    private void getConnect() throws IOException {

        zk = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {

            }
        });
    }
}
