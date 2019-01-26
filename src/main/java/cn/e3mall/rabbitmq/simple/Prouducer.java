package cn.e3mall.rabbitmq.simple;

import cn.e3mall.rabbitmq.utils.ConnectionUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @ProjectName: house
 * @Package: cn.e3mall.rabbitmq
 * @ClassName: Prouducer
 * @Author: majiafei
 * @Description:
 * <p>
 *     生产者
 * </p>
 * @Date: 2019/1/26 12:35
 */
public class Prouducer {

    private static final String USER_NAME = "rabbit";
    private static final String PASSWORD = "rabbit";
    private static final String HOST = "192.168.221.99";
    private static final int PORT = 5672;
    private static final String V_HOST = "/";

    public static void main(String[] args) {
        try {
            // 获取连接
            Connection connection = ConnectionUtils.getConnection(USER_NAME, PASSWORD, V_HOST, HOST, PORT);
           // 声明通道
            Channel channel = connection.createChannel();
            // 声明队列
            channel.queueDeclare("hello", false, false, false, null);
            // 定义消息内容
            String message = "你好，过的才";
            // 发布消息
            channel.basicPublish("", "hello", null, message.getBytes());
            System.out.println("发布消息完成");
            // 关闭通道和连接
            channel.close();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
    }
    }

}
