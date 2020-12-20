package com.piwko.booking.persistence.repository;

import com.piwko.booking.persistence.model.Reservation;
import com.piwko.booking.persistence.model.User;
import com.piwko.booking.persistence.model.VirtualUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query(value = "SELECT res_id FROM pending_reservations WHERE res_date < NOW()", nativeQuery = true)
    Set<Long> findPendingReservationsToConfirm();

    @Query(value = "UPDATE reservations SET res_status = ?1 WHERE res_id IN ?2", nativeQuery = true)
    @Modifying
    void confirmPendingReservations(String status, Set<Long> reservationIds);

    @Query(value = "SELECT CASE WHEN COUNT(R) > 0 THEN FALSE ELSE TRUE END FROM RESERVATIONS R WHERE R.RES_ID IN " +
            "(SELECT P.RES_ID FROM PENDING_RESERVATIONS P WHERE P.RES_DATE BETWEEN ?1 AND ?2) AND R.RES_EMP_ID = ?3", nativeQuery = true)
    boolean isDateFreeForReservation(LocalDateTime from, LocalDateTime to, Long employeeId);

    Optional<Reservation> findByIdAndUser(Long reservationId, User user);

    @Query(value = "SELECT * FROM RESERVATIONS WHERE RES_ID = ?1 AND RES_LOC_ID IN " +
            "(SELECT LOC_ID FROM LOCATIONS WHERE LOC_CMP_ID IN (SELECT CMP_ID FROM COMPANIES WHERE LOWER(CMP_CODE) = LOWER(?2)))", nativeQuery = true)
    Optional<Reservation> findByIdAndCompanyUser(Long reservationId, String companyCode);

    Page<Reservation> findAllByUser(User user, Pageable pageable);

    @Query(value = "SELECT * FROM RESERVATIONS WHERE RES_LOC_ID = (SELECT LOC_ID FROM LOCATIONS WHERE LOWER(LOC_CODE) = LOWER(?1))", nativeQuery = true)
    Page<Reservation> findAllByLocationCode(String locationCode, Pageable pageable);

    @Query(value = "SELECT * FROM RESERVATIONS WHERE RES_LOC_ID = (SELECT LOC_ID FROM LOCATIONS WHERE LOWER(LOC_CODE) = LOWER(?1)) " +
            "AND RES_STATUS = ?2", nativeQuery = true)
    Page<Reservation> findAllByLocationCodeAndStatus(String locationCode, String status, Pageable pageable);

    Page<Reservation> findAllByStatus(String status, Pageable pageable);

    List<Reservation> findAllByVirtualUser(VirtualUser virtualUser);
}
