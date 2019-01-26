package cn.e3mall.rabbitmq.worker;

import cn.e3mall.rabbitmq.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @ProjectName: house
 * @Package: cn.e3mall.rabbitmq.worker
 * @ClassName: Consumer2
 * @Author: majiafei
 * @Description:
 * @Date: 2019/1/26 13:18
 */
public class Consumer2 {

    public static final String QUEUE_NAME = "work_queue";
    private static final String USER_NAME = "rabbit";
    private static final String PASSWORD = "rabbit";
    private static final String HOST = "192.168.221.99";
    private static final int PORT = 5672;
    private static final String V_HOST = "/";

    public static void main(String[] args) {
        // 获取连接
        try {
            Connection connection = ConnectionUtils.getConnection(USER_NAME, PASSWORD, V_HOST, HOST, PORT);
            Channel channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.basicQos(1);
            QueueingConsumer queueingConsumer = new QueueingConsumer(channel);
            channel.basicConsume(QUEUE_NAME, false, queueingConsumer);
            while (true) {
                QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
                String message = new String(delivery.getBody());
                System.out.println("[消费者2] received message : '" + message + "'");
                Thread.sleep(1000);
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
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
