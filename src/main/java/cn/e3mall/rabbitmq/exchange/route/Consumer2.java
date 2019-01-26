package cn.e3mall.rabbitmq.exchange.route;

import cn.e3mall.rabbitmq.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @ProjectName: house
 * @Package: cn.e3mall.rabbitmq.exchange.route
 * @ClassName: Consumer2
 * @Author: majiafei
 * @Description:
 * @Date: 2019/1/26 13:59
 */
public class Consumer2 {

    private static final String USER_NAME = "rabbit";
    private static final String PASSWORD = "rabbit";
    private static final String HOST = "192.168.221.99";
    private static final int PORT = 5672;
    private static final String V_HOST = "/";
    public static final String QUEUE_NAME = "direct_queue_2";
    public static final String EXCHANGE_NAME = "direct_exchange";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = ConnectionUtils.getConnection(USER_NAME, PASSWORD, V_HOST, HOST, PORT);
        //2.声明信道
        Channel channel = connection.createChannel();
        //3.声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //4.绑定队列到交换器,指定路由key为select
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "select");
        //同一时刻服务器只会发送一条消息给消费者
        channel.basicQos(1);

        //5.定义队列的消费者
        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);
        //5.监听队列,手动返回完成状态
        channel.basicConsume(QUEUE_NAME, false, queueingConsumer);
        //6.获取消息
        while (true) {
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println("[消费者1] received message : '" + message + "'");
            //休眠10毫秒
            Thread.sleep(10);
            //返回确认状态
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }
    }
}
