
package com.odoo.brightloop.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AdminRepository {

    private final JdbcTemplate jdbcTemplate;

    public AdminRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // INSERT PRODUCT
    public Integer addProduct(
            String name,
            String type,
            BigDecimal salesPrice,
            BigDecimal costPrice,
            String notes) {

        return jdbcTemplate.queryForObject(
            """
            INSERT INTO products
            (name, type, sales_price, cost_price, notes)
            VALUES (?, ?, ?, ?, ?)
            RETURNING id
            """,
            Integer.class,
            name, type, salesPrice, costPrice, notes
        );
    }

    // INSERT PRODUCT VARIANT
    public void addProductVariant(
            Integer productId,
            String variantName,
            String variantValue,
            BigDecimal additionalPrice) {

        jdbcTemplate.update(
            """
            INSERT INTO product_variants
            (product_id, variant_name, variant_value, additional_price)
            VALUES (?, ?, ?, ?)
            """,
            productId, variantName, variantValue, additionalPrice
        );
    }

    // GET ALL PRODUCTS
    public List<Map<String, Object>> getAllProducts() {

        return jdbcTemplate.queryForList(
            """
            SELECT id, name
            FROM products
            ORDER BY name
            """
        );
    }

    // INSERT SUBSCRIPTION PLAN
    public void addPlan(
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

        jdbcTemplate.update(
            """
            INSERT INTO plans (
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
            """,
            productId,
            price,
            billingInterval,
            minQuantity,
            availableFrom,
            availableUntil,
            autoClose,
            allowEarlyClose,
            allowPause,
            allowRenew
        );
    }
}

