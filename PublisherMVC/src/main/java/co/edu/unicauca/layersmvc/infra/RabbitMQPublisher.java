package co.edu.unicauca.layersmvc.infra;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;


public class RabbitMQPublisher implements IPublisher{

    private final String EXCHANGE_NAME = "ExchangeMVC";
    public RabbitMQPublisher(){

    }


    @Override
    public void publish(String msg) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
            
            channel.basicPublish(EXCHANGE_NAME, "", null, msg.getBytes("UTF-8"));

        } catch (Exception e) {
            e.printStackTrace();
        }
            
        
    }
    

    
}
