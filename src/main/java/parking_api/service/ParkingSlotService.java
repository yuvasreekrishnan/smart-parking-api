package parking_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import parking_api.dto.ParkingSlotRequest;
import parking_api.entity.ParkingLot;
import parking_api.entity.ParkingSlot;
import parking_api.entity.SlotStatus;
import parking_api.entity.SlotType;
import parking_api.repository.ParkingLotRepository;
import parking_api.repository.ParkingSlotRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParkingSlotService {

    private final ParkingSlotRepository parkingSlotRepository;
    private final ParkingLotRepository parkingLotRepository;

    public ParkingSlot createSlot(ParkingSlotRequest request) {
        ParkingLot lot = parkingLotRepository.findById(request.getParkingLotId())
                .orElseThrow(() -> new RuntimeException("Parking lot not found with id: " + request.getParkingLotId()));

        ParkingSlot slot = new ParkingSlot();
        slot.setSlotNumber(request.getSlotNumber());
        slot.setSlotType(request.getSlotType());
        slot.setParkingLot(lot);
        slot.setStatus(SlotStatus.AVAILABLE);

        return parkingSlotRepository.save(slot);
    }

    public List<ParkingSlot> getAvailableSlots(Long lotId, SlotType slotType) {
        return parkingSlotRepository.findByParkingLotIdAndSlotTypeAndStatus(
                lotId, slotType, SlotStatus.AVAILABLE
        );
    }

    public List<ParkingSlot> getAllSlots() {
        return parkingSlotRepository.findAll();
    }

    public ParkingSlot getSlotById(Long id) {
        return parkingSlotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Slot not found with id: " + id));
    }
}