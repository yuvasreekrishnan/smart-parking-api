package parking_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import parking_api.dto.VehicleRequest;
import parking_api.entity.Vehicle;
import parking_api.repository.VehicleRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public Vehicle registerVehicle(VehicleRequest request) {
        vehicleRepository.findByVehicleNumber(request.getVehicleNumber())
                .ifPresent(v -> {
                    throw new RuntimeException("Vehicle already registered: " + request.getVehicleNumber());
                });

        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleNumber(request.getVehicleNumber());
        vehicle.setOwnerName(request.getOwnerName());
        vehicle.setVehicleType(request.getVehicleType());

        return vehicleRepository.save(vehicle);
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public Vehicle getVehicleById(Long id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found with id: " + id));
    }
}