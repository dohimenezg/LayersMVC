package co.edu.unicauca.productconsumer.domain.service;

import co.edu.unicauca.productconsumer.infra.ISubscriber;
import co.edu.unicauca.productconsumer.infra.Subject;
import co.edu.unicauca.productconsumer.access.Factory;
import co.edu.unicauca.productconsumer.access.IProductRepository;
import co.edu.unicauca.productconsumer.domain.Product;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * Representa el Modelo del MVC
 *
 * @author Libardo, Julio
 */
public class ServiceModel extends Subject implements ISubscriber{

    // Ahora hay una dependencia de una abstracción, no es algo concreto,
    // no sabe cómo está implementado.
    private IProductRepository repository;

    /**
     * Inyección de dependencias en el constructor.Ya no conviene que el mismo
     * servicio cree un repositorio concreto
     *
     */
    public ServiceModel() {
        repository = Factory.getInstance().getRepository();
    }

    public boolean saveProduct(Product newProduct) {

        //Validate product
        if (newProduct == null || newProduct.getProductId() < 0 || newProduct.getName().isEmpty()) {
            return false;
        }

        // Antes de guardar, falta validar que no exista el producto
        Product productAux = repository.find(newProduct.getProductId());

        if (productAux != null) {
            // El producto ya existe
            return false;
        }

        repository.save(newProduct);
        // Notifica a todos los observadores que el modelo cambió
        this.notifyAllObserves();
        return true;
    }

    public List<Product> listProducts() {
        List<Product> products = new ArrayList<>();
        products = repository.list();
        return products;
    }

    public boolean updateProduct(Product newProduct) {
        //Validate product
        if (newProduct == null || newProduct.getProductId() < 0 || newProduct.getName().isEmpty()) {
            return false;
        }

        // Notifica a todos los observadores que el modelo cambió
        repository.update(newProduct);
        this.notifyAllObserves();
        return true;
    }

    @Override
    public void onMessage(String msg) {
        Gson gson = new Gson();
        JsonObject outObject = gson.fromJson(msg, JsonObject.class);
        Product newProduct = new Product(outObject.get("productId").getAsInt(),outObject.get("name").getAsString(),outObject.get("price").getAsDouble());
        String requestType = outObject.get("requestType").getAsString();
        if(requestType.equals("SAVE")){
            saveProduct(newProduct);
        } else if (requestType.equals("UPDATE")){
            updateProduct(newProduct);
        }
    }

}