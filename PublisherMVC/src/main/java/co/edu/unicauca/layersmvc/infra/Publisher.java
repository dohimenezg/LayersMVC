package co.edu.unicauca.layersmvc.infra;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import co.edu.unicauca.layersmvc.domain.Product;

public class Publisher implements IPublisher {

    private final String EXCHANGE_NAME = "ExchangeMVC";

    public Publisher() {
        

    }

    @Override
    public void publish(Product product, String requestType) {

        Gson gson = new Gson();
        String msgJson = gson.toJson(product);
        JsonObject inputObj = gson.fromJson(msgJson, JsonObject.class);
        inputObj.addProperty("requestType", requestType);
        msgJson = gson.toJson(inputObj);

        ConnectionFactory factory = new ConnectionFactory();

        factory.setHost("3.143.212.31");
        factory.setPort(5672);
        
        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
            channel.basicPublish(EXCHANGE_NAME, "", null, msgJson.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + msgJson + "'");
        } catch (Exception e) {
            e.printStackTrace();
        }
        

        

    }

}
