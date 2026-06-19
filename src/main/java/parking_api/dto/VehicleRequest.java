package parking_api.dto;

import lombok.Data;
import parking_api.entity.SlotType;

@Data
public class VehicleRequest {
    private String vehicleNumber;
    private String ownerName;
    private SlotType vehicleType;
}