package com.example.demo.controller;

import com.example.demo.entity.Shipper;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.ServiceInterface.IShipperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shipper")
public class ShipperController {

    @Autowired
    private IShipperService shipperService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<String>> addShipper(@RequestBody Shipper shipper) {
        shipperService.addShipper(shipper);
        return ResponseEntity.ok(new ApiResponse<>(200, "Shipper added successfully", null));
    }

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<List<Shipper>>> getAllShippers() {
        List<Shipper> shippers = shipperService.getAllShippers();
        return ResponseEntity.ok(new ApiResponse<>(200, "Success", shippers));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<Shipper>> getShipperById(@PathVariable int id) {
        Shipper shipper = shipperService.getShipperById(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Success", shipper));
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<String>> updateShipper(@RequestBody Shipper shipper) {
        shipperService.updateShipper(shipper);
        return ResponseEntity.ok(new ApiResponse<>(200, "Shipper updated successfully", null));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> deleteShipper(@PathVariable int id) {
        shipperService.removeShipper(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Shipper deleted successfully", null));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<Shipper>>> searchShippersByEmail(@RequestParam String email) {
        List<Shipper> shippers = shipperService.searchShippersByEmail(email);
        return ResponseEntity.ok(new ApiResponse<>(200, "Success", shippers));
    }

    @GetMapping("/filter")
    public ResponseEntity<ApiResponse<List<Shipper>>> filterShippersByStatus(@RequestParam int status) {
        List<Shipper> shippers = shipperService.filterShippersByStatus(status);
        return ResponseEntity.ok(new ApiResponse<>(200, "Success", shippers));
    }
}
