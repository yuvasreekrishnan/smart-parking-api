package parking_api.dto;

import lombok.Data;

@Data
public class ParkingLotRequest {
    private String name;
    private String location;
    private Integer totalSlots;
}