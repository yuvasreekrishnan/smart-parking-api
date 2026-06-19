package parking_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import parking_api.dto.ParkingSlotRequest;
import parking_api.entity.ParkingSlot;
import parking_api.entity.SlotType;
import parking_api.service.ParkingSlotService;

import java.util.List;

@RestController
@RequestMapping("/api/slots")
@RequiredArgsConstructor
public class ParkingSlotController {

    private final ParkingSlotService parkingSlotService;

    @PostMapping
    public ResponseEntity<ParkingSlot> createSlot(@RequestBody ParkingSlotRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(parkingSlotService.createSlot(request));
    }

    @GetMapping
    public ResponseEntity<List<ParkingSlot>> getAllSlots() {
        return ResponseEntity.ok(parkingSlotService.getAllSlots());
    }

    @GetMapping("/available")
    public ResponseEntity<List<ParkingSlot>> getAvailableSlots(
            @RequestParam Long lotId,
            @RequestParam SlotType slotType) {
        return ResponseEntity.ok(parkingSlotService.getAvailableSlots(lotId, slotType));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingSlot> getSlotById(@PathVariable Long id) {
        return ResponseEntity.ok(parkingSlotService.getSlotById(id));
    }
}