package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.yearup.data.ProductDao;
import org.yearup.data.ShoppingCartDao;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class MySqlShoppingCartDao extends MySqlDaoBase implements ShoppingCartDao {

    private ProductDao productDao;

    public MySqlShoppingCartDao(DataSource dataSource, ProductDao productDao) {
        super(dataSource);
        this.productDao = productDao;
    }

    @Override
    public ShoppingCart getByUserId (int userId){

        ShoppingCart cart = new ShoppingCart();

        String query = "SELECT * FROM shopping_cart WHERE user_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int productId = rs.getInt("product_id");
                int quantity = rs.getInt("quantity");

                Product product = productDao.getById(productId);
                if (product != null) {
                    ShoppingCartItem item = new ShoppingCartItem(product, quantity);
                    cart.addItem(item);

                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cart;
    }

    @Override
    public ShoppingCart create (int userId, int productId){
        String checkSql = "SELECT quantity FROM shopping_cart WHERE user_id = ? AND product_id = ?";
        String insertSql = "INSERT INTO shopping_cart (user_id, product_id, quantity) VALUES (?, ?, 1)";
        String updateSql = "UPDATE shopping_cart SET quantity = quantity + 1 WHERE user_id = ? AND product_id = ?";

        try (Connection conn = getConnection()) {
            // Check if product already exists in cart
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setInt(1, userId);
                checkStmt.setInt(2, productId);
                ResultSet rs = checkStmt.executeQuery();

                if (rs.next()) {
                    // Product exists, update quantity
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                        updateStmt.setInt(1, userId);
                        updateStmt.setInt(2, productId);
                        updateStmt.executeUpdate();
                    }
                } else {
                    // Product not in cart, insert new record
                    try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                        insertStmt.setInt(1, userId);
                        insertStmt.setInt(2, productId);
                        insertStmt.executeUpdate();
                    }
                }
            }
            // Return updated cart
            return getByUserId(userId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        }

    @Override
    public void update (int userId, int productId, int quantity){
        String query = "UPDATE shopping_cart SET quantity = ? WHERE user_id = ? AND product_id = ?";

        try(Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setInt(1, quantity);
            stmt.setInt(2, userId);
            stmt.setInt(3, productId);
            stmt.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete (int userId){
        String query = "DELETE FROM shopping_cart WHERE user_id = ?";
         try(Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

             stmt.setInt(1, userId);
             stmt.executeUpdate();

         }catch (SQLException e) {
             throw new RuntimeException(e);
         }
    }
}
