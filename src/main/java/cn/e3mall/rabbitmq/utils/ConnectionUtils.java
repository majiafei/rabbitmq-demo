package cn.e3mall.rabbitmq.utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @ProjectName: house
 * @Package: cn.e3mall.rabbitmq.utils
 * @ClassName: ConnectionUtils
 * @Author: majiafei
 * @Description:
 * @Date: 2019/1/26 12:39
 */
public class ConnectionUtils {

    /**
     * 获取连接
     * @param userName 用户名
     * @param password 密码
     * @param vHost 虚拟主机
     * @param host  ip地址
     * @param port  端口号
     * @return
     */
    public static Connection getConnection(String userName, String password, String vHost, String host, int port) throws IOException, TimeoutException {
        // 创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        // 设置用户名称
        connectionFactory.setUsername(userName);
        // 设置密码
        connectionFactory.setPassword(password);
        // 设置主机名称
        connectionFactory.setHost(host);
        // 设置端口号
        connectionFactory.setPort(port);
        // 设置虚拟主机
        connectionFactory.setVirtualHost(vHost);

        return connectionFactory.newConnection();
    }

}
