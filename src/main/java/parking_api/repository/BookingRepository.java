package parking_api.repository;

import parking_api.entity.Booking;
import parking_api.entity.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByStatus(BookingStatus status);

    @Query("SELECT b FROM Booking b WHERE b.parkingSlot.id = :slotId " +
            "AND b.status = 'ACTIVE' " +
            "AND b.startTime < :endTime " +
            "AND b.endTime > :startTime")
    List<Booking> findConflictingBookings(
            @Param("slotId") Long slotId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );

    @Query("SELECT b FROM Booking b WHERE b.status = 'ACTIVE' AND b.endTime < :now")
    List<Booking> findExpiredBookings(@Param("now") LocalDateTime now);
}