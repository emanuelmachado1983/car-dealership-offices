package org.emanuel.offices.controller;

import org.emanuel.offices.business.IDeliveryScheduleService;
import org.emanuel.offices.business.dto.DeliveryScheduleGetDto;
import org.emanuel.offices.business.dto.DeliverySchedulePostDto;
import org.emanuel.offices.exceptions.DeliveryScheduleBadRequestException;
import org.emanuel.offices.exceptions.DeliveryScheduleNotExistsException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/delivery-schedules-configuration")
public class DeliveryScheduleController {
    private final IDeliveryScheduleService deliveryScheduleService;

    public DeliveryScheduleController(IDeliveryScheduleService deliveryScheduleService) {
        this.deliveryScheduleService = deliveryScheduleService;
    }

    @GetMapping()
    public ResponseEntity<List<DeliveryScheduleGetDto>> getDeliverySchedules(@RequestParam(required = false) Long officeTo) throws DeliveryScheduleBadRequestException {
        if (officeTo == null) {
            return getAllDeliverySchedules();
        } else {
            return getDeliverySchedulesByOfficeTo(officeTo);
        }
    }

    @PostMapping
    public ResponseEntity<Void> addDeliverySchedule(
            @RequestBody DeliverySchedulePostDto post) throws DeliveryScheduleBadRequestException {

        deliveryScheduleService.addDeliverySchedule(
                post.getOfficeFrom(),
                post.getOfficeTo(),
                post.getDays());
        return ResponseEntity.status(201).build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeliverySchedule(@PathVariable Long id) throws DeliveryScheduleNotExistsException {
        deliveryScheduleService.deleteDeliverySchedule(id);
        return ResponseEntity.noContent().build();
    }


    private ResponseEntity<List<DeliveryScheduleGetDto>> getAllDeliverySchedules() {
        var deliverySchedules = deliveryScheduleService.getAllDeliverySchedules();
        if (deliverySchedules.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(deliverySchedules);
    }

    private ResponseEntity<List<DeliveryScheduleGetDto>> getDeliverySchedulesByOfficeTo(@RequestParam Long officeTo) throws DeliveryScheduleBadRequestException {
        var deliverySchedules = deliveryScheduleService.getAllDeliverySchedules(officeTo);
        if (deliverySchedules.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(deliverySchedules);
    }
}
