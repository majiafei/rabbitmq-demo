package cn.e3mall.rabbitmq.exchange.topic;

import cn.e3mall.rabbitmq.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @ProjectName: house
 * @Package: cn.e3mall.rabbitmq.exchange.topic
 * @ClassName: Producer
 * @Author: majiafei
 * @Description:
 * <p>
 *     上面的路由模式是根据路由key进行完整的匹配(完全相等才发送消息),这里的通配符模式通俗的来讲就是模糊匹配.
 * </p>
 * @Date: 2019/1/26 14:08
 */
public class Producer {

    private final static String EXCHANGE_NAME = "topic_exchange";
    private static final String USER_NAME = "rabbit";
    private static final String PASSWORD = "rabbit";
    private static final String HOST = "192.168.221.99";
    private static final int PORT = 5672;
    private static final String V_HOST = "/";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection(USER_NAME, PASSWORD, V_HOST, HOST, PORT);
        //2.声明信道
        Channel channel = connection.createChannel();
        //3.声明交换器,类型为direct
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        //4.定义消息内容
        String message = "hello rabbitmq";
        //5.发布消息
        channel.basicPublish(EXCHANGE_NAME, "update.Name", null, message.getBytes());
        System.out.println("[x] send'" + message + "'");
        //6.关闭通道和连接
        channel.close();
        connection.close();
    }
}
