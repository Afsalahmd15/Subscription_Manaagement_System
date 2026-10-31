package com.odoo.brightloop.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.odoo.brightloop.repository.AdminRepository;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public enum ProductStatus {
        PRODUCT_SUCCESS,
        PRODUCT_FAILED,
        INVALID_PRICE,
        INVALID_VARIANT
    }

    public enum PlanStatus {
        PLAN_SUCCESS,
        PLAN_FAILED,
        INVALID_PRICE,
        INVALID_QUANTITY,
        INVALID_DATE,
        INVALID_INTERVAL
    }

    // ADD PRODUCT
    public ProductStatus addProduct(
            String name,
            String type,
            BigDecimal salesPrice,
            BigDecimal costPrice,
            String notes,
            String variantName,
            String variantValue,
            BigDecimal additionalPrice) {

        if (salesPrice.compareTo(BigDecimal.ZERO) < 0
                || costPrice.compareTo(BigDecimal.ZERO) < 0
                || additionalPrice.compareTo(BigDecimal.ZERO) < 0) {

            return ProductStatus.INVALID_PRICE;
        }

        if ((variantName == null || variantName.isBlank())
                != (variantValue == null || variantValue.isBlank())) {

            return ProductStatus.INVALID_VARIANT;
        }

        Integer productId = adminRepository.addProduct(
            name,
            type,
            salesPrice,
            costPrice,
            notes
        );

        if (productId == null) {
            return ProductStatus.PRODUCT_FAILED;
        }

        // ADD VARIANT IF PROVIDED
        if (variantName != null && !variantName.isBlank()) {

            boolean variantAdded = adminRepository.addProductVariant(
                productId,
                variantName,
                variantValue,
                additionalPrice
            );

            if (!variantAdded) {
                return ProductStatus.PRODUCT_FAILED;
            }
        }

        return ProductStatus.PRODUCT_SUCCESS;
    }

    // GET ALL PRODUCTS
    public List<Map<String, Object>> getAllProducts() {

        return adminRepository.getAllProducts();
    }

    // ADD SUBSCRIPTION PLAN
    public PlanStatus addPlan(
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

        if (price.compareTo(BigDecimal.ZERO) < 0) {
            return PlanStatus.INVALID_PRICE;
        }

        if (minQuantity < 1) {
            return PlanStatus.INVALID_QUANTITY;
        }

        if (availableUntil.isBefore(availableFrom)) {
            return PlanStatus.INVALID_DATE;
        }

        if (!billingInterval.equals("DAILY")
                && !billingInterval.equals("WEEKLY")
                && !billingInterval.equals("MONTHLY")
                && !billingInterval.equals("YEARLY")) {

            return PlanStatus.INVALID_INTERVAL;
        }

        boolean result = adminRepository.addPlan(
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

        if (!result) {
            return PlanStatus.PLAN_FAILED;
        }

        return PlanStatus.PLAN_SUCCESS;
    }
}
