package co.edu.unicauca.layersmvc.infra;

import co.edu.unicauca.layersmvc.domain.Product;

public interface IPublisher {

    void publish(Product product, String requestType);
}
