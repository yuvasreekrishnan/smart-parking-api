package parking_api.dto;

import lombok.Data;
import parking_api.entity.BookingStatus;
import java.time.LocalDateTime;

@Data
public class BookingResponse {
    private Long bookingId;
    private String vehicleNumber;
    private String ownerName;
    private String slotNumber;
    private String parkingLotName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private BookingStatus status;
}