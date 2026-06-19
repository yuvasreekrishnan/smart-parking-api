package parking_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import parking_api.dto.BookingRequest;
import parking_api.dto.BookingResponse;
import parking_api.entity.*;
import parking_api.repository.BookingRepository;
import parking_api.repository.ParkingSlotRepository;
import parking_api.repository.VehicleRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final ParkingSlotRepository parkingSlotRepository;
    private final VehicleRepository vehicleRepository;

    public BookingResponse createBooking(BookingRequest request) {

        // Step 1: fetch vehicle
        Vehicle vehicle = vehicleRepository.findById(request.getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        // Step 2: fetch slot
        ParkingSlot slot = parkingSlotRepository.findById(request.getParkingSlotId())
                .orElseThrow(() -> new RuntimeException("Slot not found"));

        // Step 3: check vehicle type matches slot type
        if (!vehicle.getVehicleType().equals(slot.getSlotType())) {
            throw new RuntimeException("Vehicle type does not match slot type");
        }

        // Step 4: check slot is available
        if (slot.getStatus() == SlotStatus.BOOKED) {
            throw new RuntimeException("Slot is already booked");
        }

        // Step 5: conflict detection — the key logic
        List<Booking> conflicts = bookingRepository.findConflictingBookings(
                slot.getId(),
                request.getStartTime(),
                request.getEndTime()
        );
        if (!conflicts.isEmpty()) {
            throw new RuntimeException("Slot is already booked for this time period");
        }

        // Step 6: create booking
        Booking booking = new Booking();
        booking.setParkingSlot(slot);
        booking.setVehicle(vehicle);
        booking.setStartTime(request.getStartTime());
        booking.setEndTime(request.getEndTime());
        booking.setStatus(BookingStatus.ACTIVE);

        // Step 7: mark slot as booked
        slot.setStatus(SlotStatus.BOOKED);
        parkingSlotRepository.save(slot);

        Booking saved = bookingRepository.save(booking);

        // Step 8: build and return response
        return mapToResponse(saved);
    }

    public BookingResponse cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new RuntimeException("Booking is already cancelled");
        }

        // free the slot back
        ParkingSlot slot = booking.getParkingSlot();
        slot.setStatus(SlotStatus.AVAILABLE);
        parkingSlotRepository.save(slot);

        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);

        return mapToResponse(booking);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public List<Booking> getBookingsByStatus(BookingStatus status) {
        return bookingRepository.findByStatus(status);
    }

    // Runs every 5 minutes automatically
    @Scheduled(fixedRate = 300000)
    public void autoReleaseExpiredBookings() {
        List<Booking> expired = bookingRepository.findExpiredBookings(LocalDateTime.now());
        for (Booking booking : expired) {
            booking.setStatus(BookingStatus.COMPLETED);
            ParkingSlot slot = booking.getParkingSlot();
            slot.setStatus(SlotStatus.AVAILABLE);
            parkingSlotRepository.save(slot);
            bookingRepository.save(booking);
        }
        if (!expired.isEmpty()) {
            System.out.println("Auto-released " + expired.size() + " expired bookings");
        }
    }

    private BookingResponse mapToResponse(Booking booking) {
        BookingResponse response = new BookingResponse();
        response.setBookingId(booking.getId());
        response.setVehicleNumber(booking.getVehicle().getVehicleNumber());
        response.setOwnerName(booking.getVehicle().getOwnerName());
        response.setSlotNumber(booking.getParkingSlot().getSlotNumber());
        response.setParkingLotName(booking.getParkingSlot().getParkingLot().getName());
        response.setStartTime(booking.getStartTime());
        response.setEndTime(booking.getEndTime());
        response.setStatus(booking.getStatus());
        return response;
    }
}