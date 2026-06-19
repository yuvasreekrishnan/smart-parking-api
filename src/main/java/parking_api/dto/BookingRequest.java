package parking_api.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BookingRequest {
    private Long vehicleId;
    private Long parkingSlotId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}