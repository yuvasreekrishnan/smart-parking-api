package parking_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import parking_api.dto.ParkingLotRequest;
import parking_api.entity.ParkingLot;
import parking_api.service.ParkingLotService;

import java.util.List;

@RestController
@RequestMapping("/api/lots")
@RequiredArgsConstructor
public class ParkingLotController {

    private final ParkingLotService parkingLotService;

    @PostMapping
    public ResponseEntity<ParkingLot> createLot(@RequestBody ParkingLotRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(parkingLotService.createLot(request));
    }

    @GetMapping
    public ResponseEntity<List<ParkingLot>> getAllLots() {
        return ResponseEntity.ok(parkingLotService.getAllLots());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingLot> getLotById(@PathVariable Long id) {
        return ResponseEntity.ok(parkingLotService.getLotById(id));
    }
}