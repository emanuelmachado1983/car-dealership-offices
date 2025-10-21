package org.emanuel.offices.controller;

import org.emanuel.offices.business.ICountryService;
import org.emanuel.offices.business.dto.CountryGetDto;
import org.emanuel.offices.business.dto.CountryModifyDto;
import org.emanuel.offices.exceptions.CountryBadRequestException;
import org.emanuel.offices.exceptions.CountryNotExistException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/")
public class AliveRest {

    @GetMapping("/")
    public ResponseEntity<String> getCountryById() throws CountryNotExistException {
        return ResponseEntity.ok("I'm alive. Couldn't make the actuator endpoint work.");
    }


}
