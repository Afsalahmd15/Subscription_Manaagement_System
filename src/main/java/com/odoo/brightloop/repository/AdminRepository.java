package com.odoo.brightloop.repository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.springframework.stereotype.Repository;

import com.odoo.brightloop.database.DBConnection;

@Repository
public class AdminRepository {

    // ADD PRODUCT
    public Integer addProduct(
            String name,
            String type,
            BigDecimal salesPrice,
            BigDecimal costPrice,
            String notes) {

        String query = """
                INSERT INTO products
                (name, type, sales_price, cost_price, notes)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                query,
                Statement.RETURN_GENERATED_KEYS
            )
        ) {

            ps.setString(1, name);
            ps.setString(2, type);
            ps.setBigDecimal(3, salesPrice);
            ps.setBigDecimal(4, costPrice);
            ps.setString(5, notes);

            int rows = ps.executeUpdate();

            if (rows > 0) {

                try (ResultSet rs = ps.getGeneratedKeys()) {

                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // ADD PRODUCT VARIANT
    public boolean addProductVariant(
            Integer productId,
            String variantName,
            String variantValue,
            BigDecimal additionalPrice) {

        String query = """
                INSERT INTO product_variants
                (product_id, variant_name, variant_value, additional_price)
                VALUES (?, ?, ?, ?)
                """;

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)
        ) {

            ps.setInt(1, productId);
            ps.setString(2, variantName);
            ps.setString(3, variantValue);
            ps.setBigDecimal(4, additionalPrice);

            int rows = ps.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // GET ALL PRODUCTS
    public List<Map<String, Object>> getAllProducts() {

        List<Map<String, Object>> products = new ArrayList<>();

        String query = "SELECT id, name FROM products ORDER BY name";

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Map<String, Object> product = new HashMap<>();

                product.put("id", rs.getInt("id"));
                product.put("name", rs.getString("name"));

                products.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    // ADD SUBSCRIPTION PLAN
    public boolean addPlan(
            Integer productId,
            BigDecimal price,
            String billingInterval,
            Integer minQuantity,
            LocalDate availableFrom,
            LocalDate availableUntil,
            boolean autoClose,
            boolean allowEarlyClose,
            boolean allowPause,
            boolean allowRenew) {

        String query = """
                INSERT INTO plans
                (
                    product_id,
                    price,
                    billing_interval,
                    min_quantity,
                    available_from,
                    available_until,
                    auto_close,
                    allow_early_close,
                    allow_pause,
                    allow_renew
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)
        ) {

            ps.setInt(1, productId);
            ps.setBigDecimal(2, price);
            ps.setString(3, billingInterval);
            ps.setInt(4, minQuantity);
            ps.setObject(5, availableFrom);
            ps.setObject(6, availableUntil);
            ps.setBoolean(7, autoClose);
            ps.setBoolean(8, allowEarlyClose);
            ps.setBoolean(9, allowPause);
            ps.setBoolean(10, allowRenew);

            int rows = ps.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}