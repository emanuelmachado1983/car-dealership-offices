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
@RequestMapping("/api/v1/countries")
public class CountryRest {
    private final ICountryService countryService;

    public CountryRest(ICountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CountryGetDto> getCountryById(@PathVariable Long id) throws CountryNotExistException {
        return ResponseEntity.ok(countryService.getCountryById(id));
    }

    @GetMapping("")
    public ResponseEntity<List<CountryGetDto>> getAllCountries() {
        var countries = countryService.getAllCountries();
        if (countries.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(countries);
    }

    @PostMapping("")
    public ResponseEntity<CountryGetDto> addCountry(@RequestBody CountryModifyDto country) {

        var countryDto = countryService.addCountry(country.getName());

        return ResponseEntity.created(URI.create("/countries/" + countryDto.getId()))
                .body(countryDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CountryGetDto> updateCountry(@PathVariable Long id, @RequestBody CountryModifyDto countryModifyDto) throws CountryNotExistException {
        return ResponseEntity.ok(
                countryService.updateCountry(id, countryModifyDto.getName()));
    }

    @DeleteMapping("/{id}")
    public void deleteCountry(@PathVariable Long id) throws CountryNotExistException, CountryBadRequestException {
        countryService.deleteCountry(id);
    }

}
