package co.edu.unicauca.layersmvc.access;

import co.edu.unicauca.layersmvc.domain.Product;
import co.edu.unicauca.layersmvc.domain.service.ServiceModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Es una implementación que tiene libertad de hacer una implementación del
 * contrato. Lo puede hacer con Sqlite, postgres, mysql, u otra tecnología
 *
 * @author Libardo, Julio
 */
public class ProductRepository implements IProductRepository {

    private Connection conn;

    public ProductRepository() {
        initDatabase();
    }

    @Override
    public boolean save(Product newProduct) {

        try {
            //Validate product
            if (newProduct == null || newProduct.getProductId() < 0 || newProduct.getName().isEmpty()) {
                return false;
            }
            //this.connect();

            String sql = "INSERT INTO Product ( ProductId, Name, Price ) "
                    + "VALUES ( ?, ?, ? )";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, newProduct.getProductId());
            pstmt.setString(2, newProduct.getName());
            pstmt.setDouble(3, newProduct.getPrice());
            pstmt.executeUpdate();
            //this.disconnect();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ServiceModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public List<Product> list() {
        List<Product> products = new ArrayList<>();
        try {

            String sql = "SELECT ProductId, Name, Price FROM Product";
            //this.connect();

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Product newProduct = new Product();
                newProduct.setProductId(rs.getInt("ProductId"));
                newProduct.setName(rs.getString("Name"));
                newProduct.setPrice(rs.getDouble("Price"));

                products.add(newProduct);
            }
            //this.disconnect();

        } catch (SQLException ex) {
            Logger.getLogger(ServiceModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return products;
    }

    private void initDatabase() {
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS Product (\n"
                + "	ProductId integer PRIMARY KEY,\n"
                + "	Name text NOT NULL,\n"
                + "	Price real\n"
                + ");";

        try {
            this.connect();
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            //this.disconnect();

        } catch (SQLException ex) {
            Logger.getLogger(ServiceModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void connect() {
        // SQLite connection string
        //String url = "jdbc:sqlite:./mydatabase.db";
        String url = "jdbc:sqlite::memory:";

        try {
            conn = DriverManager.getConnection(url);

        } catch (SQLException ex) {
            Logger.getLogger(ServiceModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void disconnect() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public boolean update(Product newProduct) {
        try {
            //Validate product
            if (newProduct == null || newProduct.getProductId() < 0 || newProduct.getName().isEmpty()) {
                return false;
            }
            //this.connect();

            String sql = "UPDATE Product "
                    + "SET Name = ?, "
                    + "Price = ? "
                    + "WHERE ProductId = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newProduct.getName());
            pstmt.setDouble(2, newProduct.getPrice());
            pstmt.setInt(3, newProduct.getProductId());
            pstmt.executeUpdate();
            //this.disconnect();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ServiceModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;

    }

    @Override
    public Product find(int id) {
        Product product = null;
        try {

            String sql = "SELECT ProductId, Name, Price FROM Product Where ProductId=" + id;
            //this.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                product = new Product();
                product.setProductId(rs.getInt("ProductId"));
                product.setName(rs.getString("Name"));
                product.setPrice(rs.getDouble("Price"));
            }
            //this.disconnect();

        } catch (SQLException ex) {
            Logger.getLogger(ServiceModel.class.getName()).log(Level.SEVERE, "Error al buscar el producto en la base de datos", ex);
        }
        return product;
    }

}
