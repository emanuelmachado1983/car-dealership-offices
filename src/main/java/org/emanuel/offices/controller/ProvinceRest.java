package org.emanuel.offices.controller;

import org.emanuel.offices.business.IProvinceService;
import org.emanuel.offices.business.dto.ProvinceGetDto;
import org.emanuel.offices.business.dto.ProvinceModifyDto;
import org.emanuel.offices.exceptions.CountryNotExistException;
import org.emanuel.offices.exceptions.ProvinceNotExistException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/v1/countries/{idCountry}/provinces")
public class ProvinceRest {

    private final IProvinceService provinceService;

    public ProvinceRest(IProvinceService provinceService) {
        this.provinceService = provinceService;
    }

    @GetMapping("/{idProvince}")
    public ResponseEntity<ProvinceGetDto> getProvinceById(@PathVariable Long idCountry, @PathVariable Long idProvince) throws ProvinceNotExistException {
        return ResponseEntity.ok(provinceService.getProvinceById(idCountry, idProvince));
    }

    @GetMapping("")
    public ResponseEntity<List<ProvinceGetDto>> getAllProvinces(@PathVariable Long idCountry) {
        var provinces = provinceService.getAllProvinces(idCountry);
        if (provinces.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(provinces);
    }

    @PostMapping("")
    public ResponseEntity<ProvinceGetDto> addProvince(@PathVariable Long idCountry, @RequestBody ProvinceModifyDto province) throws CountryNotExistException {

        var provinceDto = provinceService.addProvince(
                idCountry,
                province.getName()
        );

        return ResponseEntity.created(URI.create("/countries/" + idCountry + "/provinces/" + provinceDto.getId()))
                .body(provinceDto);
    }

    @PutMapping("/{idProvince}")
    public ResponseEntity<ProvinceGetDto> updateProvince(@PathVariable Long idCountry, @PathVariable Long idProvince, @RequestBody ProvinceModifyDto provinceModifyDto) throws ProvinceNotExistException {
        return ResponseEntity.ok(
                provinceService.updateProvince(idCountry, idProvince, provinceModifyDto.getName()));
    }

    @DeleteMapping("/{idProvince}")
    public void deleteProvince(@PathVariable Long idCountry, @PathVariable Long idProvince) throws ProvinceNotExistException, CountryNotExistException {
        provinceService.deleteProvince(idCountry, idProvince);
    }

}
