package cn.e3mall.rabbitmq.exchange.fanout;

import cn.e3mall.rabbitmq.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @ProjectName: house
 * @Package: cn.e3mall.rabbitmq.exchange
 * @ClassName: Consumer2
 * @Author: majiafei
 * @Description:
 * @Date: 2019/1/26 13:35
 */
public class Consumer2 {
    private static final String USER_NAME = "rabbit";
    private static final String PASSWORD = "rabbit";
    private static final String HOST = "192.168.221.99";
    private static final int PORT = 5672;
    private static final String V_HOST = "/";
    public static final String QUEUE_NAME = "fanout_queue_2";

    private final static String EXCHANGE_NAME = "fanout_exchange";


    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = ConnectionUtils.getConnection(USER_NAME, PASSWORD, V_HOST, HOST, PORT);
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");
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
    }
}
