package cn.e3mall.rabbitmq.simple;

import cn.e3mall.rabbitmq.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @ProjectName: house
 * @Package: cn.e3mall.rabbitmq
 * @ClassName: Consumer
 * @Author: majiafei
 * @Description:
 * <p>
 *     消费者
 * </p>
 * @Date: 2019/1/26 12:36
 */
public class Consumer {

    private static final String USER_NAME = "rabbit";
    private static final String PASSWORD = "rabbit";
    private static final String HOST = "192.168.221.99";
    private static final int PORT = 5672;
    private static final String V_HOST = "/";

    public static void main(String[] args) {
        // 获取连接
        try {
            // 获取连接
            Connection connection = ConnectionUtils.getConnection(USER_NAME, PASSWORD, V_HOST, HOST, PORT);
            // 声明通道
            Channel channel = connection.createChannel();
            // 声明队列
            channel.queueDeclare("hello", false, false, false, null);
            // 定义队列的消费者
            QueueingConsumer queueingConsumer = new QueueingConsumer(channel);
            /**
             *   true:表示自动确认,只要消息从队列中获取,无论消费者获取到消息后是否成功消费,都会认为消息成功消费.
             *   false:表示手动确认,消费者获取消息后,服务器会将该消息标记为不可用状态,等待消费者的反馈,
             *   如果消费者一直没有反馈,那么该消息将一直处于不可用状态,并且服务器会认为该消费者已经挂掉,不会再给其发送消息,
             *   直到该消费者反馈.
             */
            channel.basicConsume("hello", true, queueingConsumer);
            // 获取消息
            while (true) {
                QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
                String message = new String(delivery.getBody());
                System.out.println("消费者收到的消息为：" + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
