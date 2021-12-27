package co.edu.unicauca.productconsumer.access;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

import co.edu.unicauca.productconsumer.infra.Utilities;

/**
 * Fabrica que se encarga de instanciar ProductRepository o cualquier otro que
 * se cree en el futuro.
 *
 * @author Libardo, Julio
 */
public class Factory {

    private static Factory instance;

    private Factory() {
    }

    /**
     * Clase singleton
     *
     * @return
     */
    public static Factory getInstance() {

        if (instance == null) {
            instance = new Factory();
        }
        return instance;
    }

    /**
     * Método que crea una instancia concreta de la jerarquia IProductRepository
     *
     * @return una clase hija de la abstracción IProductRepository
     */
    public IProductRepository getRepository(String type) {
        IProductRepository result = null;
        
        try { 
            switch (type) {
                case "memory":
                    result = (IProductRepository) Class.forName(Utilities.loadProperty("repositoryClass")).getConstructor().newInstance();
                    break;
                case "database":
                    result = (IProductRepository) Class.forName(Utilities.loadProperty("repositoryDBClass")).getConstructor().newInstance();
                    break;
                default:
                    break;
            }
        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(Factory.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(result == null)
            result = new ProductRepository();
        return result;
    }

}
