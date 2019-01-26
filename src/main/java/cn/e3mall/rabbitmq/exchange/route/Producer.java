package cn.e3mall.rabbitmq.exchange.route;

import cn.e3mall.rabbitmq.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @ProjectName: house
 * @Package: cn.e3mall.rabbitmq.exchange.route
 * @ClassName: Producer
 * @Author: majiafei
 * @Description:
 * <p>
 *     生产者将消息发送到direct交换器,在绑定队列和交换器的时候有一个路由key,
 *     生产者发送的消息会指定一个路由key,那么消息只会发送到相应key相同的队列,接着监听该队列的消费者消费信息.
 * </p>
 * <p>
 *     应用场景
 * 利用消费者能够有选择性的接收消息的特性,比如商场系统的后台管理系统对于商品进行修改、删除、
 * 新增操作都需要更新前台系统的界面展示,而查询操作不需要,那么这两个队列分开接收消息就比较好.
 * </p>
 * @Date: 2019/1/26 13:51
 */
public class Producer {

    private static final String USER_NAME = "rabbit";
    private static final String PASSWORD = "rabbit";
    private static final String HOST = "192.168.221.99";
    private static final int PORT = 5672;
    private static final String V_HOST = "/";
    private final static String EXCHANGE_NAME = "direct_exchange";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection(USER_NAME, PASSWORD, V_HOST, HOST, PORT);
        //2.声明信道
        Channel channel = connection.createChannel();
        //3.声明交换器,类型为direct
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        //4.定义消息内容
        String message = "hello rabbitmq";
        //5.发布消息
        channel.basicPublish(EXCHANGE_NAME, "select", null, message.getBytes());
        System.out.println("[x] send'" + message + "'");
        //6.关闭通道和连接
        channel.close();
        connection.close();
    }
}
