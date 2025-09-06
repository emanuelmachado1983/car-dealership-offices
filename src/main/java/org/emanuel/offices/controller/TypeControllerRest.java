package org.emanuel.offices.controller;

import org.emanuel.offices.business.ITypeOfficeService;
import org.emanuel.offices.business.dto.TypeOfficeDto;
import org.emanuel.offices.exceptions.TypeOfficeNotExistsException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/types")
public class TypeControllerRest {
    private final ITypeOfficeService typeOfficeService;

    public TypeControllerRest(ITypeOfficeService typeOfficeService) {
        this.typeOfficeService = typeOfficeService;
    }

    @GetMapping
    public ResponseEntity<List<TypeOfficeDto>> getAllTypeOffices() {
        var typeOffices = typeOfficeService.getTypeOffices();
        if (typeOffices.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(typeOffices);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TypeOfficeDto> getTypeOfficeById(@PathVariable Long id) throws TypeOfficeNotExistsException {
        return ResponseEntity.ok(typeOfficeService.getTypeOfficeById(id));
    }
}
