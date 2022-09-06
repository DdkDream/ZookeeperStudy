package com.nuc.case1;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DistributeClient {

    private String connectString = "hadoop101:2181,hadoop102:2181,hadoop103:2181";
    private int sessionTimeout = 20000;
    private ZooKeeper zk;

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        DistributeClient client = new DistributeClient();

        // 1 获取zk连接
        client.getConnection();

        // 2 监听/servers下面子节点的增加和删除
        client.getServerList();


        // 3 业务逻辑（睡觉）
        client.business();

    }

    private void business() throws InterruptedException {
        Thread.sleep(Long.MAX_VALUE);
    }

    private void getServerList() throws InterruptedException, KeeperException {
        List<String> children = zk.getChildren("/servers", true);

        ArrayList<String> servers = new ArrayList<String>();
        for (String child : children) {
            byte[] data = zk.getData("/servers/" + child, false, null);

            servers.add(new String(data));
        }

        // 打印
        System.out.println(servers);
    }

    private void getConnection() throws IOException {
        zk = new ZooKeeper(connectString, sessionTimeout, new Watcher() {

            @Override
            public void process(WatchedEvent watchedEvent) {
                try {
                    getServerList();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (KeeperException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
