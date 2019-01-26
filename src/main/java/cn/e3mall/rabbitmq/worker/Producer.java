package cn.e3mall.rabbitmq.worker;

import cn.e3mall.rabbitmq.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @ProjectName: house
 * @Package: cn.e3mall.rabbitmq.worker
 * @ClassName: Producer
 * @Author: majiafei
 * @Description:
 * <p>
 *     woker模式下的生产者
 * </p>
 * @Date: 2019/1/26 13:09
 */
public class Producer {

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
            // 声明通道
            Channel channel = connection.createChannel();
            // 声明队列
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            // 定义消息内容，发送多条消息
            for (int i = 0; i < 10; i++) {
                String message = "hello, guodecai," + i;
                // 发布消息
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
                // 模拟发送消息延时，便于展示多个消费者竞争接受消息
                Thread.sleep(i * 10);
            }
            channel.close();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
