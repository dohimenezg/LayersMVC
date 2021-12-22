package co.edu.unicauca.productconsumer.presentation;

import co.edu.unicauca.productconsumer.domain.service.ServiceModel;
import co.edu.unicauca.productconsumer.infra.RabbitListener;

/**
 *
 * @author Libardo, Julio
 */
public class ClientMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new ClientMain().execute();
    }

    public void execute() {
        ServiceModel model = new ServiceModel();
        // Inyección de dependencias
        GUIListProductViewController instance = new GUIListProductViewController(model);
        instance.setVisible(true);
        model.addObserver(instance);
        Runnable subscriber = new RabbitListener(model);
        new Thread(subscriber).start();
    }
}
