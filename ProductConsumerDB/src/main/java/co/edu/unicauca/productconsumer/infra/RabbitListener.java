package co.edu.unicauca.productconsumer.infra;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author ahurtado,wpantoja
 */
public class RabbitListener implements Runnable{
    private static final String EXCHANGE_NAME = "ExchangeMVC";
    ISubscriber productVC;    
    public RabbitListener(ISubscriber productVC) {
        this.productVC = productVC;
    }
    
    @Override
    public void run() {
    try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
            String queueName = channel.queueDeclare().getQueue();
            channel.queueBind(queueName, EXCHANGE_NAME, "");
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
            
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
                productVC.onMessage(message);
            };
            channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
        } catch (IOException | TimeoutException ex) {
            Logger.getLogger(RabbitListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
