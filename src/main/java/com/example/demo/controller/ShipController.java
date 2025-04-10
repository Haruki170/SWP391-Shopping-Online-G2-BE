package com.example.demo.controller;

import com.example.demo.entity.Shipper;
import com.example.demo.service.ShipperService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shippers")
@RequiredArgsConstructor
public class ShipController {

    private final ShipService shipService;

    @GetMapping
    public ResponseEntity<List<Shipper>> getAllShippers() {
        return ResponseEntity.ok(shipService.getAllShippers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Shipper> getShipperById(@PathVariable Long id) {
        return shipService.getShipperById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Shipper> createShipper(@RequestBody Shipper shipper) {
        return ResponseEntity.ok(shipService.createShipper(shipper));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Shipper> updateShipper(@PathVariable Long id, @RequestBody Shipper shipper) {
        return ResponseEntity.ok(shipService.updateShipper(id, shipper));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShipper(@PathVariable Long id) {
        shipService.deleteShipper(id);
        return ResponseEntity.noContent().build();
    }
}
