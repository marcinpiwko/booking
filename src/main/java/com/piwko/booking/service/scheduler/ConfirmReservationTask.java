package com.piwko.booking.service.scheduler;

import com.google.common.collect.Iterables;
import com.piwko.booking.service.interfaces.ReservationService;
import com.piwko.booking.util.CollectionsUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Log4j2
@Component
@RequiredArgsConstructor
public class ConfirmReservationTask {

    private final ReservationService reservationService;

    @Value("${booking.scheduler.confirmReservations.partitionSize}")
    private Integer partitionSize;

    @Scheduled(cron = "${booking.scheduler.confirmReservations.cron}")
    public void process() {
        log.info("Start executing confirm reservations task");
        Long startTime = System.currentTimeMillis();
        Set<Long> reservationIds = reservationService.getPendingReservationsToConfirm();
        if (CollectionsUtil.isEmpty(reservationIds)) {
            log.info("Nothing to confirm, end of job");
            return;
        }
        log.info("Found " + reservationIds.size() + " reservations to confirm");
        for (Set<Long> partitionSet : createPartitions(reservationIds)) {
            reservationService.confirmPendingReservations(partitionSet);
        }
        Long endTime = System.currentTimeMillis();
        log.info("End of confirm reservations job, execution time: " + (endTime - startTime) + " ms");
    }

    private List<Set<Long>> createPartitions(Set<Long> reservationIds) {
        List<Set<Long>> partitions = new ArrayList<>();
        if (reservationIds.size() <= partitionSize) {
            partitions.add(reservationIds);
            return partitions;
        }
        for (List<Long> partition : Iterables.partition(reservationIds, partitionSize)) {
            partitions.add(new HashSet<>(partition));
        }
        return partitions;
    }
}
