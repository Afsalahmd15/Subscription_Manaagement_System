package com.odoo.brightloop.controller;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;

import com.odoo.brightloop.service.AdminService;
import com.odoo.brightloop.service.AdminService.ProductStatus;
import com.odoo.brightloop.service.AdminService.PlanStatus;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // ADMIN DASHBOARD
    @GetMapping
    public String adminDashboard(
            HttpSession session,
            Model model) {

        if (!"ADMIN".equals(session.getAttribute("role"))) {
            return "redirect:/";
        }

        model.addAttribute(
            "products",
            adminService.getAllProducts()
        );

        return "admin";
    }

    // ADD PRODUCT
    @PostMapping("/products")
    public String addProduct(
            @RequestParam String name,
            @RequestParam String type,
            @RequestParam BigDecimal salesPrice,
            @RequestParam BigDecimal costPrice,
            @RequestParam(required = false) String notes,
            @RequestParam(required = false) String variantName,
            @RequestParam(required = false) String variantValue,
            @RequestParam(defaultValue = "0") BigDecimal additionalPrice,
            HttpSession session,
            Model model) {

        if (!"ADMIN".equals(session.getAttribute("role"))) {
            return "redirect:/";
        }

        ProductStatus status = adminService.addProduct(
            name,
            type,
            salesPrice,
            costPrice,
            notes,
            variantName,
            variantValue,
            additionalPrice
        );

        if (status == ProductStatus.PRODUCT_SUCCESS) {

            model.addAttribute(
                "successMessage",
                "Product added successfully!"
            );

        } else if (status == ProductStatus.INVALID_PRICE) {

            model.addAttribute(
                "errorMessage",
                "Prices cannot be negative."
            );

        } else if (status == ProductStatus.INVALID_VARIANT) {

            model.addAttribute(
                "errorMessage",
                "Both variant name and variant value are required."
            );

        } else {

            model.addAttribute(
                "errorMessage",
                "Unable to add product."
            );
        }

        model.addAttribute(
            "products",
            adminService.getAllProducts()
        );

        return "admin";
    }

    // ADD SUBSCRIPTION PLAN
    @PostMapping("/plans")
    public String addPlan(
            @RequestParam Integer productId,
            @RequestParam BigDecimal price,
            @RequestParam String billingInterval,
            @RequestParam Integer minQuantity,
            @RequestParam LocalDate availableFrom,
            @RequestParam LocalDate availableUntil,
            @RequestParam(defaultValue = "false") boolean autoClose,
            @RequestParam(defaultValue = "false") boolean allowEarlyClose,
            @RequestParam(defaultValue = "false") boolean allowPause,
            @RequestParam(defaultValue = "false") boolean allowRenew,
            HttpSession session,
            Model model) {

        if (!"ADMIN".equals(session.getAttribute("role"))) {
            return "redirect:/";
        }

        PlanStatus status = adminService.addPlan(
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

        if (status == PlanStatus.PLAN_SUCCESS) {

            model.addAttribute(
                "successMessage",
                "Subscription plan added successfully!"
            );

        } else if (status == PlanStatus.INVALID_PRICE) {

            model.addAttribute(
                "errorMessage",
                "Plan price cannot be negative."
            );

        } else if (status == PlanStatus.INVALID_QUANTITY) {

            model.addAttribute(
                "errorMessage",
                "Minimum quantity must be at least 1."
            );

        } else if (status == PlanStatus.INVALID_DATE) {

            model.addAttribute(
                "errorMessage",
                "Available until date cannot be before available from date."
            );

        } else if (status == PlanStatus.INVALID_INTERVAL) {

            model.addAttribute(
                "errorMessage",
                "Invalid billing interval."
            );

        } else {

            model.addAttribute(
                "errorMessage",
                "Unable to add subscription plan."
            );
        }

        model.addAttribute(
            "products",
            adminService.getAllProducts()
        );

        return "admin";
    }
}
