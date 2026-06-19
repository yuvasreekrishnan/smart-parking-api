package parking_api.dto;

import lombok.Data;
import parking_api.entity.SlotType;

@Data
public class ParkingSlotRequest {
    private String slotNumber;
    private SlotType slotType;
    private Long parkingLotId;
}