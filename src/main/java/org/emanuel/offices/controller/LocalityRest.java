package org.emanuel.offices.controller;

import org.emanuel.offices.business.ILocalityService;
import org.emanuel.offices.business.dto.LocalityGetDto;
import org.emanuel.offices.business.dto.LocalityModifyDto;
import org.emanuel.offices.exceptions.CountryNotExistException;
import org.emanuel.offices.exceptions.LocalityBadRequestException;
import org.emanuel.offices.exceptions.LocalityNotExistException;
import org.emanuel.offices.exceptions.ProvinceNotExistException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/v1/countries/{idCountry}/provinces/{idProvince}/localities")
public class LocalityRest {
    private final ILocalityService localityService;

    public LocalityRest(ILocalityService localityService) {
        this.localityService = localityService;
    }

    @GetMapping("/{idLocality}")
    public ResponseEntity<LocalityGetDto> getLocalityById(@PathVariable Long idCountry, @PathVariable Long idProvince, @PathVariable Long idLocality) throws LocalityNotExistException, CountryNotExistException {
        return ResponseEntity.ok(localityService.getLocalityById(idCountry, idProvince, idLocality));
    }

    @GetMapping("")
    public ResponseEntity<List<LocalityGetDto>> getAllLocalities(@PathVariable Long idCountry, @PathVariable Long idProvince) throws CountryNotExistException {
        var localities = localityService.getAllLocalities(idCountry, idProvince);
        if (localities.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(localities);
    }

    @PostMapping("")
    public ResponseEntity<LocalityGetDto> addLocality(@PathVariable Long idCountry, @PathVariable Long idProvince, @RequestBody LocalityModifyDto locality) throws ProvinceNotExistException, CountryNotExistException {
        var localityDto = localityService.addLocality(
                idCountry,
                idProvince,
                locality.getName()
        );

        return ResponseEntity.created(URI.create("/countries/" + idCountry + "/provinces/" + idProvince + "/localities/" + localityDto.getId()))
                .body(localityDto);
    }

    @PutMapping("/{idLocality}")
    public ResponseEntity<LocalityGetDto> updateLocality(@PathVariable Long idCountry, @PathVariable Long idProvince, @PathVariable Long idLocality, @RequestBody LocalityModifyDto localityModifyDto) throws LocalityNotExistException, ProvinceNotExistException, CountryNotExistException {
        return ResponseEntity.ok(
                localityService.updateLocality(idCountry, idProvince, idLocality, localityModifyDto.getName()));
    }

    @DeleteMapping("/{idLocality}")
    public void deleteLocality(@PathVariable Long idCountry, @PathVariable Long idProvince, @PathVariable Long idLocality) throws LocalityNotExistException, ProvinceNotExistException, CountryNotExistException, LocalityBadRequestException {
        localityService.deleteLocality(idCountry, idProvince, idLocality);
    }


}
