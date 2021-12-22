package co.edu.unicauca.layersmvc.infra;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;


public class RabbitMQPublisher implements IPublisher{

    private final String EXCHANGE_NAME = "ExchangeMVC";
    public RabbitMQPublisher(){

    }


    @Override
    public void publish(String msg, String requestType) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

            //Crea el mensaje de nuevo producto con Save request
            
            Gson gson = new Gson();

            JsonObject inputObj = gson.fromJson(msg, JsonObject.class);
            inputObj.addProperty("requestType", requestType);
            msg = gson.toJson(inputObj);
            
            channel.basicPublish(EXCHANGE_NAME, "", null, msg.getBytes("UTF-8"));

            System.out.println(" [x] Sent '" + message + "'");

        } catch (Exception e) {
            e.printStackTrace();
        }
            
        
    }
    

    
}
