package cn.e3mall.rabbitmq.exchange.fanout;

import cn.e3mall.rabbitmq.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @ProjectName: house
 * @Package: cn.e3mall.rabbitmq.exchange
 * @ClassName: Producer
 * @Author: majiafei
 * @Description:
 * <p>
 *     交换器模式的生产者
 * </p>
 * <p>
 *     一个消费者将消息首先发送到交换器,交换器绑定多个队列,然后被监听该队列的消费者所接收并消费.
 * </p>
 * @Date: 2019/1/26 13:31
 */
public class Producer {
    private final static String EXCHANGE_NAME = "fanout_exchange";
    private static final String USER_NAME = "rabbit";
    private static final String PASSWORD = "rabbit";
    private static final String HOST = "192.168.221.99";
    private static final int PORT = 5672;
    private static final String V_HOST = "/";

    public static void main(String[] args) {
        // 获取连接
        try {
            Connection connection = ConnectionUtils.getConnection(USER_NAME, PASSWORD, V_HOST, HOST, PORT);
            //2.声明信道
            Channel channel = connection.createChannel();
            //3.声明交换器
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
            //4.定义消息内容
            String message = "hello rabbitmq";
            //5.发布消息
            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
            System.out.println("[x] send'" + message + "'");
            //6.关闭通道和连接
            channel.close();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
