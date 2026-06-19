package parking_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import parking_api.dto.ParkingLotRequest;
import parking_api.entity.ParkingLot;
import parking_api.repository.ParkingLotRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParkingLotService {

    private final ParkingLotRepository parkingLotRepository;

    public ParkingLot createLot(ParkingLotRequest request) {
        ParkingLot lot = new ParkingLot();
        lot.setName(request.getName());
        lot.setLocation(request.getLocation());
        lot.setTotalSlots(request.getTotalSlots());
        return parkingLotRepository.save(lot);
    }

    public List<ParkingLot> getAllLots() {
        return parkingLotRepository.findAll();
    }

    public ParkingLot getLotById(Long id) {
        return parkingLotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Parking lot not found with id: " + id));
    }
}