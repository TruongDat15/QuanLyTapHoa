package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/inventory/stock")
public class InventoryController {

    @GetMapping
    public String checkStock() {
        return "Inventory Service is up and running!"+ LocalDateTime.now().toString();
    }

    // kiểm tra tồn kho sản phẩm theo mã vạch
    @GetMapping("/check/{barcode}")
    public String checkProductStock(@PathVariable String barcode) {

        return "Stock for product with barcode " + barcode + " is sufficient.";
    }

    // Kiemer tra nhieuef san pham theo ma vach qua POST
    @PostMapping("check-stock")
    public String checkStockPost(@RequestParam String barcode) {
        return "POST: Stock for product with barcode " + barcode + " is sufficient.";
    }

    // Cập nhật tồn kho
    @PutMapping("/update-stock")
    public String updateStock(@RequestParam String barcode, @RequestParam Integer quantity) {
        return "Updated stock for product with barcode " + barcode + " by " + quantity + " units.";
    }

    // Tạm giữ sản phẩm


    // HUỷ tạm giữ sản phẩm

    // Sắp hết hàng cảnh báo

//    GET    /api/inventory/{productId}    # Kiểm tra tồn kho
//    POST   /api/inventory/check-stock    # Kiểm tra stock nhiều sản phẩm
//    POST   /api/inventory/update-stock   # Cập nhật tồn kho
//    POST   /api/inventory/reserve        # Tạm giữ sản phẩm
//    POST   /api/inventory/cancel-reserve # Hủy tạm giữ
//    GET    /api/inventory/low-stock      # Sản phẩm sắp hết hàng

}
