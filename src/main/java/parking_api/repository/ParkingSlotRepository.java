package parking_api.repository;

import parking_api.entity.ParkingSlot;
import parking_api.entity.SlotStatus;
import parking_api.entity.SlotType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingSlotRepository extends JpaRepository<ParkingSlot, Long> {

    List<ParkingSlot> findByParkingLotIdAndSlotTypeAndStatus(
            Long parkingLotId,
            SlotType slotType,
            SlotStatus status
    );

    @Query("SELECT COUNT(s) FROM ParkingSlot s WHERE s.parkingLot.id = :lotId AND s.status = 'AVAILABLE'")
    Long countAvailableSlots(@Param("lotId") Long lotId);
}