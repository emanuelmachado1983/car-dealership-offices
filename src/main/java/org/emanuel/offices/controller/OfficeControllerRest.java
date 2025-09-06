package org.emanuel.offices.controller;

import org.emanuel.offices.business.IOfficeService;
import org.emanuel.offices.business.dto.OfficeGetDto;
import org.emanuel.offices.business.dto.OfficeModifyDto;
import org.emanuel.offices.exceptions.OfficeBadRequestException;
import org.emanuel.offices.exceptions.OfficeLocalityNotExistsException;
import org.emanuel.offices.exceptions.OfficeNotExistsException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/offices")
public class OfficeControllerRest {
    private final IOfficeService officeService;

    public OfficeControllerRest(IOfficeService officeService) {
        this.officeService = officeService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OfficeGetDto> getOfficeById(@PathVariable Long id) throws OfficeNotExistsException {
        return ResponseEntity.ok(officeService.getOfficeById(id));
    }

    @GetMapping
    public ResponseEntity<List<OfficeGetDto>> getAllOffices() {
        var offices = officeService.getAllOffices();
        if (offices.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(offices);
    }

    @PostMapping
    public ResponseEntity<OfficeGetDto> addOffice(@RequestBody OfficeModifyDto officeModifyDto) throws OfficeLocalityNotExistsException, OfficeBadRequestException {
        var newOffice = officeService.addOffice(officeModifyDto);
        return ResponseEntity.status(201).body(newOffice);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OfficeGetDto> updateOffice(@PathVariable Long id, @RequestBody OfficeModifyDto officeModifyDto) throws OfficeLocalityNotExistsException, OfficeNotExistsException, OfficeBadRequestException {
        var updatedOffice = officeService.updateOffice(id, officeModifyDto);
        return ResponseEntity.ok(updatedOffice);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOffice(@PathVariable Long id) throws OfficeNotExistsException {
        officeService.deleteOffice(id);
        return ResponseEntity.noContent().build();
    }

}
