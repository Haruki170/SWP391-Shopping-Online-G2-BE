package com.example.demo.controller;

import com.example.demo.entity.ReturnOrder;

public class ReturnOrderController {
    @Autowired
    private ReturnOrderService returnOrderService;

    // Shop Owner gửi yêu cầu hoàn trả
    @PostMapping
    public ResponseEntity<ReturnOrder> createReturnOrder(@RequestBody ReturnOrder returnOrder) {
        return ResponseEntity.ok(returnOrderService.createReturnOrder(returnOrder));
    }

    // Admin xem tất cả yêu cầu hoàn trả
    @GetMapping
    public ResponseEntity<List<ReturnOrder>> getAllReturnOrders() {
        return ResponseEntity.ok(returnOrderService.getAllReturnOrders());
    }

    // Shop Owner xem các yêu cầu của họ
    @GetMapping("/shop/{shopOwnerId}")
    public ResponseEntity<List<ReturnOrder>> getByShopOwner(@PathVariable Long shopOwnerId) {
        return ResponseEntity.ok(returnOrderService.getReturnOrdersByShopOwner(shopOwnerId));
    }

    // Admin cập nhật trạng thái đơn hoàn trả
    @PostMapping("/{id}/status")
    public ResponseEntity<ReturnOrder> updateStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> payload
    ) {
        String status = payload.get("status"); // APPROVED or REJECTED
        String adminNote = payload.get("adminNote");
        return ResponseEntity.ok(returnOrderService.updateStatus(id, status, adminNote));
    }
}
