package _book._rabbitmq_guide._3_client_develop;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author 734070824@qq.com
 * @date 2018/8/29 15:31
 */
public class ExchangeDeclareApp {

    private static final String EXCHANGE_NAME = "exchange_demo";
    private static final String ROUTING_KEY = " routingkey_demo";
    private static final String QUEUE_NAME = "queue_demo";
    private static final String IP_ADDRESS = "192.168.101.167";
    private static final int PORT = 5672;//RabbitMQ 服务端默认端口号为 5672

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(IP_ADDRESS);
        factory.setPort(PORT);
        factory.setUsername("datacenter");
        factory.setPassword("123");
        //创建连接
        Connection connection = factory.newConnection();
        //创建信道
        Channel channel = connection.createChannel();
        //type="direct" 持久化的, 非自动删除的交换器
        channel.exchangeDeclare(EXCHANGE_NAME, "direct", true, false, null);
    }
}
